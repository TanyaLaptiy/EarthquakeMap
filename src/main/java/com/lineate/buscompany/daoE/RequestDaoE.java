package com.lineate.buscompany.daoE;

import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.Earthquake;
import com.lineate.buscompany.modelsE.Request;

import java.time.LocalDate;
import java.util.List;

public interface RequestDaoE {

    Request insert(Request request) throws ServerException;

    Request getById(int id) throws ServerException;

    List<Request> getByClientId( int id) throws ServerException;

    List<Request> getByStatus(String status) throws ServerException;

    List<Request> getByData(LocalDate data) throws ServerException;

    String getStatusByClientId(int id) throws ServerException;

    void approve (Request request) throws ServerException;

    Request getClone(Request earthquake) throws ServerException;

    void delete(Request request) throws ServerException;

    List<Request> getAll() throws ServerException;

    void deleteAll() throws ServerException;
}
