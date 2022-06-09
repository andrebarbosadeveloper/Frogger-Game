package edu.ufp.inf.sd.rmi.projecto.server;

import edu.ufp.inf.sd.rmi.projecto.client.ObserverRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class FroggerGameImpl extends UnicastRemoteObject implements FroggerGameRI {

    private int id;
    private State froggerGameState;
    private ArrayList<ObserverRI> froggers = new ArrayList<>();
    private User user;



    protected FroggerGameImpl(int id, User user) throws RemoteException{
        this.id = id;
        this.user = user;
    }
    protected FroggerGameImpl() throws RemoteException{
        this.froggerGameState = new State(null, null, null, null, null);
    }


    /**
     * Metodo para fazer attach de um frogger
     * @param frogger
     * @throws RemoteException
     */
    @Override
    public void attach(ObserverRI frogger) throws RemoteException {
            this.froggers.add(frogger);
    }

    /**
     * Metodo para fazer detach de um frogger
     * @param frogger
     * @throws RemoteException
     */
    @Override
    public void detach(ObserverRI frogger) throws RemoteException{
        this.froggers.remove(frogger);
    }

    /**
     * Notifica os Observers
     * @param state
     * @throws RemoteException
     */
    @Override
    public void notifyObserver(State state) throws RemoteException {
        for (ObserverRI observerRI : froggers) {
            observerRI. updateGameState(state);
        }
    }

    //Retorna o id do Frogger
    @Override
    public int getFrogger() throws RemoteException {
        return froggers.size() + 1;
    }

    @Override
    public int getId() throws RemoteException {
        return id;
    }

    public void setId(int id) throws RemoteException {
        this.id = id;
    }

    @Override
    public State getFroggerGameState() throws RemoteException {
        return froggerGameState;
    }

    @Override
    public void setFroggerGameState(State froggerGameState) throws RemoteException {
        this.froggerGameState = froggerGameState;
        notifyObserver(froggerGameState);
    }

    @Override
    public ArrayList<ObserverRI> getFroggers() throws RemoteException {
        return froggers;
    }
    @Override
    public void setFroggers(ArrayList<ObserverRI> froggers) throws RemoteException {
        this.froggers = froggers;
    }

    public User getUser() throws RemoteException {
        return user;
    }

    public void setUser(User user) throws RemoteException {
        this.user = user;
    }

}
