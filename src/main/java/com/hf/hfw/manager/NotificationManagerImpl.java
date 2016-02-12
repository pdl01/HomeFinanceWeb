package com.hf.hfw.manager;

import com.hf.hfw.dao.NotificationDAO;
import com.hf.hfw.notifications.Notification;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author pldorrell
 */
public class NotificationManagerImpl implements NotificationManager {
    
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
    private NotificationDAO notificationDAO;

    public void setNotificationDAO(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

    @Override
    public List<Notification> getNotifications() {
        return this.notificationDAO.getNotifications();
    }

    @Override
    public Notification getById(String id) {
        return this.notificationDAO.getById(id);
    }

    @Override
    public Notification createNotification(Notification _notification) {
        Date createdDate = new Date();
        _notification.setCreatedOn(createdDate);
        _notification.setCreatedOnAsString(sdf.format(createdDate));
        Notification notification = this.notificationDAO.saveNotification(_notification);
        return notification;
    }

    @Override
    public void deleteNotification(Notification notification) {
        this.notificationDAO.deleteNotification(notification);
    }

    @Override
    public Notification saveNotification(Notification _notification) {
        Notification notification = this.notificationDAO.saveNotification(_notification);
        return notification;
    }

    @Override
    public List<Notification> getNotificationByStatus(short status) {
        return this.notificationDAO.getNotificationByStatus(status);
    }

    @Override
    public Notification createNotification(String subject, String message) {
        Notification notification = new Notification();
        notification.setStatus(Notification.UNREAD);
        notification.setSubject(subject);
        notification.setMessage(message);
        return this.createNotification(notification);
    }

}
