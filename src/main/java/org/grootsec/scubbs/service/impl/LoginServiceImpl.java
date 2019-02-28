package org.grootsec.scubbs.service.impl;

import org.grootsec.scubbs.entity.CookieInfo;
import org.grootsec.scubbs.entity.Student;
import org.grootsec.scubbs.entity.StudentInfo;
import org.grootsec.scubbs.mapper.CookieMapper;
import org.grootsec.scubbs.mapper.StudentInfoMapper;
import org.grootsec.scubbs.mapper.StudentMapper;
import org.grootsec.scubbs.service.LoginService;
import org.grootsec.scubbs.util.DateUtils;
import org.grootsec.scubbs.util.InfospiderUtils;
import org.grootsec.scubbs.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.sql.Date;

//@Repository
@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Autowired
    private CookieMapper cookieMapper;

    @Override
    public Boolean localLogin(Student student) {
        Student student1 = studentMapper.getStudentByNumber(student.getStudentNumber());
        if (student1 == null) {
            return false;
        }
        if (StringUtils.md5Encode(student.getPassword()).equals(student1.getPassword())) {
            return true;
        }
        return false;

    }

    @Override
    public Boolean remoteLogin(Student student) {
        Student student1 = studentMapper.getStudentByNumber(student.getStudentNumber());

        StudentInfo studentInfo;
        String html = InfospiderUtils.login(student);
        if (html.equals("error")) {
            return false;
        }
        studentInfo = InfospiderUtils.getData(html);
        if (student1 == null) {
            student.setPassword(StringUtils.md5Encode(student.getPassword()));
            studentMapper.insert(student);
            studentInfoMapper.insert(studentInfo);
        } else {
            studentMapper.update(student);
        }
        return true;
    }

    @Override
    public StudentInfo getStudentInfo(String studentNumber) {
        StudentInfo studentInfo = studentInfoMapper.getStudentInfoByNumber(studentNumber);
        if (studentInfo == null) {
            studentInfo.setStudentAcademy("");
            studentInfo.setStudentClass("");
            studentInfo.setStudentGrade("");
            studentInfo.setStudentName("");
            studentInfo.setStudentSex("");
            studentInfo.setStudentProfession("");
            studentInfo.setStudentNumber("");
        }
        return studentInfo;
    }



}
