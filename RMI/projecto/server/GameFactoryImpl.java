package edu.ufp.inf.sd.rmi.projecto.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameFactoryImpl extends UnicastRemoteObject implements GameFactoryRI {

    public DBMockup dbMockup;
    public HashMap<String, GameSessionImpl> user_session;

    protected GameFactoryImpl() throws RemoteException {
        super();
        this.dbMockup = new DBMockup();//base de dados
        this.user_session = new HashMap<>();//mapeamento de users e sessoes
    }

    /**
     * Metodo para efetuar o login de um utilizador
     * @param username
     * @param pwd
     * @return
     * @throws RemoteException
     */
    public GameSessionRI login(String username, String pwd) throws RemoteException {
        if (this.dbMockup.exists(username,pwd)){
            if (user_session.containsKey(username)){
                System.out.println("User efetuou Login com sucesso na sessao");
                return user_session.get(username);
            }
            else{
                GameSessionImpl gameSessionImpl = new GameSessionImpl(this) ;
                user_session.put(username, gameSessionImpl);
                System.out.println("Sessao criada para o User com sucesso");
                return gameSessionImpl;
            }
        }
        else {
            System.out.println("Erro ao efetuar o Login! As credenciais nao correspondem");
            return null;
        }

    }
    /**
     * Regista utilizador na DBMockUp
     * @param username username a registar
     * @param pwd password a registar
     * @return true - Registado | false - Nao registado
     * @throws RemoteException
     */
    @Override
    public boolean register(String username, String pwd) throws RemoteException {
        return this.dbMockup.register(username, pwd);
    }

    /**
     * Apaga sessao do utilizador
     * @param u
     * @throws RemoteException
     */
    @Override
    public void destroySession (String u) throws RemoteException {
        this.user_session.remove(u);
    }


    public DBMockup getDbMockup() throws RemoteException {
        return dbMockup;
    }
}
