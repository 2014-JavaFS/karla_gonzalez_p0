package com.revature.bankingapp.util;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConnectionFactoryTestSuite {

    @Test
    public void test_getConnection_returnValidConnection() {
        try(Connection con = ConnectionFactory.getConnectionFactory().getConnection()) {
            assertNotNull(con);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
