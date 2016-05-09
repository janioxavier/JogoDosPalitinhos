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
    /**
     * Verifica se o nick já está cadastrado.
     * @param nick nick do jogador
     * @return true se o nome não existe, false caso contrário
     * @throws RemoteException 
     */
    boolean entrar(String nick) throws RemoteException;
    
    /**
     * Torna o jogador apto a iniciar a partida. A partida só começara
     * quando todos os jogadores que estiverem na sala estiverem aptos
     * a iniciar a partida.
     * @param nick do jogador que esta preparado para jogar.
     * @throws RemoteException 
     */    
    void iniciarPartida(String nick) throws RemoteException;
    
    /**
     * Apenas jogadores preparados para começar uma partida estão na lista.
     * @return uma lista contendo o nick de cada jogador preparado para começar
     * a partida.
     * @throws RemoteException 
     */
    List<String> getJogadoresPreparados() throws RemoteException;
    
    /**
     * Apenas jogadores não preparados para inicar a partida estão na lista.
     * @return uma lista contendo o nick de cada jogador que não está preparado
     * para começar a partida
     * @throws RemoteException 
     */
    List<String> getJogadoresNaoPreparados() throws RemoteException;
    
    /**
     * Só farão parte da lista os jogadores que estiverem ativos em uma partida.
     * @return uma lista contendo o nick de cada jogador de uma partida.
     * @throws RemoteException 
     */
    List<String> getJogadoresAtivos() throws RemoteException;
    
    /**
     * Os palitos restantes é o somatório da quantidade de palitos
     * de cada jogador numa partida.
     * @return a quantidade de palitinhos restantes.
     * @throws RemoteException 
     */
    int getPalitosRestantes() throws RemoteException;
            
    /**
     *  O jogador que é o primeiro a jogar.
     * @return o nick do jogador da vez.
     * @throws RemoteException 
     */
    String getJogadorDaVez() throws RemoteException;    
    
    /**
     * Todos os jogadores que estiverem aguardando sua vez farão parte da lista.
     * @return uma lista contendo o nick de cada jogador que está aguardando
     * a sua jogada.
     * @throws RemoteException 
     */
    List<String> getJogadoresAguardando() throws RemoteException;
    
    /**
     * Um palpite é dado para tentar adivinhar o somatório da quantidade de pa-
     * litos do jogo. Não é permitido palpites iguais.
     * @param nick do jogador que irá dar um palpite.
     * @param quantidade palpite da quantidade de palitinhos.
     * @return true se o palpite não foi dado ainda, false caso contrário.
     * @throws RemoteException 
     */
    boolean darPalpite(String nick, int quantidade) throws RemoteException;
    
    /**
     * Remove o personagem do jogo.
     * @param nick do jogador que deseja sair do jogo.
     * @throws RemoteException 
     */
    void sair(String nick) throws RemoteException;
    
    
    void jogar(int quantidade) throws RemoteException;
    void login(String nome) throws RemoteException;
    List<String> getLista() throws RemoteException; // retorna a lista de jogadores
}