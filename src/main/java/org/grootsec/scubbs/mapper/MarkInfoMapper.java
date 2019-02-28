package org.grootsec.scubbs.mapper;

import org.apache.ibatis.annotations.*;
import org.grootsec.scubbs.entity.MarkCount;
import org.grootsec.scubbs.entity.MarkInfo;

import java.util.List;

@Mapper
public interface MarkInfoMapper {

    @Select("select * from mark")
    @Results({
            @Result(property = "messageid", column = "messageid"),
            @Result(property = "markerid", column = "markerid"),
            @Result(property = "type", column = "type")
    })
    List<MarkInfo> getAllMarkInfo();


    @Select("select * from mark where messageid = #{messageid} and markerid = #{markerid} and type = #{type}")
    @Results({
            @Result(property = "messageid", column = "messageid"),
            @Result(property = "markerid", column = "markerid"),
            @Result(property = "type", column = "type")
    })
    MarkInfo getMarkInfo(MarkInfo markInfo);

    @Select("select * from mark where markerid = #{markerid}")
    @Results({
            @Result(property = "messageid", column = "messageid"),
            @Result(property = "markerid", column = "markerid"),
            @Result(property = "type", column = "type")
    })
    List<MarkInfo> getMarkInfosByMarker(@Param("markerid") String markerid);

//    @Select("select m.messageid, m.markerid, o.name, m.type from mark m, shortMessage s, object o WHERE m.messageid = s.messageid and m.markerid = #{markeid} and o.id = s.sendid and s.isanonymous = \"0\"")
//    @Results({
//            @Result(property = "messageid", column = "messageid"),
//            @Result(property = "markerid", column = "markerid"),
//            @Result(property = "name", column = "name"),
//            @Result(property = "type", column = "type")
//    })
//    List<?> getMarkInfosByMarker(@Param("markerid") String markerid);

    @Select("select * from mark where messageid = #{messageid} and type = #{type}")
    @Results({
            @Result(property = "messageid", column = "messageid"),
            @Result(property = "markerid", column = "markerid"),
            @Result(property = "type", column = "type")
    })
    List<MarkInfo> getMarkInfos(MarkInfo markInfo);

    @Select("select * from mark where messageid = #{messageid} and type = #{type}")
    @Results({
            @Result(property = "messageid", column = "messageid"),
            @Result(property = "markerid", column = "markerid"),
            @Result(property = "type", column = "type")
    })
    List<MarkInfo> getMarkCount(MarkInfo markInfo);

    @Select("select messageid, count(*) from mark where type = \"good\" group by messageid ORDER BY count(*) desc")
    @Results({
            @Result(property = "messageid", column = "messageid"),
            @Result(property = "count", column = "count(*)")
    })
    List<MarkCount> getGoodOrder();

    @Insert("insert into mark(messageid, markerid, type) values (#{messageid}, #{markerid}, #{type})")
    void insert(MarkInfo markInfo);

//    @Update("update mark set hasreaded=#{messageid} where messageid = #{messageid}")
//    void update(MarkInfo criticalInfo);

    @Delete("delete from mark where messageid = #{messageid} and markerid = #{markerid} and type = #{type}")
    void delete(MarkInfo markInfo);
}
