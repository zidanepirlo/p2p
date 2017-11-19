package com.newland.financial.p2p.domain.dao;

import com.newland.financial.p2p.domain.entity.User_1;

public interface IUserDao_1 {

    void insert(User_1 user);
    User_1 findById(String id);
}
