package com.zk.graduation.metadata.common;

/**
 * 任务信息
 *
 * @author pengchenglin
 * @create 2020-05-19 17:38
 */
public class TaskInfo {

    private int userId;
    private SourceInfo sourceInfo;
    private SinkInfo sinkInfo;
    private String sql;
    private int taskId;
    private String taskName;

    public TaskInfo(){}

    public TaskInfo(int taskId){
        this.taskId = taskId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
