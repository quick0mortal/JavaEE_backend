package org.grootsec.scubbs.mapper;

import org.apache.ibatis.annotations.*;
import org.grootsec.scubbs.entity.TeamMemberInfo;

import java.util.List;

@Mapper
public interface TeamMemberInfoMapper {

    @Select("select * from teamMember")
    @Results({
            @Result(property = "teamnumber", column = "teamnumber"),
            @Result(property = "studentnumber", column = "studentnumber"),
            @Result(property = "isadmin", column = "isadmin"),
            @Result(property = "joindate", column = "joindate")
    })
    List<TeamMemberInfo> getAllTeamMemberInfo();


    @Select("select * from teamMember where studentnumber = #{studentnumber} and teamnumber = #{teamnumber}")
    @Results({
            @Result(property = "teamnumber", column = "teamnumber"),
            @Result(property = "studentnumber", column = "studentnumber"),
            @Result(property = "isadmin", column = "isadmin"),
            @Result(property = "joindate", column = "joindate")
    })
    TeamMemberInfo getTeamMemberInfo(@Param("teamnumber") String teamnumber,@Param("studentnumber") String studentnumber);

    @Select("select * from teamMember where teamnumber = #{teamnumber}")
    @Results({
            @Result(property = "teamnumber", column = "teamnumber"),
            @Result(property = "studentnumber", column = "studentnumber"),
            @Result(property = "isadmin", column = "isadmin"),
            @Result(property = "joindate", column = "joindate")
    })
    List<TeamMemberInfo> getAllTeamMemberInfoByTId(String teamnumber);

    @Insert("insert into teamMember(teamnumber, studentnumber, isadmin, joindate) values (#{teamnumber}, #{studentnumber}, #{isadmin}, #{joindate})")
    void insert(TeamMemberInfo teamMemberInfo);

    @Update("update teamMember set isadmin = #{isadmin} where teamnumber = #{teamnumber} and studentnumber = #{studentNumber}")
    void update(TeamMemberInfo teamMemberInfo);

    @Delete("delete from teamMember where studentnumber = #{studentnumber} and teamnumber = #{teamnumber}")
    void delete(@Param("teamnumber") String teamnumber,@Param("studentnumber") String studentnumber);

    @Delete("delete from teamMember where teamnumber = #{teamnumber}")
    void deleteAllMember(@Param("teamnumber") String teamnumber);

}
