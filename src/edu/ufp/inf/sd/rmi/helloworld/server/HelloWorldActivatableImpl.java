package edu.ufp.inf.sd.rmi.helloworld.server;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.rmi.*;
import java.rmi.activation.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * Title: Projecto SD</p>
 * <p>
 * Description: Projecto apoio aulas SD</p>
 * <p>
 * Copyright: Copyright (c) 2009</p>
 * <p>
 * Company: UFP </p>
 *
 * @author Rui Moreira
 * @version 1.0
 */
public class HelloWorldActivatableImpl extends Activatable implements HelloWorldRI {

    private RandomAccessFile raf;
    private int count;
    private final Object countLock = new Object();

    /**
     *
     * @param id - identifier for the activatable remote object (given by rmid
     * upon object registration); (the activation ID is also contained in the
     * remote object's stub).
     * @param persistentData - MarshalledObject that contains initialization
     * data pre-registered with rmid (e.g. may be a filename containing data/state
     * persisted by the activatable object).
     * @throws RemoteException
     */
    public HelloWorldActivatableImpl(ActivationID id, MarshalledObject persistentData) throws RemoteException {
        // Register object with  activation system and export it on an anonymous port
        super(id, 0);
        if (persistentData != null) {
            try {
                String filename = (String) persistentData.get();
                System.err.println("HelloWorldActivatableImpl(): filename = " + filename);
                synchronized (countLock) {
                    count = ressurectData(filename);
                }
                System.err.println("HelloWorldActivatableImpl(): count upon activation = " + count);
            } catch (IOException ex) {
                Logger.getLogger(HelloWorldActivatableImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HelloWorldActivatableImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.err.println("HelloWorldActivatableImpl(): persistentData is null!!");
        }
    }

    private int ressurectData(String filename) throws IOException {
        System.err.println("HelloWorldActivatableImpl - ressurectData(): filename = " + filename);
        if (filename != null && !filename.equals("")) {
            File file = new File(filename);
            System.err.println("HelloWorldActivatableImpl(): file.exists() = " + file.exists());
            boolean fileExists = file.exists();
            raf = new RandomAccessFile(file, "rws");
            return (fileExists ? raf.readInt() : persistData(0));

        } else {
            throw new IOException("invalid filename");
        }
    }

    private int persistData(int value) throws IOException {
        raf.setLength(0);
        raf.writeInt(value);
        return value;
    }

    public void print(String msg) throws RemoteException {
        try {
            System.out.println("HelloWorldActivatable - print(): someone called "+ ++count +"-th times with msg = " + msg);
            persistData(count);
        } catch (IOException ex) {
            Logger.getLogger(HelloWorldActivatableImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
