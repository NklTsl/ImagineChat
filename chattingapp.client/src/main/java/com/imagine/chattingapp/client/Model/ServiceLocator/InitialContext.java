
package com.imagine.chattingapp.client.Model.ServiceLocator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
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
        FileInputStream fileInStream = null;
        try{
            fileInStream = new FileInputStream("server.properties");
            Properties currentConnectionProperties = new Properties();
            currentConnectionProperties.load(fileInStream);
            
            Registry registry;
            registry = LocateRegistry.getRegistry(currentConnectionProperties.getProperty("IP"), Integer.parseInt(currentConnectionProperties.getProperty("PORT")));
            requestedRemoteService = registry.lookup(remoteService);
        
        } catch (RemoteException ex) {
            Logger.getLogger(InitialContext.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(InitialContext.class.getName()).log(Level.SEVERE, null, ex);
   
    }   catch (FileNotFoundException ex) {
            Logger.getLogger(InitialContext.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InitialContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    return requestedRemoteService;
    }
}
