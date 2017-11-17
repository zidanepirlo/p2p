package com.newland.financial.p2p.domain.dao;

import com.newland.financial.p2p.domain.entity.User;

public interface IUserDao {

    void insert(User user);
    User findById(String id);
}
