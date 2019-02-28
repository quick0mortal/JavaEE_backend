package org.grootsec.scubbs.entity;

public class AtInfo {

    private int atid;
    private String sendid;
    private String targetid;
    private String time;
    private String hasreaded;

    public int getAtid() {
        return atid;
    }

    public void setAtid(int atid) {
        this.atid = atid;
    }

    public String getSendid() {
        return sendid;
    }

    public void setSendid(String sendid) {
        this.sendid = sendid;
    }

    public String getTargetid() {
        return targetid;
    }

    public void setTargetid(String targetid) {
        this.targetid = targetid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHasreaded() {
        return hasreaded;
    }

    public void setHasreaded(String hasreaded) {
        this.hasreaded = hasreaded;
    }
}
