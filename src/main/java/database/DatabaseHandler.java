package database;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

import controllers.GameController;
import models.GameState;
import models.Player;
import utils.GsonFactory;

public class DatabaseHandler {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "";

    public static void createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS game_saves (" + 
            "id SERIAL PRIMARY KEY," +
            "save_name VARCHAR(100) NOT NULL," +
            "game_state JSONB," +
            "player1 JSONB," +
            "player2 JSONB," +
            "timer INTEGER);";

        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertValues(String saveName, String gameStateJson, String player1Json, String player2Json, int timer){
        String insertSql = 
            "INSERT INTO game_saves (save_name, game_state, player1, player2, timer)" +
            "VALUES (?, ?::json, ?::json, ?::json, ?)";

        
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(insertSql);
            pstmt.setString(1, saveName);
            pstmt.setString(2, gameStateJson);
            pstmt.setString(3, player1Json);
            pstmt.setString(4, player2Json);
            pstmt.setInt(5, timer);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Game saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getGameSaves(){
        ArrayList<String> gameSaves = new ArrayList<>();
        String sql = "SELECT * FROM game_saves";
        
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String saveName = rs.getString("save_name");
                String player1Json = rs.getString("player1");
                String player2Json = rs.getString("player2");
                Gson gson = GsonFactory.create();
                Player player1 = gson.fromJson(player1Json, Player.class);
                Player player2 = gson.fromJson(player2Json, Player.class);
                String player1name = player1.getName();
                String player2name = player2.getName();


                String x = new String(saveName + ": " + player1name + " vs " + player2name);
                gameSaves.add(x);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameSaves;

    }

    public static ArrayList<String> getSaveNames(){
        ArrayList<String> saveNames = new ArrayList<>();
        String sql = "SELECT * FROM game_saves";
        
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String saveName = rs.getString("save_name");
                saveNames.add(saveName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return saveNames;

    }

    public static void deleteBySaveName(String saveName) {
        String deleteSql = "DELETE FROM game_saves WHERE save_name = ?";

        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(deleteSql);
            pstmt.setString(1, saveName);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(null, "Record deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "there is sth wrong...");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    
    }
    
    public static void loadGame(String saveName, GameController gc){
        String sql = "SELECT * FROM game_saves WHERE save_name = ?";

        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, saveName);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            String gameStateJson = rs.getString("game_state");
            String player1Json = rs.getString("player1");
            String player2Json = rs.getString("player2");
            int timer = rs.getInt("timer");
            Gson gson = GsonFactory.create();

            GameState gameState = gson.fromJson(gameStateJson, GameState.class);
            Player player1 = gson.fromJson(player1Json, Player.class);
            Player player2 = gson.fromJson(player2Json, Player.class);

            gc.setGameState(gameState);
            gc.setPlayer1(player1);
            gc.setPlayer2(player2);
            gc.setTimeLeft(timer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }       
}
