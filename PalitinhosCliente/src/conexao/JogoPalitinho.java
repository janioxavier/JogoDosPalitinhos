package conexao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface JogoPalitinho extends Remote{
    void jogar(int quantidade) throws RemoteException;
    void login(String nome) throws RemoteException;
    List<String> getLista() throws RemoteException; // retorna a lista de jogadores
}