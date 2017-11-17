package com.newland.financial.p2p.service.Impl;

import com.newland.financial.p2p.common.util.JedisUtil;
import com.newland.financial.p2p.service.ICacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CacheServiceImpl implements ICacheService{

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JedisUtil jedisUtil;

    public Boolean add(String key, String value) {

        Boolean addResult = Boolean.TRUE;
        try {
            long result = jedisUtil.getObject().setnx(key, value);
            if (result != 1) {
                addResult = Boolean.FALSE;
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(),e);
            addResult = Boolean.FALSE;
        }
        return addResult;
    }

    public Boolean add(String key, String value, Date expiry) {

        Boolean addResult = Boolean.TRUE;
        try {
            long result = jedisUtil.getObject().setnx(key, value);
            if (result == 1) {
                jedisUtil.getObject().expire(key, (int) ((expiry.getTime() - System.currentTimeMillis()) / 1000));
            } else {
                addResult = Boolean.FALSE;
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(),e);
            addResult = Boolean.FALSE;
        }
        return addResult;
    }

    public Boolean set(String key, String value, Date expiry) {
        try {
            jedisUtil.getObject().setex(key, (int) ((expiry.getTime() - System.currentTimeMillis()) / 1000), value);
        } catch (Throwable e) {
            logger.error(e.getMessage(),e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
