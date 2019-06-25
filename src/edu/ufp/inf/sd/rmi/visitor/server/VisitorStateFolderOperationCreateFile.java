package edu.ufp.inf.sd.rmi.visitor.server;

import java.io.Serializable;

public class VisitorStateFolderOperationCreateFile implements Serializable, VisitorStateFolderOperationsI {
    private String fileName;

    public VisitorStateFolderOperationCreateFile(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Object visitConcreteElementStateBooks(ElementStateRI e) {
        StateFolderOperationsBooks books = ((ConcreteElementStateBooksImpl)e).getStateFolderOperationsBooks();
        books.createFile(fileName);
        //books.deleteFile(fileName);
        return null;
    }
}
