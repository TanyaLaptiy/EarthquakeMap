package com.lineate.buscompany.daoE;

import com.lineate.buscompany.dtoE.requestE.LoginRequestE;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.AdministratorE;

import java.util.List;

public interface AdministratorDaoE {

    AdministratorE insert(AdministratorE administrator) throws ServerException;

    AdministratorE getById(int id) throws ServerException;

    AdministratorE getByLogin(LoginRequestE log) throws ServerException;

    void deleteSessionByToken(String token);

    void deleteAll() throws ServerException;

    List<AdministratorE> getAll() throws ServerException;


}
