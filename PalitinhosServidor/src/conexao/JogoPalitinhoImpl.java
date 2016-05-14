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
    private List<String> lista;
    private List<Jogador> listaPlayers;
    private int numerodePL = 0; // numero de jogadores online
    private int jogadorDaVez;   // indice que indica o jogador da vez
    private boolean partidaIniciada;    // indica que o jogo está rolando
    private JogoBD bd;  // banco de dados do jogo    
    
    public JogoPalitinhoImpl() throws RemoteException {
        super();
        lista = new ArrayList<String>();
        listaPlayers = new ArrayList<>();
        jogadorDaVez = 0;
        partidaIniciada = false;
        bd = new JogoBD();
    }
    
    public boolean isPartidaIniciada() throws RemoteException {
        return partidaIniciada;
    }
    
    public String getVencedorDaPartida() throws RemoteException {
        String vencedor = null;
        for (Jogador jogador : listaPlayers) {
            if (jogador.getNumeroPalito() == 0) {
                vencedor = jogador.getNome();
                break;
            }
        }
        return vencedor;
    }
    
    
    /**
     * Busca o jogador dentro da lista de jogadores.
     *
     * @param nome do jogador que esta a ser buscado.
     *
     * @return (indice do jogador dentro do array) return j caso seja encontrado
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
            Jogador novoJogador = new Jogador(nick);
            listaPlayers.add(novoJogador);
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
    public void iniciarJogo() throws RemoteException {
        // verifica se todos os sjogadores estão preparados
        if (isTodosPreparados()) {
            jogadorDaVez = 0;
            partidaIniciada = true;
            definirProximoJogador();
        }
    }
    
    @Override
    public void iniciarNovaRodada() throws RemoteException {
        for (Jogador jogador : listaPlayers) {
            jogador.setDeuPalpite(false);
            jogador.setPalpite(-1);
        }
        jogadorDaVez = 0;
    }
    
    @Override
    public int getPalpiteJogador(String nick) throws RemoteException {
        int palpite = -1;
        Jogador jogador = encontrarJogador(nick);
        if (jogador != null)
            palpite = jogador.getPalpite();
        return palpite;
    }
    
    @Override
    public int posicaoJogador(String nick) throws RemoteException {
        int posicao = -1;
        if (encontrarJogador(nick) != null) {
            posicao++;
            for (Jogador jogador : listaPlayers) {
                if (jogador.getNome().equals(nick)) {
                    break;
                } else {
                    posicao ++;
                }
            }
        }
        return posicao;
        
        
    }
    
    
    private void definirProximoJogador() {
        if (jogadorDaVez == listaPlayers.size()) {
            jogadorDaVez = 0;
        }
        listaPlayers.get(jogadorDaVez).setJogadorDaVez(true);
    }
    
    private boolean isTodosPreparados() {
        boolean todosProntos = true;
        for (Jogador jogador : listaPlayers) {
            if (!jogador.isPreparado()) {
                todosProntos = false;
            }
        }
        return todosProntos;
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
            palitosRestantes += jogador.getApostaPalitos();
        }
        return palitosRestantes;
    }

    @Override
    public String getJogadorDaVez() throws RemoteException {
        String nick = null;
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
            jogador.setJogadorDaVez(false);
            jogadorDaVez++;
            definirProximoJogador();
            sucesso = true;
        }
        return sucesso;        
    }
    
    @Override
    public String definirVencedor() throws RemoteException {        
        String vencedor = null;
        // definir o vencedor quando todos derem o seu palpite
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
            totalPalitos = jogador.getApostaPalitos();
        return totalPalitos;
    }
    
    @Override
    public boolean isTodosDeramPalpite() {
        return !isAlguemNaoDeuPalpite();
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
    public void esconderPalitinhos(String nick, int quantidade) throws RemoteException {
        Jogador jogador = encontrarJogador(nick);
        if (jogador != null) {
            jogador.setApostaPalitos(quantidade);
            
        }
    }

    @Override
    public void sair(String nick) throws RemoteException {
        Jogador jogador = encontrarJogador(nick);
        if (jogador != null)
            listaPlayers.remove(jogador);
    }        
}
