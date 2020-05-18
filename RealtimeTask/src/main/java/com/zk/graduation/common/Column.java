package com.zk.graduation.common;

/**
 * 列信息
 *
 * @author pengchenglin
 * @create 2020-05-15 17:38
 */
public class Column {

    private int index;
    private String name;
    private DataType dataType;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }
}
