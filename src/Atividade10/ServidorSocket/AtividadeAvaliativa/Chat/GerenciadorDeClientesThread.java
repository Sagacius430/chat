package Atividade10.ServidorSocket.AtividadeAvaliativa.Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lincoln
 */
public class GerenciadorDeClientesThread extends Thread {

    public Socket cliente;
    private String nomeCliente;
    private BufferedReader leitor;
    private PrintWriter escrever;
    //Lista dos Clientes, Chave valor (chave:Nome digitado pelo cliente)(valor:gerenciador)
    //ArrayList<GerenciadorDeClientesThread> clientes = new ArrayList<>(); 
    private static final Map<String,GerenciadorDeClientesThread> clientes = new HashMap<String,GerenciadorDeClientesThread>();

    public GerenciadorDeClientesThread(Socket cliente) {
        this.cliente = cliente;
        start();
    }

    @Override
    public void run() {

        try {
            //O InputStream receber do cliente um pacote de dados em bytes.
            //O BufferedReader lê os bytes e converte em String.
            leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            //Falar com o cliente mandando mensagem pra fora. O true manda o println para o cliente de forma automático.
            escrever = new PrintWriter(cliente.getOutputStream(), true);
            escrever.println("Qual o seu nome? ");
            //O que o for escrito pelo cliente é guardado em mensagem.
            String mensagem = leitor.readLine();
            //Guarda o nome digitado
            this.nomeCliente = mensagem;
            escrever.println("E aí?" + this.nomeCliente);
            //Colocar no mapa o próprio cliente.
            clientes.put(this.nomeCliente, this);

            //Teste para conversar com o servidor
            while (true) {
                mensagem = leitor.readLine();
                if (mensagem.equalsIgnoreCase("SAIR")) {
                    this.cliente.close();
                } else if (mensagem.startsWith("mensagem:destinatário:")) {
                    String nomeDestinatario = mensagem.substring(21, mensagem.length());
                    System.out.println("enviando para " + nomeDestinatario);
                    GerenciadorDeClientesThread destinatario = clientes.get(nomeDestinatario);
                    
                    if (destinatario == null) {
                        escrever.println("Cliente não existe");
                    } else {
                        destinatario.getEscrever().println(this.nomeCliente + " disse " + mensagem);
                    }
                    //Listar todos os clientes
                }if(mensagem.equals("lista_usuariios")){
                    StringBuffer str = new StringBuffer();
                            for(String c: clientes.keySet()){
                                str.append(c);
                                str.append("  ");
                            }
                            escrever.println(str.toString());
                }else {
                    escrever.println(this.nomeCliente + " Você disse: " + mensagem);
                }
            }

        } catch (IOException e) {
            //Se o cliente não responder, cair ou fechar.
            System.err.println("O cliente fechou");
        }
    }

    public BufferedReader getLeitor() {
        return leitor;
    }

    public PrintWriter getEscrever() {
        return escrever;
    }

}
