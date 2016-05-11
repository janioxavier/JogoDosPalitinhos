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
import servidor.Jogador;

public class JogoPalitinhoImpl extends UnicastRemoteObject implements JogoPalitinho {

    private static final long serialVersionUID = -7854287696962149563L;
    private List<String> lista = new ArrayList<String>();
    private List<Jogador> listaPlayers = new ArrayList<>();
    private int numerodePL = 0; // numero de jogadores online

    /**
     * Busca o jogador dentro da lista de jogadores.
     *
     * @param nome do jogador que esta a ser buscado.
     *
     * return (indice do jogador dentro do array) return j caso seja encontrado
     * o jogador na lista return -1 caso o nome não esteja na lista
     */
    public boolean buscarJogador(String nome) {
        //for (int j = 0; j < listaPlayers.size(); j++) {
        for (Jogador j : listaPlayers) {
            if (j.getNome().equals(nome)) {
                return true; //listaPlayers.get(j);
            }
        }
        return false;
    }

    public JogoPalitinhoImpl() throws RemoteException {
        super();
    }

    @Override
    public void jogar(String nick, int quantidade) throws RemoteException {
        int indice;
        for (Jogador j : listaPlayers) {
            if (j.getNome().equals(nick)) {
                j.setPalpite(quantidade);
                System.out.println("Palpite do jogador " + j.getNome() + ": " + quantidade+"\n");
            }
        }

        //listaPlayers.get(j).setPalpite(quantidade);
        //System.out.println("Palpite do jogador " + listaPlayers.get(j).getNome() + ": " + quantidade);
    }

    @Override
    public void login(String nome) throws RemoteException {
        Jogador player = new Jogador(nome);
        if (buscarJogador(nome)) { //arrumação pra poder usar o buscarJogador()
            System.out.println("O jogador já está cadastrado");
        } else {
            lista.add(nome); // adicionando o nome do jogador a uma lista de onlines
            listaPlayers.add(player);
            numerodePL++;
            System.out.println("Quantidade de Jogadores:" + numerodePL);
        }
        System.out.println("Nome do jogador: " + player.getNome());

    }

    @Override
    public List<String> getLista() throws RemoteException {
        return lista;
    }

    @Override
    public boolean entrar(String nick) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void iniciarPartida(String nick) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getJogadoresPreparados() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getJogadoresNaoPreparados() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getJogadoresAtivos() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getPalitosRestantes() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getJogadorDaVez() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getJogadoresAguardando() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean darPalpite(String nick, int quantidade) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sair(String nick) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
