package com.hf.hfw.dao.h2;

import javax.sql.DataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 *
 * @author pldor
 */
public class H2DataSourceFactory {
    public DataSource getDataSource() {
                
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		builder.setType(EmbeddedDatabaseType.H2);
                builder.addScript("db/h2/sql/createTransactionTable.sql");
                builder.addScript("db/h2/sql/createOnlineTransactionTable.sql");
                builder.addScript("db/h2/sql/createScheduledTransactionTable.sql");
                EmbeddedDatabase db = builder.build();
		return db;

	} 
}
