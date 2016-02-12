/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.dao.lwmds.converter;

import com.hf.hfw.api.v1.settings.SettingsBean;
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
public class SettingsConverter implements CollectionObjectConverter<SettingsBean>{
    private static final Logger log = Logger.getLogger(SettingsConverter.class);

    @Override
    public CollectionObject convertToCollectionObject(SettingsBean k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SettingsBean convertFromCollectionObject(CollectionObject object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getValue(SettingsBean k, String attribute) throws AttributeNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SettingsBean convertFromJSONNode(Map.Entry<String, JsonNode> jsonNode) {
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        //JsonNode jsonNodex = mapper.valueToTree(jsonNode);
        try {
            SettingsBean sBean = mapper.treeToValue(jsonNode.getValue(), SettingsBean.class);
            return sBean;
        } catch (IOException ex) {
            log.error(ex);
            
        }

        return null;

    }

    @Override
    public CollectionObject setId(CollectionObject collectionObject, String _id) {
        ((SettingsBean)collectionObject.getTarget()).setId(_id);
        return collectionObject;

    }
    
}
