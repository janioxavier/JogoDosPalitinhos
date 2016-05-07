/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

/**
 * Representa um jogador do jogo dos palitinhos. 
 * @author Jânio Xavier
 */
public class Jogador {
    private String nome;
    private int numeroPalito;
    private int palpite;
    
    /**
     * Inicializa um jogador com o nome.
     * @param nome 
     */
    public Jogador (String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public int getNumeroPalito() {
        return numeroPalito;
    }

    public void setNumeroPalito(int numeroPalito) {
        this.numeroPalito = numeroPalito;
    }

    public int getPalpite() {
        return palpite;
    }

    public void setPalpite(int palpite) {
        this.palpite = palpite;
    }        
    
    
}
