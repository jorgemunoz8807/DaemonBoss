/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msp.controller;

import com.msp.database.DaemonQuery;
import com.msp.database.GetConnection;
import com.msp.entity.Task;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jmunoz
 */
public class DaemonController {

    public static LinkedList<Task> getTaskList() throws Exception {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection rds = GetConnection.AWS(1);
        pstmt = rds.prepareStatement(DaemonQuery.GET_TASK_LIST());
        rs = (ResultSet) pstmt.executeQuery();
        LinkedList<Task> listTask = new LinkedList<Task>();
        while (rs.next()) {
            Task task = new Task();
            task.setId(rs.getInt("id"));
            task.setIdConfig(rs.getInt("id_config"));
            task.setConfigName(rs.getString("config_name"));
            task.setScriptName(rs.getString("script_name"));
            task.setUserOwner(rs.getInt("userowner"));
            task.setUserExecute(rs.getInt("userexecute"));
            task.setComment(rs.getString("comment"));
            task.setScriptPath(rs.getString("script_path"));
            task.setStatus(rs.getString("status"));
            task.setScriptLang(rs.getString("script_language"));
            listTask.add(task);
        }
//        System.out.println(listTask.size());
        return listTask;
    }

    public static void SaveLogs(String fileName, String output) {

        FileWriter fw = null;
        try {
            fw = new FileWriter(new File(fileName), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(output);
            bw.newLine();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(DaemonController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(DaemonController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static String GetLogContent(File file) {
        String fileAsString = "";
        try {
            InputStream is = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line).append("\n");
                line = br.readLine();
            }
            fileAsString = sb.toString();
//            System.out.println("Contents : " + fileAsString);
        } catch (IOException ex) {
            Logger.getLogger(DaemonController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileAsString;
    }

    public static long GetJVMPID() {
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        String jvmName = runtimeBean.getName();
        long pid = Long.valueOf(jvmName.split("@")[0]);
//        System.out.println("JVM PID  = " + pid);
        return pid;
    }
}
