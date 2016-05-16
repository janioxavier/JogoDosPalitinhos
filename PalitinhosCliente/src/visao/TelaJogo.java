/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import conexao.JogoPalitinho;
import static java.lang.Thread.sleep;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.lang.Thread.sleep;

/**
 *
 * @author Jânio Xavier
 */
public class TelaJogo extends javax.swing.JFrame{

    public static final int MAXIMO_JOGADORES = 6;
    private JogoPalitinho jogo;
    private String nomeJogador;
    private int numeroJogadoresOnline;
    private List<String> jogadoresOnline;
    private boolean escolheuAposta;
    private boolean deuPalpite;
    
    /**
     * Creates new form TelaJogo
     *
     * @param jogo jogo dos palitinhos
     */
    public TelaJogo(JogoPalitinho jogo) {
        initComponents();                    
        this.jogo = jogo;
        nomeJogador = null;
        numeroJogadoresOnline = 0;
        jogadoresOnline = new ArrayList<>();
        escolheuAposta = false;
        deuPalpite = false;
        setMensagemJogo("aguardando jogadores...");
    }   
    
    

    /**
     * Creates new form TelaJogo
     */
    public TelaJogo() {
        initComponents();
    }
    
    public String getNomeJogador() {
        return nomeJogador;
    }

    private void setNomeJogador(String nick, int posicao) throws RemoteException {
        switch (posicao) {
            case 0:
                jogador1.setText(nick);
                //quantidadeJ1.setText("Q: "+jogo.getTotalPalitosJogador(nick));
                break;
            case 1:
                jogador2.setText(nick);
                //quantidadeJ2.setText("Q: "+jogo.getTotalPalitosJogador(nick));
                break;
            case 2:
                jogador3.setText(nick);
                //quantidadeJ3.setText("Q: "+jogo.getTotalPalitosJogador(nick));
                break;
            case 3:
                jogador4.setText(nick);
                //quantidadeJ4.setText("Q: "+jogo.getTotalPalitosJogador(nick));
                break;
            case 4:
                jogador5.setText(nick);
                //quantidadeJ5.setText("Q: "+jogo.getTotalPalitosJogador(nick));
                break;
            case 5:
                jogador6.setText(nick);
                //quantidadeJ6.setText("Q: "+jogo.getTotalPalitosJogador(nick));
                break;
        }
    }
    
    private void setQuantidadePalitoJogador(String quantidade, int posicao) {
        switch (posicao) {
            case 0:        
                quantidadeJ1.setText(quantidade);
                break;
            case 1:                
                quantidadeJ2.setText(quantidade);
                break;
            case 2:                
                quantidadeJ3.setText(quantidade);
                break;
            case 3:                
                quantidadeJ4.setText(quantidade);
                break;
            case 4:                
                quantidadeJ5.setText(quantidade);
                break;
            case 5:                
                quantidadeJ6.setText(quantidade);
                break;
        }
    }
    
    private void setPalpiteJogador(String palpite, int posicao) {
        switch (posicao) {
            case 0:
                palpite1.setText(palpite);
                break;
            case 1:
                palpite2.setText(palpite);
                break;
            case 2:
                palpite3.setText(palpite);
                break;
            case 3:
                palpite4.setText(palpite);
                break;
            case 4:
                palpite5.setText(palpite);
                break;
            case 5:
                palpite6.setText(palpite);
                break;
        }
    }
    
    public void iniciarJogo() {
        
        try {
            aguardarInicioPartida();
            atualizarJogadoresPartida();            
            
            String vencedorJogo = null;                        
            while (vencedorJogo == null && isVisible()) {                
                //atualizarPalpitesEscolhidos();
                realizarJogada();           
                //atualizarPalpitesEscolhidos();
                verificarVencedorRodada();
                vencedorJogo = jogo.getVencedorDaPartida();                
            }            
            if (!isVisible()) {
                jogo.sair(nomeJogador);                
                dispose();
                System.exit(0);
            }
            setMensagemJogo(vencedorJogo + " Venceu o jogo");
            sleep(10000);            
        } catch (RemoteException | InterruptedException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void atualizarJogadoresPartida() throws RemoteException {
        List<String> jogadoresPreparados = jogo.getJogadoresPreparados();
        int count = 0;
        int posicao;
        for (String nick : jogadoresPreparados) {
            posicao = jogo.posicaoJogador(nick);
            setNomeJogador(nick, posicao);
            setQuantidadePalitoJogador("Q: " + jogo.getTotalPalitosJogador(nick), posicao);
            count++;
        }
        numeroJogadoresOnline = count;
        while (count < MAXIMO_JOGADORES) {
            setNomeJogador("", count);
            setPalpiteJogador("", count);
            setQuantidadePalitoJogador("", count);
            count++;
        }
    }

    private void realizarJogada() throws RemoteException {
        String jogadorDaVez;
        jogadorDaVez = jogo.getJogadorDaVez();        
        if (jogadorDaVez.equals(nomeJogador)) {
            aguardarEscolhaDaAposta();
            aguardarPalpite();            
        } else {
            setMensagemJogo("Aguardando... " + jogadorDaVez);
        }        
    }

    private void verificarVencedorRodada() throws InterruptedException, RemoteException {
        String vencedorRodada;
        if (jogo.isTodosDeramPalpite()) {
            atualizarPalpitesEscolhidos();
            atualizarQuantidadePalitos();
            vencedorRodada = jogo.getVencedorRodada();
            if (vencedorRodada != null) {
                setMensagemJogo("Jogador " + vencedorRodada + " acertou o palpite");
            } else {
                setMensagemJogo("Ninguem deu o palpite correto");
                sleep(2000);
                setMensagemJogo("O total de palitos foi: "+jogo.getTotalPalitosMostrados());
            }
            sleep(5000);
            jogo.iniciarNovaRodada();
        }
    }        
    
    private void atualizarPalpitesEscolhidos() throws RemoteException {
        List<String> jogadoresPreparados = jogo.getJogadoresPreparados();
        int palpite;
        for (String nick : jogadoresPreparados) {
            palpite = jogo.getPalpiteJogador(nick);
            if (palpite == -1) {
                setPalpiteJogador("Palpite não escolhido", jogo.posicaoJogador(nick));
            } else {
                setPalpiteJogador(Integer.toString(palpite),
                            jogo.posicaoJogador(nick));    
            }
        }        
    }
    
    private void atualizarQuantidadePalitos() throws RemoteException {
        List<String> jogadoresPreparados = jogo.getJogadoresPreparados();
        int totalPalitos;
        for (String nick : jogadoresPreparados) {
            totalPalitos = jogo.getTotalPalitosJogador(nick);
            setQuantidadePalitoJogador("Q: " + totalPalitos, jogo.posicaoJogador(nick));
        }
    }

    /*
    private boolean adicionarJogador(List<String> jogadores) throws RemoteException {
        // adiciona um jogador na lista de jogadores online apenas se
        // o jogador for novo.
        boolean novoJogadorAdicionado = false;
        for (String nick : jogadores) {
            if (!jogadoresOnline.contains(nick)) {            
                jogadoresOnline.add(nick);
                setNomeJogador(nick, jogo.posicaoJogador(nick));
                novoJogadorAdicionado = true;
            }
        }
        return novoJogadorAdicionado;
    }*/

    private void aguardarInicioPartida() throws RemoteException {
        do {
            //adicionarJogador(jogo.getJogadoresPreparados());
            atualizarJogadoresPartida();
            if (numeroJogadoresOnline >= 2 && nomeJogador != null) {
                aguardarInicioPartida(10);
            }
        } while (!jogo.isPartidaIniciada());
    }
    
    private void aguardarInicioPartida(int tempo) throws RemoteException {
        int count = tempo;
        int numeroJogadoresOnlineAntes = numeroJogadoresOnline;
        do {
            setMensagemJogo("O jogo será iniciado em " + count + " segundos");
            count--;
            try {
                sleep(1000);
                numeroJogadoresOnlineAntes = numeroJogadoresOnline;
                atualizarJogadoresPartida();                
                if (numeroJogadoresOnlineAntes < numeroJogadoresOnline) {
                    setMensagemJogo("Novo jogador adicionado");
                    //sleep(5000);
                    count = 10;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (count >= 0);
        jogo.iniciarJogo();
    }


    private void aguardarEscolhaDaAposta() {                
        do {                        
            setMensagemJogo("Escolha sua aposta");
        } while (!escolheuAposta && isVisible());
        escolheuAposta = false;
    }

    private void aguardarPalpite() {        
        do {            
            setMensagemJogo("Dê o seu palpite");
        } while (!deuPalpite && isVisible());
        deuPalpite = false;
    }
    
    private void setMensagemJogo(String messagem) {
        mensagemJogo.setText(messagem);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jogador1 = new javax.swing.JLabel();
        palpite1 = new javax.swing.JLabel();
        quantidadeJ1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jogador2 = new javax.swing.JLabel();
        palpite2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jogador3 = new javax.swing.JLabel();
        palpite3 = new javax.swing.JLabel();
        quantidadeJ3 = new javax.swing.JLabel();
        quantidadeJ2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jogador5 = new javax.swing.JLabel();
        palpite5 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jogador6 = new javax.swing.JLabel();
        palpite6 = new javax.swing.JLabel();
        quantidadeJ6 = new javax.swing.JLabel();
        quantidadeJ5 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jogador4 = new javax.swing.JLabel();
        palpite4 = new javax.swing.JLabel();
        quantidadeJ4 = new javax.swing.JLabel();
        mensagemJogo = new javax.swing.JLabel();
        campoField = new javax.swing.JTextField();
        confirmButton = new javax.swing.JButton();
        iniciarButton = new javax.swing.JButton();
        campoName = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 220), new java.awt.Dimension(0, 220), new java.awt.Dimension(32767, 220));

        setTitle("Jogo Palitinhos");
        setBackground(new java.awt.Color(0, 51, 51));
        setForeground(java.awt.Color.black);

        jogador1.setText("jogador1");

        palpite1.setText("palpite1");

        quantidadeJ1.setText("Q:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(palpite1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(quantidadeJ1))
                    .addComponent(jogador1))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jogador1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(palpite1)
                    .addComponent(quantidadeJ1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jogador2.setText("jogador2");

        palpite2.setText("palpite2");

        jogador3.setText("jogador3");

        palpite3.setText("palpite3");

        quantidadeJ3.setText("Q:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(palpite3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quantidadeJ3))
                    .addComponent(jogador3))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jogador3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(palpite3)
                    .addComponent(quantidadeJ3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        quantidadeJ2.setText("Q:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(palpite2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quantidadeJ2))
                    .addComponent(jogador2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jogador2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(palpite2)
                    .addComponent(quantidadeJ2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jogador5.setText("jogador5");

        palpite5.setText("palpite5");

        jogador6.setText("jogador6");

        palpite6.setText("palpite6");

        quantidadeJ6.setText("Q:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(palpite6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addComponent(quantidadeJ6))
                    .addComponent(jogador6))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jogador6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(palpite6)
                    .addComponent(quantidadeJ6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        quantidadeJ5.setText("Q:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(palpite5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(quantidadeJ5))
                    .addComponent(jogador5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jogador5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(palpite5)
                    .addComponent(quantidadeJ5))
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jogador4.setText("jogador4");

        palpite4.setText("palpite4");

        quantidadeJ4.setText("Q:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(palpite4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(quantidadeJ4))
                    .addComponent(jogador4))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jogador4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(palpite4)
                    .addComponent(quantidadeJ4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mensagemJogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mensagemJogo.setText("MENSAGENS DO JOGO");

        campoField.setText("campo");
        campoField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoFieldActionPerformed(evt);
            }
        });

        confirmButton.setText("confirmar");
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed(evt);
            }
        });

        iniciarButton.setText("iniciar jogo");
        iniciarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iniciarButtonActionPerformed(evt);
            }
        });

        campoName.setText("nome jogador");
        campoName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator2))
                        .addGap(50, 50, 50)
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                                .addComponent(campoField, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 144, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(confirmButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(210, 210, 210)
                                .addComponent(campoName, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(177, 177, 177)
                                .addComponent(iniciarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(177, 177, 177)
                                .addComponent(mensagemJogo, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(campoName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(iniciarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mensagemJogo, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(campoField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(confirmButton)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void campoFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoFieldActionPerformed

    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmButtonActionPerformed
        // TODO add your handling code here:
        int campoValor = Integer.parseInt(campoField.getText());
        try {
            if (mensagemJogo.getText().contains("palpite")) {
                if (!jogo.darPalpite(nomeJogador, campoValor)) {
                    setMensagemJogo("Esse palpite já foi dado"); 
                    sleep(5000);                                       
                } else {                    
                    deuPalpite = true;
                }
            } else if (mensagemJogo.getText().contains("aposta")) {
                //if(campoValor > )
                jogo.esconderPalitinhos(nomeJogador, campoValor);
                escolheuAposta = true;
            }
        } catch (RemoteException | InterruptedException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_confirmButtonActionPerformed

    private void campoNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoNameActionPerformed
        // TODO add your handling code here:
        campoName.setText("");

    }//GEN-LAST:event_campoNameActionPerformed

    private void iniciarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iniciarButtonActionPerformed
        // TODO add your handling code here:
        String nomeJogadorCampo = campoName.getText();
        if (nomeJogadorCampo != null) {
            try {
                if (jogo.entrar(nomeJogadorCampo)) {
                    nomeJogador = nomeJogadorCampo;
                    jogadoresOnline.add(nomeJogadorCampo);
                    jogo.iniciarPartida(nomeJogadorCampo);
                    setNomeJogador(nomeJogadorCampo, jogo.posicaoJogador(nomeJogadorCampo));
                    iniciarButton.setVisible(false);
                    campoName.setVisible(false);
                } else {
                    mensagemJogo.setText("NOME JÁ SE ENCONTRA EM USO");
                }
            } catch (RemoteException ex) {
                Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_iniciarButtonActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaJogo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField campoField;
    private javax.swing.JTextField campoName;
    private javax.swing.JButton confirmButton;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton iniciarButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel jogador1;
    private javax.swing.JLabel jogador2;
    private javax.swing.JLabel jogador3;
    private javax.swing.JLabel jogador4;
    private javax.swing.JLabel jogador5;
    private javax.swing.JLabel jogador6;
    private javax.swing.JLabel mensagemJogo;
    private javax.swing.JLabel palpite1;
    private javax.swing.JLabel palpite2;
    private javax.swing.JLabel palpite3;
    private javax.swing.JLabel palpite4;
    private javax.swing.JLabel palpite5;
    private javax.swing.JLabel palpite6;
    private javax.swing.JLabel quantidadeJ1;
    private javax.swing.JLabel quantidadeJ2;
    private javax.swing.JLabel quantidadeJ3;
    private javax.swing.JLabel quantidadeJ4;
    private javax.swing.JLabel quantidadeJ5;
    private javax.swing.JLabel quantidadeJ6;
    // End of variables declaration//GEN-END:variables

}
