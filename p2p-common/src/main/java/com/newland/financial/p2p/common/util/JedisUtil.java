package com.newland.financial.p2p.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil implements FactoryBean<JedisCluster>, InitializingBean {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Resource addressConfig;
    private String addressKeyPrefix;
    private JedisCluster jedisCluster;
    private Integer timeout;
    private Integer maxRedirections;

    private JedisPoolConfig poolConfig;
    private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");

    public JedisCluster getObject() {
        return jedisCluster;
    }

    public Class<? extends JedisCluster> getObjectType() {
        return (this.jedisCluster != null ? this.jedisCluster.getClass() : JedisCluster.class);
    }

    public boolean isSingleton() {
        return true;
    }

    private Set<HostAndPort> parseHostAndPort() {
        try {
            Properties prop = new Properties();
            prop.load(this.addressConfig.getInputStream());

            Set<HostAndPort> haps = new HashSet<HostAndPort>();
            for (Object key : prop.keySet()) {

                if (!((String) key).startsWith(addressKeyPrefix)) {
                    continue;
                }
                String val = ((String) prop.get(key)).trim();
                boolean isIpPort = p.matcher(val).matches();
                if (!isIpPort) {
                    throw new Exception("ip �� port ���Ϸ�");
                }
                String[] ipAndPort = val.split(":");
                HostAndPort hap = new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
                haps.add(hap);
            }

            return haps;
        } catch (Exception ex) {
            logger.error("���� redis.properties ʧ��!");
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public void afterPropertiesSet() {
        try {
            Set<HostAndPort> haps = this.parseHostAndPort();
            jedisCluster = new JedisCluster(haps, timeout, maxRedirections, poolConfig);
        } catch (Exception ex) {
            logger.error("��ʼ�� jedisCluster ʧ��!");
            logger.error(ex.getMessage(), ex);
        }
    }

    public void closeJedisCluster() {
        logger.info("closeJedisCluster start!");
        try {
            if (jedisCluster != null) {
                jedisCluster.close();
                logger.warn("JedisUtil �ر�redis����!");
            }
        } catch (Throwable t) {
            logger.error("closeJedisCluster exception", t);
        }
    }

    public void setAddressConfig(Resource addressConfig) {
        this.addressConfig = addressConfig;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setMaxRedirections(int maxRedirections) {
        this.maxRedirections = maxRedirections;
    }

    public void setAddressKeyPrefix(String addressKeyPrefix) {
        this.addressKeyPrefix = addressKeyPrefix;
    }

    public void setPoolConfig(JedisPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }


}
