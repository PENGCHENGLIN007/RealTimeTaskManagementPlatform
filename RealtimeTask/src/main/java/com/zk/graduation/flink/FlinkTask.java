package com.zk.graduation.flink;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * @author 你的名字
 * @date 2020/5/13 22:12
 */
@Slf4j
public class FlinkTask {
    public static void main(String[] args) {
        //获取运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        //创建一个tableEnvironment
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env, settings);

        String taskId;
        final ParameterTool params = ParameterTool.fromArgs(args);
        taskId =params.get("taskid");
        if("".equals(taskId)){
            log.error("No taskid  specified. Please run 'job " +
                    "--taskid <taskId> ");
            return;
        }

        String topic = "";
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("zookeeper.connect", "localhost:2181");
        properties.setProperty("group.id", "pcl01");

        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>(topic,
                new SimpleStringSchema(Charset.forName("utf8")),properties);

        DataStream<String> stream = env.addSource(consumer);
        stream.print();

        TypeInformation[] types ={Types.SQL_TIMESTAMP,Types.STRING,Types.LONG,Types.INT,Types.INT};

        DataStream<Row> streammap = stream.map(new MapFunction<String, Row>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public Row map(String value) throws Exception {
                // TODO Auto-generated method stub
                String[] str = value.split(",");
                Row row = new Row(types.length);
                for(int i=0 ;i<str.length;i++){
                    switch(types[i].toString().toUpperCase()){
                        case "STRING":{
                            row.setField(i,(String)str[i]);
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
    }

}
