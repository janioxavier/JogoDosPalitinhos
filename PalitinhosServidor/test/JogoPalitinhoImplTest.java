/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import conexao.JogoPalitinho;
import conexao.JogoPalitinhoImpl;
import java.rmi.RemoteException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jânio Xavier
 */
public class JogoPalitinhoImplTest {
    private JogoPalitinho jogo;
    
    public JogoPalitinhoImplTest() throws RemoteException {
        jogo = new JogoPalitinhoImpl();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testEntrar() throws RemoteException {
        assertEquals(true, jogo.entrar("joão"));
        assertEquals(true, jogo.entrar("maria"));
        assertEquals(true, jogo.entrar("pedro"));
        assertEquals(false, jogo.entrar("joão"));
        assertEquals(3,jogo.getJogadoresNaoPreparados().size());        
    }
    
    @Test
    public void testIniciarPartida() throws RemoteException {
        jogo.entrar("joão");
        jogo.entrar("pedro");
        jogo.iniciarPartida("joão");
        jogo.iniciarPartida("pedro");
        jogo.iniciarJogo();
        assertEquals(2, jogo.getJogadoresPreparados().size());
        assertEquals("joão", jogo.getJogadorDaVez());
    }
    
    @Test
    public void testGetJogadorDaVez() throws RemoteException{
        jogo.entrar("joão");
        jogo.entrar("pedro");
        jogo.entrar("lucas");
        jogo.entrar("mario");
        
        jogo.iniciarPartida("joão");
        jogo.iniciarPartida("pedro");
        jogo.iniciarPartida("lucas");
        jogo.iniciarPartida("mario");
        
        jogo.iniciarJogo();
        jogo.iniciarJogo();
        
        assertEquals("joão", jogo.getJogadorDaVez());
        assertEquals("joão", jogo.getJogadorDaVez());
        jogo.darPalpite("joão", 1);
        assertEquals("pedro", jogo.getJogadorDaVez());
        jogo.darPalpite("pedro", 3);
        assertEquals("lucas", jogo.getJogadorDaVez());
        jogo.darPalpite("lucas", 2);
        assertEquals("mario", jogo.getJogadorDaVez());
        jogo.darPalpite("mario", 6);
        assertEquals("joão", jogo.getJogadorDaVez());
    }
}
