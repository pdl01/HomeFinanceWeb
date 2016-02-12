/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.dao.lwmds.converter;

import com.hf.homefinanceshared.Budget;
import com.hf.lwdatastore.CollectionObject;
import com.hf.lwdatastore.CollectionObjectConverter;
import com.hf.lwdatastore.exception.AttributeNotFoundException;
import java.util.Map;
import org.codehaus.jackson.JsonNode;

/**
 *
 * @author pldorrell
 */
public class BudgetConverter implements CollectionObjectConverter<Budget>{

    @Override
    public CollectionObject convertToCollectionObject(Budget k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Budget convertFromCollectionObject(CollectionObject object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getValue(Budget k, String attribute) throws AttributeNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Budget convertFromJSONNode(Map.Entry<String, JsonNode> jsonNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CollectionObject setId(CollectionObject collectionObject, String _id) {
        ((Budget)collectionObject.getTarget()).setId(_id);
        return collectionObject;    }
    
}
