package org.grootsec.scubbs.mapper;

import org.apache.ibatis.annotations.*;
import org.grootsec.scubbs.entity.CookieInfo;

import java.util.List;

@Mapper
public interface CookieMapper {

    @Select("select * from cookies")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "studentnumber", column = "studentnumber"),
            @Result(property = "cookie", column = "cookie"),
            @Result(property = "time", column = "time")
    })
    List<CookieInfo> getAllCookies();

    @Select("select * from cookies where cookie = #{cookie}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "studentnumber", column = "studentnumber"),
            @Result(property = "cookie", column = "cookie"),
            @Result(property = "time", column = "time")
    })
    CookieInfo getCookie(String cookie);

    @Insert("insert into cookies(studentnumber, cookie, time) values (#{studentnumber}, #{cookie}, #{time})")
    void insert(CookieInfo cookie);

//    @Update("update cookies set password=${password} where studentnumber = ${studentNumber}")
//    void update(Cookie cookie);

    @Delete("delete from cookies where studentnumber = #{studentnumber}")
    void delete(CookieInfo cookie);




}
