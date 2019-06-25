package edu.ufp.inf.sd.rmi.digLib.server;

/**
 *
 * @author rmoreira
 */
import java.io.Serializable;

public class Book implements Serializable {

    private String author = "";
    private String title = "";

    public Book(String t, String a) {
        author = a;
        title = t;
    }

    @Override
    public String toString() {
        return "Book{" + "author=" + getAuthor() + ", title=" + getTitle() + '}';
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
