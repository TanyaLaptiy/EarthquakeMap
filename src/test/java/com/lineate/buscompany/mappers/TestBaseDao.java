package com.lineate.buscompany.mappers;


import com.lineate.buscompany.daoE.UserDaoE;
import com.lineate.buscompany.errors.ServerException;
import com.lineate.buscompany.modelsE.UserE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestBaseDao {
    @Autowired
    UserDaoE userDao;

    public void clear() throws ServerException {
        if (userDao != null) {
            userDao.deleteAll();
        }
    }

    @Test
    public void testInsertAndGetUserByLogin() throws ServerException {
        userDao.deleteAll();
        LocalDate birthday = LocalDate.of(2000,5,14);

        UserE user = new UserE("lastName",
                "firstName",
                "patronymic",
                "login1",
                "password",
                "Russia",
                "male",
                birthday);
        userDao.insert(user);

        UserE userFromDb = userDao.getById(user.getId());

        Assertions.assertEquals(user, userFromDb);
        userDao.deleteAll();
    }

}
