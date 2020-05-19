package com.zk.graduation.metadata.common;

import java.util.List;

/**
 * kafka(source)信息
 *
 * @author pengchenglin
 * @create 2020-05-19 17:18
 */
public class KafkaSourceInfo extends SourceInfo {

    private String groupId;
    private String bootstrapServers;
    private String topic;
    private List<Column> sourceColumnList;

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

    public List<Column> getSourceColumnList() {
        return sourceColumnList;
    }

    public void setSourceColumnList(List<Column> sourceColumnList) {
        this.sourceColumnList = sourceColumnList;
    }
}
