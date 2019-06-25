package edu.ufp.inf.sd.rmi.pingpong.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PingServer {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        PingRI serverPlayerRI = (PingRI) new PingImpl();
        String serviceAddress = "rmi//localhost/PingpongServer";

        try {
            Naming.rebind(serviceAddress, serverPlayerRI);
        } catch (MalformedURLException ex) {
            Logger.getLogger(PingServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
