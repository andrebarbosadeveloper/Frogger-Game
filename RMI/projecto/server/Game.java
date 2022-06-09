package edu.ufp.inf.sd.rmi.projecto.server;

import edu.ufp.inf.sd.rmi.projecto.client.ObserverRI;

import java.io.Serializable;

public class Game implements Serializable {

    int id = 0;
    private int maxPlayers;
    private int difficulty;
    private FroggerGameRI froggerGameRI;
    private ObserverRI observerRI;
    GameFactoryImpl gameFactoryImpl;


    public Game(int difficulty,int maxPlayers, FroggerGameRI froggerGameRI) {
        this.difficulty = difficulty;
        this.maxPlayers = maxPlayers;
        this.froggerGameRI = froggerGameRI;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public FroggerGameRI getFroggerRI() {
        return froggerGameRI;
    }

    public void setFroggerRI(FroggerGameRI froggerRI) {
        this.froggerGameRI = froggerRI;
    }

    public ObserverRI getObserverRI() {
        return observerRI;
    }
}
