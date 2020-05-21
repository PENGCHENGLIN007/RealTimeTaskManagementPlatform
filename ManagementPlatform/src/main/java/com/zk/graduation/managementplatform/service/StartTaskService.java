package com.zk.graduation.managementplatform.service;

import com.zk.graduation.managementplatform.util.InputStreamRunnable;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 启动任务
 *
 * @author pengchenglin
 * @create 2020-05-20 20:04
 */

@Slf4j
public class StartTaskService {

    public void execute(int taskId){

        new Thread(() -> {
            // TODO Auto-generated method stub
//					String[] strings = {"--jobname",userName+"_"+startStreamStatement.getObjectName(),"--username",userName};
            // Linux 环境启动JOB
            Process exec = null;
            try {
                String rootDir = System.getProperty("user.dir");
                String[] fileCommand = {"/bin/sh", "-c", "cd " + rootDir
                        + "/;./cirroStreamFlink/flink-1.7.2/bin/flink run -d cirrostream.executeplan.jar --jobname "
                        + taskId };

                exec = Runtime.getRuntime().exec(fileCommand);
                StringBuffer result = new StringBuffer();

                /* 为"错误输出流"单独开一个线程读取之,否则会造成标准输出流的阻塞 */
                Thread t = new Thread(new InputStreamRunnable(exec.getErrorStream(), "ErrorStream"));
                t.start();

                /* "标准输出流"就在当前方法中读取 */
                BufferedInputStream in = new BufferedInputStream(exec.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String lineStr = "";
                while ((lineStr = br.readLine()) != null) {
                    result.append(lineStr);
                }
                log.info(result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                exec.destroy();
            }
        }).start();

    }

}
