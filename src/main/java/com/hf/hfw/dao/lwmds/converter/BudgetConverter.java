
package com.hf.hfw.dao.lwmds.converter;

import com.hf.homefinanceshared.Budget;
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
public class BudgetConverter implements CollectionObjectConverter<Budget> {

    private static final Logger log = Logger.getLogger(BudgetConverter.class);

    @Override
    public CollectionObject convertToCollectionObject(Budget k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Budget convertFromCollectionObject(CollectionObject object) {
        return (Budget) object.getTarget();
    }

    @Override
    public String getValue(Budget k, String attribute) throws AttributeNotFoundException {
            if (attribute.equalsIgnoreCase("id")) {
                return k.getId();
            } else {
                throw new AttributeNotFoundException();
            }       }

    @Override
    public Budget convertFromJSONNode(Map.Entry<String, JsonNode> jsonNode) {
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        //JsonNode jsonNodex = mapper.valueToTree(jsonNode);
        try {
            Budget budget = mapper.treeToValue(jsonNode.getValue(), Budget.class);
            return budget;
        } catch (IOException ex) {
            log.error(ex);

        }

        return null;
    }

    @Override
    public CollectionObject setId(CollectionObject collectionObject, String _id) {
        ((Budget) collectionObject.getTarget()).setId(_id);
        return collectionObject;
    }

}
