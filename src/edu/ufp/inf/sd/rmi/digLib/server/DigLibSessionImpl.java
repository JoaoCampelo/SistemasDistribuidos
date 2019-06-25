package edu.ufp.inf.sd.rmi.digLib.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DigLibSessionImpl implements DigLibSessionRI, Runnable {

    protected DigLibFactoryImpl factory;
    private User user;
    private boolean keepRunning = false;

    public DigLibSessionImpl(DigLibFactoryImpl f, User u) throws RemoteException {
        this.factory = f;
        this.user = u;
        this.keepRunning = true;
        this.exportObject();
    }

    private void exportObject() throws RemoteException {
        UnicastRemoteObject.exportObject((Remote) this, 0);
    }

    public Book[] search(String author, String title) throws RemoteException {
        return factory.db.select(author, title);
    }

    public void logout() throws RemoteException {
        this.keepRunning = false;
        this.factory.removeSession(this.user.getUname(), this);
    }

    public void run() {
        while (this.keepRunning) {
            Thread t = Thread.currentThread();
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, t.getName());
            try {
                t.sleep(2000);
            } catch (InterruptedException e) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
