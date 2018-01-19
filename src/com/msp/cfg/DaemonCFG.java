/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msp.cfg;

/**
 *
 * @author jmunoz
 */
public class DaemonCFG {

    public static final String decryptPath = "http://172.16.1.169/php_decrypt_ws/index.php/";
//    public static final String scriptsPath = "C:\\Project\\DaemonBoss\\scripts\\";
//    public static final String logsPath = "C:\\Project\\DaemonBoss\\logs\\";

    public static final String scriptsPath = "/opt/boss/php_daemon/scripts/";
    public static final String logsPath = "/opt/boss/php_daemon/logs/";
//    a_task
    public static final int sleepingTask = 0;
    public static final int runTask = 1;
    public static final int hiddenTask = 2;
    public static final int stopTask = 3;

//    a_task_log
    public static final int unrunTaskLog = 0;
    public static final int succesTaskLog = 1;
    public static final int runningTaskLog = 2;
    public static final int stoppedTaskLog = 3;
    public static final int errorTaskLog = 4;
    public static final int serverErrorTaskLog = 5;
    public static final int warningTaskLog = 6;
    public static final int warningSuccessTaskLog = 7;
    
}
