package com.zk.graduation.managementplatform.service;

import com.zk.graduation.metadata.common.TaskInfo;

/**
 * 创建任务服务
 *
 * @author pengchenglin
 * @create 2020-05-21 19:30
 */
public class CreateTaskService {

    public void execute(TaskInfo taskInfo) throws Exception{
        throw new Exception("任务名已存在");

    }
}
