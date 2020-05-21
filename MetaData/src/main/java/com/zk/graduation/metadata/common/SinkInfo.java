package com.zk.graduation.metadata.common;

import java.util.LinkedList;
import java.util.List;

/**
 * 输出源信息
 *
 * @author pengchenglin
 * @create 2020-05-19 17:40
 */
public class SinkInfo {

    private int id;

    private String name;

    private SourceType sourceType;

    private int userId;

    private int taskId;

    private String param;

    private String schema;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public List<ColumnInfo> getColumnList(){
        List<ColumnInfo> columnInfoList = new LinkedList<>();
        String[] columnArray = schema.split(",");
        for(int i = 0;i<columnArray.length;i++){
            columnArray[i] = columnArray[i].trim();
            String columnName = columnArray[i].split("\\s+")[0];
            String dataType = columnArray[i].split("\\s+")[1];
            ColumnInfo columnInfo = new ColumnInfo();
            columnInfo.setName(columnName);
            columnInfo.setDataType(dataType);
            columnInfo.setIndex(i);
            columnInfo.setTableId(id);
            columnInfo.setTableType("SINK");
            columnInfoList.add(columnInfo);
        }
        return columnInfoList;
    }

}
