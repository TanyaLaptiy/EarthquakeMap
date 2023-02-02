package com.lineate.buscompany.database.mappers;

import com.lineate.buscompany.modelsE.ClientE;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component

public interface ClientMapperE {
    @Insert("INSERT INTO `clientE` (id, mail, userId) VALUES "
            + "(#{client.id}, #{client.mail}, #{client.id})")
    Integer insert(@Param("client") ClientE client);

    @Select("SELECT id, mail, userId FROM clientE WHERE id = #{id}")
    ClientE getById(@Param("id") int id);

    @Select("SELECT mail FROM clientE WHERE id = #{id}")
    String getMailById(@Param("id") int id);


    @Select("SELECT clientE.id, lastName,  firstName,  patronymic,  mail,  login,  password, country, userId FROM clientE JOIN userE WHERE userId = userE.id")
    List<ClientE> getAll();

    @Delete("DELETE FROM clientE ")
    void deleteAll();
}
