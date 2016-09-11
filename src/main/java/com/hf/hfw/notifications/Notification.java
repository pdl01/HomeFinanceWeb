package com.hf.hfw.notifications;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pldorrell
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Notification implements Comparable<Notification>{
    public static final short READ = 1;
    public static final short UNREAD = 0;
    protected String id;
    protected String subject;
    protected String externalId;
    protected String message;
    protected short status;
    protected Date createdOn;
    protected String createdOnAsString;

    public String getCreatedOnAsString() {
        return createdOnAsString;
    }

    public void setCreatedOnAsString(String createdOnAsString) {
        this.createdOnAsString = createdOnAsString;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
           
    @Override
    public int compareTo(Notification o) {
        return this.subject.compareTo(o.getSubject());                
    }

}
