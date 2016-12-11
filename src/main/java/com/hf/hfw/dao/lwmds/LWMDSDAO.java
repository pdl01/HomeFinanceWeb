
package com.hf.hfw.dao.lwmds;

import com.hf.hfw.application.ApplicationState;
import com.hf.lwdatastore.DataStore;

/**
 *
 * @author pldor
 */
public abstract class LWMDSDAO {
    protected DataStore getDataStore() {
        return ApplicationState.getApplicationState().getDbFactory().getLwDataStore();
    }
}
