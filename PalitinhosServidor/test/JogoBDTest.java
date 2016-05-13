/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import servidor.JogoBD;

/**
 *
 * @author Jânio Xavier
 */
public class JogoBDTest {
    private JogoBD bd;
    
    public JogoBDTest() {
        bd = new JogoBD();
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
    
    @Test
    public void testInsertDataInTable() {
        bd.createConnection("Teste_Insert");
        List<String> statementList, values;
        
        values = new ArrayList<>();
        statementList = new ArrayList<>();
        
        statementList.add("id integer");
        statementList.add("name string");
        bd.createNewTable("person", statementList);
        
        values.add("1");
        values.add("'joao'");
        bd.insertDataInTable("person", values);
        
        values.clear();
        values.add("2");        
        values.add("'joana'");
        bd.insertDataInTable("person", values);
        
        values.clear();
        values.add("3");
        values.add("'jorge'");
        bd.insertDataInTable("person", values);
        
        assertEquals(true, bd.containsInTable("person", "id", "1"));
        assertEquals(true, bd.containsInTable("person", "name", "joao"));
        
        assertEquals(true, bd.containsInTable("person", "id", "2"));
        assertEquals(true, bd.containsInTable("person", "name", "joana"));
        
        assertEquals(true, bd.containsInTable("person", "id", "3"));
        assertEquals(true, bd.containsInTable("person", "name", "jorge"));        
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
