package org.grootsec.scubbs.mapper;

import org.apache.ibatis.annotations.*;
import org.grootsec.scubbs.entity.AtInfo;

import java.util.List;

@Mapper
public interface AtInfoMapper {

    @Select("select * from at")
    @Results({
            @Result(property = "atid", column = "atid"),
            @Result(property = "sendid", column = "sendid"),
            @Result(property = "targetid", column = "targetid"),
            @Result(property = "time", column = "time"),
            @Result(property = "hasreaded", column = "hasreaded")
    })
    List<AtInfo> getAllAtInfo();

    @Select("select * from at where atid = #{atid}")
    @Results({
            @Result(property = "atid", column = "atid"),
            @Result(property = "sendid", column = "sendid"),
            @Result(property = "targetid", column = "targetid"),
            @Result(property = "time", column = "time"),
            @Result(property = "hasreaded", column = "hasreaded")
    })
    AtInfo getAtInfo(int atid);

    @Insert("insert into at(sendid, targetid, time, hasreaded) values (#{sendid}, #{targetid}, #{time}, #{hasreaded})")
    void insert(AtInfo atInfo);

    @Update("update at set hasreaded=#{hasreaded} where atid = #{atid}")
    void updateRead(AtInfo atInfo);

    @Delete("delete from at where atid = #{atid}")
    void delete(AtInfo atInfo);

    @Delete("delete from at where atid = #{atid}")
    void deleteById(@Param("atid") int atid);

    @Select("select * from at where targetid = #{targetid} and hasreaded = #{hasreaded} order by time desc")
    @Results({
            @Result(property = "atid", column = "atid"),
            @Result(property = "sendid", column = "sendid"),
            @Result(property = "targetid", column = "targetid"),
            @Result(property = "time", column = "time"),
            @Result(property = "hasreaded", column = "hasreaded")
    })
    List<AtInfo> getAtInfoByTarget(@Param("targetid") String targetid, @Param("hasreaded") String hasreaded);


}
