package com.newland.financial.p2p.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.taobao.diamond.client.DiamondConfigure;
import com.taobao.diamond.manager.ManagerListener;
import com.taobao.diamond.manager.impl.DefaultDiamondManager;
import com.taobao.diamond.manager.impl.PropertiesListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class SystemConfig {


    private final static String DATA_ID = "com.sfebiz.config.stock_timing_update";
    private final static String GROUP_NAME = "HAITAO";

    private final static class DynamicConfigHolder {
        private final static SystemConfig INSTANCE = new SystemConfig();

        static {
            ManagerListener managerListener = new DynamicConfigListener();
            DefaultDiamondManager defaultDiamondManager = new DefaultDiamondManager(GROUP_NAME, DATA_ID,
                    managerListener);
            DiamondConfigure configure = new DiamondConfigure();
            configure.setPollingIntervalTime(60);
            defaultDiamondManager.setDiamondConfigure(configure);
            String config = defaultDiamondManager.getConfigureInfomation(1000);
            managerListener.receiveConfigInfo(config);
        }
    }

    static class DynamicConfigListener extends PropertiesListener {

        protected final Logger logger = LoggerFactory.getLogger(this.getClass());

        @Override
        public void innerReceive(Properties properties) {

            logger.warn("DynamicConfig configuration changes happen!");
            String taskOpen = properties.getProperty("task.open", "false");
            logger.info("task.open:"+taskOpen);
        }
    }

   public static SystemConfig getInstance(){
        return DynamicConfigHolder.INSTANCE;
   }

}

