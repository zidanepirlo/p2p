package com.newland.financial.p2p.domain.dao.impl;

import com.newland.financial.p2p.domain.dao.ITCmmCityDao;
import com.newland.financial.p2p.domain.dao.IUserDao;
import com.newland.financial.p2p.domain.entity.TCmmCity;
import com.newland.financial.p2p.domain.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TCmmCityDao extends MybatisBaseDao<TCmmCity> implements ITCmmCityDao {

    public TCmmCity findById(String cityCd) {
        return super.selectByPrimaryKey(cityCd);
    }

    public List<TCmmCity> findByProvNm(String provNm) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("provNm", provNm);
        return super.select("selectByProperties", params);
    }
}
