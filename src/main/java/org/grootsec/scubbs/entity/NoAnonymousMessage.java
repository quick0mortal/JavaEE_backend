package org.grootsec.scubbs.entity;

public class NoAnonymousMessage extends ShortMessageInfo {

    private String goodcount;
    private String badcount;
    private String icon;
    private String nickname;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void set(ShortMessageInfo shortMessageInfo) {
        this.content = shortMessageInfo.getContent();
        this.isanonymous = shortMessageInfo.getIsanonymous();
        this.type = shortMessageInfo.getType();
        this.localtion = shortMessageInfo.getLocaltion();
        this.messageid = shortMessageInfo.getMessageid();
        this.sendid = shortMessageInfo.getSendid();
        this.time = shortMessageInfo.getTime();
    }
}
