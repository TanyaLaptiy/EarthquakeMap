package com.lineate.buscompany.database.mappers;


import com.lineate.buscompany.modelsE.SessionE;
import com.lineate.buscompany.modelsE.UserE;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapperE {

    @Insert(" INSERT INTO userE ( lastName, firstName, patronymic, login, password, country, sex, birthday ) VALUES "
            + " (  #{user.lastName}, #{user.firstName}, #{user.patronymic}, #{user.login}, #{user.password}, #{user.country}, #{user.sex}, #{user.birthday} ) ")
    @Options(useGeneratedKeys = true, keyProperty = "user.id")
    void insert(@Param("user") UserE user);

    @Delete("DELETE FROM userE WHERE id = #{user.id}")
    void delete(@Param("user") UserE user);

    @Select("SELECT id, lastName, firstName, patronymic, login, password, country, sex, birthday FROM userE")
    List<UserE> getAll();

    @Select("SELECT id, lastName, firstName, patronymic, login, password, country, sex, birthday FROM userE WHERE id = #{id}")
    UserE getById(@Param("id") int id);

    @Select("SELECT id, lastName, firstName, patronymic, login, password, country, sex, birthday FROM userE WHERE login = #{log} AND password = #{password}")
    UserE getByLogin(@Param("log") String log, @Param("password")  String password);

    @Select("SELECT id, lastName, firstName, patronymic, login, password, country, sex, birthday FROM userE WHERE login = #{log} ")
    UserE getByLog(@Param("log") String log);

    @Delete("DELETE FROM userE ")
    void deleteAll();

    @Select("SELECT id, userId, token FROM sessionE WHERE token = #{token}")
    @Results({
            @Result(property = "userE", column = "userId", javaType = UserE.class,
                    one = @One(select = "com.lineate.buscompany.databaseE.mappersE.UserMapperE.getById",
                            fetchType = FetchType.LAZY))}
    )
    SessionE getSessionByToken(String token);

    @Select("SELECT id, userId, token FROM sessionE WHERE token = #{token}")
    SessionE getByToken(@Param("token") String token);


}
