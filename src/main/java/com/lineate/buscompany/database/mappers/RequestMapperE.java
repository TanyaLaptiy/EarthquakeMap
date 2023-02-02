package com.lineate.buscompany.database.mappers;

import com.lineate.buscompany.modelsE.Request;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Mapper
@Component
public interface RequestMapperE {

    @Insert(" INSERT INTO `request` ( clientId, data, title, message, latitude, longitude, magnitude, depth, age, onland, country, status, requestData) VALUES "
            + " (  #{request.clientId}, #{request.data}, #{request.title}, #{request.message}, #{request.latitude}, #{request.longitude}, #{request.magnitude}, #{request.depth}, #{request.age}, #{request.onland}, #{request.country}, #{request.status}, #{request.requestData} ) ")
    @Options(useGeneratedKeys = true, keyProperty = "request.id")
    void insert(@Param("request") Request request);

    @Delete("DELETE FROM `request` WHERE id = #{request.id}")
    void delete(@Param("request") Request request);

    @Select("SELECT id, clientId, data, title, message, latitude, longitude, magnitude, depth, age, onland, country, status, requestData FROM `request`")
    List<Request> getAll();

    @Select("SELECT id, clientId, data, title, message, latitude, longitude, magnitude, depth, age, onland, country, status, requestData FROM `request` WHERE id = #{id}")
    Request getById(@Param("id") int id);

    // Уже был добавлен запрос на добавление этого землятресения
    @Select("SELECT id, clientId, data, title, message, latitude, longitude, magnitude, depth, age, onland, country, status, requestData FROM `request` WHERE data = #{request.data} AND latitude = #{request.latitude} AND longitude = #{request.longitude} AND magnitude = #{request.magnitude} AND depth = #{request.depth} AND age = #{request.age} AND onland = #{request.onland} AND country = #{request.country}")
    Request getClone(@Param("request") Request request);

    @Select("SELECT id, clientId, data, title, message, latitude, longitude, magnitude, depth, age, onland, country, status, requestData FROM `request` WHERE clientId = #{id}")
    List<Request> getByClientId(@Param("id") int id);

    @Select("SELECT id, clientId, data, title, message, latitude, longitude, magnitude, depth, age, onland, country, status, requestData FROM `request` WHERE status = #{status}")
    List<Request> getByStatus(@Param("status") String status);

    @Select("SELECT id, clientId, data, title, message, latitude, longitude, magnitude, depth, age, onland, country, status, requestData FROM `request` WHERE requestData = #{data}")
    List<Request> getByData(@Param("data") LocalDate data);

    @Select("SELECT status FROM `request` WHERE clientId = #{id}")
    String getStatusByClientId(@Param("id") int id);

    @Update("UPDATE `request` SET status = #{request.status} WHERE id=#{request.id}")
    void update(@Param("request") Request request);

    @Delete("DELETE FROM `request` ")
    void deleteAll();

}
