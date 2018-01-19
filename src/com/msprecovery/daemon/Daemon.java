/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msprecovery.daemon;

import com.msp.cfg.DaemonCFG;
import com.msp.controller.DaemonController;
import com.msp.database.DaemonQuery;
import com.msp.database.GetConnection;
import com.msp.entity.Task;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jmunoz
 */
public class Daemon {

    public static void main(String[] args) {

        try {
            PreparedStatement pstmt = null;
            Connection rds = GetConnection.AWS(1);
            LinkedList<Task> listTask = new LinkedList<>();
            Map<Integer, Thread> runningTask = new HashMap<Integer, Thread>();
            String cmd = "";

            System.out.println("Started.......!!!");

            while (true) {
                listTask = DaemonController.getTaskList();
//            Starting new threads
                for (int i = 0; i < listTask.size(); i++) {
                    Task task = listTask.get(i);
                    if (task.getStatus().equals(String.valueOf(DaemonCFG.runTask))) {
//                    cmd = "sudo " + task.getScriptLang().toLowerCase() + " " + task.getScriptPath() + " " + String.valueOf(task.getId()) + " " + String.valueOf(task.getUserOwner()) + " " + String.valueOf(task.getIdConfig()) + " " + DaemonCFG.decryptPath;
                        cmd = "sudo php /opt/boss/php_daemon/test.php";

                        Thread thr = new Thread(new ProcessRunnable(cmd, task.getId()), cmd);

                        thr.setDaemon(true);
                        thr.start();

                        runningTask.put(task.getId(), thr);

                        pstmt = rds.prepareStatement(DaemonQuery.UPDATE_TASK_LIST(task.getId()));
                        pstmt.execute();
                        pstmt = rds.prepareStatement(DaemonQuery.ADD_TASK_LOG(task.getId(), thr.getId(), task.getUserExecute(), DaemonCFG.runningTaskLog));
                        pstmt.execute();

                        System.out.println("");
                        System.out.println("-----------------------------------------------------------");
                        System.out.println("Started");
                        System.out.println("Script: " + task.getScriptName());
                        System.out.println("Thread No: " + thr.getId());
                        System.out.println("JVM PID  = " + DaemonController.GetJVMPID());
                        System.out.println("CMD: " + cmd);
                        System.out.println("-----------------------------------------------------------");
                        System.out.println("");
                    }
                    if (task.getStatus().equals(String.valueOf(DaemonCFG.stopTask))) {
                        for (Map.Entry<Integer, Thread> entry : runningTask.entrySet()) {
                            Thread thr = entry.getValue();
                            int taskId = entry.getKey();
                            thr.stop();
                            runningTask.remove(taskId);
                            System.out.println("Stopped: " + taskId);
                            System.out.println("alive" + thr.isAlive());
                            System.out.println("interrupted" + thr.isInterrupted());
                        }
                    }
                }
                listTask.clear();

//            Checking thread status
                if (!runningTask.isEmpty()) {
                    for (Map.Entry<Integer, Thread> entry : runningTask.entrySet()) {
                        Thread thr = entry.getValue();
                        int taskId = entry.getKey();
                        File output = new File(DaemonCFG.logsPath + "output/output_task_" + taskId);
                        File error = new File(DaemonCFG.logsPath + "error/error_task_" + taskId);

                        System.out.println(thr.getState());

                        /* if (thr.isInterrupted()) {
        System.out.println("Interrupted...!!!!");
        pstmt = rds.prepareStatement(DaemonQuery.UPDATE_TASK_LOG(thr.getId(), "Stopped", DaemonCFG.stoppedTaskLog));
        pstmt.execute();
        runningTask.remove(taskId);
        
        System.out.println("");
        System.out.println("-----------------------------------------------------------");
        System.out.println("Interrupted");
        System.out.println("PID: " + thr.getId());
        //                    System.out.println("Script: " + task.getScriptName());
        System.out.println("CMD: " + cmd);
        System.out.println("-----------------------------------------------------------");
        System.out.println("");
        } else*/
                        if (!thr.isAlive()) {
                            if (error.exists()) {
                                pstmt = rds.prepareStatement(DaemonQuery.UPDATE_TASK_LOG(thr.getId(), DaemonController.GetLogContent(error), DaemonCFG.errorTaskLog));
                            } else {
                                pstmt = rds.prepareStatement(DaemonQuery.UPDATE_TASK_LOG(thr.getId(), DaemonController.GetLogContent(output), DaemonCFG.succesTaskLog));
                            }
                            pstmt.execute();
//                    listThreads.remove(j);
                            runningTask.remove(taskId);

                            System.out.println("");
                            System.out.println("-----------------------------------------------------------");
                            System.out.println("Done");
                            System.out.println("PID: " + thr.getId());
//                    System.out.println("Script: " + task.getScriptName());
                            System.out.println("CMD: " + cmd);
                            System.out.println("-----------------------------------------------------------");
                            System.out.println("");
                        }

//                j++;
                    }
                }
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Daemon.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("*******************************");
                    System.err.println(ex.getLocalizedMessage());
                    System.out.println("*******************************");
                    System.err.println(ex.getMessage());
                    System.out.println("*******************************");
                }
            }
        } catch (Exception ex) {
            System.out.println("------------------------------");
            System.err.println(ex.getLocalizedMessage());
            System.out.println("------------------------------");
            System.err.println(ex.getMessage());
            System.out.println("------------------------------");
            Logger.getLogger(Daemon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
