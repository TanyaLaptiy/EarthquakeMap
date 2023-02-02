package com.lineate.buscompany.database.mappers;

import com.lineate.buscompany.modelsE.AdministratorE;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component

public interface AdministratorMapperE {

    @Insert("INSERT INTO `administratorE` (id, position, userId) VALUES "
            + "(#{administrator.id}, #{administrator.position}, #{administrator.id})")
    Integer insert(@Param("administrator") AdministratorE administrator);


    @Select("SELECT id, position, userId FROM administratorE WHERE id = #{id}")
    AdministratorE getById(@Param("id") int id);

    @Select("SELECT position FROM administratorE WHERE id = #{id}")
    String getPositionById(@Param("id") int id);


    @Select("SELECT administratorE.id, lastName,  firstName,  patronymic,  position,  login,  password, country, userId FROM administratorE JOIN userE WHERE userId = userE.id")
    List<AdministratorE> getAll();

    @Update("UPDATE administratorE SET position = #{administrator.position}  WHERE id=#{administrator.id}")
    void update(@Param("administrator") AdministratorE administrator);

    @Delete("DELETE FROM administratorE ")
    void deleteAll();

}
