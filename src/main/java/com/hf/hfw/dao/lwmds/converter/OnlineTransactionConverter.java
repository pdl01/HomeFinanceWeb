package com.hf.hfw.dao.lwmds.converter;

import com.hf.homefinanceshared.OnlineTransaction;
import com.hf.lwdatastore.CollectionObject;
import com.hf.lwdatastore.CollectionObjectConverter;
import com.hf.lwdatastore.exception.AttributeNotFoundException;
import java.util.Map;
import org.codehaus.jackson.JsonNode;

/**
 *
 * @author pldorrell
 */
public class OnlineTransactionConverter implements CollectionObjectConverter<OnlineTransaction>{

    @Override
    public CollectionObject convertToCollectionObject(OnlineTransaction k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OnlineTransaction convertFromCollectionObject(CollectionObject object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getValue(OnlineTransaction k, String attribute) throws AttributeNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OnlineTransaction convertFromJSONNode(Map.Entry<String, JsonNode> jsonNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CollectionObject setId(CollectionObject collectionObject, String _id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
