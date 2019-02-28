package org.grootsec.scubbs.entity;

public class ShortMessageInfo {

    public int messageid;
    public String sendid;
    public String content;
    public String time;
    public String isanonymous;
    public String type;
    public String localtion;

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

    public String getIsanonymous() {
        return isanonymous;
    }

    public void setIsanonymous(String isanonymous) {
        this.isanonymous = isanonymous;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocaltion() {
        return localtion;
    }

    public void setLocaltion(String localtion) {
        this.localtion = localtion;
    }
}
