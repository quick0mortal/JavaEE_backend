package org.grootsec.scubbs.entity;

public class MarkInfo {

    private String messageid;

//    private int messageid;
    private String markerid;
    private String type;

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public String getMarkerid() {
        return markerid;
    }

    public void setMarkerid(String markerid) {
        this.markerid = markerid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIntMessageid() {
        int result;
        if (this.messageid.startsWith("T")||this.messageid.startsWith("C")) {
            result = Integer.parseInt(messageid.substring(1));
        } else {
            result = Integer.parseInt(messageid);
        }
        return result;
    }
}
