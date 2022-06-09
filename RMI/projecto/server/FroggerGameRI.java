package edu.ufp.inf.sd.rmi.projecto.server;

import edu.ufp.inf.sd.rmi.projecto.client.ObserverRI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface FroggerGameRI extends Remote {

    void attach(ObserverRI frogger) throws RemoteException;
    void detach(ObserverRI frogger) throws RemoteException;
    void notifyObserver(State state) throws RemoteException;
    int getFrogger() throws RemoteException;
    int getId() throws RemoteException;
    State getFroggerGameState() throws RemoteException;
    void setFroggerGameState(State froggerGameState) throws RemoteException;
    ArrayList<ObserverRI> getFroggers() throws RemoteException;
    void setFroggers(ArrayList<ObserverRI> froggers) throws RemoteException;
}
