package org.victor.javagui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class loginUsuario {

    @FXML
    private Label mensagemLoginErrado;

    @FXML
    private TextField usuarioField;

    @FXML
    private TextField senhaField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registrarButton;

    @FXML
    private void fazerLogin(ActionEvent event) {
        mensagemLoginErrado.setVisible(false);  // Esconde a mensagem de erro no início

        String usuario = usuarioField.getText();
        String senha = senhaField.getText();

        // Verificar se os campos foram preenchidos
        if (usuario.isEmpty() || senha.isEmpty()) {
            mensagemLoginErrado.setText("Preencha usuário e senha!");
            mensagemLoginErrado.setVisible(true);
            return;
        }

        // Validação no banco de dados
        boolean loginValido = Database.senhaValida(usuario, senha);

        if (loginValido) {
            System.out.println("Login realizado com sucesso: " + usuario);
            mensagemLoginErrado.setVisible(false);

            try {
                // Carrega a nova janela (abaPrincipal.fxml)
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/victor/javagui/abaPrincipal.fxml"));
                Parent root = loader.load();

                // pegar controller da aba principal
                abaPrincipal controller = loader.getController();

                // setar o usuário logado na aba principal
                controller.setUsuarioLogado(usuario);

                // Cria um novo Stage (nova janela)
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Gerenciamento de Produtos");
                stage.show();


                // Fecha a janela de login atual
                Stage stageAtual = (Stage) loginButton.getScene().getWindow();
                stageAtual.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            mensagemLoginErrado.setText("Usuário ou senha incorretos!");
            mensagemLoginErrado.setVisible(true);
        }
    }




    @FXML
    private void fazerRegistro(ActionEvent event) {
        try {
            Parent registroRoot = FXMLLoader.load(getClass().getResource("/org/victor/javagui/registroUsuario.fxml"));
            Main.changeScene(registroRoot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
