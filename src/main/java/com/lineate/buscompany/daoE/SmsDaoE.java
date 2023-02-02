package com.lineate.buscompany.daoE;

import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.Sms;

import java.time.LocalDate;
import java.util.List;

public interface SmsDaoE {
    Sms insert(Sms sms) throws ServerException;

    Sms getById(int id) throws ServerException;

    void delete(Sms sms) throws ServerException;

    List<Sms> getAll() throws ServerException;

    List<Sms> getByClientId( int id) throws ServerException;

    List<Sms> getByData(LocalDate data) throws ServerException;

    void deleteAll() throws ServerException;


}
