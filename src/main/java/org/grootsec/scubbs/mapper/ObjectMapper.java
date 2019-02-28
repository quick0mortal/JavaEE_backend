package org.grootsec.scubbs.mapper;

import org.apache.ibatis.annotations.*;
import org.grootsec.scubbs.entity.MyObject;

import java.util.List;

@Mapper
public interface ObjectMapper {

    @Select("select * from object where role = #{role}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "icon", column = "icon"),
            @Result(property = "description", column = "description"),
            @Result(property = "role", column = "role")
    })
    List<MyObject> getAllObject(@Param("role") String role);


    @Select("select * from object where id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "icon", column = "icon"),
            @Result(property = "description", column = "description"),
            @Result(property = "role", column = "role")
    })
    MyObject getObjectById(@Param("id") String id);

    @Select("select o.id, o.name, o.icon, o.description, o.role from object o, teamMember t where t.teamnumber = o.id and t.studentnumber = #{studentnumber}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "icon", column = "icon"),
            @Result(property = "description", column = "description"),
            @Result(property = "role", column = "role")
    })
    List<MyObject> getTeamObjectById(@Param("studentnumber") String studentnumber);

    @Select("select * from object where name = #{name}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "icon", column = "icon"),
            @Result(property = "description", column = "description"),
            @Result(property = "role", column = "role")
    })
    MyObject getObjectByName(@Param("name") String name);

    @Insert("insert into object(id, name, icon, description, role) values (#{id}, #{name}, #{icon}, #{description}, #{role})")
    void insert(MyObject object);

    @Update("update object set name = #{name} where id = #{id}")
    void updateName(MyObject object);

    @Update("update object set description = #{description} where id = #{id}")
    void updateDes(MyObject object);

    @Update("update object set icon = #{icon} where id = #{id}")
    void updateIcon(MyObject object);

    @Delete("delete from object where id = #{id}")
    void deleteById(@Param("id") String id);

    @Delete("delete from object where name = #{name}")
    void deleteByName(@Param("name") String name);

}
