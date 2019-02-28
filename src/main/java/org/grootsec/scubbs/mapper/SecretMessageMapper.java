package org.grootsec.scubbs.mapper;

import org.apache.ibatis.annotations.*;
import org.grootsec.scubbs.entity.SecretMessageInfo;

import java.util.List;

@Mapper
public interface SecretMessageMapper {


    @Select("select * from secretMessage")
    @Results({
            @Result(property = "messageid", column = "messageid"),
            @Result(property = "fromid", column = "fromid"),
            @Result(property = "targetid", column = "targetid"),
            @Result(property = "content", column = "content"),
            @Result(property = "time", column = "time"),
            @Result(property = "hasreaded", column = "hasreaded"),
            @Result(property = "timeout", column = "timeout")
    })
    List<SecretMessageInfo> getAllSecretMessageInfo();


    @Select("select * from secretMessage where messageid = #{messageid}")
    @Results({
            @Result(property = "messageid", column = "messageid"),
            @Result(property = "fromid", column = "fromid"),
            @Result(property = "targetid", column = "targetid"),
            @Result(property = "content", column = "content"),
            @Result(property = "time", column = "time"),
            @Result(property = "hasreaded", column = "hasreaded"),
            @Result(property = "timeout", column = "timeout")
    })
    SecretMessageInfo getSecretMessageInfo(@Param("messageid") int messageid);

    @Insert("insert into secretMessage(fromid, targetid, content, time, hasreaded, timeout) values (#{fromid}, #{targetid}, #{content}, #{time}, #{hasreaded}, #{timeout})")
    void insert(SecretMessageInfo secretMessageInfo);

    @Update("update secretMessage set hasreaded = #{hasreaded} where messageid = #{messageid}")
    void update(SecretMessageInfo secretMessageInfo);

    @Delete("delete from secretMessage where messageid = #{messageid}")
    void delete(SecretMessageInfo secretMessageInfo);
}
