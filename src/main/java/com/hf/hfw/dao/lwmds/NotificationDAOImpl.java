/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.dao.lwmds;

import com.hf.hfw.application.ApplicationState;
import com.hf.hfw.dao.NotificationDAO;
import com.hf.hfw.dao.lwmds.converter.NotificationConverter;
import com.hf.hfw.notifications.Notification;
import com.hf.lwdatastore.CollectionObject;
import com.hf.lwdatastore.DataStore;
import com.hf.lwdatastore.exception.CollectionNotFoundException;
import com.hf.lwdatastore.exception.IndexNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author pldorrell
 */
public class NotificationDAOImpl implements NotificationDAO {
    private static final Logger log = Logger.getLogger(AccountDAOImpl.class);

    private DataStore getDataStore() {
        return ApplicationState.getApplicationState().getDbFactory().getLwDataStore();
    }
    @Override
    public List<Notification> getNotifications() {
        ArrayList<Notification> al = new ArrayList<Notification>();
        try {
            List<CollectionObject> cObjects = getDataStore().getObjects("notifications");
            if (cObjects != null && cObjects.size() > 0) {
                NotificationConverter aConverter = new NotificationConverter();
                for (CollectionObject collectionObject: cObjects) {
                    al.add(aConverter.convertFromCollectionObject(collectionObject));
                }
            }
            return al;
        }catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
        return al;
    }

    @Override
    public List<Notification> getNotificationByStatus(short status) {
        ArrayList<Notification> al = new ArrayList<Notification>();
        try {
            List<CollectionObject> cObjects = getDataStore().getByIndex("notifications", "status", status+"");
            if (cObjects != null && cObjects.size() > 0) {
                NotificationConverter aConverter = new NotificationConverter();
                for (CollectionObject collectionObject: cObjects) {
                    al.add(aConverter.convertFromCollectionObject(collectionObject));
                }
                return al;
            }
        }catch (IndexNotFoundException ex) {
            log.error("Index Not Found",ex);
        }catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
        return al;
    }

    @Override
    public Notification getById(String _id) {
        try {
            CollectionObject cObject = getDataStore().getObject("notifications", _id);
            if (cObject != null) {
                return (new NotificationConverter()).convertFromCollectionObject(cObject);
            }
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
        return null;    
    }

    @Override
    public Notification createNotification(Notification notification) {
        return this.saveNotification(notification);
    }

    @Override
    public void deleteNotification(Notification notification) {
        try {
            getDataStore().removeObject("notifications", notification.getId());
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }    
    }

    @Override
    public Notification saveNotification(Notification notification) {
        try {
            getDataStore().putObject("notifications", new CollectionObject(notification), new NotificationConverter());
        } catch (CollectionNotFoundException ex) {
            log.error("Collection Not Found",ex);
        }
        return notification;
    }
    
}
