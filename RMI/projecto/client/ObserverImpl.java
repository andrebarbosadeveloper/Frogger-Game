package edu.ufp.inf.sd.rmi.projecto.client;

import edu.ufp.inf.sd.rmi.projecto.client.frogger.Main;
import edu.ufp.inf.sd.rmi.projecto.server.FroggerGameRI;
import edu.ufp.inf.sd.rmi.projecto.server.State;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI {

    private int id;
    private FroggerGameRI froggerGameRI;
    private Main main;

    public ObserverImpl(int id) throws RemoteException {
        this.id = id;
    }

    /**
     * Efetua o update do estado do jogo
     * @param state
     * @throws RemoteException
     */
    @Override
    public void updateGameState(State state) throws RemoteException {
        System.out.println(main);
        this.main.recebeState(state);
    }

    public void setObserverRIState() throws RemoteException{
        this.main.froggerKeyboardHandler();
    }


    @Override
    public int getId() {
        return id;
    }
    @Override
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public FroggerGameRI getFroggerGameRI() {
        return froggerGameRI;
    }
    @Override
    public void setFroggerGameRI(FroggerGameRI froggerGameRI) {
        this.froggerGameRI = froggerGameRI;
    }
    @Override
    public void setMain(Main main) throws RemoteException{
        this.main = main;
    }
}


