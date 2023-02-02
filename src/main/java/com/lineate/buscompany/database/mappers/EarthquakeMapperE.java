package com.lineate.buscompany.database.mappers;

import com.lineate.buscompany.modelsE.Earthquake;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Mapper
@Component
public interface EarthquakeMapperE {

    @Insert(" INSERT INTO earthquake ( latitude, longitude, magnitude, age, depth, onland, country, data) VALUES "
            + " (  #{earthquake.latitude}, #{earthquake.longitude}, #{earthquake.magnitude}, #{earthquake.age}, #{earthquake.depth}, #{earthquake.onland}, #{earthquake.country}, #{earthquake.data} ) ")
    @Options(useGeneratedKeys = true, keyProperty = "earthquake.id")
    void insert(@Param("earthquake") Earthquake earthquake);

    @Delete("DELETE FROM earthquake WHERE id = #{earthquake.id}")
    void delete(@Param("earthquake") Earthquake earthquake);

    @Select("SELECT id, latitude, longitude, magnitude, age, depth, onland, country, data FROM earthquake")
    List<Earthquake> getAll();

    @Select("SELECT id, latitude, longitude, magnitude, age, depth, onland, country, data FROM earthquake WHERE id = #{id}")
    Earthquake getById(@Param("id") int id);

    @Select("SELECT id, latitude, longitude, magnitude, age, depth, onland, country, data FROM earthquake WHERE country = #{country} AND data = #{data} ")
    List<Earthquake> getByCounrtyData(@Param("country") String country, @Param("data") LocalDate data );

    @Select("SELECT id, latitude, longitude, magnitude, age, depth, onland, country, data  FROM earthquake WHERE data = #{data}")
    List<Earthquake> getByData(@Param("data") LocalDate data);

    // Землетрясение уже есть в БД
    @Select("SELECT id, latitude, longitude, magnitude, age, depth, onland, country, data FROM earthquake WHERE data = #{earthquake.data} AND latitude = #{earthquake.latitude} AND longitude = #{earthquake.longitude} AND magnitude = #{earthquake.magnitude} AND depth = #{earthquake.depth} AND age = #{earthquake.age} AND onland = #{earthquake.onland} AND country = #{earthquake.country} ")
    Earthquake getClone(@Param("earthquake") Earthquake earthquake);

    @Delete("DELETE FROM earthquake ")
    void deleteAll();

}
