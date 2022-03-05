package com.takeaway.takeaway.configuration;

import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class PostgreSQLDialect extends PostgreSQL95Dialect {

    public PostgreSQLDialect() {
        super();
        registerFunction("json_agg", new SQLFunctionTemplate(StandardBasicTypes.STRING, "json_agg(?1)"));
    }
}
