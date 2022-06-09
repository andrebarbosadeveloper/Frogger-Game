package edu.ufp.inf.sd.rmi.projecto.server;

import edu.ufp.inf.sd.rmi.projecto.client.ObserverRI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GameSessionRI extends Remote {
    public Game createGame(int id, int difficulty, int maxPlayers, ObserverRI observerRI, String token) throws RemoteException;
    Game chooseGame(int idG, ObserverRI observerRI, String token) throws RemoteException;

    public ArrayList<Game> listFroggerGames() throws RemoteException;

    void logout() throws RemoteException;

    String getToken() throws RemoteException;
    void setToken(String token) throws RemoteException;
    String getUsername() throws RemoteException;
    void setUsername(String username) throws RemoteException;

    //public FroggerGameRI getFroggerGameId(int id) throws RemoteException;
}
