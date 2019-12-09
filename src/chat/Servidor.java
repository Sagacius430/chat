package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Lincoln CG3000001 & Carlos CG1502751
 */
public class Servidor {
    public static void main(String[] args) throws IOException {
        
        ServerSocket servidor = null;
        
        try {
            System.out.println("Iniciando o servidor");
            servidor = new ServerSocket(2424);
            System.out.println("Servidor online");
            
            //O servidor vai ficar recebendo clientes
            while(true){
                System.out.println("Servidor esperando clientes...");
                //O accept espera um cliente conectar, assim vem um socket para o servidor
                Socket cliente = servidor.accept();
                //Criando um novo cliente
                new GerenciadorDeClientesThread(cliente); 
            }            
                        
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            servidor.close();
            System.err.println("Servidor Fechado");
        }
    }
}
