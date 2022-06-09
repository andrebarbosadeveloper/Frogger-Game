package edu.ufp.inf.sd.rmi.projecto.server;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.ufp.inf.sd.rmi.projecto.client.ObserverImpl;
import edu.ufp.inf.sd.rmi.projecto.client.ObserverRI;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI {

    GameFactoryImpl gameFactoryImpl;
    private  String username;
    private ArrayList<Game> gamesArray;
    String token ;

    protected GameSessionImpl(GameFactoryImpl gameFactory) throws RemoteException {
        super();
        this.gameFactoryImpl = gameFactory;

    }

    /**
     * Metodo para criar um um jogo
     * @param id
     * @param difficulty
     * @param maxPlayers
     * @param observerRI
     * @param token
     * @return
     * @throws RemoteException
     */
    @Override
    public Game createGame(int id, int difficulty, int maxPlayers, ObserverRI observerRI, String token) throws RemoteException{

        DecodedJWT jwt = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret"); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(username)
                    .build(); //Reusable verifier instance
            jwt = verifier.verify(token);
            System.out.println(jwt.getToken());
        } catch (JWTVerificationException | UnsupportedEncodingException exception){
            //Invalid signature/claims
        }

        assert jwt != null;
        if (jwt.getToken().equals(token)){

            FroggerGameRI froggerGameRI = new FroggerGameImpl();
            Game game = gameFactoryImpl.dbMockup.insert(difficulty, maxPlayers, froggerGameRI);

            game.getFroggerRI().attach(observerRI);

            System.out.println("Jogo(FroggerGame) criado com sucesso!");
            return game;
        }
        else{
            System.out.println("Token errado!");
            return null;
        }
    }

    /**
     * Metodo para escolher e se juntar a um jogo
     * @param idG
     * @param observerRI
     * @param token
     * @return
     * @throws RemoteException
     */
    @Override
    public Game chooseGame(int idG, ObserverRI observerRI, String token) throws RemoteException{

        DecodedJWT jwt = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret"); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(username)
                    .build(); //Reusable verifier instance
            jwt = verifier.verify(token);
            System.out.println(jwt.getToken());
        } catch (JWTVerificationException | UnsupportedEncodingException exception){
            //Invalid signature/claims
        }

        assert jwt != null;
        if (jwt.getToken().equals(token)){
            Game game = gameFactoryImpl.dbMockup.select(idG);
            game.getFroggerRI().attach(observerRI);
            return game;        }
        else{
            System.out.println("Token errado!");
            return null;
        }
    }

    /**
     * Metodo para listar os jogos
     * @return
     * @throws RemoteException
     */
    @Override
    public ArrayList<Game> listFroggerGames() throws RemoteException{
         return this.gameFactoryImpl.dbMockup.printGames();
    }

    /**
     * Metodo para efetuar Logout
     * @throws RemoteException
     */
    @Override
    public void logout() throws RemoteException {
         this.gameFactoryImpl.user_session.remove(username);
    }


    @Override
    public String getToken() throws RemoteException {
        return token;
    }

    @Override
    public void setToken(String token) throws RemoteException {
        this.token = token;
    }

    public String getUsername() throws RemoteException{
        return username;
    }

    public void setUsername(String username)throws RemoteException {
        this.username = username;
    }
}
