package chat;

import java.io.BufferedReader;
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
                    this.cliente.close();
                    listarUsuarios();
//****************************login********************************************
                } else if (mensagem.startsWith("login:")) {
                    //  escrever um código que
                    //  inicia novo cliente com o nome digitado
                    String nome = mensagem.substring(6, mensagem.length());
                    boolean login = false;
                    StringBuffer str = new StringBuffer();
                    for (String c : clientes.keySet()) {
                        if (nome == c) {
                            escrever.println("O nome do usuário não pôde ser registrado pois é inválido ou já está em uso");
                            escrever.println(login);
                        } else {
                            login = true;
                            //testar isso
                            new GerenciadorDeClientesThread(cliente);
                            escrever.println("O nome de usuário foi registrado com sucesso");
                            escrever.println(login);
                        }
                    }
                    //mensagem = leitor.readLine();
                    //this.nomeCliente = mensagem.toLowerCase();
                    //clientes.put(this.nomeCliente, this);
                } //else if(mensagem.startsWith("transmitir:remetente:")){
                //Inserir 
                //} 
                //****************************mensagem*****************************************
                //startsWith verifica se a string inicia com essa palavra
                else if (mensagem.toLowerCase().startsWith("mensagem:")) {
                    String nomeEMensagem = mensagem.substring(9, mensagem.length());
                    //Dividindo o texto digitado pelo dois pontos (:)
                    String array[] = new String[2];
                    String arrayNomes[] = new String[10];
                    array = nomeEMensagem.split(":");
                    String nomes = array[0];
                    //Se a posição 0, que tem o nome, tiver mais nomes separados por ";"
                    //divide os nomes por ";" e coloca no arraynomes
                    if (nomes.equalsIgnoreCase(";")) {
                        //Guardando os nomes no arrayNomes
                        arrayNomes = nomes.split(";");
                        for (String n : arrayNomes) {
                            GerenciadorDeClientesThread destinatarios = clientes.get(n);   
                            destinatarios.getEscrever().println("Transmitir: " + this.nomeCliente + " disse"+ " : " + array[1]);
                        }                        
//                        String nomesDestinatarios = Arrays.toString(arrayNomes);                        
                        escrever.println("enviando para " + Arrays.toString(arrayNomes)+".");

                    } else {
                        //Guardando o nome na posição zero e a posição 1 recebe o texto
                        String nomeDestinatario = array[0];

                        GerenciadorDeClientesThread destinatario = clientes.get(nomeDestinatario);
                        escrever.println("enviando para " + nomeDestinatario);
                        
                        if (destinatario == null) {
                            escrever.println("Cliente não existe");
                        } else {
//                        escrever.println(array[1]);
//                        escrever.println("Digite uma mensagem para " + destinatario.getNomeCliente());
                            destinatario.getEscrever().println("Transmitir: " + this.nomeCliente + " disse"/*+ destinatario.getNomeCliente() */ + " : " + array[1]);
                        }
                    }

                } //******************************transmitir*************************************
                //                else if (mensagem.equals("transmitir:")) {
                //                    escrever.println(this.nomeCliente + " disse: " + mensagem + " para " + nomeCliente);
                //                }
                //******************************listar*****************************************
                //Listar todos os clientes                
                else if (mensagem.equals("lista_usuarios:")) {
                    listarUsuarios();
//                    StringBuffer str = new StringBuffer();
//                    for (String c : clientes.keySet()) {
//                        str.append(c);
//                        str.append(", ");
//                    }
//                    escrever.println(str.toString());
                } 
//                    else {
//                    escrever.println(this.nomeCliente + " Você disse: " + mensagem);
//                }
            }

        } catch (IOException e) {
            //Se o cliente não responder, cair ou fechar.
            System.err.println("O cliente fechou");
        }
    }

    public void listarUsuarios() {
        //StringBuffer é mais rápida que a String
        StringBuffer str = new StringBuffer();
        for (String c : clientes.keySet()) {
            str.append(c);
//            escrever.print("lista_usuarios:");
            str.append(", ");
        }
        escrever.println(str.toString());
    }

    public void login() throws IOException {
        //O InputStream receber do cliente um pacote de dados em bytes.
        //O BufferedReader lê os bytes e converte em String.
        leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        //Falar com o cliente mandando mensagem pra fora. O true manda o println para o cliente de forma automática.
        escrever = new PrintWriter(cliente.getOutputStream(), true);
        //Cliente logando
        escrever.println("login:");
//        System.out.println("Qual o seu nome? ");
        //O que o for escrito pelo cliente é guardado em mensagem.
        mensagem = leitor.readLine();
        StringBuffer buscarNome = new StringBuffer();
        for (String c : clientes.keySet()) {
            buscarNome.append(c);
            
            //verificar isso até...
                boolean confirmacaoDeUsuario = false;            
                //corrigir teste falso
                if (buscarNome.equals(mensagem)) {
                    escrever.println("login:" + confirmacaoDeUsuario);
                } else {
                confirmacaoDeUsuario = true;
                escrever.println("login:" + confirmacaoDeUsuario);
                }
            //aqui...
        }
        //Guarda o nome digitado
        this.nomeCliente = mensagem.toLowerCase();
//        escrever.println("login: " + this.nomeCliente);
        //Colocar no mapa o próprio cliente.
        clientes.put(this.nomeCliente, this);
        listarUsuarios();
    }

//    public BufferedReader getLeitor() {
//        return leitor;
//    }
    public PrintWriter getEscrever() {
        return escrever;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

}
