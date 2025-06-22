package org.victor.javagui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class registroUsuario {

    @FXML
    private Label mensagemDePreencher;

    @FXML
    private Label senhasNaoIguais;

    @FXML
    private Label cadastradoComSucesso;


    @FXML
    private TextField nomeCampo;

    @FXML
    private TextField usuarioCampo;

    @FXML
    private TextField senhaCampo;

    @FXML
    private TextField confirmarsenhaCampo;

    @FXML
    private Button botaoSalvar;

    @FXML
    private Button botaoCancelar;

    @FXML
    private void fazerRegistro(ActionEvent event) {
        String nome = nomeCampo.getText();
        String usuario = usuarioCampo.getText();
        String senha = senhaCampo.getText();
        String confirma = confirmarsenhaCampo.getText();

        mensagemDePreencher.setVisible(false);
        cadastradoComSucesso.setVisible(false);
        senhasNaoIguais.setVisible(false);

        if (nome.isEmpty() || usuario.isEmpty() || senha.isEmpty() || confirma.isEmpty()) {
            mensagemDePreencher.setText("Você precisa preencher todos os campos!");
            mensagemDePreencher.setVisible(true);
            return;
        }

        if (!senha.equals(confirma)) {
            senhasNaoIguais.setVisible(true);
            return;
        }

        boolean sucesso = Database.inserirUsuario(nome, usuario, senha);

        if (sucesso) {
            cadastradoComSucesso.setVisible(true);
        } else {
            mensagemDePreencher.setText("Usuário já existe!");
            mensagemDePreencher.setVisible(true);
        }
    }


    @FXML
    private void cancelarRegistro(ActionEvent event) {
        try {
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/org/victor/javagui/loginUsuario.fxml"));
            Main.changeScene(loginRoot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
