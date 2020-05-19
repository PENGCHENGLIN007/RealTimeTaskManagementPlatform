package com.zk.graduation.metadata.common;

/**
 * 任务信息
 *
 * @author pengchenglin
 * @create 2020-05-19 17:38
 */
public class TaskInfo {

    private SourceInfo sourceInfo;
    private SinkInfo sinkInfo;
    private String sql;
    private String taskId;

    public TaskInfo(){}

    public TaskInfo(String taskId){
        this.taskId = taskId;
    }

    public SourceInfo getSourceInfo() {
        return sourceInfo;
    }

    public void setSourceInfo(SourceInfo sourceInfo) {
        this.sourceInfo = sourceInfo;
    }

    public SinkInfo getSinkInfo() {
        return sinkInfo;
    }

    public void setSinkInfo(SinkInfo sinkInfo) {
        this.sinkInfo = sinkInfo;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
