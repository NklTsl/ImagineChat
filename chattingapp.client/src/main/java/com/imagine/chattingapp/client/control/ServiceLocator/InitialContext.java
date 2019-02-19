
package com.imagine.chattingapp.client.control.ServiceLocator;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Menna Helmy
 */
public class InitialContext {
    
    public static final String localHost = "127.0.0.1";
    
    public static Remote lookup(String remoteService){
    
        Remote requestedRemoteService = null;
        
        try{
        
            Registry registry;
            registry = LocateRegistry.getRegistry("127.0.0.1");
            System.out.println(registry+"--------------------------------");
            requestedRemoteService = registry.lookup(remoteService);
        
        } catch (RemoteException ex) {
            Logger.getLogger(InitialContext.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(InitialContext.class.getName()).log(Level.SEVERE, null, ex);
        
    
    }
    return requestedRemoteService;
    }
}
