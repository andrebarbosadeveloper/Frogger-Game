package edu.ufp.inf.sd.rmi.projecto.client;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import edu.ufp.inf.sd.rmi.projecto.client.frogger.Main;
import edu.ufp.inf.sd.rmi.projecto.server.*;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;


import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.exit;
import static java.lang.System.setOut;

/**
 * <p>
 * Title: Projecto SD</p>
 * <p>
 * Description: Projecto apoio aulas SD</p>
 * <p>
 * Copyright: Copyright (c) 2017</p>
 * <p>
 * Company: UFP </p>
 *
 * @author Rui S. Moreira
 * @version 3.0
 */
public class GameClient {

    /**
     * Context for connecting a RMI client MAIL_TO_ADDR a RMI Servant
     */
    private SetupContextRMI contextRMI;
    /**
     * Remote interface that will hold the Servant proxy
     */
    private GameFactoryRI gameFactoryRI;

    private GameFactoryImpl gameFactory;

    private ObserverRI observerRI;

    public static void printMenu(String[] options){
        for (String option : options){
            System.out.println(option);
        }
        System.out.print("Escolha uma opção: ");
    }
    private static String[] options = {"1- Criar um jogo.",
            "2- Juntar-me a um jogo já criado.",
            "3- Sair",
    };


    public static void main(String[] args) {
        if (args != null && args.length < 2) {
            System.err.println("usage: java [options] edu.ufp.sd.inf.rmi._01_helloworld.server.HelloWorldClient <rmi_registry_ip> <rmi_registry_port> <service_name>");
            System.exit(-1);
        } else {
            //1. ============ Setup client RMI context ============
            GameClient hwc=new GameClient(args);
            //2. ============ Lookup service ============
            hwc.lookupService();
            //3. ============ Play with service ============
            hwc.playService();
        }
    }

    public GameClient(String args[]) {
        try {
            //List ans set args
            SetupContextRMI.printArgs(this.getClass().getName(), args);
            String registryIP = args[0];
            String registryPort = args[1];
            String serviceName = args[2];
            //Create a context for RMI setup
            contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private Remote lookupService() {
        try {
            //Get proxy MAIL_TO_ADDR rmiregistry
            Registry registry = contextRMI.getRegistry();
            //Lookup service on rmiregistry and wait for calls
            if (registry != null) {
                //Get service url (including servicename)
                String serviceUrl = contextRMI.getServicesUrl(0);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going MAIL_TO_ADDR lookup service @ {0}", serviceUrl);
                
                //============ Get proxy MAIL_TO_ADDR HelloWorld service ============
                gameFactoryRI = (GameFactoryRI) registry.lookup(serviceUrl);
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
                //registry = LocateRegistry.createRegistry(1099);
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return gameFactoryRI;
    }
    
    private void playService() {
        try {
            //============ Call HelloWorld remote service ============

            Scanner username = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Introduza o nome:");
            String userName = username.nextLine();  // Read user input
            Scanner password = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Introduza a password:");
            String passWord = password.nextLine();  // Read user input



            boolean r = this.gameFactoryRI.register("pedro", "123");
            boolean r1 = this.gameFactoryRI.register("andre", "123");
            checkRegister(r);
            checkRegister(r1);

            //boolean r = this.gameFactoryRI.register(userName, passWord);
            //checkRegister(r);

            //GameSessionRI gameSessionRI = this.gameFactoryRI.login("pedro", "123");

            GameSessionRI gameSessionRI = this.gameFactoryRI.login(userName, passWord);


            String token = "";
            try {
                Algorithm algorithm = Algorithm.HMAC256("secret");
                token = JWT.create()
                        .withIssuer(userName)
                        .sign(algorithm);

            } catch (JWTCreationException exception){
                //Invalid Signing configuration / Couldn't convert Claims.
            }

            System.out.println(token);

            gameSessionRI.setUsername(userName);
            gameSessionRI.setToken(token);


            int option = 1;
            while (option!=4){
                printMenu(options);
                    option = username.nextInt();
                    switch (option) {
                        case 1 -> {
                            assert gameSessionRI != null;
                            criarjogo(gameSessionRI,token);

                        }
                        case 2 -> {
                            escolherjogo(gameSessionRI, token);
                        }
                        case 3 -> exit(0);
                    }
            }

            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going MAIL_TO_ADDR finish, bye. ;)");
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Verifica se o User ja se encontra registado na DBMockup
     * @param r
     */
    private void checkRegister(boolean r) {
        if(r) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "User registado com sucesso!");
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "User nao registado! Ja existe um User com esse nome");
        }
    }

    /**
     * Metodo para criar um jogo
     * @param gameSessionRI
     * @param token
     * @return
     * @throws RemoteException
     */
    private static Game criarjogo(GameSessionRI gameSessionRI, String token) throws RemoteException {
        Scanner difficulty = new Scanner(System.in);
        System.out.println("Introduza a dificuldade:");
        int dif = difficulty.nextInt();
        System.out.println("Introduza o numero maximo de jogadores:");
        int max = difficulty.nextInt();

        //chama o Create Game
        ObserverRI observerRI = new ObserverImpl(0);

        Game game = gameSessionRI.createGame(1,dif, max, observerRI, token);
        observerRI.setFroggerGameRI(game.getFroggerRI());

        while (observerRI.getFroggerGameRI().getFroggers().size() != game.getMaxPlayers()){
        }

        Main f = new Main(game, observerRI);
        f.run();

        System.out.println("Jogo criado com sucesso!");

        return game;
    }

    /**
     * Metodo para escolher um jogo para se juntar
     * @param gameSessionRI
     * @param token
     * @throws RemoteException
     */
    private static void escolherjogo(GameSessionRI gameSessionRI, String token) throws RemoteException{
        System.out.println("Lista dos jogos disponiveis:");
        //chama o listFroggerGames
        assert gameSessionRI != null;
        ArrayList<Game> games =  gameSessionRI.listFroggerGames();
        for (Game game : games){
            System.out.println(game.getId());
        }

        Scanner escolherJogo = new Scanner(System.in);
        System.out.println("Escolha um jogo para se juntar:");
        int jogo = escolherJogo.nextInt();

        ObserverRI observerRI = new ObserverImpl(1);

        //chama o Choose Game
        Game game = gameSessionRI.chooseGame(jogo, observerRI, token);
        observerRI.setFroggerGameRI(game.getFroggerRI());


        while (observerRI.getFroggerGameRI().getFroggers().size() != game.getMaxPlayers()){
        }

        System.out.println("Bom Jogo!");
        Main f = new Main(game, observerRI);
        f.run();


    }

}
