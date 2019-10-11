package Atividade10.ServidorSocket.AtividadeAvaliativa.Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Lincoln
 */
public class Cliente {
    public static void main(String[] args) {
        try {
            //O socket se conectará ao servidor com IP e porta
            Socket cliente = new Socket("192.168.56.1",12345);
            
            //Falar e ouvir ao mesmo tempo usando uma nova thread
            new Thread(){
                @Override
                public void run(){
                    try {
                        BufferedReader leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                        
                        while(true){
                            String mensagem = leitor.readLine();
                            System.out.println("O servidor disse: "+mensagem);
                        }
                        
                    } catch (IOException e) {
                        System.err.println("Não é posssível ler menssagem do servidor");
                        e.printStackTrace();
                    }                
                    
                    
                }
            }.start();
            PrintWriter escrever = new PrintWriter(cliente.getOutputStream());
            BufferedReader leitorTerminal = new BufferedReader(new InputStreamReader(System.in));
            //Scanner leitorTerminal = new Scanner(cliente.getInputStream());
            
            //Esperando digitação do terminal
            
            while(true){
                String mensagemTerminal = leitorTerminal.readLine();
                
                escrever.println(mensagemTerminal);
                if(mensagemTerminal.equalsIgnoreCase("SAIR")){
                    System.exit(0);
                }
                    
                
            }
            
            //Ouvir mensagem
            
            
        } catch (IOException e) {
            System.err.println("Servidor fora do ar");
            e.printStackTrace();
        }
    }
}
