/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.manager;

import com.hf.hfw.notifications.Notification;
import java.util.List;

/**
 *
 * @author pldorrell
 */
public interface NotificationManager {
    
    public List<Notification> getNotifications();
    public List<Notification> getNotificationByStatus(short status);
    public Notification getById(String id);

    public Notification createNotification(Notification notification);

    public void deleteNotification(Notification notification);

    public Notification saveNotification(Notification notification);
    public Notification createNotification(String subject,String message);
}
