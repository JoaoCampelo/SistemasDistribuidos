package edu.ufp.inf.sd.rmi.digLib.server;

import edu.ufp.inf.sd.rmi.util.threading.ThreadPool;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class DigLibFactoryImpl extends UnicastRemoteObject implements DigLibFactoryRI {

    protected DBMockup db;
    private ThreadPool tp;
    private HashMap<String, DigLibSessionRI> sessions;

    public DigLibFactoryImpl() throws RemoteException {
        this.db = new DBMockup();
        this.tp = new ThreadPool(10);
        this.sessions = new HashMap<>();
    }

    public DigLibSessionRI login(String username, String password) throws RemoteException {
        if (this.db.exists(username, password)) {
            DigLibSessionRI session = new DigLibSessionImpl(this, new User(username, password));
            this.sessions.put(username, session);
            this.tp.execute((Runnable) session);
            return session;
        }
        return null;
    }

    protected void removeSession(String uname, DigLibSessionImpl digLibSession) {
        this.sessions.remove(uname);
        this.tp.remove(digLibSession);
    }
}
