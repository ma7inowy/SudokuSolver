package com.progkomp.sudoku.view;

import com.progkomp.sudoku.MyException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class FXMLDocumentController implements Initializable {

    final static Logger logger = Logger.getLogger(FXMLDocumentController.class);

    @FXML
    public Stage stage;

    @FXML
    private void zamknijAplikacjeAction(ActionEvent event) {
        Platform.exit();
        logger.info("Zamknięto aplikację!");
    }

    @FXML
    private void wczytajZPliku(ActionEvent event) throws IOException, Throwable {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLGra.fxml"));
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.Message");
        loader.setResources(bundle);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(bundle.getString("load.file"));
        File file = fileChooser.showOpenDialog(stage);
        Parent root = loader.load();

        FXMLGraController scene2Controller = loader.getController();
        if (file == null) {
            java.util.ResourceBundle rb = java.util.ResourceBundle.getBundle("bundles.Message");
            logger.error("Nie wybrano żadnego pliku");
            throw new MyException(rb.getString("file.null")).initCause(new NullPointerException(rb.getString("null.pointer.exception")));
        }
        scene2Controller.wczytajPoziom(file.getAbsolutePath());
        stage.setScene(new Scene(root));
        stage.setTitle(bundle.getString("continue"));
        stage.show();
        logger.info("Wczytano sudoku z pliku!");
    }

    @FXML
    private void rozpocznijGre1(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLGra.fxml"));
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.Message");
        loader.setResources(bundle);
        Parent root = loader.load();
        // Locale.setDefault(new Locale("pl"));
        FXMLGraController scene2Controller = loader.getController();
        scene2Controller.setPoziom(1);
        // stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(bundle.getString("w1"));
        stage.show();
        logger.info("Gracz rozpocząl grę o poziomie początkującym!");
    }

    @FXML
    private void rozpocznijGre2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLGra.fxml"));
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.Message");
        loader.setResources(bundle);
        Parent root = loader.load();
        FXMLGraController scene2Controller = loader.getController();
        scene2Controller.setPoziom(2);
        // stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(bundle.getString("w2"));
        stage.show();
        logger.info("Gracz rozpoczął grę o poziomie średnim!");
    }

    @FXML
    private void rozpocznijGre3(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLGra.fxml"));
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.Message");
        loader.setResources(bundle);
        Parent root = loader.load();
        FXMLGraController scene2Controller = loader.getController();
        scene2Controller.setPoziom(3);
        // stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(bundle.getString("w3"));
        stage.show();
        logger.info("Gracz rozpoczął grę o poziomie trudnym!");
    }

    @FXML
    private void wybierzAngielski(ActionEvent event) throws IOException {
        Locale.setDefault(new Locale("en"));
        ResourceBundle bundle = java.util.ResourceBundle.getBundle("bundles.Message");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLDocument.fxml"), bundle);
        stage.close();
        stage.setScene(new Scene(root));
        stage.setTitle(bundle.getString("main.menu"));
        stage.show();
        logger.info("Gracz zmienił język gry na angielski!");
    }

    @FXML
    private void wybierzPolski(ActionEvent event) throws IOException {
        Locale.setDefault(new Locale("pl"));
        ResourceBundle bundle = java.util.ResourceBundle.getBundle("bundles.Message_pl");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/FXMLDocument.fxml"), bundle);
        stage.close();
        stage.setScene(new Scene(root));
        stage.setTitle(bundle.getString("main.menu"));
        stage.show();
        logger.info("Gracz zmienił język gry na polski!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stage = new Stage();
    }

    public void start() {
        stage = new Stage();
        Locale.setDefault(new Locale("pl"));
        String sciezka = "bundles.Message";
        ResourceBundle bundle = java.util.ResourceBundle.getBundle(sciezka);
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/FXMLDocument.fxml"), bundle);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            // Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
