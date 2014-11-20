/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.cache;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author phillip.dorrell
 */
public class CacheManagerImpl implements CacheManager {

    private static HashMap<String, HashMap<String,Object>> cache;
    
    public void init() {
        if (cache == null) {
            cache = new HashMap<String, HashMap<String,Object>>();
        }
    }
    
    private void createIfNotExists(String cacheName){
        HashMap<String,Object> x = cache.get(cacheName);
        if (x == null) {
            x=new HashMap<String,Object>();
            cache.put(cacheName, x);
        }
    }
    
    @Override
    public void save(String cacheName, String cacheKey, String object) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object get(String cacheName, String cacheKey) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String cacheName, String cacheKey) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear(String cacheName) {
        cache.remove(cacheName);

    }

    @Override
    public void clearAll() {
        cache.clear();
    }
    
}
