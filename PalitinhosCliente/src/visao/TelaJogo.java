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

/**
 *
 * @author Jânio Xavier
 */
public class TelaJogo extends javax.swing.JFrame {

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

    private void setNomeJogador(String nick, int posicao) {
        switch (posicao) {
            case 0:
                jogador1.setText(nick);
                break;
            case 1:
                jogador2.setText(nick);
                break;
            case 2:
                jogador3.setText(nick);
                break;
            case 3:
                jogador4.setText(nick);
                break;
            case 4:
                jogador5.setText(nick);
                break;
            case 5:
                jogador6.setText(nick);
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

            String vencedorJogo = null;
            String vencedorRodada = null;
            String jogadorDaVez = null;

            while (vencedorJogo == null) {
                jogadorDaVez = jogo.getJogadorDaVez();
                if (jogadorDaVez.equals(nomeJogador)) {
                    aguardarEscolhaDaAposta();
                    aguardarPalpite();                    
                    atualizarPalpitesEscolhidos();
                } else {
                    setMensagemJogo("Aguardando... " + jogadorDaVez);
                }

                if (jogo.isTodosDeramPalpite()) {
                    vencedorRodada = jogo.definirVencedor();
                    if (vencedorRodada != null) {
                        setMensagemJogo("Jogador " + nomeJogador + " acertou o palpite");
                    } else {
                        setMensagemJogo("Ninguem deu o palpite correto");
                    }
                    sleep(5000);
                    jogo.iniciarNovaRodada();
                }
                vencedorJogo = jogo.getVencedorDaPartida();                
            }
            
            setMensagemJogo(vencedorJogo + " Venceu o jogo");

        } catch (RemoteException | InterruptedException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
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
    }

    private void aguardarInicioPartida() throws RemoteException {
        do {
            adicionarJogador(jogo.getJogadoresPreparados());
            if (jogadoresOnline.size() >= 2) {
                aguardarInicioPartida(10);
            }
        } while (!jogo.isPartidaIniciada());
    }
    
    private void aguardarInicioPartida(int tempo) throws RemoteException {
        int count = tempo;
        do {
            setMensagemJogo("O jogo será iniciado em " + count + " segundos");
            count--;
            try {
                sleep(1000);
                if (adicionarJogador(jogo.getJogadoresPreparados())) {
                    setMensagemJogo("Novo jogador adicionado");
                    sleep(3000);
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
        } while (!escolheuAposta);
        escolheuAposta = false;
    }

    private void aguardarPalpite() {
        do {
            setMensagemJogo("Dê o seu palpite");
        } while (!deuPalpite);
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
        jPanel2 = new javax.swing.JPanel();
        jogador2 = new javax.swing.JLabel();
        palpite2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jogador3 = new javax.swing.JLabel();
        palpite3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jogador5 = new javax.swing.JLabel();
        palpite5 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jogador6 = new javax.swing.JLabel();
        palpite6 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jogador4 = new javax.swing.JLabel();
        palpite4 = new javax.swing.JLabel();
        mensagemJogo = new javax.swing.JLabel();
        campoField = new javax.swing.JTextField();
        confirmButton = new javax.swing.JButton();
        iniciarButton = new javax.swing.JButton();
        campoName = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Jogo Palitinhos");

        jogador1.setText("jogador1");

        palpite1.setText("palpite1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(palpite1)
                    .addComponent(jogador1))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jogador1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(palpite1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jogador2.setText("jogador2");

        palpite2.setText("palpite2");

        jogador3.setText("jogador3");

        palpite3.setText("palpite3");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(palpite3)
                    .addComponent(jogador3))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jogador3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(palpite3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(palpite2)
                    .addComponent(jogador2))
                .addContainerGap(57, Short.MAX_VALUE))
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
                .addComponent(palpite2)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jogador5.setText("jogador5");

        palpite5.setText("palpite5");

        jogador6.setText("jogador6");

        palpite6.setText("palpite6");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(palpite6)
                    .addComponent(jogador6))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jogador6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(palpite6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(palpite5)
                    .addComponent(jogador5))
                .addContainerGap(57, Short.MAX_VALUE))
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
                .addComponent(palpite5)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jogador4.setText("jogador4");

        palpite4.setText("palpite4");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(palpite4)
                    .addComponent(jogador4))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jogador4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(palpite4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(91, 91, 91)
                                .addComponent(campoField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(confirmButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addComponent(iniciarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(mensagemJogo, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createSequentialGroup()
                .addGap(227, 227, 227)
                .addComponent(campoName, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(campoName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(iniciarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(mensagemJogo, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(campoField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(confirmButton))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                } else {                    
                    deuPalpite = true;
                }
            } else if (mensagemJogo.getText().contains("aposta")) {
                jogo.esconderPalitinhos(nomeJogador, campoValor);
                escolheuAposta = true;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(TelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_confirmButtonActionPerformed

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

    private void campoNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoNameActionPerformed

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
    private javax.swing.JButton iniciarButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
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
    // End of variables declaration//GEN-END:variables
}
