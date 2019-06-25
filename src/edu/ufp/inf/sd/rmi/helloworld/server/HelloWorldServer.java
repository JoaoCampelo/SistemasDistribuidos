package edu.ufp.inf.sd.rmi.helloworld.server;

import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * Title: Projecto SD</p>
 * <p>
 * Description: Projecto apoio aulas SD</p>
 * <p>
 * Copyright: Copyright (c) 2017</p>
 * <p>
 * Company: UFP </p>
 *
 * @author Rui S. Moreira
 * @version 3.0
 */
public class HelloWorldServer {

    /**
     * Context for running a RMI Servant on a host
     */
    private SetupContextRMI contextRMI;
    /**
     * Remote interface that will hold reference to the Servant impl
     */
    private HelloWorldRI helloWorldRI;

    public static void main(String[] args) {
        if (args != null && args.length < 3) {
            System.err.println("usage: java [options] edu.ufp.sd.helloworld.server.HelloWorldServer <rmi_registry_ip> <rmi_registry_port> <service_name>");
            System.exit(-1);
        } else {
            //1. ============ Create Servant ============
            HelloWorldServer hws = new HelloWorldServer(args);
            //2. ============ Rebind servant on rmiregistry ============
            hws.rebindService();
        }
    }

    /**
     * 
     * @param args 
     */
    public HelloWorldServer(String args[]) {
        try {
            //============ List and Set args ============
            printArgs(args);
            String registryIP = args[0];
            String registryPort = args[1];
            String serviceName = args[2];
            //============ Create a context for RMI setup ============
            contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    private void rebindService() {
        try {
            //Get proxy to rmiregistry
            Registry registry = contextRMI.getRegistry();
            //Bind service on rmiregistry and wait for calls
            if (registry != null) {
                //============ Create Servant ============
                helloWorldRI= new HelloWorldImpl();

                //Get service url (including servicename)
                String serviceUrl = contextRMI.getServicesUrl(0);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going to rebind service @ {0}", serviceUrl);

                //============ Rebind servant ============
                //Naming.bind(serviceUrl, helloWorldRI);
                registry.rebind(serviceUrl, helloWorldRI);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "service bound and running. :)");
            } else {
                //System.out.println("HelloWorldServer - Constructor(): create registry on port 1099");
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
                //registry = LocateRegistry.createRegistry(1099);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void printArgs(String args[]) {
        for (int i = 0; args != null && i < args.length; i++) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "args[{0}] = {1}", new Object[]{i, args[i]});
        }
    }
}
