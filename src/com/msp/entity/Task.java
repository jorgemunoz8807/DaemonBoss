/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msp.entity;

/**
 *
 * @author jmunoz
 */
public class Task {

    private int id;
    private int idConfig;
    private String configName;
    private String scriptName;
    private int userOwner;
    private int userExecute;
    private String comment;
    private String scriptLang;
    private String scriptPath;
    private String status;

    public Task() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdConfig() {
        return idConfig;
    }

    public void setIdConfig(int idConfig) {
        this.idConfig = idConfig;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public int getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(int userOwner) {
        this.userOwner = userOwner;
    }

    public int getUserExecute() {
        return userExecute;
    }

    public void setUserExecute(int userExecute) {
        this.userExecute = userExecute;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getScriptLang() {
        return scriptLang;
    }

    public void setScriptLang(String scriptLang) {
        this.scriptLang = scriptLang;
    }

    public String getScriptPath() {
        return scriptPath;
    }

    public void setScriptPath(String scriptPath) {
        this.scriptPath = scriptPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
