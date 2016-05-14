/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe responsável por gerir e manter os dados dos jogadores armazenados
 * @author Jânio Xavier
 */
public class JogoBD {
    private Connection connection;
    private Statement statement;
    private String NOME_BD = "dados_jogo";    
    
    public JogoBD() {
        createConnection(NOME_BD);
        init();
    }
    
    private void init() {
        List<String> statementList = new ArrayList<>();
        statementList.add("nome string");
        statementList.add("palpite integer");
        statementList.add("qtdPalitos integer");
        statementList.add("jogadorDaVez integer"); //boolean        
        createNewTable("Jogador", statementList);
    }    
    
    public void addPlayerData(Jogador jogador) {
        List<String> values = new ArrayList<String>();
        values.add("'"+jogador.getNome()+"'");
        values.add(Integer.toString(jogador.getPalpite()));
        values.add(Integer.toString(jogador.getApostaPalitos()));
        
        int value = 0;
        if (jogador.isJogadorDaVez())
            value = 1;
        values.add(Integer.toString(value));
        
        insertDataInTable("Jogador", values);
    }
    
    /**
     * Cria uma conecção com o banco de dados.
     * @param nome Nome do arquivo de bd que será armazenado as operações.
     */
    public void createConnection(String nome) {
        try {
            // load the sqlite-JDBC driver using the current class loader
            Class.forName("org.sqlite.JDBC");
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + nome + ".db");            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(JogoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Cria uma tabela que armazenará dados do tipo informado na lista de String
     * statement. A lista deve ser formada da seguinte forma: "identificador " +
     * " tipo". Os tipos são: "integer, string, bool = [integer (0,1)]".
     * @param tableName nome da tabela que armazenara os dados     
     * @param statementList cabeçalho da tabela.      
     */
    public void createNewTable(String tableName, List<String> statementList) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate("drop table if exists " + tableName);
            
            String statementAux = getFormatedStatement(statementList);            
            
            statement.executeUpdate("create table " + tableName + " " + statementAux);
        } catch (SQLException ex) {
            Logger.getLogger(JogoBD.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    /**
     * Adiciona dados numa tabela já existente ou cria uma caso ela não exista.
     * Se o valor for do tipo String
     * então deve conter 'value' entre o valor.
     * @param tableName nome da tabela existente.
     * @param values Lista de valores a ser adicionado em dada tabela.
     */
    public void insertDataInTable(String tableName, List<String> values) {        
        String formatedStatement = getFormatedStatement(values);
        try {            
            statement.executeUpdate("insert into " + tableName + " values" + formatedStatement);
        } catch (SQLException ex) {
            Logger.getLogger(JogoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean containsInTable(String tableName, String identifier, String value) {
        boolean find = false;
        try {
            ResultSet rs = statement.executeQuery("select * from " + tableName);
            while(rs.next()) {
                if (value.equals(rs.getString(identifier)))
                    find = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(JogoBD.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return find;
    }

    
    private String getFormatedStatement(List<String> statementList) {
        String formatedStatement = "(";
        int sizeList = statementList.size();
        for (int index = 0; index < sizeList; index++) {
            formatedStatement += statementList.get(index);
            if (index + 1 == sizeList)
                formatedStatement += " )";
            else
                formatedStatement += ", ";            
        }
        return formatedStatement;
    }
}
