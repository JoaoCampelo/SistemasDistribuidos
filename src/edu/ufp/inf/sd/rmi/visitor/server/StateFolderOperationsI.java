package edu.ufp.inf.sd.rmi.visitor.server;

import java.io.File;

/**
 * <p>Title: Projecto SD</p>
 * <p>Description: Projecto apoio aulas SD</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: UFP </p>
 * @author Rui Moreira
 * @version 1.0
 */
public interface StateFolderOperationsI {
    public File createFile(String fn);
    public File deleteFile(String fn);
}
