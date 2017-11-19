package com.newland.financial.p2p.domain.dao;

import com.newland.financial.p2p.domain.entity.TCmmCity;

import java.util.List;

public interface ITCmmCityDao {

    TCmmCity findById(String cityCd);

    List<TCmmCity> findByProvNm(String provNm);

}
