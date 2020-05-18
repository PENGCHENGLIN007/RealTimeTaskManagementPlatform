package com.zk.graduation.flink;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.descriptors.*;

/**
 * flink任务入口
 *
 * @author pengchenglin
 * @create 2020-05-11 14:42
 */
public class FlinkTaskMain {
    public static void main(String[] args) throws Exception {
        //获取运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        //创建一个tableEnvironment
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env, settings);

        Schema schema = new Schema()
                //.field("id", "VARCHAR").from("id")
                .field("id", "VARCHAR")
                //.field("name", "VARCHAR")
                .field("amount", "DOUBLE")
                //.field("proctime", "TIMESTAMP").proctime()
                .field("rowtime", Types.SQL_TIMESTAMP)
                .rowtime(
                        new Rowtime()
                                .timestampsFromField(
                                        "eventtime")
                                .watermarksPeriodicBounded(2000))
                ;

//   "0.8", "0.9", "0.10", "0.11", and "universal"
        tableEnv.connect(new Kafka().version("universal")
                                    .topic("source0511")
                                    .property("zookeeper.connect", "172.16.44.28:7758")
                                    .property("bootstrap.servers", "172.16.44.28:9096")
                                    .property("group.id", "source0511-group")
                                    .startFromEarliest()
                        )
                .withFormat(new Csv())
                .withSchema(schema)
                .inAppendMode()
                .createTemporaryTable("sourceTable");

        tableEnv.connect(
                new Kafka()
                        .version("universal")
                        // "0.8", "0.9", "0.10", "0.11", and "universal"
                        .topic("sink0511")
                        .property("acks", "all")
                        .property("retries", "0")
                        .property("batch.size", "16384")
                        .property("linger.ms", "10")
                        .property("zookeeper.connect", "172.16.44.28:7758")
                        .property("bootstrap.servers", "172.16.44.28:9096")
                        .sinkPartitionerFixed())
                .inAppendMode()
                .withFormat(new Json())
                .withSchema(
                        new Schema().field("totalamount", "DOUBLE")
                                //.field("total", "INT")
                                .field("time", Types.SQL_TIMESTAMP)
                                )
                .createTemporaryTable("sinkTable");

        tableEnv.sqlUpdate("insert into sinkTable"
                + " select sum(amount),TUMBLE_END(rowtime, INTERVAL '5' SECOND) "
                + "from sourceTable group by TUMBLE(rowtime, INTERVAL '5' SECOND)");
        //SELECT TUMBLE_START(user_action_time, INTERVAL '10' MINUTE), COUNT(DISTINCT user_name)
       // FROM user_actions
       // GROUP BY TUMBLE(user_action_time, INTERVAL '10' MINUTE);
        env.execute("test");

    }
}
