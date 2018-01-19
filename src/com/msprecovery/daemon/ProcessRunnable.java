/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msprecovery.daemon;

import com.msp.cfg.DaemonCFG;
import com.msp.controller.DaemonController;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jmunoz
 */
public class ProcessRunnable implements Runnable {

    private String cmd;
    private int taskId;

    public ProcessRunnable(String cmd, int taskId) {
        this.cmd = cmd;
        this.taskId = taskId;

    }

    @Override
    public void run() {
        try {
            File output = new File(DaemonCFG.logsPath + "output/output_task_" + taskId);
            File error = new File(DaemonCFG.logsPath + "error/error_task_" + taskId);

            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectInput(output);
            pb.redirectError(error);
            Process proc = pb.start();

            if (output.exists()) {
                output.delete();
            }
            if (error.exists()) {
                error.delete();
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                DaemonController.SaveLogs(DaemonCFG.logsPath + "output/output_task_" + taskId, line);
            }

            BufferedReader err = new BufferedReader(
                    new InputStreamReader(proc.getErrorStream()));
            while ((line = err.readLine()) != null) {
                DaemonController.SaveLogs(DaemonCFG.logsPath + "error/error_task_" + taskId, line);
            }

        } catch (Exception ex) {
            Logger.getLogger(Daemon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
