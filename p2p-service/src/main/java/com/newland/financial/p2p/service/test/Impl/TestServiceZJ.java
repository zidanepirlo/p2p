package com.newland.financial.p2p.service.test.Impl;

import com.newland.financial.p2p.domain.dao.IUserDao;
import com.newland.financial.p2p.domain.dao.IUserDao_1;
import com.newland.financial.p2p.domain.entity.User;
import com.newland.financial.p2p.domain.entity.User_1;
import com.newland.financial.p2p.service.test.ITestService;
import com.newland.financial.p2p.service.test.ITestServiceZJ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestServiceZJ implements ITestServiceZJ {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IUserDao_1 userDao_1;

    @Autowired
    private ITestService testService;

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public void testRequiresNew()  throws Exception{

//        try{

            testService.requiresNewTrans();

//        }catch (Exception ex){
//            logger.error(ex.getMessage(),ex);
//        }

        insertUserError();

    }

    @Transactional(propagation= Propagation.REQUIRES_NEW,rollbackFor=Exception.class)
    public void requiresNewTrans()  throws Exception{

        User_1 user_1 = new User_1();
        user_1.setId("2");
        user_1.setName("qing");
        userDao_1.insert(user_1);

        //throw new Exception("testRequiresNew!");
    }

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public void testRequiresNested()  throws Exception{

//        try{
            testService.requiresNestedTrans();

//        }catch (Exception ex){
//            logger.error(ex.getMessage(),ex);
//        }
        insertUser();
    }

    @Transactional(propagation= Propagation.NESTED,rollbackFor=Exception.class)
    public void requiresNestedTrans()  throws Exception{

        User_1 user_1 = new User_1();
        user_1.setId("2");
        user_1.setName("qing");
        userDao_1.insert(user_1);

    }

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public void insertUserError() {
        User user = new User();
        user.setId("1111111111111111111111111111111111111111111111111111111111111111111111111");
        user.setName("yuan");
        userDao.insert(user);
    }

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public void insertUser_1_Error() {
        User_1 user_1 = new User_1();
        user_1.setId("222222222222222222222222222222222222222222222222222222222222222222222");
        user_1.setName("qing");
        userDao_1.insert(user_1);
    }
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public void insertUser() {
        User user = new User();
        user.setId("1111");
        user.setName("yuan");
        userDao.insert(user);
    }

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public void ordTest() throws Exception{
        try{
            insertUser();
        }catch (Exception ex){
            throw ex;
        }

        try{
            insertUser_1_Error();
        }catch (Exception ex){
            throw ex;
        }
    }


}
