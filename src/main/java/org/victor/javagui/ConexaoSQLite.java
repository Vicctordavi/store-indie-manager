package org.victor.javagui;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoSQLite {
    private static final String url = "jdbc:sqlite:banco_usuarios.db";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
