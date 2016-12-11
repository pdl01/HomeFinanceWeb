package com.hf.hfw.dao.h2;

import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 *
 * @author pldor
 */
public abstract class H2DAO {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {

        return new NamedParameterJdbcTemplate(dataSource);
    }
}
