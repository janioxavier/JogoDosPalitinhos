/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.util.List;

/**
 * Simula uma rodada do jogo dos palitinhos armazenando o turno,
 * os jogadores, o vencedor da rodada e  o jogador da vez.
 * @author Jânio Xavier
 */
public class Rodada {
    private List<Jogador> jogadores;
    private Jogador vencedorRodada;
    private Jogador jogadorDaVez;
    private int indexJogadorDaVez;
    private int turno;    
    private boolean acabouRodada;
    private boolean novaRodada;
    
    /**
     * Inicializa uma rodada informando os jogadores que farão
     * parte desta.
     * @param jogadores jogadores que farão parte da rodada.
     */
    public Rodada(List<Jogador> jogadores) {
        this.jogadores = jogadores;
        vencedorRodada = null;
        jogadorDaVez = null;
        turno = 0;
        proximoJogador();
    }
    
    /**
     * inicia uma nova rodada com novos jogadores
     * @param jogadores novos jogadores
     */
    public void iniciarNovaRodada(List<Jogador> jogadores) {
        if (novaRodada) {
            this.jogadores = jogadores;
            turno++;
            vencedorRodada = null;
            indexJogadorDaVez = 0;
            jogadorDaVez = null;
            acabouRodada = false;
            proximoJogador();
            novaRodada = false;
        }        
    }
        
    /**
     * define o proximo jogador. O proximo jogador será o proximo jogador
     * da rodada ou null caso a rodada tenha acabado
     */
    public void proximoJogador() {                      
        if (acabouRodada) {            
            return;
        }
                      
        if (indexJogadorDaVez == jogadores.size()) {
            definirVencedor();
            novaRodada = true;
            acabouRodada = true;            
            indexJogadorDaVez = 0;
            jogadorDaVez = null;
        } else {
            jogadorDaVez = jogadores.get(indexJogadorDaVez);  
            indexJogadorDaVez ++;
        }                                
    }        
    
    /**
     * 
     * @return o jogador da vez
     */
    public Jogador getJogadorDaVez() {
        return jogadorDaVez;
    }
            
    private void definirVencedor() {        
        int totalPalitosMostrados = getTotalPalitosMostrados();
        for (Jogador jogador : jogadores) {
            if (jogador.getPalpite() == totalPalitosMostrados) {
                vencedorRodada = jogador;
                break;
            }                        
        }
    }
    
    /**
     * 
     * @return o total de palitos amostrados.
     */
    public int getTotalPalitosMostrados() {
        int totalPalitosMostrados = 0;
        for (Jogador jogador : jogadores) {
            totalPalitosMostrados += jogador.getApostaPalitos();
        }
        return totalPalitosMostrados;
    }
    
    /**
     * 
     * @return o vencedor da rodada, ou null caso ninguem tenha vencido ainda
     */
    public Jogador getVencedorRodada() {        
        return vencedorRodada;        
    }            
}
