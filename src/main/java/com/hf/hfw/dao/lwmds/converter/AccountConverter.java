
package com.hf.hfw.dao.lwmds.converter;

import com.hf.hfw.dao.lwmds.AccountDAOImpl;
import com.hf.homefinanceshared.Account;
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
public class AccountConverter implements CollectionObjectConverter<Account>{
    private static final Logger log = Logger.getLogger(AccountConverter.class);

    @Override
    public CollectionObject convertToCollectionObject(Account k) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Account convertFromCollectionObject(CollectionObject object) {
        return (Account)object.getTarget();
    }

    @Override
    public String getValue(Account k, String attribute) throws AttributeNotFoundException {
            if (attribute.equalsIgnoreCase("id")) {
                return k.getId();
            } else if (attribute.equalsIgnoreCase("accountType")) {
                return k.getAccountType();
            } else if (attribute.equalsIgnoreCase("accountNumber")) {
                return k.getAccountNumber();
            } else if (attribute.equalsIgnoreCase("name")) {
                return k.getName();
            } else if (attribute.equalsIgnoreCase("externalId")) {
                return k.getExternalId();
            } else {
                throw new AttributeNotFoundException();
            }
    }

    @Override
    public Account convertFromJSONNode(Map.Entry<String, JsonNode> jsonNode) {
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        //JsonNode jsonNodex = mapper.valueToTree(jsonNode);
        try {
            Account account = mapper.treeToValue(jsonNode.getValue(), Account.class);
            return account;
        } catch (IOException ex) {
            log.error(ex);
            
        }

        return null;
    }

    @Override
    public CollectionObject setId(CollectionObject collectionObject, String _id) {
        ((Account)collectionObject.getTarget()).setId(_id);
        return collectionObject;
    }
    
}
