package org.grootsec.scubbs.entity;

public class SecretMessageInfo {

    private int messageid;
    private String fromid;
    private String targetid;
    private String content;
    private String time;
    private String hasreaded;
    private String timeout;

    public int getMessageid() {
        return messageid;
    }

    public void setMessageid(int messageid) {
        this.messageid = messageid;
    }

    public String getFromid() {
        return fromid;
    }

    public void setFromid(String fromid) {
        this.fromid = fromid;
    }

    public String getTargetid() {
        return targetid;
    }

    public void setTargetid(String targetid) {
        this.targetid = targetid;
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

    public String getHasreaded() {
        return hasreaded;
    }

    public void setHasreaded(String hasreaded) {
        this.hasreaded = hasreaded;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
}
