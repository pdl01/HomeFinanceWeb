package com.hf.hfw.dao.lwmds.converter;

import com.hf.homefinanceshared.OnlineTransaction;
import com.hf.homefinanceshared.RegisterTransaction;
import com.hf.lwdatastore.CollectionObject;
import com.hf.lwdatastore.CollectionObjectConverter;
import com.hf.lwdatastore.exception.AttributeNotFoundException;
import java.io.IOException;
import java.util.Map;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author pldorrell
 */
public class OnlineTransactionConverter implements CollectionObjectConverter<OnlineTransaction>{
private static final Logger log = Logger.getLogger(OnlineTransactionConverter.class);
    @Override
    public CollectionObject convertToCollectionObject(OnlineTransaction k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OnlineTransaction convertFromCollectionObject(CollectionObject object) {
        return (OnlineTransaction)object.getTarget();
    }

    @Override
    public String getValue(OnlineTransaction k, String attribute) throws AttributeNotFoundException {
            if (attribute.equalsIgnoreCase("id")) {
                return k.getId();
            } else if (attribute.equalsIgnoreCase("accountId")) {
                return k.getPrimaryAccount();
            } else {
                throw new AttributeNotFoundException();
            }
    }

    @Override
    public OnlineTransaction convertFromJSONNode(Map.Entry<String, JsonNode> jsonNode) {
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        //JsonNode jsonNodex = mapper.valueToTree(jsonNode);
        try {
            OnlineTransaction txn = mapper.treeToValue(jsonNode.getValue(), OnlineTransaction.class);
            return txn;
        } catch (IOException ex) {
            log.error(ex);
            
        }

        return null;    }

    @Override
    public CollectionObject setId(CollectionObject collectionObject, String _id) {
       ((OnlineTransaction)collectionObject.getTarget()).setId(_id);
        return collectionObject;    
    }
    
}
