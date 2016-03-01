/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.api.v1;

import com.hf.hfw.manager.NotificationManager;
import com.hf.hfw.notifications.Notification;
import java.util.List;
import javax.ws.rs.Path;

/**
 *
 * @author pldorrell
 */
@Path("/notifications")
public class NotificationServiceImpl implements NotificationService {

    private NotificationManager notificationManager;

    public void setNotificationManager(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }
    
    @Override
    public List<Notification> getNotificationsByStatus(short status, String number, String start) {
        return this.notificationManager.getNotificationByStatus(status);
    }

    @Override
    public List<Notification> getNotificationsByStatus(short status, String start) {
        return this.getNotificationsByStatus(status, "20", start);
    }

    @Override
    public List<Notification> getNotificationsByStatus(short status) {
        return this.getNotificationsByStatus(status,"1");
    }

    @Override
    public void markRead(String notificationId) {
        Notification notification = this.notificationManager.getById(notificationId);
        if (notification != null) {
            notification.setStatus(Notification.READ);
            this.notificationManager.saveNotification(notification);
        }
    }

    @Override
    public int getNotificationCountByStatus(short status) {
        List<Notification> notifications = this.notificationManager.getNotificationByStatus(status);
        return notifications.size();
    }

    @Override
    public List<Notification> getAllNotifications() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Notification> getAllNotifications(short status, String number, String start) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Notification> getAllNotificationsForAccount(String accountId, String number, String start) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

