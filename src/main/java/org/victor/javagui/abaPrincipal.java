package org.victor.javagui;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


import java.net.URL;
import java.util.List;

public class abaPrincipal {

    @FXML
    private TableView<Produto> tabelaProdutos;

    @FXML
    private TableColumn<Produto, String> colunaNome;

    @FXML
    private TableColumn<Produto, String> colunaCodigo;

    @FXML
    private TableColumn<Produto, String> colunaPreco;

    @FXML
    private TableColumn<Produto, String> colunaValidade;

    @FXML
    private Text nomeUsuario;

    @FXML
    private Text dataAtual;

    @FXML
    private Text numeroTotalProdutos;

    @FXML
    private Text produtosVencer;

    private String usuarioLogado;

    @FXML
    private Button btnExcluirProduto;

    @FXML
    private Text vazioExcluir;

    @FXML
    private void excluirProdutoSelecionado() {
        Produto produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();

        if (produtoSelecionado == null) {
            mostrarMensagemTemporaria("Selecione um produto para excluir.");
            return;
        }

        String codigo = produtoSelecionado.getCodigo();

        boolean sucesso = ProdutoDAO.excluirProdutoPorCodigo(codigo);

        if (sucesso) {
            carregarProdutosDoUsuario();
            mostrarMensagemTemporaria("Produto excluído com sucesso!");
        } else {
            mostrarMensagemTemporaria("Não foi possível excluir o produto.");
        }
    }

    private void atualizarDataAtual() {
        LocalDate hoje = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dataAtual.setText(hoje.format(formato));
    }


    public void mostrarMensagemTemporaria(String mensagem) {
        vazioExcluir.setText(mensagem);
        vazioExcluir.setVisible(true);

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> vazioExcluir.setVisible(false));
        pause.play();
    }


    public void setUsuarioLogado(String usuario) {
        this.usuarioLogado = usuario;
        nomeUsuario.setText(usuario);
        atualizarDataAtual();
        carregarProdutosDoUsuario();
    }



    public void carregarProdutosDoUsuario() {
        System.out.println("Carregando produtos para o usuário: " + usuarioLogado);

        ProdutoDAO.criarTabelaProdutos();

        List<Produto> produtos = ProdutoDAO.listarProdutosPorUsuario(usuarioLogado);
        System.out.println("Produtos carregados: " + produtos.size());

        ObservableList<Produto> lista = FXCollections.observableArrayList(produtos);

        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colunaValidade.setCellValueFactory(new PropertyValueFactory<>("validade"));

        tabelaProdutos.setItems(lista);

        numeroTotalProdutos.setText(String.valueOf(produtos.size()));


        int produtosParaVencer = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate hoje = LocalDate.now();

        for (Produto p : produtos) {
            try {
                if (p.getValidade() != null && !p.getValidade().isEmpty()) {
                    LocalDate validade = LocalDate.parse(p.getValidade(), formatter);
                    long diasRestantes = ChronoUnit.DAYS.between(hoje, validade);

                    if (diasRestantes >= 0 && diasRestantes <= 5) {
                        produtosParaVencer++;
                    }
                }
            } catch (Exception e) {
                System.out.println("Data de validade inválida: " + p.getValidade());
            }
        }

        produtosVencer.setText(String.valueOf(produtosParaVencer));


        tabelaProdutos.setRowFactory(tv -> new javafx.scene.control.TableRow<Produto>() {
            @Override
            protected void updateItem(Produto item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle("");
                } else {
                    try {
                        if (item.getValidade() != null && !item.getValidade().isEmpty()) {
                            LocalDate validade = LocalDate.parse(item.getValidade(), formatter);
                            long diasRestantes = ChronoUnit.DAYS.between(hoje, validade);

                            if (diasRestantes >= 0 && diasRestantes <= 5) {
                                setStyle("-fx-background-color: #FFB6B6;");  // Vermelho claro
                            } else {
                                setStyle("");
                            }
                        } else {
                            setStyle("");
                        }
                    } catch (Exception e) {
                        setStyle("");
                    }
                }
            }
        });
    }








    @FXML
    private void abrirJanelaAdicionar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/victor/javagui/secaoAdicionar.fxml"));

            URL fxmlUrl = getClass().getResource("/org/victor/javagui/secaoAdicionar.fxml");
            if (fxmlUrl == null) {
                System.out.println("Arquivo FXML não encontrado!");
            } else {
                System.out.println("Arquivo FXML encontrado em: " + fxmlUrl);
            }

            Parent root = loader.load();

            secaoAdicionar controller = loader.getController();
            controller.setUsuarioLogado(usuarioLogado);
            controller.setAbaPrincipalController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Adicionar Produto");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
