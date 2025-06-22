package org.victor.javagui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private static final String URL = "jdbc:sqlite:/home/victor-davi/Códigos/ProjetosJava/Javagui/produtos.db";


    public static boolean excluirProdutoPorCodigo(String codigo) {
        String sql = "DELETE FROM produtos WHERE codigo = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codigo);
            int linhasAfetadas = pstmt.executeUpdate();

            return linhasAfetadas > 0;

        } catch (Exception e) {
            System.out.println("Erro ao excluir produto com código: " + codigo);
            e.printStackTrace();
            return false;
        }
    }

    public static void criarTabelaProdutos() {
        System.out.println("Executando criarTabelaProdutos() em ProdutoDAO...");
        String sql = "CREATE TABLE IF NOT EXISTS produtos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "usuario TEXT, "
                + "nome TEXT, "
                + "codigo TEXT, "
                + "preco TEXT, "
                + "validade TEXT"
                + ");";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Tabela produtos criada ou já existia.");

        } catch (Exception e) {
            System.out.println("Erro ao criar a tabela de produtos:");
            e.printStackTrace();
        }
    }


    public static void inserirProduto(String usuario, String nome, String codigo, String preco, String validade) {
        System.out.println("Inserindo produto para usuário: " + usuario);

        String sql = "INSERT INTO produtos (usuario, nome, codigo, preco, validade) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario);
            pstmt.setString(2, nome.isEmpty() ? null : nome);
            pstmt.setString(3, codigo.isEmpty() ? null : codigo);
            pstmt.setString(4, preco.isEmpty() ? null : preco);
            pstmt.setString(5, validade.isEmpty() ? null : validade);

            pstmt.executeUpdate();
            System.out.println("Produto inserido com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao inserir o produto:");
            e.printStackTrace();
        }
    }


    public static List<Produto> listarProdutosPorUsuario(String usuario) {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT nome, codigo, preco, validade FROM produtos WHERE usuario = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String nome = rs.getString("nome");
                String codigo = rs.getString("codigo");
                String preco = rs.getString("preco");
                String validade = rs.getString("validade");

                Produto produto = new Produto(nome, codigo, preco, validade);
                lista.add(produto);
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar os produtos:");
            e.printStackTrace();
        }

        return lista;
    }
}
