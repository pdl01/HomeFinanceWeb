package com.hf.hfw.application;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

public class ApplicationRepositoryConfig {

    private boolean valid;
    private String type;
    private String host;
    private String port;
    private String username;
    private String password;
    private String db;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHost() {
        return host;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public boolean validate() {
        return true;
    }

    public static ApplicationRepositoryConfig loadFromConfig(File file) {
        ApplicationRepositoryConfig applicationRepositoryConfig = new ApplicationRepositoryConfig();
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        try {
            JsonNode rootNode = mapper.readTree(file);
            Iterator<Map.Entry<String, JsonNode>> fields = rootNode.getFields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                if (field.getKey().equals("repository")) {
                    Iterator<Map.Entry<String, JsonNode>> repo_fields = field.getValue().getFields();
                    while (repo_fields.hasNext()) {
                        Map.Entry<String, JsonNode> repo_field = repo_fields.next();
                        if (repo_field.getKey().equals("host")) {
                            applicationRepositoryConfig.setHost(repo_field.getValue().asText());
                        } else if (repo_field.getKey().equals("port")) {
                            applicationRepositoryConfig.setPort(repo_field.getValue().asText());
                        } else if (repo_field.getKey().equals("db")) {
                            applicationRepositoryConfig.setDb(repo_field.getValue().asText());
                        } else if (repo_field.getKey().equals("username")) {
                            applicationRepositoryConfig.setUsername(repo_field.getValue().asText());
                        } else if (repo_field.getKey().equals("password")) {
                            applicationRepositoryConfig.setPassword(repo_field.getValue().asText());
                        } else if (repo_field.getKey().equals("type")) {
                            applicationRepositoryConfig.setType(repo_field.getValue().asText());
                        }
                    }
                }
            }
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (applicationRepositoryConfig.validate()) {
            applicationRepositoryConfig.setValid(true);
        } else {
            applicationRepositoryConfig.setValid(false);
        }
        return applicationRepositoryConfig;
    }
        public static void saveToConfig(ApplicationRepositoryConfig config,String fileName) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonFactory factory = new JsonFactory();
            HashMap<String,ApplicationRepositoryConfig> hashMap = new HashMap<String,ApplicationRepositoryConfig>();
            hashMap.put("repository", config);
            JsonNode jsonNode = objectMapper.valueToTree(hashMap);
        try {
            objectMapper.writeValue(new File(fileName), jsonNode);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
            
        }
    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
