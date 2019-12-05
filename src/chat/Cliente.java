package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Lincoln CG3000001 & Carlos CG1502751
 */
public class Cliente {
    public static void main(String[] args) {
        try {
            //O socket se conectará ao servidor com IP e porta
            final Socket cliente = new Socket("localhost",2424);
            
            //Falar e ouvir ao mesmo tempo usando uma nova thread
            new Thread(){
                @Override
                public void run(){
                    try {
                        //Ouvindo o que o servidor ou outro cliente está escrevendo
                        BufferedReader leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                        
                        while(true){
                            String mensagem = leitor.readLine();
                            System.out.println(mensagem);
                        }
                        
                    } catch (IOException e) {
                        System.err.println("Não é posssível ler menssagem do servidor ou cliente");
                        e.printStackTrace();
                    }                
                    
                    
                }
            }.start();
            
            //Escrevendo para o servidor
            PrintWriter escrever = new PrintWriter(cliente.getOutputStream(),true);
            BufferedReader leitorTerminal = new BufferedReader(new InputStreamReader(System.in));
            //Scanner leitorTerminal = new Scanner(cliente.getInputStream());
            
            //Esperando digitação do terminal            
            while(true){
                String mensagemTerminal = leitorTerminal.readLine();
                
                escrever.println(mensagemTerminal);
                if(mensagemTerminal.equalsIgnoreCase("SAIR:")){
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
