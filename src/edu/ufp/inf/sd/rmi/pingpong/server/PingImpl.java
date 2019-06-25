package edu.ufp.inf.sd.rmi.pingpong.server;

import edu.ufp.inf.sd.rmi.pingpong.client.PongRI;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PingImpl extends UnicastRemoteObject implements PingRI {

    public PingImpl() throws RemoteException {
        super();
    }

    @Override
    public void ping(PongRI clientPongRI, Ball b) throws RemoteException {
        PingThread t = new PingThread(clientPongRI, b);
        t.start();
    }
}

class PingThread extends Thread {

    PongRI pongRI = null;
    Ball ball = null;

    public PingThread(PongRI pongRI, Ball b) {
        super();
        this.pongRI = pongRI;
        this.ball = ball;
    }

    public void run() {
        Random r = new Random();

        Random generactor = new Random();
        int playErr = Math.abs(generactor.nextInt(99) + 1);
        if (playErr >= 0) {

        } else {

        }
        try {
            pongRI.pong(ball);
        } catch (RemoteException ex) {
            Logger.getLogger(PingThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
