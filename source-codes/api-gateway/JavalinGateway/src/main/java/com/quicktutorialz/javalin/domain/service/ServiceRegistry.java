package com.quicktutorialz.javalin.domain.service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
    This class expect the services to be registered as environment variables using the following notation:
        SERVICE_NAME_1 : recommendation-service
        SERVICE_HOST_1 : http://<recommendation-service-host:port>
        SERVICE_NAME_2 : post-service
        SERVICE_HOST_2 : http://<post-service-host:port>
        SERVICE_NAME_3 : review-service
        SERVICE_HOST_3 : http://<review-service-host:port>
        ...

 */
public class ServiceRegistry {

    private final static String SERVICE_NAME = "SERVICE_NAME";
    private final static String SERVICE_HOST = "SERVICE_HOST";

    public static Map<String, String> serviceMapping = new HashMap<>();

    static {
       Map<String, String> servicesEnvMap = System.getenv()
                                                  .entrySet()
                                                  .stream()
                                                  .filter(e -> e.getKey().startsWith(SERVICE_NAME) || e.getKey().startsWith(SERVICE_HOST))
                                                  .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));

       int servicesNumber = servicesEnvMap.size()/2;

       for(int i=1; i<=servicesNumber; i++) {
           serviceMapping.put(servicesEnvMap.get(SERVICE_NAME.concat("_"+i)), servicesEnvMap.get(SERVICE_HOST.concat("_"+i)));
       }

     //   serviceMapping.put("javalin-api", "http://localhost:7000");
     //   serviceMapping.put("another-service", "http://service.host.address");
    }

}
