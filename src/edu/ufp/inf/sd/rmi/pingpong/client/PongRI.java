/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufp.inf.sd.rmi.pingpong.client;

import edu.ufp.inf.sd.rmi.pingpong.server.Ball;
import java.rmi.RemoteException;

/**
 *
 * @author joaoc
 */
public interface PongRI {
    public void pong(Ball b) throws RemoteException;
}
