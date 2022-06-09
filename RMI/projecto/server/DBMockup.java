package edu.ufp.inf.sd.rmi.projecto.server;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * This class simulates a DBMockup for managing users and books.
 *
 * @author rmoreira
 *
 */
public class DBMockup {

    private final ArrayList<User> users;
    private ArrayList<Game> games;


    /**
     * This constructor creates and inits the database with some books and users.
     */
    public DBMockup() {
        this.users = new ArrayList<>();
        this.games = new ArrayList<>();
    }


    /**
     * Registers a new user.
     * 
     * @param u username
     * @param p passwd
     */
    public boolean register(String u, String p) {
        if (getUser(u) == null) {
            users.add(new User(u, p));
            System.out.println("User Registado com sucesso!");
            return true;
        }
        return false;
    }

    /**
     * Checks the credentials of an user.
     * 
     * @param u username
     * @param p passwd
     * @return
     */
    public boolean exists(String u, String p) {
        for (User usr : this.users) {
            if (usr.getUname().compareTo(u) == 0 && usr.getPword().compareTo(p) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Seleciona o jogo
     * @param idG
     * @return
     * @throws RemoteException
     */
    public Game select(int idG) throws RemoteException{
        for (Game g : games) {
            if (g.getId() == idG) {
                System.out.println("Dificuldade: "  + g.getDifficulty() + " && " + "Numero Jogadores Maximo: " + g.getMaxPlayers());
                return g;
            }
        }
        return null;
    }

    /**
     * Insere o jogo no ArrayList de jogos
     * @param dif
     * @param max
     * @param froggerGameRI
     * @return
     * @throws RemoteException
     */
    public Game insert(int dif, int max, FroggerGameRI froggerGameRI) throws RemoteException{
        Game game = new Game(dif, max, froggerGameRI);
        if (games.size() < 1){
            game.id = 1;
        }
        else{
           game.id = games.size() + 1;
        }
        games.add(game);
        return game;
    }

    /**
     * Imprime os jogos existentes
     * @return
     * @throws RemoteException
     */
    public ArrayList<Game> printGames() throws RemoteException{
        for (Game game : games)
            System.out.println(game.id);
        return games;
    }



    public User getUser(String username) {
        for(User u : this.users) {
            if(u.getUname().compareTo(username) == 0) {
                return u;
            }
        }
        return null;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }
}
