/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
 
public class RMICliente implements Serializable{
    private static final long serialVersionUID = 3063470053215537339L;
 
    private static final String RMI_PORT_SERVER = ":1099/server";
 
    private Remote remoteObject;
 
    public RMICliente(String ip) throws MalformedURLException, RemoteException,
            NotBoundException {
        try {
            remoteObject = Naming.lookup("rmi://" + ip + RMI_PORT_SERVER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
    public Remote getRemoteObject() {
        return remoteObject;
    }
}