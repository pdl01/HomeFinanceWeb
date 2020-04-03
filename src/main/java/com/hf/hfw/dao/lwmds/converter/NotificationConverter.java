/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.dao.lwmds.converter;

import com.hf.hfw.notifications.Notification;
import com.hf.homefinanceshared.Account;
import com.hf.lwdatastore.CollectionObject;
import com.hf.lwdatastore.CollectionObjectConverter;
import com.hf.lwdatastore.exception.AttributeNotFoundException;
import java.io.IOException;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author pldorrell
 */
public class NotificationConverter implements CollectionObjectConverter<Notification>{
    private static final Logger log = LogManager.getLogger(NotificationConverter.class);

    @Override
    public CollectionObject convertToCollectionObject(Notification k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Notification convertFromCollectionObject(CollectionObject object) {
        return (Notification)object.getTarget();
    }

    @Override
    public String getValue(Notification k, String attribute) throws AttributeNotFoundException {
            if (attribute.equalsIgnoreCase("id")) {
                return k.getId();
            } else if (attribute.equalsIgnoreCase("status")) {
                return k.getStatus()+"";
            } else {
                throw new AttributeNotFoundException();
            }
    }

    @Override
    public Notification convertFromJSONNode(Map.Entry<String, JsonNode> jsonNode) {
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        //JsonNode jsonNodex = mapper.valueToTree(jsonNode);
        try {
            Notification notification = mapper.treeToValue(jsonNode.getValue(), Notification.class);
            return notification;
        } catch (IOException ex) {
            log.error(ex);
            
        }

        return null;

    }

    @Override
    public CollectionObject setId(CollectionObject collectionObject, String _id) {
        ((Notification)collectionObject.getTarget()).setId(_id);
        return collectionObject;    }
    
}
