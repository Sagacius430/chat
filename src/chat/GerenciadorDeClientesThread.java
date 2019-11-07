package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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
                    String array[] = new String[10];
                        //Verificar como dividir o que o usuário digitou e pegar o ponto e virgula
                        //para identificar quais usuários receberão a mensagem.
                        if(nomeEMensagem.equalsIgnoreCase(";")){
                            array = nomeEMensagem.split(";");
                            //dividir os nomes digitados em um array para enviar mensagem para a lista do usuário
                        }
                    
                    array = nomeEMensagem.split(":");
                    //Guardando o nome na posição zero e a posição 1 recebe o texto
                    String nomeDestinatario = array[0];

                    GerenciadorDeClientesThread destinatario = clientes.get(nomeDestinatario);
                    escrever.println("enviando para " + nomeDestinatario);

                    if (destinatario == null) {
                        escrever.println("Cliente não existe");
                    } else {
//                        escrever.println(array[1]);
//                        escrever.println("Digite uma mensagem para " + destinatario.getNomeCliente());
                        destinatario.getEscrever().println("Transmitir: "+this.nomeCliente + " disse"/*+ destinatario.getNomeCliente() */+" : "+ array[1]);
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
                } else {
                    escrever.println(this.nomeCliente + " Você disse: " + mensagem);
                }
            }

        } catch (IOException e) {
            //Se o cliente não responder, cair ou fechar.
            System.err.println("O cliente fechou");
        }
    }

    public void listarUsuarios() {
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
        //Falar com o cliente mandando mensagem pra fora. O true manda o println para o cliente de forma automático.
        escrever = new PrintWriter(cliente.getOutputStream(), true);
        //Cliente logando
        escrever.println("login:");
//        System.out.println("Qual o seu nome? ");
        //O que o fo r escrito pelo cliente é guardado em mensagem.
        mensagem = leitor.readLine();
        StringBuffer buscarNome = new StringBuffer();
        for (String c : clientes.keySet()) {
            buscarNome.append(c);
            boolean confirmacaoDeUsuario = false;
            
            //corrigir teste falso
            if (buscarNome.equals(mensagem)) {
                
                escrever.println("login:"+ confirmacaoDeUsuario );
            }else{                
                confirmacaoDeUsuario = true;
                escrever.println("login:"+ confirmacaoDeUsuario );
                
            }
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
