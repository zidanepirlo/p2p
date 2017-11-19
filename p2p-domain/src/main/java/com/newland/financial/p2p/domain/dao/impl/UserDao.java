package com.newland.financial.p2p.domain.dao.impl;

import com.newland.financial.p2p.domain.dao.IUserDao;
import com.newland.financial.p2p.domain.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends MybatisBaseDao<User> implements IUserDao {

    public void insert(User user) {
        super.insertSelective(user);
    }

    public User findById(String id) {
        return super.selectByPrimaryKey(id);
    }
}
