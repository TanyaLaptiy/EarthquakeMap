package com.lineate.buscompany.daoE;

import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.SessionE;
import com.lineate.buscompany.modelsE.UserE;

import java.util.List;

public interface UserDaoE {
    UserE insert(UserE user) throws ServerException;

    UserE getById(int id) throws ServerException;

    UserE getByLog(String log) throws ServerException;

    void delete(UserE user) throws ServerException;

    List<UserE> getAll() throws ServerException;

    void deleteAll() throws ServerException;

    SessionE getSessionByToken(String token);

    void deleteSessionByToken(String token);
}
