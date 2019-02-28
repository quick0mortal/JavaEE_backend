package org.grootsec.scubbs.entity;

import org.springframework.beans.factory.annotation.Autowired;

public class Student {

    private String studentNumber;
    private String password;

    public Student() {}

    public Student(String studentNumber, String password) {
        this.studentNumber = studentNumber;
        this.password = password;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
