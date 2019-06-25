package edu.ufp.inf.sd.rmi.digLib.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DigLibSessionRI extends Remote {

    public Book[] search(String a, String b) throws RemoteException;

    public void logout() throws RemoteException;
}
