/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.dao.lwmds.converter;

import com.hf.homefinanceshared.ScheduledTransaction;
import com.hf.lwdatastore.CollectionObject;
import com.hf.lwdatastore.CollectionObjectConverter;
import com.hf.lwdatastore.exception.AttributeNotFoundException;
import java.util.Map;
import org.codehaus.jackson.JsonNode;

/**
 *
 * @author pldorrell
 */
public class ScheduledTransactionConverter implements CollectionObjectConverter<ScheduledTransaction>{

    @Override
    public CollectionObject convertToCollectionObject(ScheduledTransaction k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ScheduledTransaction convertFromCollectionObject(CollectionObject object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getValue(ScheduledTransaction k, String attribute) throws AttributeNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ScheduledTransaction convertFromJSONNode(Map.Entry<String, JsonNode> jsonNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CollectionObject setId(CollectionObject collectionObject, String _id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
