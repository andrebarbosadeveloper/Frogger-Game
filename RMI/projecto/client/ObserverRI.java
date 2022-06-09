package edu.ufp.inf.sd.rmi.projecto.client;

import edu.ufp.inf.sd.rmi.projecto.client.frogger.Main;
import edu.ufp.inf.sd.rmi.projecto.server.FroggerGameRI;
import edu.ufp.inf.sd.rmi.projecto.server.State;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ObserverRI extends Remote {
    void updateGameState(State state) throws RemoteException;
    int getId() throws RemoteException;
    void setId(int id) throws RemoteException;
    FroggerGameRI getFroggerGameRI() throws RemoteException;
    void setFroggerGameRI(FroggerGameRI froggerGameRI) throws RemoteException;
    void setMain(Main main) throws RemoteException;


}
