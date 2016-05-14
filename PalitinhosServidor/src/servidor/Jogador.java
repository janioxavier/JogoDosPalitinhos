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
    private int numeroPalito = 3; // total de palitos do jogador
    private int palpite;
    private int qtdPalitos; // quantidade de palitos dentro da mão do jogador
    private boolean preparado;
    private boolean jogadorDaVez;
    private boolean deuPalpite;
    
    /**
     * Inicializa um jogador com o nome.
     * @param nome 
     */
    public Jogador (String nome) {
        this.nome = nome;
    }

    public boolean isDeuPalpite() {
        return deuPalpite;
    }

    public void setDeuPalpite(boolean deuPalpite) {
        this.deuPalpite = deuPalpite;
    }

    public void decrementarNumeroPalito() {
        if (numeroPalito > 0)
            numeroPalito--;
    }
    public int getQtdPalitos() {
        return qtdPalitos;
    }

    public void setQtdPalitos(int qtdPalitos) {
        this.qtdPalitos = qtdPalitos;
    }

    public boolean isJogadorDaVez() {
        return jogadorDaVez;
    }

    public void setJogadorDaVez(boolean jogadorDaVez) {
        this.jogadorDaVez = jogadorDaVez;
    }

    
    public boolean isPreparado() {
        return preparado;
    }

    public void setPreparado(boolean preparado) {
        this.preparado = preparado;
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
