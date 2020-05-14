package com.progkomp.sudoku;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {

    static final Logger logger = Logger.getLogger(JdbcSudokuBoardDao.class);
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String JDBC_URL = "jdbc:derby:database;create=true";
    private String readQuery = "Select * from ROW r where r.board_id in (select board_id from BOARDS where name = ? )";
    private String rowUpdateQuery = "UPDATE ROW "
            + "SET Y0 = ?, Y1 = ?, Y2 = ?, Y3 = ?, Y4 = ?, Y5 = ?, Y6 = ?, Y7 = ?, Y8 = ? "
            + "WHERE board_id = ? and rowNumber = ?";
    private String rowWriteQuery = "INSERT INTO ROW (board_id, rowNumber, Y0, Y1, Y2, Y3, Y4, Y5, Y6, Y7, Y8) " + "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private String boardWriteQuery = "INSERT INTO BOARDS(name) " + "VALUES(?)";
    private String checkExistanceQuery = "Select board_id from BOARDS where name = ?";
    private String boardName;
    private Connection dataConn;

    public JdbcSudokuBoardDao(final String boardName) {
        try {
            this.boardName = boardName;
            Class.forName(DRIVER);
            dataConn = DriverManager.getConnection(JDBC_URL);
            dataConn.setAutoCommit(false);
            createTables();
        } catch (ClassNotFoundException | SQLException e) {

        }
    }

    @Override
    public void finalize() throws Throwable {
        super.finalize();
    }

    private void createTables() {
        DatabaseMetaData databaseMetadata;
        try {
            databaseMetadata = dataConn.getMetaData();

            try (ResultSet resultBoards = databaseMetadata.getTables(null, null, "BOARDS", null);
                    Statement statement = dataConn.createStatement();) {
                if (!resultBoards.next()) {
                    statement.execute(
                            "CREATE TABLE Boards "
                            + "(board_id INT NOT NULL CONSTRAINT BOA_PK PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                            + "name CHAR(20))");
                }
            }
            try (ResultSet resultRows = databaseMetadata.getTables(null, null, "ROW", null);
                    Statement statement = dataConn.createStatement();) {
                if (!resultRows.next()) {
                    statement.execute(
                            "CREATE TABLE Row "
                            + "(row_id INT NOT NULL CONSTRAINT ROW_PK PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                            + "board_id int not null references Boards(board_id), "
                            + "rowNumber INT NOT NULL, "
                            + "Y0 INT, "
                            + "Y1 INT, "
                            + "Y2 INT, "
                            + "Y3 INT, "
                            + "Y4 INT, "
                            + "Y5 INT, "
                            + "Y6 INT, "
                            + "Y7 INT, "
                            + "Y8 INT) ");
                }
            }
            dataConn.commit();
        } catch (SQLException e1) {
            logger.debug(e1, e1);
            try {
                if (dataConn != null) {
                    dataConn.rollback();
                }
            } catch (SQLException e2) {

            }

        }
    }

    public void close() {
        try {
            dataConn.close();
        } catch (SQLException e) {
            // throw new JdbcException("closeDaoMsg", e);
        }
    }

    @Override
    public SudokuBoard read() {
        SudokuBoard result = new SudokuBoard();
        try (PreparedStatement stmt = dataConn.prepareStatement(readQuery)) {
            stmt.setString(1, boardName);
            try (ResultSet resultSet = stmt.executeQuery();) {
                while (resultSet.next()) {
                    int row = resultSet.getInt("rowNumber");
                    for (int i = 0; i < 9; i++) {
                        result.setForTests(resultSet.getInt("Y" + i), row, i);
                    }
                }
            }
            dataConn.commit();
        } catch (SQLException e) {
            logger.debug(e, e);
            try {
                if (dataConn != null) {
                    dataConn.rollback();
                }
            } catch (SQLException e2) {
                // throw new JdbcException("readDaoMsg", e2);
            }
            return null;
        }// catch (SudokuFieldException e) {
        //  throw new JdbcException("readDaoMsg", e);
        //   }
        return result;
    }

    @Override
    public boolean write(final SudokuBoard obj) {
        boolean exist = false;
        int idBoard = -1;
        try (PreparedStatement stmt = dataConn.prepareStatement(checkExistanceQuery);) {
            stmt.setString(1, boardName);
            try (ResultSet resultSet = stmt.executeQuery();) {
                if (resultSet.next()) {
                    idBoard = resultSet.getInt("board_id");
                    exist = true;
                }
            }
            dataConn.commit();
        } catch (SQLException e) {
            logger.debug(e, e);
            try {
                if (dataConn != null) {
                    dataConn.rollback();
                }
            } catch (SQLException e2) {
                //    throw new JdbcException("writeDaoMsg", e2);
            }
        }
        if (exist) {
            try {
                for (int i = 0; i < 9; i++) {
                    try (PreparedStatement stmt = dataConn.prepareStatement(rowUpdateQuery)) {
                        for (int j = 0; j < 9; j++) {
                            stmt.setInt(j + 1, obj.get(i, j));
                        }
                        stmt.setInt(10, idBoard);
                        stmt.setInt(11, i);
                        stmt.executeUpdate();
                    }
                }
                dataConn.commit();
            } catch (SQLException e) {
                logger.debug(e, e);
                try {
                    if (dataConn != null) {
                        dataConn.rollback();
                    }
                } catch (SQLException e2) {
                    //  throw new JdbcException("writeDaoMsg2", e2);
                }
            }
        } else {
            try {
                try (PreparedStatement stmt = dataConn.prepareStatement(boardWriteQuery, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, boardName);
                    stmt.executeUpdate();
                    ResultSet keys = stmt.getGeneratedKeys();
                    if (keys.next()) {
                        idBoard = keys.getInt(1);
                    }
                }
                for (int i = 0; i < 9; i++) {
                    try (PreparedStatement stmt = dataConn.prepareStatement(rowWriteQuery)) {

                        stmt.setInt(1, idBoard);
                        stmt.setInt(2, i);
                        for (int j = 0; j < 9; j++) {
                            stmt.setInt(j + 3, obj.get(i, j));
                        }
                        stmt.executeUpdate();
                    }
                }
                dataConn.commit();
            } catch (SQLException e) {
                logger.debug(e, e);
                try {
                    if (dataConn != null) {
                        dataConn.rollback();
                    }
                } catch (SQLException e2) {
                    //  throw new JdbcException("writeDaoMsg3", e2);
                }
            }

        }
        return true; //dopisane nasze
    }
    /*
    public void connect() throws SQLException {
        // Connectivity settings - change according to your discretion.
        final String port = "1433";
        final String URL = "jdbc:sqlserver://localhost;databaseName=biblioteka;integratedSecurity=true";
        final String database = "biblioteka";

        // Try to connect with the database.
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(URL);
            PreparedStatement stmt = conn.prepareStatement("select * from pracownicy");
            ResultSet r = stmt.executeQuery();
            System.out.println(r.getString(1));

        } catch (ClassNotFoundException | SQLException e) {

        }
    }

     */
}
