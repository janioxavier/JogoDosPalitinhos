/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import conexao.JogoPalitinhoImpl;
 
public class StandaloneServer implements Serializable {
    private static final long serialVersionUID = -1942995922934818734L;
 
    private JogoPalitinhoImpl server;
    private Registry registry;
 
    public void init() throws RemoteException {
        server = new JogoPalitinhoImpl();
        registry = LocateRegistry.createRegistry(1099);
    }
 
    public void connect() throws RemoteException, MalformedURLException {
        init();
        registry.rebind("server", server);
    }
 
    public void disconnect() throws AccessException, RemoteException,
            NotBoundException {
        UnicastRemoteObject.unexportObject(registry, true);
    }
 
    public static void main(String[] args) {
        StandaloneServer server = new StandaloneServer();
 
        try {
            server.connect();
        } catch (RemoteException e) {       
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
 
}
