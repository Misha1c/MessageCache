package net.gorbov.dao;

import java.util.Date;

/**
 * Created by Mihail on 13.01.2015.
 */
public class Message {

    private long id;
    private String author;
    private Date dataCreated;
    private String message;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDateCreated() {
        return dataCreated;
    }

    public void setDateCreated(Date dataCreated) {
        this.dataCreated = dataCreated;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
