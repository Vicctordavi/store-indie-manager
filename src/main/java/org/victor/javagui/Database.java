package org.victor.javagui;

import java.sql.*;

public class Database {

    private static final String URL = "jdbc:sqlite:/home/victor-davi/Códigos/ProjetosJava/Javagui/banco_usuarios.db";



    public static void criarTabela() {
        System.out.println("Executando criarTabela() em Database...");
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT NOT NULL," +
                    "usuario TEXT UNIQUE NOT NULL," +
                    "senha TEXT NOT NULL" +
                    ");";
            stmt.execute(sql);
            System.out.println("Tabela usuarios criada ou já existia.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static boolean inserirUsuario(String nome, String usuario, String senha) {
        String verificaSql = "SELECT COUNT(*) FROM usuarios WHERE usuario = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement verificaStmt = conn.prepareStatement(verificaSql)) {

            verificaStmt.setString(1, usuario);
            ResultSet rs = verificaStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false;
            }


            String insertSql = "INSERT INTO usuarios (nome, usuario, senha) VALUES (?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, nome);
                insertStmt.setString(2, usuario);
                insertStmt.setString(3, senha);
                insertStmt.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



    public static boolean usuarioExiste(String usuario) {
        String sql = "SELECT 1 FROM usuarios WHERE usuario = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean senhaValida(String usuario, String senha) {
        String sql = "SELECT senha FROM usuarios WHERE usuario = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String senhaBanco = rs.getString("senha");
                return senhaBanco.equals(senha);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
