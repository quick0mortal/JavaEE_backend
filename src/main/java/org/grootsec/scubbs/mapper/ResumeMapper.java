package org.grootsec.scubbs.mapper;

import org.apache.ibatis.annotations.*;
import org.grootsec.scubbs.entity.ResumeInfo;

import java.util.List;

@Mapper
public interface ResumeMapper {

    @Select("select * from resume")
    @Results({
            @Result(property = "studentnumber", column = "studentnumber"),
            @Result(property = "brief", column = "brief"),
            @Result(property = "content", column = "content"),
            @Result(property = "time", column = "time")
    })
    List<ResumeInfo> getAllResumeInfo();

    @Select("select * from resume where studentnumber = #{studentnumber}")
    @Results({
            @Result(property = "studentnumber", column = "studentnumber"),
            @Result(property = "brief", column = "brief"),
            @Result(property = "content", column = "content"),
            @Result(property = "time", column = "time")
    })
    ResumeInfo getResumeInfo(@Param("studentnumber") String studentnumber);

    @Insert("insert into resume(studentnumber, brief, content, time) values (#{studentnumber}, #{brief}, #{content}, #{time})")
    void insert(ResumeInfo resumeInfo);

    @Update("update resume set brief=#{brief} where studentnumber = #{studentnumber}")
    void updateBrief(ResumeInfo resumeInfo);

    @Update("update resume set content=#{content} where studentnumber = #{studentnumber}")
    void updateContent(ResumeInfo resumeInfo);

    @Delete("delete from resume where studentnumber = #{studentnumber}")
    void delete(ResumeInfo resumeInfo);
}
