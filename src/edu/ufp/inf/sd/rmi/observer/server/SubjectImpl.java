package edu.ufp.inf.sd.rmi.observer.server;

import edu.ufp.inf.sd.rmi.observer.client.ObserverRI;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


public class SubjectImpl extends UnicastRemoteObject implements SubjectRI {

    private State subjectState = new State("Teste", "1");
    private ArrayList<ObserverRI> observers = new ArrayList<>();

    public SubjectImpl() throws RemoteException {
        super(); 
    }

    @Override
    public void atach(ObserverRI obsRI) throws RemoteException {
        observers.add(obsRI);
    }

    @Override
    public void detach(ObserverRI obsRI) throws RemoteException {
        observers.remove(obsRI);
    }

    @Override
    public State getState() throws RemoteException {
        return this.subjectState;
    }

    @Override
    public void setState(State state) throws RemoteException {
        this.subjectState = state;
        for(ObserverRI obs : observers){
            obs.update();
        }
    }
}