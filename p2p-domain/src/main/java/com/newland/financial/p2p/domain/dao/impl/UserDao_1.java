package com.newland.financial.p2p.domain.dao.impl;

import com.newland.financial.p2p.domain.dao.IUserDao;
import com.newland.financial.p2p.domain.dao.IUserDao_1;
import com.newland.financial.p2p.domain.entity.User_1;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao_1 extends MybatisBaseDao<User_1> implements IUserDao_1 {

    public void insert(User_1 user) {
        super.insertSelective(user);
    }

    public User_1 findById(String id) {
        return super.selectByPrimaryKey(id);
    }
}
