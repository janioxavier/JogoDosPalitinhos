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
    
    public JogoBD() {                
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
     * " tipo". Os tipos são: "integer, string, bool, float".
     * @param tableName nome da tabela que armazenara os dados     
     * @param statement cabeçalho da tabela.      
     */
    public void createNewTable(String tableName, List<String> statementList) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate("drop table if exists " + tableName);
            
            String statementAux = "(";
            int sizeList = statementList.size();
            for (int index = 0; index < sizeList; index++) {
                statementAux += statementList.get(index);
                if (index + 1 == sizeList)
                    statementAux += " )";
                else
                    statementAux += ", ";
            }
            
            
            statement.executeUpdate("create table " + tableName + " " + statementAux);
        } catch (SQLException ex) {
            Logger.getLogger(JogoBD.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    /**
     * Adiciona dados numa tabela já existente. Se o valor for do tipo String
     * então deve conter 'value' entre o valor.
     * @param tableName nome da tabela existente.
     * @param values Lista de valores a ser adicionado em dada tabela.
     */
    public void insertDataInTable(String tableName, List<String> values) {
        String value = "(";
        int sizeList = values.size();
        for (int index = 0; index < sizeList; index++) {
            value += values.get(index);
            if (index + 1 == sizeList)
                value += " )";
            else
                value += ", ";
            
        }
        try {
            statement.executeUpdate("insert into " + tableName + " values" + value);
        } catch (SQLException ex) {
            Logger.getLogger(JogoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
