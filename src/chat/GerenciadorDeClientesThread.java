package chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.Arrays;
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
    private String mensagem;
    //Lista dos Clientes, Chave valor (chave:Nome digitado pelo cliente)(valor:gerenciador)
    private static final Map<String, GerenciadorDeClientesThread> clientes = new HashMap<String, GerenciadorDeClientesThread>();

    //Recebendo um cliente para cada gerenciador de cliente
    public GerenciadorDeClientesThread(Socket cliente) {
        this.cliente = cliente;
        start();
    }

    @Override
    public void run() {

        try {
            login();

            //Teste para conversar com o servidor
            while (true) {
                mensagem = leitor.readLine();

//****************************sair*********************************************
                if (mensagem.equalsIgnoreCase("sair:")) {
                    sair();
                } //****************************mensagem*****************************************
                //startsWith verifica se a string inicia com essa palavra
                else if (mensagem.toLowerCase().startsWith("mensagem:")) {
                    mensagem();

                } //****************************listar usuarios**********************************
                //Listar todos os clientes                
                else if (mensagem.equals("lista_usuarios:")) {
                    listarUsuarios();
                }
            }

        } catch (IOException e) {
            //Se o cliente não responder, cair ou fechar.
            System.err.println("O cliente fechou");
        }
    }

    public void sair() throws IOException {
        // isso ainda não funciona
        String sair = this.nomeCliente;
        escrever.println(sair);
            for (String c: clientes.keySet()) {
                boolean equals = sair.equals(c);
                if (equals) {
                    clientes.remove(this.nomeCliente, this); 
                    escrever.println("Será q foi?");
                    this.cliente.close();
                }else{
                    escrever.println("Merda não removeu");
                }
            }
        
            
        }
   
    public void listarUsuarios() {
        //StringBuffer é mais rápida que a String
        StringBuilder str = new StringBuilder();
        for (String c : clientes.keySet()) {
            str.append(c);
            str.append("; ");
        }
        //isso ainda não funciona
        synchronized (clientes) {
            for (String sincronizacao : clientes.keySet()) {
                if(sincronizacao == null)
                escrever.println("lista_usuarios: "+clientes.keySet());//teste
            }
            escrever.println("lista_usuarios: " + str.toString());
        }
    }

    public void login() throws IOException {
        //O InputStream receber do cliente um pacote de dados em bytes.
        //O BufferedReader lê os bytes e converte em String.
        leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        //Falar com o cliente mandando mensagem pra fora. O true manda o println para o cliente de forma automática.
        escrever = new PrintWriter(cliente.getOutputStream(), true);
        boolean login = false;
        while (!login) {
            //Cliente logando
            escrever.println("login:");
            //O que o for escrito pelo cliente é guardado em mensagem.
            mensagem = leitor.readLine();
            //Guarda o nome digitado com letras minusculas
            this.nomeCliente = mensagem.toLowerCase();
            boolean reset = false;
            for (String c : clientes.keySet()) {
                if (this.nomeCliente.equals(c)) {
                    escrever.println("login: false");
                    reset = true;
                    break;
                }
            }
            if (!reset) {
                escrever.println("Login: true");
                //Colocar no mapa o próprio cliente.
                clientes.put(this.nomeCliente, this);
                listarUsuarios();
                login = true;
            }

        }

    }

    public void mensagem() {
        String nomeEMensagem = mensagem.substring(9, mensagem.length());
        //Dividindo o texto digitado pelo dois pontos (:)
        String array[] = new String[2];
        String arrayNomes[] = new String[10];
        array = nomeEMensagem.split(":");
        String nomes = array[0];
        //Se a posição 0, que tem o nome, tiver mais nomes separados por ";"
        //divide os nomes por ";" e coloca no arraynomes
        if (array[0].contains(";")) {
            //Guardando os nomes no arrayNomes
            arrayNomes = nomes.split(";");
            //ainda não funciona
            for (String n : arrayNomes) {
                GerenciadorDeClientesThread destinatarios = clientes.get(n);
                destinatarios.getEscrever().println("Transmitir: " + this.nomeCliente + " disse" + " : " + array[1]);
            }
            escrever.println("enviando para " + Arrays.toString(arrayNomes) + ".");
            //Ainda não funciona
        } else if (nomes.equals("*")) {
            BufferedWriter buferDeEscrita;
            for (String n : arrayNomes) {
                GerenciadorDeClientesThread destinatarios = clientes.get(n);
                destinatarios.getEscrever().println("Transmitir: " + this.nomeCliente + " disse" + " : " + array[1]);

            }
        } else {
            //Guardando o nome na posição zero e a posição 1 recebe o texto
            String nomeDestinatario = array[0];

            GerenciadorDeClientesThread destinatario = clientes.get(nomeDestinatario);
            escrever.println("enviando para " + nomeDestinatario);

            if (destinatario == null) {
                escrever.println("Cliente não existe");
            } else {
                destinatario.getEscrever().println("Transmitir: " + this.nomeCliente + " disse"/*+ destinatario.getNomeCliente() */ + " : " + array[1]);
            }
        }
    }

    public PrintWriter getEscrever() {
        return escrever;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

}
