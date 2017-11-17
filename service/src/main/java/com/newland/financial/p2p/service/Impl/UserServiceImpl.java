package com.newland.financial.p2p.service.Impl;


import com.newland.financial.p2p.domain.dao.ITCmmCityDao;
import com.newland.financial.p2p.domain.dao.IUserDao;
import com.newland.financial.p2p.domain.dao.IUserDao_1;
import com.newland.financial.p2p.domain.entity.TCmmCity;
import com.newland.financial.p2p.domain.entity.User;
import com.newland.financial.p2p.domain.entity.User_1;
import com.newland.financial.p2p.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IUserDao_1 userDao_1;

    @Autowired
    private ITCmmCityDao tCmmCityDao;

    public void addUser(User user) {
        userDao.insert(user);
    }

    public User getUserById(String userId) {
        return userDao.findById(userId);
    }

    public void addUsersForTest() {

//        User user = new User();
//        user.setId("1111111111111111111111111111111111111111111");
//        user.setName("yuan");
//        userDao.insert(user);
//
//        User_1 user_1 = new User_1();
//        user_1.setId("2222");
//        user_1.setName("qing");
//        userDao_1.insert(user_1);
    }

    public List<TCmmCity> findCityByName(String provNm) {
        return tCmmCityDao.findByProvNm(provNm);
    }
}
