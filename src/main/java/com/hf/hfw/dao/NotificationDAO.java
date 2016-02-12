
package com.hf.hfw.dao;

import com.hf.hfw.notifications.Notification;
import java.util.List;

/**
 *
 * @author pldorrell
 */
public interface NotificationDAO {

    public List<Notification> getNotifications();
    public List<Notification> getNotificationByStatus(short status);

    public Notification getById(String id);

    public Notification createNotification(Notification notification);

    public void deleteNotification(Notification notification);

    public Notification saveNotification(Notification notification);
}
