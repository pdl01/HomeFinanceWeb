
package com.hf.hfw.dao.lwmds.converter;

import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.RegisterTransaction;
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
public class TransactionConverter implements CollectionObjectConverter<RegisterTransaction>{
    private static final Logger log = LogManager.getLogger(AccountConverter.class);

    @Override
    public CollectionObject convertToCollectionObject(RegisterTransaction k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RegisterTransaction convertFromCollectionObject(CollectionObject object) {
        return (RegisterTransaction)object.getTarget();

    }

    @Override
    public String getValue(RegisterTransaction k, String attribute) throws AttributeNotFoundException {
            if (attribute.equalsIgnoreCase("id")) {
                return k.getId();
            } else if (attribute.equalsIgnoreCase("accountId")) {
                return k.getPrimaryAccount();
            } else if (attribute.equalsIgnoreCase("category")) {
                return k.getCategory();
            }else if (attribute.equalsIgnoreCase("year")) {
                return k.getTxnDate().substring(0,4);
            }else if (attribute.equalsIgnoreCase("month")) {
                return k.getTxnDate().substring(0,7);
            } else {
                throw new AttributeNotFoundException();
            }   
    }

    @Override
    public RegisterTransaction convertFromJSONNode(Map.Entry<String, JsonNode> jsonNode) {
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        //JsonNode jsonNodex = mapper.valueToTree(jsonNode);
        try {
            RegisterTransaction txn = mapper.treeToValue(jsonNode.getValue(), RegisterTransaction.class);
            return txn;
        } catch (IOException ex) {
            log.error(ex);
            
        }

        return null;

    }

    @Override
    public CollectionObject setId(CollectionObject collectionObject, String _id) {
       ((RegisterTransaction)collectionObject.getTarget()).setId(_id);
        return collectionObject;    
    }
    
}
