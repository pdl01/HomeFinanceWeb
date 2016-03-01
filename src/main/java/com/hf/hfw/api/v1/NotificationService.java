
package com.hf.hfw.api.v1;

import com.hf.hfw.notifications.Notification;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author pldorrell
 */
public interface NotificationService {
    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/getAll")    
    public List<Notification> getAllNotifications();

    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/getAll/{start}/{number}")    
    public List<Notification> getAllNotifications(@PathParam("status") short status,@PathParam("number") String number,@PathParam("start") String start);

    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/getAll/{accountId}/{start}/{number}")    
    public List<Notification> getAllNotificationsForAccount(@PathParam("accoundId") String accountId,@PathParam("number") String number,@PathParam("start") String start);
    
    
    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/getByStatus/{status}/{start}/{number}")    
    public List<Notification> getNotificationsByStatus(@PathParam("status") short status,@PathParam("number") String number,@PathParam("start") String start);

    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/getByStatus/{status}/{start}")    
    public List<Notification> getNotificationsByStatus(@PathParam("status") short status,@PathParam("start") String start);
    
    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/getByStatus/{status}")    
    public List<Notification> getNotificationsByStatus(@PathParam("status") short status);

    @GET
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/getCountByStatus/{status}")    
    public int getNotificationCountByStatus(@PathParam("status") short status);

    
    @POST
    @Produces({"application/json"})
    @Consumes({"application/json"})
    @Path("/markRead/{notificationId}")    
    public void markRead(@PathParam("notificationId") String notificationId);
    

    
}
