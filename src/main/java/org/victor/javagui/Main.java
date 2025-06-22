package org.victor.javagui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Database.criarTabela();
        ProdutoDAO.criarTabelaProdutos();


        mainStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/org/victor/javagui/loginUsuario.fxml"));
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.setTitle("Login");
        mainStage.show();



    }


    public static void changeScene(Parent root) {
        mainStage.getScene().setRoot(root);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
