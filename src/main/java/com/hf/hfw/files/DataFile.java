package com.hf.hfw.files;

import com.hf.hfw.license.Version;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author pldor
 */
public class DataFile<T> implements Serializable {
    private static final Logger log = Logger.getLogger(DataFile.class);

    protected String descriptor;
    protected Version version;
    protected List<T> objectData;

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public List<T> getObjectData() {
        return objectData;
    }

    public void setObjectData(List<T> objectData) {
        this.objectData = objectData;
    }

    public void loadFromFile(File _file,Class<T> clazz) {
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        try {

            JsonNode rootNode = mapper.readTree(_file);
            JsonNode file_descriptor = rootNode.findValue("descriptor");
            if (!file_descriptor.getTextValue().equals("accounts")) {
                //error
            }
            this.setDescriptor(file_descriptor.getTextValue());
            
            JsonNode file_version = rootNode.findValue("version");
            Version version = mapper.readValue(file_version, Version.class);
            
            
            
            List<JsonNode> dataEntries = rootNode.findValues("objectData");
            this.objectData = new ArrayList<T>();
            for (JsonNode jsonNode: dataEntries) {
                Iterator<JsonNode> iterator = jsonNode.getElements();
                while (iterator.hasNext()) {
                    JsonNode jsonNodeEntry = iterator.next();
                    this.objectData.add( mapper.readValue(jsonNodeEntry,clazz) );
                }
            }

        } catch (JsonProcessingException e) {
            log.error(e);
        } catch (IOException e) {
            log.error(e);
        } catch (Exception e) {
            log.error(e);
        }

    }
    public void writeToFile(File _file,Class<T> clazz){
         
        if (!_file.exists()) {
            try {
                _file.createNewFile();
            } catch (IOException ex) {
                log.error(ex);
                //log.warn("IOExcption encountered", ex);
            }

        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonFactory factory = new JsonFactory();
        
        //HashMap<String, Object> hashMap = new HashMap<String, Object>();
        
        //hashMap.put("_data", this);
        
        try {
            JsonNode jsonNode = objectMapper.valueToTree(this);
            //log.debug(jsonNode);
            objectMapper.writeValue(_file, jsonNode);
        } catch (IOException ex) {
           log.error(ex);
        } catch (Exception ex) {
            log.error(ex);
            //log.error("Error in writing out jsonNode", e);
        }
    }
}
