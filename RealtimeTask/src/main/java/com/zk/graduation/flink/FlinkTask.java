package com.zk.graduation.flink;

import com.zk.graduation.flink.util.CSVDataFormat;
import com.zk.graduation.flink.util.DataTypeUtil;
import com.zk.graduation.metadata.common.Column;
import com.zk.graduation.metadata.common.KafkaSinkInfo;
import com.zk.graduation.metadata.common.KafkaSourceInfo;
import com.zk.graduation.metadata.common.TaskInfo;
import com.zk.graduation.metadata.dao.SinkDao;
import com.zk.graduation.metadata.dao.SourceDao;
import com.zk.graduation.metadata.dao.TaskDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * @author 你的名字
 * @date 2020/5/13 22:12
 */
@Slf4j
public class FlinkTask {
    public static void main(String[] args) throws Exception {
        //获取运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
        //创建一个tableEnvironment
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env, settings);

        int taskId;
        final ParameterTool params = ParameterTool.fromArgs(args);
        taskId =Integer.valueOf(params.get("taskid"));
        if("".equals(taskId)){
            log.error("No taskid  specified. Please run 'job " +
                    "--taskid <taskId> ");
            return;
        }
        //获取元数据信息
        TaskInfo taskInfo = TaskDao.getTaskInfo(taskId);
        KafkaSourceInfo kafkaSourceInfo = (KafkaSourceInfo)SourceDao.getSourceInfo(taskId);
        KafkaSinkInfo kafkaSinkInfo = (KafkaSinkInfo)SinkDao.getSinkInfo(taskId);


        String sql = taskInfo.getSql();

        String taskName = taskInfo.getTaskName();





        String sourceBootstrapServers = kafkaSourceInfo.getBootstrapServers();
        String sourceTopic = kafkaSourceInfo.getTopic();
        String groupId = kafkaSourceInfo.getGroupId();
        List<Column> sourceColumnList = kafkaSourceInfo.getSourceColumnList();
        String sourceName = kafkaSourceInfo.getName();
        String sourceFields = kafkaSourceInfo.getSourceFields();




        String sinkBootstrapServers = kafkaSinkInfo.getBootstrapServers();
        String sinkTopic = kafkaSinkInfo.getTopic();
        List<Column> sinkColumnList = kafkaSinkInfo.getSinkColumnList();
        String sinkName = kafkaSinkInfo.getName();


        //addSource
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", sourceBootstrapServers);
        properties.setProperty("group.id", groupId);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>(sourceTopic,
                new SimpleStringSchema(Charset.forName("utf8")),properties);

        DataStream<String> stream = env.addSource(consumer);


        TypeInformation[] types = DataTypeUtil.getDataTypes(sourceColumnList);
        System.out.println("type:-------------------"+Arrays.toString(types));

        DataStream<Row> streammap = stream.map(new MapFunction<String, Row>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Row map(String value) {
                // TODO Auto-generated method stub
                String[] str = value.split(",");
                Row row = new Row(types.length);
                for(int i=0 ;i<str.length;i++){

                    switch(types[i].toString().toUpperCase()){
                        case "STRING":{
                            row.setField(i, str[i]);
                            break;
                        }
                        case "INTEGER":{
                            row.setField(i,Integer.valueOf(str[i]));
                            break;
                        }
                        case "LONG":{
                            row.setField(i,Long.valueOf(str[i]));
                            break;
                        }
                        case "DOUBLE":{
                            row.setField(i,Double.valueOf(str[i]));
                            break;
                        }

                        case "TIMESTAMP":{
                            Timestamp tms = Timestamp.valueOf(str[i]);
                            row.setField(i,tms);
                            break;
                        }


                        default:break;
                    }
                }
                return row;
            }
        }).returns(new RowTypeInfo(types) );


        streammap.print();
        tableEnv.registerDataStream(sourceName, streammap,sourceFields+",proctime.proctime");


        //todo test
        //sql = "select sum(column1) from source group by TUMBLE(proctime, INTERVAL '1' MINUTE)";

        Table table = tableEnv.sqlQuery(sql);

        DataStream<Row> rowDataStream = tableEnv.toAppendStream(table, Row.class);


        Properties sinkProperties = new Properties();

        sinkProperties.setProperty("bootstrap.servers", sinkBootstrapServers);
        //在Kafka的Partition发生leader切换时,不重启flink，做3次尝试连接
        sinkProperties.setProperty("retries", "3");
        //关闭指标监控防止taskmanager崩溃
        sinkProperties.setProperty(FlinkKafkaProducer.KEY_DISABLE_METRICS, "true");

        //new FlinkKafkaProducer(String, KafkaSerializationSchema, sinkProperties, FlinkKafkaProducer.Semantic.EXACTLY_ONCE);

        RichSinkFunction  producer = new FlinkKafkaProducer(sinkTopic, new SimpleStringSchema(Charset.forName("UTF-8")), sinkProperties);



        DataStream lineDataStream = CSVDataFormat.outputFormat(rowDataStream, kafkaSinkInfo);
        DataStreamSink dataStreamSink = lineDataStream.addSink(producer);
        dataStreamSink.name(sinkName);

        env.execute(taskName);





    }

}
