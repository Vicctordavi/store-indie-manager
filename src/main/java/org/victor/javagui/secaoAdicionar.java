package org.victor.javagui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class secaoAdicionar {

    @FXML
    private TextField nomeAdicionado;

    @FXML
    private TextField idAdicionado;

    @FXML
    private TextField precoAdicionado;

    @FXML
    private TextField validadeAdicionado;

    @FXML
    private Button btnSairAdd;

    @FXML
    private Button btnSalvarAdd;

    @FXML
    private Text textoFaltando;

    @FXML
    private Text textoAdicionado;

    private String usuarioLogado;
    private abaPrincipal abaController;

    public void setUsuarioLogado(String usuario) {
        this.usuarioLogado = usuario;
    }

    public void setAbaPrincipalController(abaPrincipal controller) {
        this.abaController = controller;
    }

    @FXML
    private void initialize() {
        textoFaltando.setVisible(false);
        textoAdicionado.setVisible(false);
    }

    @FXML
    private void salvarProduto(ActionEvent event) {
        System.out.println("Usuário logado ao salvar: " + usuarioLogado);

        String nome = nomeAdicionado.getText().trim();
        String id = idAdicionado.getText().trim();
        String preco = precoAdicionado.getText().trim();
        String validade = validadeAdicionado.getText().trim();

        boolean algumPreenchido = !nome.isEmpty() || !id.isEmpty() || !preco.isEmpty() || !validade.isEmpty();

        if (!algumPreenchido) {
            textoFaltando.setVisible(true);
            textoAdicionado.setVisible(false);
            return;
        }

        ProdutoDAO.inserirProduto(usuarioLogado, nome, id, preco, validade);

        textoFaltando.setVisible(false);
        textoAdicionado.setVisible(true);

        if (abaController != null) {
            abaController.carregarProdutosDoUsuario();
        }
    }

    @FXML
    private void fecharJanela(ActionEvent event) {
        Stage stage = (Stage) btnSairAdd.getScene().getWindow();
        stage.close();
    }
}
