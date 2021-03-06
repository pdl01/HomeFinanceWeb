
package com.hf.hfw.api.v1;

import com.hf.hfw.manager.CategoryCacheManagerImpl;
import java.util.Collection;
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

    @Override
    public Collection<String> getAll() {
        return this.categoryCacheManagerImpl.getCategorySet();
    }
    
}
