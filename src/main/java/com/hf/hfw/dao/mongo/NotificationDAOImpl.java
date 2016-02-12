package com.hf.hfw.dao.mongo;

import com.hf.hfw.dao.NotificationDAO;
import com.hf.hfw.notifications.Notification;
import com.hf.homefinanceshared.DailyBalance;
import java.util.List;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 *
 * @author pldorrell
 */
public class NotificationDAOImpl extends AbstractMongoDAO implements NotificationDAO {

    @Override
    public List<Notification> getNotifications() {
        return this.getMongoTemplate().findAll(Notification.class);
    }

    @Override
    public Notification getById(String _id) {
      Query searchNotificationQuery = new Query(Criteria.where("id").is(_id));
        return this.getMongoTemplate().findOne(searchNotificationQuery, Notification.class);
  
    }

    @Override
    public Notification createNotification(Notification notification) {
        this.getMongoTemplate().save(notification);
        return notification;

    }

    @Override
    public void deleteNotification(Notification notification) {
        this.getMongoTemplate().remove(notification);

    }

    @Override
    public Notification saveNotification(Notification notification) {
        this.getMongoTemplate().save(notification);
        return notification;

    }

    @Override
    public List<Notification> getNotificationByStatus(short status) {
        Query searchNotificationsQuery = new Query(Criteria.where("status").is(status));

        return this.getMongoTemplate().find(searchNotificationsQuery, Notification.class);  
    }
    
}
