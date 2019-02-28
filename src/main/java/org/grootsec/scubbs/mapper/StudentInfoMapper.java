package org.grootsec.scubbs.mapper;

import org.apache.ibatis.annotations.*;
import org.grootsec.scubbs.entity.StudentInfo;

import java.util.List;

@Mapper
public interface StudentInfoMapper {

    @Select("select * from studentInfo")
    @Results({
            @Result(property = "studentNumber", column = "studentnumber"),
            @Result(property = "studentName", column = "realname"),
            @Result(property = "studentGrade", column = "grade"),
            @Result(property = "studentAcademy", column = "academy"),
            @Result(property = "studentProfession", column = "profession"),
            @Result(property = "studentClass", column = "class"),
            @Result(property = "studentSex", column = "sex")
    })
    List<StudentInfo> getAllStudentInfo();


    @Select("select * from studentInfo where studentnumber = #{studentNumber}")
    @Results({
            @Result(property = "studentNumber", column = "studentnumber"),
            @Result(property = "studentName", column = "realname"),
            @Result(property = "studentGrade", column = "grade"),
            @Result(property = "studentAcademy", column = "academy"),
            @Result(property = "studentProfession", column = "profession"),
            @Result(property = "studentClass", column = "class"),
            @Result(property = "studentSex", column = "sex")
    })
    StudentInfo getStudentInfoByNumber(@Param("studentNumber") String studentNumber);

    @Insert("insert into studentInfo(studentnumber, realname, grade, academy, profession, class, sex) values (#{studentNumber}, #{studentName}, #{studentGrade}, #{studentAcademy}, #{studentProfession}, #{studentClass}, #{studentSex})")
    void insert(StudentInfo studentInfo);

    @Delete("delete from studentInfo where studentnumber = #{studentNumber}")
    void delete(StudentInfo studentInfo);

}
