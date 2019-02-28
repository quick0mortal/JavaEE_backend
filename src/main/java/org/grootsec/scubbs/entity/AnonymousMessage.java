package org.grootsec.scubbs.entity;

public class AnonymousMessage {

    private int messageid;
    private String sex;
    private String content;
    private String isanonymous;
    private String time;
    private String goodcount;
    private String badcount;


    public int getMessageid() {
        return messageid;
    }

    public void setMessageid(int messageid) {
        this.messageid = messageid;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsanonymous() {
        return isanonymous;
    }

    public void setIsanonymous(String isanonymous) {
        this.isanonymous = isanonymous;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGoodcount() {
        return goodcount;
    }

    public void setGoodcount(String goodcount) {
        this.goodcount = goodcount;
    }

    public String getBadcount() {
        return badcount;
    }

    public void setBadcount(String badcount) {
        this.badcount = badcount;
    }
}
