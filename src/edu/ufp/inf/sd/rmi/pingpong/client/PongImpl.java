/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufp.inf.sd.rmi.pingpong.client;

import edu.ufp.inf.sd.rmi.pingpong.server.Ball;
import edu.ufp.inf.sd.rmi.pingpong.server.PingRI;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

/**
 *
 * @author joaoc
 */
public class PongImpl extends UnicastRemoteObject implements PongRI {

    Ball ball;
    PingRI pingRI;

    public PongImpl(int PlayerId, int rmiRegisteryPort, String rmiRegisteryIp) throws RemoteException {
        String serviceAddress = "rmi//" + rmiRegisteryPort + ":" + rmiRegisteryIp + "/PingpongServer";
        try {
            pingRI = (PingRI) Naming.lookup(serviceAddress);
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
        }
        ball = new Ball(PlayerId);
        this.startPlaying();
    }

    private void startPlaying() throws RemoteException {
        Remote exportObject = java.rmi.server.UnicastRemoteObject.exportObject(this, 0);
        pingRI.ping((PongRI) this, ball);
    }

    @Override
    public void pong(Ball b) throws RemoteException {
        Random generactor = new Random();
        int playErr = Math.abs(generactor.nextInt(99) + 1);
        if (playErr >= 0) {

        } else {

        }
        pingRI.ping((PongRI) this, b);
    }

}
