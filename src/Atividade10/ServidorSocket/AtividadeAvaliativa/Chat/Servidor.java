package Atividade10.ServidorSocket.AtividadeAvaliativa.Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Lincoln
 */
public class Servidor {
    public static void main(String[] args) throws IOException {
        
        ServerSocket servidor = null;
        
        try {
            System.out.println("Iniciando o servidor");
            servidor = new ServerSocket(12345);
            System.out.println("Servidor iniciado");
            
            //O servidor vai ficar recebendo clientes
            while(true){
                System.out.println("Servidor conectando ao cliente");
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
