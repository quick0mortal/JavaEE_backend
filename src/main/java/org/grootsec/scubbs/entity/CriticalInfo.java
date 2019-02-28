package org.grootsec.scubbs.entity;


public class CriticalInfo {

    private int criticalid;
    private int messageid;
    private String sendid;
    private String content;
    private String time;

    public int getCriticalid() {
        return criticalid;
    }

    public void setCriticalid(int criticalid) {
        this.criticalid = criticalid;
    }

    public int getMessageid() {
        return messageid;
    }

    public void setMessageid(int messageid) {
        this.messageid = messageid;
    }

    public String getSendid() {
        return sendid;
    }

    public void setSendid(String sendid) {
        this.sendid = sendid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
