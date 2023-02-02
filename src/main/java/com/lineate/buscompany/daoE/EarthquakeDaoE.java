package com.lineate.buscompany.daoE;

import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.Earthquake;
import com.lineate.buscompany.modelsE.Request;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

public interface EarthquakeDaoE {
    Earthquake insert(Earthquake earthquake) throws ServerException;

    Earthquake getById(int id) throws ServerException;

    List<Earthquake> getByData(LocalDate data) throws ServerException;

    List<Earthquake> getByCounrtyData(String country, LocalDate data ) throws ServerException;

    Earthquake getClone(Earthquake earthquake) throws ServerException;

    void delete(Earthquake earthquake) throws ServerException;

    List<Earthquake> getAll() throws ServerException;

    void deleteAll() throws ServerException;
}
