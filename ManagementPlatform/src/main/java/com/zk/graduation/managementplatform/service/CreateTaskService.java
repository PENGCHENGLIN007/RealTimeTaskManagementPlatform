package com.zk.graduation.managementplatform.service;

import com.zk.graduation.metadata.common.ColumnInfo;
import com.zk.graduation.metadata.common.TaskInfo;
import com.zk.graduation.metadata.dao.ColumnDao;
import com.zk.graduation.metadata.dao.SinkDao;
import com.zk.graduation.metadata.dao.SourceDao;
import com.zk.graduation.metadata.dao.TaskDao;

import java.util.List;

/**
 * 创建任务服务
 *
 * @author pengchenglin
 * @create 2020-05-21 19:30
 */
public class CreateTaskService {

    public void execute(TaskInfo taskInfo) throws Exception{
        boolean exist = TaskDao.checkTaskNameExist(taskInfo.getTaskName());
        if(exist){
            throw new Exception("task name already exists!");
        }

        TaskDao.createTask(taskInfo);
        int taskId = TaskDao.getIdByName(taskInfo.getTaskName());

        taskInfo.getSourceInfo().setTaskId(taskId);
        taskInfo.getSinkInfo().setTaskId(taskId);

        SourceDao.createSource(taskInfo.getSourceInfo());
        SinkDao.createSink(taskInfo.getSinkInfo());


        int sourceId = SourceDao.getIdByName(taskInfo.getSourceInfo().getName());
        int sinkId = SinkDao.getIdByName(taskInfo.getSinkInfo().getName());

        taskInfo.getSourceInfo().setId(sourceId);
        taskInfo.getSinkInfo().setId(sinkId);

        List<ColumnInfo> sourceColumnList = taskInfo.getSourceInfo().getColumnList();
        List<ColumnInfo> sinkColumnList = taskInfo.getSinkInfo().getColumnList();

        sourceColumnList.forEach(ColumnDao::insertColumn);
        sinkColumnList.forEach(ColumnDao::insertColumn);

    }
}
