package edu.ufp.inf.sd.rmi.helloworld2.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote {
    String sayHello() throws RemoteException;
}