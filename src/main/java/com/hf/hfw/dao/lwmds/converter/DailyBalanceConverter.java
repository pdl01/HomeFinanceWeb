/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.dao.lwmds.converter;

import com.hf.homefinanceshared.DailyBalance;
import com.hf.lwdatastore.CollectionObject;
import com.hf.lwdatastore.CollectionObjectConverter;
import com.hf.lwdatastore.exception.AttributeNotFoundException;
import java.util.Map;
import org.codehaus.jackson.JsonNode;

/**
 *
 * @author pldorrell
 */
public class DailyBalanceConverter implements CollectionObjectConverter<DailyBalance> {

    @Override
    public CollectionObject convertToCollectionObject(DailyBalance k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DailyBalance convertFromCollectionObject(CollectionObject object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getValue(DailyBalance k, String attribute) throws AttributeNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DailyBalance convertFromJSONNode(Map.Entry<String, JsonNode> jsonNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CollectionObject setId(CollectionObject collectionObject, String _id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
