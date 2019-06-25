package edu.ufp.inf.sd.rmi.visitor.server;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>Title: Projecto SD</p>
 * <p>Description: Projecto apoio aulas SD</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: UFP </p>
 * @author Rui S. Moreira
 * @version 3.0
 */
public class ConcreteElementStateBooksImpl extends UnicastRemoteObject implements ElementStateRI {

    private StateFolderOperationsBooks stateFolderOperationsBooks;

    public ConcreteElementStateBooksImpl() throws RemoteException {
        super();
        stateFolderOperationsBooks = new StateFolderOperationsBooks(new File("/Users/joaoc/Documents/NetBeansProjects/SD/src/edu/ufp/inf/sd/rmi/visitor/books/"));
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, " - constructor()");
    }

    public StateFolderOperationsBooks getStateFolderOperationsBooks() {
        return stateFolderOperationsBooks;
    }

    @Override
    public Object acceptVisitor(VisitorStateFolderOperationsI v) {
        return v.visitConcreteElementStateBooks(this);
    }
}
