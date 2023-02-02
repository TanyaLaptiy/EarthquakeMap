package com.lineate.buscompany.database.mappers;

import com.lineate.buscompany.modelsE.SessionE;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SessionMapperE {
    @Insert("INSERT INTO `sessionE` (token, userId) VALUES ( #{session.token}, #{session.userId}) ")
    @Options(useGeneratedKeys = true, keyProperty = "session.id")
    void insert(@Param("session") SessionE session);

    @Update("UPDATE `sessionE` SET token = #{session.token}, userId = #{session.userId} WHERE id=#{session.id}")
    void update(@Param("session") SessionE session);

    @Delete("DELETE FROM `sessionE` WHERE id = #{session.id}")
    void delete(@Param("session") SessionE session);

    @Delete("DELETE FROM `sessionE` WHERE token = #{token}")
    int deleteByToken(@Param("token") String token);

    @Delete("DELETE FROM `sessionE` WHERE userId = #{session}")
    void deleteByUserId(@Param("session") int session);

    @Select("SELECT id, token, userId FROM `sessionE` ")
    List<SessionE> getAll();

    @Select("SELECT id, token, userId FROM `sessionE` WHERE userId = #{id}")
    SessionE getById(@Param("id") int id);

    @Select("SELECT id, token, userId FROM `sessionE` WHERE token = #{token}")
    SessionE getByToken(@Param("token") String token);

    @Delete("DELETE FROM `sessionE`  ")
    void deleteAll();

}
