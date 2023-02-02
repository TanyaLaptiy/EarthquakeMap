package com.lineate.buscompany.daoE;

import com.lineate.buscompany.dtoE.requestE.LoginRequestE;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.ClientE;

import java.util.List;

public interface ClientDaoE {

    ClientE insert(ClientE client) throws ServerException;

    ClientE getById(int id) throws ServerException;

    void deleteAll() throws ServerException;

    List<ClientE> getAll() throws ServerException;

    ClientE getByLogin(LoginRequestE login);

    void deleteSessionByToken(String token);
}
