package edu.ufp.inf.sd.rmi.visitor.server;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StateFolderOperationsBooks implements StateFolderOperationsI {

    private File booksFolder;

    public StateFolderOperationsBooks(File booksFolder) {
        this.booksFolder = booksFolder;
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, " - constructor(@ {0})", this.booksFolder);
    }

    @Override
    public File createFile(String fn) {
        String path = new StringBuilder()
                .append(this.booksFolder)
                .append('/')
                .append(fn)
                .toString();
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path), "utf-8"))) {
            writer.write("Coiso");
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, " - createFile()");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public File deleteFile(String fn) {
        String path = new StringBuilder().append(this.booksFolder).append('/').append(fn).toString();
        File f = new File(path);
        if (f.delete()) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Delete file @ {0}", f.getName());
        } else {
            System.out.println("Delete operation is failed.");
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Delete operation is failed.");
        }
        return f;
    }
}
