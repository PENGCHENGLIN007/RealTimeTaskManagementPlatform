package com.zk.graduation.metadata.common;

import java.util.List;

/**
 * kafka(sink)信息
 *
 * @author pengchenglin
 * @create 2020-05-19 17:33
 */
public class KafkaSinkInfo extends SinkInfo {

    private String groupId;
    private String bootstrapServers;
    private String topic;
    private List<Column> sinkColumnList;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<Column> getSinkColumnList() {
        return sinkColumnList;
    }

    public void setSinkColumnList(List<Column> sinkColumnList) {
        this.sinkColumnList = sinkColumnList;
    }


}
