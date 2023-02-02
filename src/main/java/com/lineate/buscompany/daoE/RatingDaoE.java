package com.lineate.buscompany.daoE;

import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.Rating;

import java.util.List;

public interface RatingDaoE {

    Rating insert(Rating rating) throws ServerException;

    Rating getById(int id) throws ServerException;

    Rating getByClientId(int id) throws ServerException;

    List<Rating> getAll() throws ServerException;

    void deleteAll() throws ServerException;


}
