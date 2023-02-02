package com.lineate.buscompany.database.mappers;

import com.lineate.buscompany.modelsE.Sms;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Mapper
@Component
public interface SmsMapperE {


    @Insert(" INSERT INTO `sms` ( clientId, data, message ) VALUES "
            + " (  #{sms.clientId}, #{sms.data}, #{sms.message} ) ")
    @Options(useGeneratedKeys = true, keyProperty = "sms.id")
    void insert(@Param("sms") Sms sms);

    @Delete("DELETE FROM `sms` WHERE id = #{sms.id}")
    void delete(@Param("sms") Sms sms);

    @Select("SELECT id, clientId, data, message FROM `sms`")
    List<Sms> getAll();

    @Select("SELECT id, clientId, data, message FROM `sms` WHERE id = #{id}")
    Sms getById(@Param("id") int id);

    @Select("SELECT id, clientId, data, message FROM `sms` WHERE clientId = #{id}")
    List<Sms> getByClientId(@Param("id") int id);

    @Select("SELECT id, clientId, data, message FROM `sms` WHERE data = #{data}")
    List<Sms> getByData(@Param("data") LocalDate data);

    @Delete("DELETE FROM `sms` ")
    void deleteAll();



}
