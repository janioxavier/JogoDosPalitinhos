/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao;

/**
 *
 * @author Jânio Xavier
 */
import java.net.MalformedURLException;
import javax.swing.JOptionPane;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import conexao.TelaCliente;
 
public class Conector {
    private RMICliente cliente;
    private JogoPalitinho jogo;
     
    void connect() {
        try {
            cliente = new RMICliente("localhost");
            jogo = (JogoPalitinho) cliente.getRemoteObject();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
		
    public JogoPalitinho getJogo() {
    	return jogo;
    }
    
    public static void main(String[] args) {
        Conector conector = new Conector();
        conector.connect();
        
        JogoPalitinho jogo = conector.getJogo();
        try {
            String nomeJogador = JOptionPane.showInputDialog("Digite seu nome:");
            jogo.login(nomeJogador);            
            List<String> jogadores = jogo.getLista();
            //if(jogadores.size() >=4){
                //String palpite = JOptionPane.showInputDialog("Digite seu palpite:");
                
                TelaCliente tela = new TelaCliente(jogadores, jogo , nomeJogador);
                tela.setVisible(true);
                
                
               
                
                /*for (String j : jogadores) {
                    jogo.jogar(j, Integer.parseInt(JOptionPane.showInputDialog("Digite sua quantidade de palitos:"+j)));
                }*/
            //}
            
            for (String nome : jogadores) {
                System.out.println("Jogador: " + nome + " está no jogo.");
            }     
        } catch (RemoteException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }                
    }
}
