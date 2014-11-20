/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.cache;

/**
 *
 * @author phillip.dorrell
 */
public interface CacheManager {
    public void save(String cacheName,String cacheKey, String object);
    public Object get(String cacheName,String cacheKey);
    public void delete(String cacheName,String cacheKey);
    public void clear(String cacheName);
    public void clearAll();
}
