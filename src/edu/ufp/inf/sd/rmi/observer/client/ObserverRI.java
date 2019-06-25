/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufp.inf.sd.rmi.observer.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author joaoc
 */
public interface ObserverRI extends Remote {
    public void update() throws RemoteException;
}
