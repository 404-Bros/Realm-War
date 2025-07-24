package log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import controllers.GameController;

public class LogHandler {
    private static final String LOG_DIR = "logs";
    private static final String DATA_FILE = LOG_DIR + "/data.txt";
    private static final int MAX_LOG_FILES = 10;

    private BufferedWriter writer;
    private int currentLogId;
    private int currentCount;
    private GameController gameController;

    public LogHandler() {
        try {
            File dir = new File(LOG_DIR);
            if (!dir.exists()) dir.mkdirs();

            readData();

            currentLogId++;

            if (currentCount >= MAX_LOG_FILES) {
                deleteOldestLog();
                currentCount--;
            }

            File logFile = new File(LOG_DIR, "log_" + currentLogId + ".txt");
            writer = new BufferedWriter(new FileWriter(logFile, true));
            log("game started");

            currentCount++;
            saveData();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String message) {
        try {
            writer.write(message + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            log("game ended");
            if (writer != null) writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteOldestLog() {
        int oldestId = currentLogId - currentCount;
        File oldestLog = new File(LOG_DIR, "log_" + oldestId + ".txt");
        if (oldestLog.exists()) {
            oldestLog.delete();
        }
    }

    private void readData() {
        File file = new File(DATA_FILE);
        currentLogId = 0;
        currentCount = 0;

        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("lastId=")) {
                    currentLogId = Integer.parseInt(line.substring("lastId=".length()));
                } else if (line.startsWith("count=")) {
                    currentCount = Integer.parseInt(line.substring("count=".length()));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            writer.write("lastId=" + currentLogId + "\n");
            writer.write("count=" + currentCount + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logDependsOnPlayer(String message) {
        if (gameController != null) {
            int currentPlayer = gameController.getGameState().getCurrentPlayerTurn();
            String playerName = currentPlayer == 0 ? gameController.getPlayer1().getName() : gameController.getPlayer2().getName();
            log(playerName + ": " + message);
        } else {
            log("Unknown Player: " + message);
        }
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
}