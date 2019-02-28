package org.grootsec.scubbs.mapper;

import org.apache.ibatis.annotations.*;
import org.grootsec.scubbs.entity.Student;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("select * from student")
    @Results({
            @Result(property = "studentNumber", column = "studentnumber"),
            @Result(property = "password", column = "password")
    })
    List<Student> getAllStudent();

    @Select("select * from student where studentnumber = #{studentNumber}")
    @Results({
            @Result(property = "studentNumber", column = "studentnumber"),
            @Result(property = "password", column = "password")
    })
    Student getStudentByNumber(@Param("studentNumber") String studentNumber);

    @Insert("insert into student(studentnumber, password) values (#{studentNumber}, #{password})")
    void insert(Student student);

    @Update("update student set password=${password} where studentnumber = ${studentNumber}")
    void update(Student student);

    @Delete("delete from student where studentnumber = #{studentNumber}")
    void delete(Student student);

}
