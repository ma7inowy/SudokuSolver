package com.progkomp.sudoku.view;

import com.progkomp.sudoku.*;
import com.progkomp.sudoku.SudokuLevel;
import static com.progkomp.sudoku.SudokuLevel.getP1;
import static com.progkomp.sudoku.SudokuLevel.getP2;
import static com.progkomp.sudoku.SudokuLevel.getP3;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import org.apache.log4j.Logger;

public class FXMLGraController implements Initializable {

    final static Logger logger = Logger.getLogger(FXMLGraController.class);
    private Dao<SudokuBoard> dao;
    private SudokuBoard sb;
    @FXML
    private int poziom;

    @FXML
    private TextField p00, p01, p02, p03, p04, p05, p06, p07, p08,
            p10, p11, p12, p13, p14, p15, p16, p17, p18,
            p20, p21, p22, p23, p24, p25, p26, p27, p28,
            p30, p31, p32, p33, p34, p35, p36, p37, p38,
            p40, p41, p42, p43, p44, p45, p46, p47, p48,
            p50, p51, p52, p53, p54, p55, p56, p57, p58,
            p60, p61, p62, p63, p64, p65, p66, p67, p68,
            p70, p71, p72, p73, p74, p75, p76, p77, p78,
            p80, p81, p82, p83, p84, p85, p86, p87, p88;

    public void setPoziom(int x) throws MyException {
        try {
            poziom = x;
            logger.info("Wybrano poziom: " + poziom);
            ustawPlansze();
        } catch (Exception e) {
            ResourceBundle rb = ResourceBundle.getBundle("bundles.Message");
            throw new MyException(rb.getString("level.error"));
        }
    }

    public void setSB(SudokuBoard sb) {
        this.sb = new SudokuBoard();
        this.sb = sb;
    }

    @FXML
    public void powrotDoMenu() { //zamykanie okienka
        Stage stage = (Stage) p00.getScene().getWindow();
        stage.close();
        logger.info("Powrot do menu gry");
    }

    @FXML
    private void wczytajZPliku(ActionEvent event) throws IOException {
        /*  FileChooser fileChooser = new FileChooser();
        FXMLLoader loader = new FXMLLoader();
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.Message");
        loader.setResources(bundle);
        fileChooser.setTitle(bundle.getString("load.file"));
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        if (file == null) {
            java.util.ResourceBundle rb = java.util.ResourceBundle.getBundle("bundles.Message");
            logger.fatal("Nie wybrano żadnego pliku");
            throw new MyException(rb.getString("file.null")).initCause(new NullPointerException(rb.getString("null.pointer.exception")));
        }
        FileSudokuBoardDao fsbd = new FileSudokuBoardDao(file.getAbsolutePath());
        sb = fsbd.read();
        przepiszDoView();*/

        //    Dao<SudokuBoard> dao = new JdbcSudokuBoardDao("sudoku1");
        try {
            sb = dao.read();
            dao.write(sb);
            przepiszDoView();
            wypiszPlanszeNaEkran();
            logger.info("Wczytano plansze z bazy danych");
        } catch (Exception e) {
            ResourceBundle rb = ResourceBundle.getBundle("bundles.Message");

            logger.error(rb.getString("io.exception"), e);
            throw new MyException(rb.getString("io.exception"));
        }
    }

    @FXML
    private void zapiszDoPliku(ActionEvent event) throws Throwable {
        /*  FileChooser fileChooser = new FileChooser();
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.Message");
        fileChooser.setTitle(bundle.getString("save.file"));
        Stage stage = new Stage();
        File file = fileChooser.showSaveDialog(stage);
        if (file == null) {
            java.util.ResourceBundle rb = java.util.ResourceBundle.getBundle("bundles.Message");
            logger.fatal("Nie wybrano żadnego pliku");
            throw new MyException(rb.getString("file.null")).initCause(new NullPointerException(rb.getString("null.pointer.exception")));
        }
        FileSudokuBoardDao fsbd = new FileSudokuBoardDao(file.getAbsolutePath());
        przepiszDoSB();
        fsbd.write(sb);*/
         try {
        dao = SudokuBoardDaoFactory.getJdbcDao("sudoku1");
        przepiszDoSB();
        dao.write(sb);
        wypiszPlanszeNaEkran();
        logger.info("Zapisano plansze do bazy danych");
         }
         catch (Exception e) {
            ResourceBundle rb = ResourceBundle.getBundle("bundles.Message");

            logger.error(rb.getString("io.exception"), e);
            throw new MyException(rb.getString("io.exception"));
        }
         
    }

    @FXML
    public void sprawdzTablice(ActionEvent event) {
        przepiszDoSB();
        if (sb.callCheckBoard()) {
            logger.info("Poprawnie rozwiązano sudoku");

            powrotDoMenu();
        } else {
            logger.info("Coś jest nie tak! Musisz poprawić!");

        }
    }

    public void wczytajPoziom(String absolutePath) {
        FileSudokuBoardDao fsbd = new FileSudokuBoardDao(absolutePath);
        sb = fsbd.read();
        przepiszDoView();
    }

    private void przepiszDoSB() {
        TextField[] pola = {p00, p01, p02, p03, p04, p05, p06, p07, p08,
            p10, p11, p12, p13, p14, p15, p16, p17, p18,
            p20, p21, p22, p23, p24, p25, p26, p27, p28,
            p30, p31, p32, p33, p34, p35, p36, p37, p38,
            p40, p41, p42, p43, p44, p45, p46, p47, p48,
            p50, p51, p52, p53, p54, p55, p56, p57, p58,
            p60, p61, p62, p63, p64, p65, p66, p67, p68,
            p70, p71, p72, p73, p74, p75, p76, p77, p78,
            p80, p81, p82, p83, p84, p85, p86, p87, p88};

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!"".equals(pola[(i * 9 + j)].getText())) {
                    // set for tests, bo nie sprawdzamy srodka, tylko przepisujemy wartosci
                    // nie ma wtedy problemu przy np wpisywaniu 0
                    sb.setForTests(Integer.parseInt(pola[(i * 9 + j)].getText()), i, j);
                } else {
                    sb.setForTests(0, i, j);
                }
            }
        }
        // System.out.println("przepiszdoSB: " + sb.toString());
    }

    private void przepiszDoView() {
        TextField[] pola = {p00, p01, p02, p03, p04, p05, p06, p07, p08,
            p10, p11, p12, p13, p14, p15, p16, p17, p18,
            p20, p21, p22, p23, p24, p25, p26, p27, p28,
            p30, p31, p32, p33, p34, p35, p36, p37, p38,
            p40, p41, p42, p43, p44, p45, p46, p47, p48,
            p50, p51, p52, p53, p54, p55, p56, p57, p58,
            p60, p61, p62, p63, p64, p65, p66, p67, p68,
            p70, p71, p72, p73, p74, p75, p76, p77, p78,
            p80, p81, p82, p83, p84, p85, p86, p87, p88};

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //  if (sb.get(i, j) != 0) {
                pola[(i * 9 + j)].setText(String.valueOf(sb.get(i, j)));
                //  } else {
                //   pola[(i * 9 + j)].setText("");
                //}
            }
        }
        // System.out.println("przepiszdoView: " + sb.toString());
    }

    private void wypiszPlanszeNaEkran() {
        TextField[] pola = {p00, p01, p02, p03, p04, p05, p06, p07, p08,
            p10, p11, p12, p13, p14, p15, p16, p17, p18,
            p20, p21, p22, p23, p24, p25, p26, p27, p28,
            p30, p31, p32, p33, p34, p35, p36, p37, p38,
            p40, p41, p42, p43, p44, p45, p46, p47, p48,
            p50, p51, p52, p53, p54, p55, p56, p57, p58,
            p60, p61, p62, p63, p64, p65, p66, p67, p68,
            p70, p71, p72, p73, p74, p75, p76, p77, p78,
            p80, p81, p82, p83, p84, p85, p86, p87, p88};

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sb.get(i, j) != 0) {
                    // sb.set(pola[(i * 9 + j)].getText(), i, j);//;
                    pola[(i * 9 + j)].setText(String.valueOf(sb.get(i, j)));
                }
                System.out.print(pola[(i * 9 + j)].getText() + " ");
            }
            System.out.println();
        }
    }

    public void ustawPlansze() {

        new BacktrackingSudokuSolver().solve(sb);
        switch (poziom) {
            case 1:
                getP1().usunPola(SudokuLevel.P1, sb);
                break;
            case 2:
                getP2().usunPola(SudokuLevel.P2, sb);
                break;
            case 3:
                getP3().usunPola(SudokuLevel.P3, sb);
                break;
            default:
                getP1().usunPola(SudokuLevel.P1, sb);
                break;
        }
        wypiszPlanszeNaEkran();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sb = new SudokuBoard();
        dao = new JdbcSudokuBoardDao("sudoku1");
    }

}
