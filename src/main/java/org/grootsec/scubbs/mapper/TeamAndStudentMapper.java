package org.grootsec.scubbs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.grootsec.scubbs.entity.CookieInfo;

import java.util.List;

@Mapper
public interface TeamAndStudentMapper {

//    @Select("select isadmin from object ,teammember where studentnumber = #{studentnumber} and object.id = teammember.teamnumber and object.id = #{teamnumber}")
//    @Results({
////            @Result(property = "id", column = "id"),
////            @Result(property = "name", column = "name"),
////            @Result(property = "icon", column = "icon"),
////            @Result(property = "description", column = "description"),
////            @Result(property = "role", column = "role"),
//            @Result(property = "teamnumber", column = "teamnumber"),
//            @Result(property = "studentnumber", column = "studentnumber"),
//            @Result(property = "isadmin", column = "isadmin")
////            @Result(property = "joindate", column = "joindate")
//    })
//    String getIsAdmin(String studentnumber, String teamnumber);

    @Select("select isadmin from teamMember where studentnumber = #{studentnumber} and teamnumber = #{teamnumber}")
    @Results({
            @Result(property = "isadmin", column = "isadmin")
    })
    String getIsAdmin(String studentnumber, String teamnumber);

}
