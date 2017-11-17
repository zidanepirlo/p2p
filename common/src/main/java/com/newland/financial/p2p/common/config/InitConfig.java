package com.newland.financial.p2p.common.config;


import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class InitConfig implements InitializingBean,DisposableBean {

    private SystemConfig systemConfig;

    public SystemConfig getSystemConfig() {
        return systemConfig;
    }
    public void destroy() throws Exception {

    }

    public void afterPropertiesSet() throws Exception {
        systemConfig=SystemConfig.getInstance();
    }
}
