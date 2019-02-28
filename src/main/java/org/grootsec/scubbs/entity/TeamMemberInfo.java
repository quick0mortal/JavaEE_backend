package org.grootsec.scubbs.entity;

public class TeamMemberInfo {

    private String teamnumber;
    private String studentnumber;
    private String isadmin;
    private String joindate;

    public String getTeamnumber() {
        return teamnumber;
    }

    public void setTeamnumber(String teamnumber) {
        this.teamnumber = teamnumber;
    }

    public String getStudentnumber() {
        return studentnumber;
    }

    public void setStudentnumber(String studentnumber) {
        this.studentnumber = studentnumber;
    }

    public String getIsadmin() {
        return isadmin;
    }

    public void setIsadmin(String isadmin) {
        this.isadmin = isadmin;
    }

    public String getJoindate() {
        return joindate;
    }

    public void setJoindate(String joindate) {
        this.joindate = joindate;
    }
}
