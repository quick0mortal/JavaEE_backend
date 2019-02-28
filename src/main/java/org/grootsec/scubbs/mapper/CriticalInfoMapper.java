package org.grootsec.scubbs.mapper;

import org.apache.ibatis.annotations.*;
import org.grootsec.scubbs.entity.CriticalInfo;
import org.grootsec.scubbs.entity.MarkResponseInfo;

import java.util.List;

@Mapper
public interface CriticalInfoMapper {


    @Select("select * from critical order by time desc")
    @Results({
            @Result(property = "criticalid", column = "criticalid"),
            @Result(property = "messageid", column = "messageid"),
            @Result(property = "sendid", column = "sendid"),
            @Result(property = "content", column = "content"),
            @Result(property = "time", column = "time")
    })
    List<CriticalInfo> getAllCriticalInfo();


    @Select("select * from critical where criticalid = #{criticalid}")
    @Results({
            @Result(property = "criticalid", column = "criticalid"),
            @Result(property = "messageid", column = "messageid"),
            @Result(property = "sendid", column = "sendid"),
            @Result(property = "content", column = "content"),
            @Result(property = "time", column = "time")
    })
    CriticalInfo getCriticalInfo(@Param("criticalid") int criticalid);

    @Select("select * from critical where messageid = #{messageid}")
    @Results({
            @Result(property = "criticalid", column = "criticalid"),
            @Result(property = "messageid", column = "messageid"),
            @Result(property = "sendid", column = "sendid"),
            @Result(property = "content", column = "content"),
            @Result(property = "time", column = "time")
    })
    List<CriticalInfo> getCriticalInfos(@Param("messageid") int messageid);

    @Select("select c.criticalid, c.messageid, c.sendid, c.content, c.time from critical c, shortMessage s where c.sendid = #{sendid} and c.messageid = s.messageid and s.isanonymous = '0'")
    @Results({
            @Result(property = "criticalid", column = "criticalid"),
            @Result(property = "messageid", column = "messageid"),
            @Result(property = "sendid", column = "sendid"),
            @Result(property = "content", column = "content"),
            @Result(property = "time", column = "time")
    })
    List<CriticalInfo> getNACriticalInfos(@Param("sendid") String sendid);

    @Insert("insert into critical(messageid, sendid, content, time) values (#{messageid}, #{sendid}, #{content}, #{time})")
    void insert(CriticalInfo criticalInfo);

//    @Update("update critical set hasreaded=#{hasreaded} where atid = #{atid}")
//    void update(CriticalInfo criticalInfo);

    @Delete("delete from critical where criticalid = #{criticalid}")
    void delete(CriticalInfo criticalInfo);
}
