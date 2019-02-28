package org.grootsec.scubbs.entity;

public class StudentInfo {

    private String studentNumber;
    private String studentName;
    private String studentSex;
    private String studentClass;
    private String studentGrade;
    private String studentAcademy;
    private String studentProfession;

    public StudentInfo() {
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentSex() {
        return studentSex;
    }

    public void setStudentSex(String studentSex) {
        this.studentSex = studentSex;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getStudentGrade() {
        return studentGrade;
    }

    public void setStudentGrade(String studentGrade) {
        this.studentGrade = studentGrade;
    }

    public String getStudentAcademy() {
        return studentAcademy;
    }

    public void setStudentAcademy(String studentAcademy) {
        this.studentAcademy = studentAcademy;
    }

    public String getStudentProfession() {
        return studentProfession;
    }

    public void setStudentProfession(String studentProfession) {
        this.studentProfession = studentProfession;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "studentNumber='" + studentNumber + '\'' +
                ", studentName='" + studentName + '\'' +
                ", studentSex='" + studentSex + '\'' +
                ", studentClass='" + studentClass + '\'' +
                ", studentGrade='" + studentGrade + '\'' +
                ", studentAcademy='" + studentAcademy + '\'' +
                ", studentProfession='" + studentProfession + '\'' +
                '}';
    }
}
