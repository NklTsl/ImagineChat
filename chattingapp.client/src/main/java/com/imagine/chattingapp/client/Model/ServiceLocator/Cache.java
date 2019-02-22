
package com.imagine.chattingapp.client.control.ServiceLocator;

import java.rmi.Remote;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Menna Helmy
 */
public class Cache {
    
    private Map<String,Remote> services;
    String requestedService = null;
    
    public Cache(){
    
        services = new HashMap<>();
    }
    
    public Remote getService(String serviceName){
    
        requestedService= serviceName;
        for(Map.Entry<String,Remote> entry: services.entrySet()){
        
            if(serviceName.equalsIgnoreCase(entry.getKey())){
            
                return entry.getValue();
            }
        }
        return null;
    
    }
    
    public void addService(Remote newService){
    
        boolean exists = false;
        
        if(services.containsValue(newService)){
            exists = true;
        }
        
        if(!exists){
        
            services.put(requestedService, newService);
        }
    
    }
    
}
