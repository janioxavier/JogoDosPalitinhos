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
    private int totalPalitos;
    private int palpite;
    private int apostaPalitos;
    private boolean preparado;
    private boolean jogadorDaVez;
    private boolean deuPalpite;
    
    /**
     * Inicializa um jogador com o nome.
     * @param nome 
     */
    public Jogador (String nome) {
        this.nome = nome;
        totalPalitos = 3;
        jogadorDaVez = false;
        deuPalpite = false;
        preparado = false;
    }

    public boolean isDeuPalpite() {
        return deuPalpite;
    }

    public void setDeuPalpite(boolean deuPalpite) {
        this.deuPalpite = deuPalpite;
    }

    public void decrementarTotalPalitos() {
        if (totalPalitos > 0)
            totalPalitos--;
    }
    public int getApostaPalitos() {
        return apostaPalitos;
    }

    public void setApostaPalitos(int qtdPalitos) {
        this.apostaPalitos = qtdPalitos;
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
        return totalPalitos;
    }

    public void setNumeroPalito(int numeroPalito) {
        this.totalPalitos = numeroPalito;
    }

    public int getPalpite() {
        return palpite;
    }

    public void setPalpite(int palpite) {
        this.palpite = palpite;
    }
    
    
}
