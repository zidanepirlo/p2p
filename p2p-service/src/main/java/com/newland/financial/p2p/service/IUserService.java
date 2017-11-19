package com.newland.financial.p2p.service;


import com.newland.financial.p2p.domain.entity.TCmmCity;
import com.newland.financial.p2p.domain.entity.User;

import java.util.List;

public interface IUserService {

    void addUser(User user);
    User getUserById(String userId);
    void addUsersForTest();
    List<TCmmCity> findCityByName(String provNm);
}
