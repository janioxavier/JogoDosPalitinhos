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
import servidor.JogoBD;

public class JogoPalitinhoImpl extends UnicastRemoteObject implements JogoPalitinho {

    private static final long serialVersionUID = -7854287696962149563L;
    private List<String> lista = new ArrayList<String>();
    private List<Jogador> listaPlayers = new ArrayList<>();
    private int numerodePL = 0; // numero de jogadores online
    private JogoBD bd;  // banco de dados do jogo
    

    
    public JogoPalitinhoImpl() throws RemoteException {
        super();
        bd = new JogoBD();
    }
    
    /**
     * Busca o jogador dentro da lista de jogadores.
     *
     * @param nome do jogador que esta a ser buscado.
     *
     * return (indice do jogador dentro do array) return j caso seja encontrado
     * o jogador na lista return -1 caso o nome não esteja na lista
     */
    public boolean buscarJogador(String nome) {
        boolean find = false;
        for (Jogador j : listaPlayers) {
            if (j.getNome().equals(nome)) {
                find = true; //listaPlayers.get(j);
                break;
            }
        }
        return find;
    }
    
    /**
     *  Encontra um jogador que está atualmente no jogo.
     * @param nome nome do jogador
     * @return um objeto jogador do jogo
     */
    private Jogador encontrarJogador(String nome) {
        Jogador jogador = null;
        for (Jogador j : listaPlayers) {
            if (j.getNome().equals(nome)) {
                jogador = j;
                break;
            }
        }
        return jogador;
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
        boolean sucesso = false;
        if (!buscarJogador(nick)) { 
            Jogador jogador = new Jogador(nick);            
            sucesso = true;
        }        
        return sucesso;
    }

    @Override
    public void iniciarPartida(String nick) throws RemoteException {
        Jogador jogador = encontrarJogador(nick);
        if (jogador != null) {
            jogador.setPreparado(true);
        }
    }

    @Override
    public List<String> getJogadoresPreparados() throws RemoteException {
        List<String> jogadoresPreparados = new ArrayList<>();
        for (Jogador jogador: listaPlayers) {
            if (jogador.isPreparado())
                jogadoresPreparados.add(jogador.getNome());
        }
        return jogadoresPreparados;
    }

    @Override
    public List<String> getJogadoresNaoPreparados() throws RemoteException {
        List<String> jogadoresNaoPreparados = new ArrayList<>();
        for (Jogador jogador: listaPlayers) {
            if (!jogador.isPreparado())
                jogadoresNaoPreparados.add(jogador.getNome());
        }
        return jogadoresNaoPreparados;
    }

    @Override
    public List<String> getJogadoresAtivos() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getPalitosRestantes() throws RemoteException {
        int palitosRestantes = 0;
        for (Jogador jogador : listaPlayers) {
            palitosRestantes += jogador.getQtdPalitos();
        }
        return palitosRestantes;
    }

    @Override
    public String getJogadorDaVez() throws RemoteException {
        String nick = "";
        for (Jogador jogador : listaPlayers) {
            if (jogador.isJogadorDaVez()) {
                nick = jogador.getNome();
                break;
            }
        }
        return nick;
    }

    @Override
    public List<String> getJogadoresAguardando() throws RemoteException {
        List<String> jogadoresAguardando = new ArrayList<>();
        for (Jogador jogador : listaPlayers) {
            if (!jogador.isJogadorDaVez()) {
                jogadoresAguardando.add(jogador.getNome());
            }
        }
        return jogadoresAguardando;
    }

    @Override
    public boolean darPalpite(String nick, int quantidade) throws RemoteException {
        boolean sucesso = false;
        Jogador jogador = encontrarJogador(nick);
        if (jogador != null) {
            jogador.setPalpite(quantidade);
            jogador.setDeuPalpite(true);
            sucesso = true;
        }
        return sucesso;        
    }
    
    @Override
    public String definirVencedor() throws RemoteException {        
        String vencedor = null;
        if (!isAlguemNaoDeuPalpite()) {
            int totalPalitos = getTotalPalitosMostrados();
            
            for (Jogador jogador : listaPlayers) {
                if (jogador.getPalpite() == totalPalitos) {
                    vencedor = jogador.getNome();
                    break;
                }
            }
            
        }
        
        return vencedor;
    }
    
    @Override
    public int getTotalPalitosMostrados() {
        int totalPalitos = 0;
        for (Jogador jogador : listaPlayers)
            totalPalitos = jogador.getQtdPalitos();
        return totalPalitos;
    }
    
    /**
     * 
     * @return true se alguem ainda não deu o seu palpite, false caso
     * contrário.
     */
    private boolean isAlguemNaoDeuPalpite() {
        boolean alguemNaoDeuPalpite = false;
        for (Jogador jogador : listaPlayers) {
            if (!jogador.isDeuPalpite()) {
                alguemNaoDeuPalpite = true;
                break;
            }
        }
        return alguemNaoDeuPalpite;
    }
    

    @Override
    public void sair(String nick) throws RemoteException {
        Jogador jogador = encontrarJogador(nick);
        if (jogador != null)
            listaPlayers.remove(jogador);
    }    
    
}
