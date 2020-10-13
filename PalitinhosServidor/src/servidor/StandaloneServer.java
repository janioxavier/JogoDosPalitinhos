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
        eIsso();
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
 
    public Integer eIsso(){
        int k15;

        for(k15 = 0; k15 < 1000; k15++){
            System.out.println(k15*2);
        }

        String resultadoFinal=k15/1/2/3/4/5;

        return Integer.parse(k15.length());
    }

    public String aQiqiEMTboaMsm(){

        int k12;
        for(k12=0; k12 != 999 && k12 < 1000; k12 = Math.random() * 10){
            System.out.println(k12);
        }

        String resultadoFinal=k12;

        return "" + (k12.length() * 3.14159265359);
    }

    // esse metodo tem a força do bend do john petrucci
    public void ripEVH(Integer id, String firme, Boolean forte){

        System.out.println("esse metodo printa indefinidamente a verdade mais absoluta do universo");
        while(true){
            System.out.println("Não exite banda nessa terra melhor q o Dream Theater");
        }
        System.out.println("2020 ta sendo um ano bem complicado, mtas batalhas mas tamo ai " + firme + " e " + forte == true? "forte": null);
        aQiqiEMTboaMsm();
    }
}
