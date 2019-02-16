/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccg.ancientaliens.server;

import com.ccg.ancientaliens.game.*;
import com.ccg.ancientaliens.protocol.ServerMessages.NewGame;
import com.ccg.ancientaliens.protocol.ServerMessages.ServerMessage;
import com.ccg.ancientaliens.protocol.Types;
import helpers.Hashmap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.EncodeException;

/**
 *
 * @author pseudo
 */
public class GameServer {
    
    public Hashmap<String, GeneralEndpoint> playerConnections;
    public Hashmap<UUID, Table> tables;
    public Hashmap<UUID, Game> games;
    public ArrayList<String> chatLog;
    public ArrayList<String> gameQueue;

    public GameServer() {
        playerConnections = new Hashmap<>();
        tables = new Hashmap<>();
        chatLog = new ArrayList<>();
        games = new Hashmap<>();
    }


    void activateCard(UUID gameID, String username, UUID fromString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void concede(UUID gameID, String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    void mulligan(UUID gameID, String username) {
        games.get(gameID).mulligan(username);
        games.get(gameID).updatePlayers();
    }
    
    void keep(UUID gameID, String username) {
        games.get(gameID).keep(username);
        games.get(gameID).updatePlayers();
    }
    
    void pass(UUID gameID, String username) {
        games.get(gameID).pass(username);
        games.get(gameID).updatePlayers();
    }
    
    void studyCard(UUID gameID, String username, UUID cardID, List<Types.Knowledge> knowledgeList) {
        games.get(gameID).studyCard(username, cardID);
        games.get(gameID).updatePlayers();
    }
    
    void playCard(UUID gameID, String username, UUID cardID) {
        games.get(gameID).playCard(username, cardID);
        games.get(gameID).updatePlayers();
    }

    void addConnection(String username, GeneralEndpoint connection) {
        playerConnections.put(username, connection);
    }

    void removeConnection(String username) {
        playerConnections.remove(username);
        gameQueue.remove(username);
    }

    void addGameConnection(UUID gameID, String username, GameEndpoint connection) {
        games.get(gameID).addConnection(username, connection);
    }
    
    void removeGameConnection(UUID gameID, String username) {
        games.get(gameID).removeConnection(username);
    }

    void joinTable(String username) {
        if (gameQueue.isEmpty()){
            System.out.println("Adding " + username + " to game queue!");
            gameQueue.add(username);
        } else {
            if(gameQueue.get(0).equals(username))
                return;
            String p1 = gameQueue.remove(0);
            System.out.println("Starting a new game with " + username + " and " + p1);
            Game g = new Game(p1, username);
            games.put(g.id, g);
            try {
                playerConnections.get(p1).send(ServerMessage.newBuilder()
                        .setNewGame(NewGame.newBuilder()
                                .setGame(g.toGameState(p1))));
                playerConnections.get(username).send(ServerMessage.newBuilder()
                        .setNewGame(NewGame.newBuilder()
                                .setGame(g.toGameState(username))));
            } catch (IOException ex) {
                Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EncodeException ex) {
                Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
