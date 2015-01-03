/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.api.v1;

import com.hf.hfw.manager.CategoryCacheManagerImpl;
import java.util.List;
import javax.ws.rs.Path;

/**
 *
 * @author pldorrell
 */
@Path("/category")
public class CategoryLookupServiceImpl implements CategoryLookupService {
   private CategoryCacheManagerImpl categoryCacheManagerImpl;

    public CategoryCacheManagerImpl getCategoryCacheManagerImpl() {
        return categoryCacheManagerImpl;
    }

    public void setCategoryCacheManagerImpl(CategoryCacheManagerImpl categoryCacheManagerImpl) {
        this.categoryCacheManagerImpl = categoryCacheManagerImpl;
    }
   
    @Override
    public List<String> lookupCategories(String categoryString) {
        return this.categoryCacheManagerImpl.get(categoryString, false);
    }
    
}
