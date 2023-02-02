package com.lineate.buscompany.daoE;

import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.SessionE;

import java.util.List;

public interface SessionDaoE {
    SessionE insert(SessionE session) throws ServerException;

    SessionE update(SessionE session) throws ServerException;

    void delete(SessionE session) throws ServerException;

    void deleteByUserId(int session) throws ServerException;

    List<SessionE> getAll() throws ServerException;

    SessionE getById(int id) throws ServerException;

    SessionE getByToken(String token) throws ServerException;

    void deleteAll() throws ServerException;

}
