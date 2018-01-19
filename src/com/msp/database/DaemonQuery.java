/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msp.database;

import com.msp.cfg.DaemonCFG;

/**
 *
 * @author jmunoz
 */
public class DaemonQuery {

    public final static String GET_TASK_LIST() {
        String sql = "SELECT task.*"
                + " FROM `MSP_BOSS_DEV`.`a_task` task"
                + " WHERE"
                + " task.status = " + DaemonCFG.runTask + ""
                + " OR task.status = " + DaemonCFG.stopTask + ";";
        return sql;
    }

    public final static String ADD_TASK_LOG(int idTask, long pid, int userExecute, int status) {
        String sql = "INSERT INTO `MSP_BOSS_DEV`.`a_task_log`"
                + " (`id_task`, `pid`, `error`, `userexecute`, `status`)"
                + " VALUES(" + idTask + ", " + pid + ", 'Running', " + userExecute + ", " + status + ")";
        return sql;
    }

    public final static String UPDATE_TASK_LIST(int id) {
        String sql = "UPDATE `MSP_BOSS_DEV`.`a_task`"
                + "SET "
                + "`status` = " + DaemonCFG.sleepingTask + ""
                + " WHERE "
                + "id = '" + id + "';";
        return sql;
    }

    public final static String UPDATE_TASK_LOG(long pid, String errorMsg, int status) {
        String sql = "UPDATE `MSP_BOSS_DEV`.`a_task_log` "
                + "SET `error` = '" + errorMsg + "', "
                + "`status` = " + status + " "
                + "WHERE `pid` = " + pid + " "
                + "AND `status` = " + DaemonCFG.runningTaskLog + ";";
        return sql;
    }

}
