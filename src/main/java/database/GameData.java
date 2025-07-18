package database;

import javax.swing.JOptionPane;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import controllers.GameController;
import models.GameState;
import models.Player;
import utils.GsonFactory;
import views.GameFrame;

public class GameData {
    private static GameState gameState;
    private static Player player1;
    private static Player player2;
    private static int timer;





    public static void saveGame(String saveName) throws JsonProcessingException{
//        ObjectMapper mapper = new ObjectMapper();
//        String gameStateJson = mapper.writeValueAsString(gameState);
//        String player1Json = mapper.writeValueAsString(player1);
//        String player2Json = mapper.writeValueAsString(player2);
        Gson gson = GsonFactory.create();
        String gameStateJson = gson.toJson(gameState);
        String player1Json = gson.toJson(player1);
        String player2Json = gson.toJson(player2);
        DatabaseHandler.insertValues(saveName, gameStateJson, player1Json, player2Json, timer);
    }

    public static boolean isOkSaveName(String saveName){
        for(String save : DatabaseHandler.getSaveNames()){
            if(save.equals(saveName)){
                return false;
            }
        }
        return true;
    }

    public static void saveJoption(GameFrame gameFrame, GameController gameController){
            gameState = gameController.getGameState();
            player1 = gameController.getPlayer1();
            player2 = gameController.getPlayer2();
            timer = gameController.getTimeLeft();
            



            String player1Name = player1.getName();
            String player2Name = player2.getName();

            String saveName = JOptionPane.showInputDialog(gameFrame,"please enter file name:", "Save", JOptionPane.OK_CANCEL_OPTION);
            if(saveName == null){
                return;
            }
            if(saveName.isBlank()){
                JOptionPane.showMessageDialog(null, "Save name cannot be empty.");
                saveJoption(gameFrame, gameController);
                return;
            }
            else if(!isOkSaveName(saveName)){
                JOptionPane.showMessageDialog(null, "Save name already exists.");
                saveJoption(gameFrame, gameController);
                return;
            }else{
                for(int i = 0; i < saveName.length(); i++){
                    if(saveName.charAt(i) == ':'){
                        JOptionPane.showMessageDialog(
                            null,
                            "🎉 Congratulations! You found a secret Easter Egg!\nYou're awesome, keep exploring! 😄",
                            "Secret Easter Egg 🤫",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                        saveJoption(gameFrame, gameController);
                        return;
                    }
                }
                try {
                    saveGame(saveName);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            
            gameController.getPauseFrame().dispose();

            gameFrame.remove(gameFrame.getMainInfoPanel());
            gameFrame.remove(gameFrame.getActionPanel());
            gameFrame.remove(gameFrame.getGamePanel());

            gameFrame.add(gameFrame.getMenuPanel());
            gameFrame.pack();
            gameFrame.setLocationRelativeTo(null);
            gameFrame.revalidate();
            gameFrame.repaint();
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }
   public static void setGameState(GameState gameState) {
       GameData.gameState = gameState;
   }
    public static void setPlayer1(Player player1) {
        GameData.player1 = player1;
    }
    public static void setPlayer2(Player player2) {
        GameData.player2 = player2;
    }
    public int getTimer() {
        return timer;
    }
    public GameState getGameState() {
        return gameState;
    }
    public static Player getPlayer1() {
        return player1;
    }
    public static Player getPlayer2() {
        return player2;
    }
}
