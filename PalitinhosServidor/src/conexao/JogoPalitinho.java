/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
 
public interface JogoPalitinho extends Remote{
    void jogar(int quantidade) throws RemoteException;
    void login(String nome) throws RemoteException;
    List<String> getLista() throws RemoteException; // retorna a lista de jogadores
}