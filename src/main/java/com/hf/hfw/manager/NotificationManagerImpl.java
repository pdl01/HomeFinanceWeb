package com.hf.hfw.manager;

import com.hf.hfw.dao.NotificationDAO;
import com.hf.hfw.notifications.Notification;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author pldorrell
 */
public class NotificationManagerImpl implements NotificationManager {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
    //protected Map<String, Notification> notifications;

    private NotificationDAO notificationDAO;
    
    public void setNotificationDAO(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }
    
    @Override
    public List<Notification> getNotifications() {
        return this.notificationDAO.getNotifications();
        /*
        ArrayList<Notification> localNotifications = new ArrayList<>();
        if (this.notifications == null || this.notifications.isEmpty()) {
            return localNotifications;
        }
        for (String key : this.notifications.keySet()) {
            localNotifications.add(this.notifications.get(key));
        }
        return localNotifications;
        */
    }

    @Override
    public Notification getById(String id) {
        return this.notificationDAO.getById(id);
        //return this.notifications.get(id);

    }

    @Override
    public Notification createNotification(Notification _notification) {
        Date createdDate = new Date();
        _notification.setCreatedOn(createdDate);
        _notification.setCreatedOnAsString(sdf.format(createdDate));
        _notification.setId(UUID.randomUUID().toString());
        //this.addNotificationToSystem(_notification);
        Notification notification = this.notificationDAO.saveNotification(_notification);
        return notification;
    }

    @Override
    public void deleteNotification(Notification notification) {
        /*
        if (this.notifications == null || this.notifications.isEmpty()) {
            return;
        }
        this.notifications.remove(notification.getId());
        */
        this.notificationDAO.deleteNotification(notification);
    }

    @Override
    public Notification saveNotification(Notification _notification) {
        Notification notification = this.notificationDAO.saveNotification(_notification);
        /*
        this.addNotificationToSystem(_notification);
        */
        return notification;
    }

    @Override
    public List<Notification> getNotificationByStatus(short status) {
        return this.notificationDAO.getNotificationByStatus(status);
        /*
        ArrayList<Notification> localNotifications = new ArrayList<>();
        if (this.notifications == null || this.notifications.isEmpty()) {
            return localNotifications;
        }
        for (String key : this.notifications.keySet()) {
            Notification notification = this.getById(key);
            if (notification != null && notification.getStatus() == status) {
                localNotifications.add(this.notifications.get(key));

            }
        }
        return localNotifications;
        */
    }

    @Override
    public Notification createNotification(String subject, String message) {
        Notification notification = new Notification();
        notification.setStatus(Notification.UNREAD);
        notification.setSubject(subject);
        notification.setMessage(message);
        return this.createNotification(notification);
    }

    @Override
    public Notification addNotificationToSystem(Notification _notification) {
        return null;
        /*
        if (this.notifications == null) {
            this.notifications = new HashMap<>();
        }
        this.notifications.put(_notification.getId(), _notification);
        return _notification;
        */
    }
}
