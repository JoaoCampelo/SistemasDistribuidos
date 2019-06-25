/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufp.inf.sd.rmi.observer.client;

import edu.ufp.inf.sd.rmi.observer.server.State;
import edu.ufp.inf.sd.rmi.observer.server.SubjectRI;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joaoc
 */
public class ObserverImpl implements ObserverRI {
    private String id;
    private State lastObserversState;
    private SubjectRI subjectRI;
    private ObserverGuiClient chatFrame;
    
    public ObserverImpl(String id, ObserverGuiClient j) throws RemoteException{
        this.id = id;
        this.lastObserversState = lastObserversState;
        this.chatFrame = j;
        try {
            this.subjectRI = (SubjectRI) Naming.lookup("rmi//localhost:1099/ObserverServer") ;
        } catch (NotBoundException ex) {
            Logger.getLogger(ObserverImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ObserverImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        UnicastRemoteObject.exportObject(this, 0);
        this.subjectRI.atach(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public State getLastObserversState() {
        return lastObserversState;
    }

    public void setLastObserversState(State lastObserversState) {
        this.lastObserversState = lastObserversState;
    }

    public SubjectRI getSubjectRI() {
        return subjectRI;
    }

    public void setSubjectRI(SubjectRI subjectRI) {
        this.subjectRI = subjectRI;
    }

    public ObserverGuiClient getChatFrame() {
        return chatFrame;
    }

    public void setChatFrame(ObserverGuiClient chatFrame) {
        this.chatFrame = chatFrame;
    }
    
    @Override
    public void update() throws RemoteException {
        this.lastObserversState = this.subjectRI.getState();
        this.chatFrame.updateTextArea();
    }
}
