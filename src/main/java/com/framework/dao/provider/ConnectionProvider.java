package com.framework.dao.provider;

import java.sql.Connection;

public interface ConnectionProvider {

	Connection getConnection() throws Exception;

}
