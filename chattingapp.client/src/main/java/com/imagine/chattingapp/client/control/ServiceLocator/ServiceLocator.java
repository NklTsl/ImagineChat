
package com.imagine.chattingapp.client.control.ServiceLocator;

import java.rmi.Remote;

/**
 *
 * @author Menna Helmy
 */
public class ServiceLocator {
    
    private static Cache cache = new Cache();
    
    public static Remote getService(String chosenService){
    
        Remote service = cache.getService(chosenService);
        if(service !=null){
        
            return service;
        }
        
        InitialContext initialContext = new InitialContext();
        Remote serviceRequired = initialContext.lookup(chosenService);
        cache.addService(serviceRequired);
        System.out.println("service Required is "+serviceRequired);
        return serviceRequired;
        
    }
    
}
