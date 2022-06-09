/**
 * <p>
 * Title: Projecto SD</p>
 * <p>
 * Description: Projecto apoio aulas SD</p>
 * <p>
 * Copyright: Copyright (c) 2011</p>
 * <p>
 * Company: UFP </p>
 *
 * @author Rui Moreira
 * @version 2.0
 */
package edu.ufp.inf.sd.rabbitmqservices.projecto.chatgui;

import com.rabbitmq.client.BuiltinExchangeType;
import edu.ufp.inf.sd.rabbitmqservices.projecto.frogger.Main;
import edu.ufp.inf.sd.rabbitmqservices.util.RabbitUtils;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author rjm
 */
public class ObserverGuiClient extends JFrame {

    private Observer observer;

    public Main main;


    /**
     * Creates new form ChatClientFrame
     *
     * @param args
     */
    public ObserverGuiClient(String args[]) throws IOException, TimeoutException {
        try {
            //1. Init the GUI components

            Logger.getLogger(this.getClass().getName()).log(Level.INFO, " After initComponents()...");

            RabbitUtils.printArgs(args);

            //Read args passed via shell command
            String host = args[0];
            int port = Integer.parseInt(args[1]);
            String exchangeName = args[2];
            //int idJogo= Integer.parseInt(args[3]);
            int idJogador = Integer.parseInt(args[3]);
            //String general=args[5];

            //2. Create the _05_observer object that manages send/receive of messages to/from rabbitmq
            this.observer = new Observer(this, host, port, "guest", "guest", idJogador, exchangeName, BuiltinExchangeType.FANOUT, "UTF-8");
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, " After initObserver()...");

            while (observer.frogs.size() < 2) {
                System.out.println("Espera!");
            }

            this.main = new Main(this.observer, idJogador);

            main.run();


        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    //================================================ BEGIN TO CHANGE ================================================

    /**
     * Sends msg through the _05_observer to the exchange where all observers are binded
     *
     * @param msgToSend
     */
    private void sendMsg(String user, String msgToSend) {
        try {
            msgToSend = "[" + user + "]: " + msgToSend;
            this.observer.sendMessage(msgToSend);
        } catch (IOException ex) {
            Logger.getLogger(ObserverGuiClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //================================================ END TO CHANGE ================================================


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException, TimeoutException {

        new ObserverGuiClient(args);

    }

    public Main getMain() {
        return main;
    }
}
