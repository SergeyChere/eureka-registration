package config;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;

public class EurekaConfiguration {

    private static ApplicationInfoManager applicationInfoManager;
    private static EurekaClient eurekaClient;

    private static synchronized ApplicationInfoManager initializeApplicationInfoManager(EurekaInstanceConfig instanceConfig) {
        if (applicationInfoManager == null) {
            InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(
                    instanceConfig).get();
            applicationInfoManager = new ApplicationInfoManager(instanceConfig,
                    instanceInfo);
        }
        return applicationInfoManager;
    }

    private static synchronized EurekaClient initializeEurekaClient(ApplicationInfoManager applicationInfoManager,
                                                                    EurekaClientConfig clientConfig) {
        if (eurekaClient == null) {
            eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
        }
        return eurekaClient;
    }

    public static EurekaClient getEurekaClient() {
        ApplicationInfoManager applicationInfoManager = initializeApplicationInfoManager(
                new MyDataCenterInstanceConfig());
        initializeEurekaClient(applicationInfoManager, new DefaultEurekaClientConfig());
        return eurekaClient;
    }
}
