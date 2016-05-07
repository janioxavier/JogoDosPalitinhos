/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class JogoPalitinhoImpl extends UnicastRemoteObject implements JogoPalitinho {

    private static final long serialVersionUID = -7854287696962149563L;
    private List<String> lista = new ArrayList<String>();
    private int j; // apagar depois

    public JogoPalitinhoImpl() throws RemoteException {
        super();
    }

    @Override
    public void jogar(int quantidade) throws RemoteException {
        System.out.println("Adicionando String: " + quantidade);
    }

    @Override
    public void login(String nome) throws RemoteException {
        lista.add(nome); // adicionando o nome do jogador a uma lista de onlines
    }

    @Override
    public List<String> getLista() throws RemoteException {
        return lista;
    }
}