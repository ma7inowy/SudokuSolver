package com.progkomp.sudoku.view;

import com.progkomp.sudoku.MyException;
import java.util.Locale;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

public class View extends Application {

    final static Logger logger = Logger.getLogger(View.class);

    @Override
    public void start(Stage stage) throws Exception {
        Locale.setDefault(new Locale("en"));
        try {
            FXMLDocumentController fxmldc = new FXMLDocumentController();
            fxmldc.start();
            ResourceBundle info = ResourceBundle.getBundle("bundles.Authors");
            String autorzy = info.getString("Autor1") + "\r\n" + info.getString("Autor2");
            logger.info("Włączono aplikację");
            System.out.println(autorzy);
        } catch (Throwable ex) {
            ResourceBundle rb = ResourceBundle.getBundle("bundles.Message");
            logger.error(rb.getString("start.error"), ex);
            throw new MyException(rb.getString("throwable.exception"));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
