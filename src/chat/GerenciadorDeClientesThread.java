package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
                //Se o usuáro sair, sai do while e entra no catch
                if (mensagem.equalsIgnoreCase("sair:")) {
                    this.cliente.close();
                } 
//****************************mensagem*****************************************
                //startsWith verifica se a string inicia com essa palavra
                else if (mensagem.toLowerCase().startsWith("mensagem:")) {
                    mensagem(mensagem);
                } 
//****************************listar usuarios**********************************
                //Listar todos os clientes                
                else if (mensagem.equals("lista_usuarios:")) {
                    listarUsuarios(this.nomeCliente);
                }
            }

        } catch (IOException e) {
            //Se o cliente não responder, cair ou fechar.
            System.err.println("O cliente fechou");
            clientes.remove(this.nomeCliente);
            listarUsuarios(this.nomeCliente);
        }
    }

    public synchronized void login() throws IOException {
        while (true) {
            escrever.println("login:");
            
            this.nomeCliente = leitor.readLine().toLowerCase().replaceAll(";", "");

            if (this.nomeCliente.equalsIgnoreCase("null") || this.nomeCliente.isEmpty()) {
                escrever.println("login: false");
            } else if (clientes.containsKey(this.nomeCliente)) {
                escrever.println("login: false");
            } else {
                escrever.println("login: true");
                escrever.println(this.nomeCliente + " está online");
                clientes.put(this.nomeCliente, this);                
                listarUsuarios(this.nomeCliente);
                break;
            }
        }
    }

    public void listarUsuarios(String nomeCliente) {
        //StringBuffer é mais rápida que a String
        StringBuffer strCliente = new StringBuffer();
        for (String x : clientes.keySet()) {
//            if (nomeCliente.equals(x)) {
//                continue;
//            }
            strCliente.append(x);
            strCliente.append(";");
        }
        // remover usuário "fantasma" da lista
        if (strCliente.length() > 0) {
            strCliente.delete(strCliente.length() - 1, strCliente.length());
        }
        //Manda para a area de texto de usuarios
        for (String x : clientes.keySet()) {
            GerenciadorDeClientesThread cliente = clientes.get(x);
            //lista os usuários
            cliente.getEscrever().println("lista_usuarios:");
            cliente.getEscrever().println(strCliente.toString());
        }
    }

    public void mensagem(String mensagem) {
        String nomeEMensagem = mensagem.substring(9, mensagem.length());
        String array[] = new String[2];//está somente usando a posição 1 e 0
        String arrayNomes[] = new String[20];// manter 10 até encontrar um lógica dinâmica
        //Dividindo o texto digitado pelo dois pontos (:)
        array = nomeEMensagem.split(":");
        String nomes = array[0];
        String msg = array[1];

        //Se a posição 0, que tem o nome, tiver mais nomes separados por ";"
        //divide os nomes por ";" e coloca no arraynomes
        if (array[0].contains(";") || array[0].contains(",")) {
            //Guardando os nomes no arrayNomes
            if (array[0].contains(";")) {       //código de teste para enviar
                arrayNomes = nomes.split(";");  //msg para grupo selecionado
            }                                   //testar depois de
            else if (array[0].contains(",")) {  //corrigir reconhecimento
                arrayNomes = nomes.split(",");  //dos outros usuário no chat
            }                                   //obs: o objeto usuarios que vem do ClienteFrame utiliza ","
            //Envia pra uma lista de usuário feita pelo usuário OBS:talvez vire um método
            for (String n : arrayNomes) {
                GerenciadorDeClientesThread destinatarios = clientes.get(n);
                destinatarios.getEscrever().println(this.nomeCliente + ":" + array[1]);                
                escrever.println("transmitir:" + this.nomeCliente + ":" + nomeCliente +  mensagem);
                
            }
            escrever.println("enviando para " + Arrays.toString(arrayNomes) + ".");
        } else if (array[0].contains("*")) {
            //mensagem anviada para todos
            for (String n : clientes.keySet()) {
                GerenciadorDeClientesThread destinatarios = clientes.get(n);
                //falando como servidor
                System.out.println("transmitir:*"+":" + array[1]);
                //falando com o frame
                destinatarios.getEscrever().println(this.nomeCliente+" disse para todos: " + array[1]);
                //escrever.println("transmitir:*"+":" + array[1] /*mensagem*/);
            }

        } else {                
            //Guardando o nome na posição zero e a posição 1 recebe o texto
            String nomeDestinatario = array[0];
            GerenciadorDeClientesThread destinatario = clientes.get(nomeDestinatario);
            if (destinatario == null) {
                escrever.println("Cliente não existe");
            } else {
                //falando com o servidor
                System.out.println("transmitir:"+this.nomeCliente+ ":" + destinatario.nomeCliente + ":" + array[1]);
                //falando com o terminal
                escrever.println(this.nomeCliente+ " disse para " + destinatario.nomeCliente + ": " + array[1]);
                //falando com o frame
                destinatario.getEscrever().println(this.nomeCliente+ " disse para " + destinatario.getNomeCliente() + ":" + array[1]);
            }
//            }
        }
    }

    public PrintWriter getEscrever() {
        return escrever;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

}
