package edu.ufp.inf.sd.rmi.observer.server;

import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ObserverServer {

    private SetupContextRMI contextRMI;

    private SubjectRI myRI;

    public static void main(String[] args) {
        if (args != null && args.length < 3) {
            System.exit(-1);
        } else {
            ObserverServer srv = new ObserverServer(args);
            srv.rebindService();
        }
    }

    public ObserverServer(String args[]) {
        try {
            String registryIP = args[0];
            String registryPort = args[1];
            String serviceName = args[2];
            contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    private void rebindService() {

        try {
            Registry registry = contextRMI.getRegistry();
            if (registry != null) {
                myRI = new SubjectImpl();

                //Get service url (including servicename)
                String serviceUrl = contextRMI.getServicesUrl(0);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going to rebind service @ {0}", serviceUrl);

                //============ Rebind servant ============
                registry.rebind("rmi//localhost:1099/ObserverServer", myRI);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "service bound and running. :)");
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
            }
        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
}
