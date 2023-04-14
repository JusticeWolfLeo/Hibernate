package org.example;

import java.sql.Connection;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionTest {

    @Test
    @SneakyThrows
    public void testConnection() {
        Connection connection = Config.getConnection();
        Assert.assertTrue(connection.isValid(1));
        Assert.assertFalse(connection.isClosed());
    }

}
