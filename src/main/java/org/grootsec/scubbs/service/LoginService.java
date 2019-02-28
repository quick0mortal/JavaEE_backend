package org.grootsec.scubbs.service;

import org.grootsec.scubbs.entity.CookieInfo;
import org.grootsec.scubbs.entity.Student;
import org.grootsec.scubbs.entity.StudentInfo;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface LoginService {

    public Boolean localLogin(Student student);

    public Boolean remoteLogin(Student student); // include spider info

    public StudentInfo getStudentInfo(String studentNumber);


}
