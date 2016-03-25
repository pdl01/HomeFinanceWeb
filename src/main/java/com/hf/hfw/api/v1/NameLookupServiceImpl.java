package com.hf.hfw.api.v1;

import com.hf.hfw.manager.NameCacheManagerImpl;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.Path;

/**
 *
 * @author pldorrell
 */
@Path("/name")
public class NameLookupServiceImpl implements NameLookupService{
   private NameCacheManagerImpl nameCacheManagerImpl;

    @Override
    public List<String> lookupNames(String name) {
        return this.nameCacheManagerImpl.get(name, false);
    }

    @Override
    public Collection<String> getAll() {
        return this.nameCacheManagerImpl.getNames();
    }
    
}
