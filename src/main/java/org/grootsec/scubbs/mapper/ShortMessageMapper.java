package org.grootsec.scubbs.mapper;

import org.apache.ibatis.annotations.*;
import org.grootsec.scubbs.entity.ShortMessageInfo;
import sun.awt.image.ShortInterleavedRaster;

import java.util.List;

@Mapper
public interface ShortMessageMapper {

    @Select("select * from shortMessage where type = #{type} order by time desc")
    @Results({
            @Result(property = "messageid", column = "messageid"),
            @Result(property = "sendid", column = "sendid"),
            @Result(property = "content", column = "content"),
            @Result(property = "time", column = "time"),
            @Result(property = "isanonymous", column = "isanonymous"),
            @Result(property = "type", column = "type"),
            @Result(property = "location", column = "location")
    })
    List<ShortMessageInfo> getAllShortMessageInfo(@Param("type") String type);

    @Select("select * from shortMessage where sendid = #{sendid} order by time desc")
    @Results({
            @Result(property = "messageid", column = "messageid"),
            @Result(property = "sendid", column = "sendid"),
            @Result(property = "content", column = "content"),
            @Result(property = "time", column = "time"),
            @Result(property = "isanonymous", column = "isanonymous"),
            @Result(property = "type", column = "type"),
            @Result(property = "location", column = "location")
    })
    List<ShortMessageInfo> getAllShortMessagesBySendid(@Param("sendid") String sendid);


    @Select("select * from shortMessage where type = #{type} order by time desc")
    @Results({
            @Result(property = "messageid", column = "messageid"),
            @Result(property = "sendid", column = "sendid"),
            @Result(property = "content", column = "content"),
            @Result(property = "time", column = "time"),
            @Result(property = "isanonymous", column = "isanonymous"),
            @Result(property = "type", column = "type"),
            @Result(property = "location", column = "location")
    })
    List<ShortMessageInfo> getRecentMessage(@Param("type") String type);

    @Select("select * from shortMessage where messageid = #{messageid}")
    @Results({
            @Result(property = "messageid", column = "messageid"),
            @Result(property = "sendid", column = "sendid"),
            @Result(property = "content", column = "content"),
            @Result(property = "time", column = "time"),
            @Result(property = "isanonymous", column = "isanonymous"),
            @Result(property = "type", column = "type"),
            @Result(property = "location", column = "location")
    })
    ShortMessageInfo getShortMessageInfo(@Param("messageid") int messageid);

    @Insert("insert into shortMessage(sendid, content, time, isanonymous, type, localtion) values (#{sendid}, #{content}, #{time}, #{isanonymous}, #{type} ,#{localtion})")
    void insert(ShortMessageInfo shortMessageInfo);

    @Select("select * from shortMessage where sendid = #{sendid} and isanonymous = #{isanonymous} order by time desc")
    @Results({
            @Result(property = "messageid", column = "messageid"),
            @Result(property = "sendid", column = "sendid"),
            @Result(property = "content", column = "content"),
            @Result(property = "time", column = "time"),
            @Result(property = "isanonymous", column = "isanonymous"),
            @Result(property = "type", column = "type"),
            @Result(property = "location", column = "location")
    })
    List<ShortMessageInfo> getShortMessageInfos(@Param("sendid") String sendid, @Param("isanonymous") String isanonymous);

//    @Update("update shortMessage set hasreaded = #{hasreaded} where messageid = #{messageid}")
//    void update(ShortMessageInfo shortMessageInfo);

    @Delete("delete from shortMessage where messageid = #{messageid}")
    void delete(ShortMessageInfo shortMessageInfo);

    @Delete("delete from shortMessage where messageid = #{messageid}")
    void deleteMessageById(@Param("id") int id);
}
