package com.hf.hfw.dao.lwmds.converter;

import com.hf.homefinanceshared.RegisterTransaction;
import com.hf.homefinanceshared.ScheduledTransaction;
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
public class ScheduledTransactionConverter implements CollectionObjectConverter<ScheduledTransaction> {

    private static final Logger log = LogManager.getLogger(ScheduledTransactionConverter.class);

    @Override
    public CollectionObject convertToCollectionObject(ScheduledTransaction k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ScheduledTransaction convertFromCollectionObject(CollectionObject object) {
        return (ScheduledTransaction) object.getTarget();
    }

    @Override
    public String getValue(ScheduledTransaction k, String attribute) throws AttributeNotFoundException {
        if (attribute.equalsIgnoreCase("id")) {
            return k.getId();
        } else if (attribute.equalsIgnoreCase("accountId")) {
            return k.getPrimaryAccount();
        } else if (attribute.equalsIgnoreCase("year")) {
            //return k.getTxnDate().substring(0,4);
            if (k.isOriginal()) {
                return k.getBeginDate().substring(0, 4);
            } else {
                return k.getScheduledDate().substring(0, 4);
            }

        } else if (attribute.equalsIgnoreCase("month")) {
            if (k.isOriginal()) {
                return k.getBeginDate().substring(0, 7);
            } else {
                return k.getScheduledDate().substring(0, 7);
            }
            //return k.getScheduledDate().substring(0, 7);
            //return k.getTxnDate().substring(0,7);
        } else if (attribute.equalsIgnoreCase("originalTransactionId")) {
            return k.getOriginalTransactionId();
        } else if (attribute.equalsIgnoreCase("original")) {
            if (k.isOriginal()) {
                return "true";
            } else {
                return "false";
            }
        } else {
            throw new AttributeNotFoundException();
        }

    }

    @Override
    public ScheduledTransaction convertFromJSONNode(Map.Entry<String, JsonNode> jsonNode) {
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        //JsonNode jsonNodex = mapper.valueToTree(jsonNode);
        try {
            ScheduledTransaction txn = mapper.treeToValue(jsonNode.getValue(), ScheduledTransaction.class);
            return txn;
        } catch (IOException ex) {
            log.error(ex);

        }

        return null;

    }

    @Override
    public CollectionObject setId(CollectionObject collectionObject, String _id) {
        ((ScheduledTransaction) collectionObject.getTarget()).setId(_id);
        return collectionObject;
    }
}
