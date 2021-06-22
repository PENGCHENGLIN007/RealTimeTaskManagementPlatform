package com.zk.graduation.managementplatform.service;

import com.zk.graduation.metadata.common.TaskInfo;
import com.zk.graduation.metadata.dao.TaskDao;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 你的名字
 * @date 2020/5/22 3:33
 */
public class GetAllTaskService {
    public List<TaskInfo> execute() throws SQLException {
        List<TaskInfo> taskInfos = TaskDao.getAll();
        return taskInfos;
    }
}
