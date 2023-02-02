package com.lineate.buscompany.database.mappers;

import com.lineate.buscompany.modelsE.Rating;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component
public interface RatingMapperE {

    @Insert(" INSERT INTO `rating` ( clientId, mark, message ) VALUES "
            + " (  #{ratin.clientId}, #{ratin.mark}, #{ratin.message} ) ")
    @Options(useGeneratedKeys = true, keyProperty = "ratin.id")
    void insert(@Param("ratin") Rating ratin);

    @Select("SELECT id, clientId, mark, message FROM `rating`")
    List<Rating> getAll();

    @Select("SELECT id, clientId, mark, message FROM `rating` WHERE id = #{id}")
    Rating getById(@Param("id") int id);

    @Select("SELECT id, clientId, mark, message FROM `rating` WHERE clientId = #{id}")
    Rating getByClientId(@Param("id") int id);

    @Delete("DELETE FROM `rating` ")
    void deleteAll();



}
