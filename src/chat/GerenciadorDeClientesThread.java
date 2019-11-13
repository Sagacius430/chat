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
import java.util.stream.Stream;

/**
 * @author Lincoln CG3000001 & Carlos CG1502751
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
            //O InputStream receber do cliente um pacote de dados em bytes.
            //O BufferedReader lê os bytes e converte em String.
            leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            //Falar com o cliente mandando mensagem pra fora. O true manda o println para o cliente de forma automática.
            escrever = new PrintWriter(cliente.getOutputStream(), true);

            login();

            //Teste para conversar com o servidor
            while (true) {
                mensagem = leitor.readLine();

//****************************sair*********************************************
                if (mensagem.equalsIgnoreCase("sair:")) {
                    sair();
                } //****************************mensagem*****************************************
                //startsWith verifica se a string inicia com essa palavra
                else if (mensagem.toLowerCase().equals("mensagem:")) {
                    mensagem();
                    String nomeDestinatario = mensagem.substring(mensagem.length());
                    System.out.println("enviando para: " + nomeDestinatario);
                    GerenciadorDeClientesThread destinatario = clientes.get(nomeDestinatario);
                    if (destinatario == null) {
                        escrever.println("O cliente informado não existe");
                    } else {
                        destinatario.getEscrever().println(this.nomeCliente + " disse: " + leitor.readLine());
                    }

                } //****************************listar usuarios**********************************
                //Listar todos os clientes                
                else if (mensagem.equals("lista_usuarios:")) {
                    listarUsuarios(this);
                } else {
                    escrever.println(this.nomeCliente + " " + mensagem);
                }
            }

        } catch (IOException e) {
            //Se o cliente não responder, cair ou fechar.
            System.err.println("O cliente fechou");
        }
    }

    public void sair() throws IOException {
        // isso ainda não funciona. corrigir
        String sair = this.nomeCliente;
        escrever.println(sair);
        GerenciadorDeClientesThread usuarioSaiu = clientes.get(sair);
        usuarioSaiu.getEscrever().println(this.nomeCliente+"Saiu");
        clientes.remove(usuarioSaiu);
        this.cliente.close();
//        for (String c : clientes.keySet()) {
//            boolean equals = sair.equals(c);
//            if (equals) {
//                clientes.remove(this.nomeCliente, this);
//                escrever.println("Será q foi?");
//                this.cliente.close();
//            } else {
//                escrever.println("Merda não removeu");
//            }
//        }

    }

    public synchronized void login() throws IOException {
        while (true) {
            escrever.println("login:");
            this.nomeCliente = leitor.readLine().toLowerCase().replaceAll(";","");
            if (this.nomeCliente.equalsIgnoreCase("null") || this.nomeCliente.isEmpty()) {
                escrever.println("login: false");
            } else if (clientes.containsKey(this.nomeCliente)) {
                escrever.println("login: false");
            } else {
                escrever.println("login: true");
                escrever.println("olá " + this.nomeCliente);
//            escrever.println(this.nomeCliente+": ");
                clientes.put(this.nomeCliente, this);

                for (String c : clientes.keySet()) {
                    listarUsuarios(clientes.get(c));
                }
            break;
            }
        }
        /*
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
         escrever.println("login_aceito");
         escrever.println("login: true");
         //Colocar no mapa o próprio cliente.
         clientes.put(this.nomeCliente, this);
         listarUsuarios();
         login = true;
         }

         }*/

    }

    public void listarUsuarios(GerenciadorDeClientesThread cliente) {
        //StringBuffer é mais rápida que a String
        StringBuilder strCliente = new StringBuilder();
        for (String c : clientes.keySet()) {
            if (cliente.getNomeCliente().equals(c)) {
                continue;
            }
            strCliente.append(c);
            strCliente.append("; ");
        }
        cliente.getEscrever().println("lista_usuarios:");
        cliente.getEscrever().println(strCliente.toString());
        //isso ainda não funciona. corrigir
//        synchronized (clientes) {
//            for (String sincronizacao : clientes.keySet()) {
//                if(sincronizacao == null)
//                escrever.println("lista_usuarios: "+clientes.keySet());//teste
//            }
//            escrever.println("lista_usuarios: " + str.toString());
//        }
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
            //Envia pra uma lista de usuário feita pelo usuário OBS:talvez vire um método
            for (String n : arrayNomes) {
                GerenciadorDeClientesThread destinatarios = clientes.get(n);
                destinatarios.getEscrever().println("Transmitir: " + this.nomeCliente + " disse" + " : " + array[1]);
            }
            escrever.println("enviando para " + Arrays.toString(arrayNomes) + ".");
            //Ainda não funciona
        } else if (array[0].contains("*")) {
            //mensagem anviada para todos, porém corrigir o enviar pra sí mesmo.OBS:talvez vire um método
            for (String n : clientes.keySet()) {
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
