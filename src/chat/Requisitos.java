package chat;

import java.util.ArrayList;

/**
 *
 * @author Lincoln
 */
public class Requisitos {
    
    ArrayList<GerenciadorDeClientesThread> clientes = new ArrayList<>(); 
    
   /**
    Especificação do Protocolo
login:nome
Mensagem enviada do cliente para o servidor para registrar o nome do usuário
Respostas
login:true
O nome de usuário foi registrado com sucesso
login:false
O nome do usuário não pôde ser registrado pois é inválido ou já está em uso
Exemplo:
login:João
* ****************************************
lista_usuarios:nomes
Mensagem enviada do servidor para o cliente quando a lista de usuários conectados for atualizada.
O parâmetro nomes deve conter uma lista de nomes separados por ponto e vírgula.
Cliente não deve responder a mensagem.
Exemplos:
lista_usuarios:João
lista_usuarios:João;Maria;José
* ****************************************
mensagem:destinatário:texto da mensagem
Mensagem enviada do cliente para o servidor quando este desejar enviar uma mensagem para os outros usuários.
O parâmetro destinatário pode ser um nome simples, uma lista de nomes separados por ponto e vírgula ou asterisco caso a mensagem deva ser enviada para todos os usuários.
O servidor não deve responder essa mensagem.
Exemplos:
mensagem:João:Bom dia
mensagem:João;Maria:Olá
mensagem:*:Bom dia galera!
* ****************************************
transmitir:remetente:destinatário:texto da mensagem
Mensagem enviada do servidor para o cliente quando o cliente é um dos destinatários da mensagem.
O parâmetro remetente é o nome do remetente
O parâmetro destinatário pode ser o nome de um destinatário, uma lista de destinatários separada por ponto e vírgula ou asterisco caso a mensagem seja destinada para todos os usuários.
O cliente não deve responder essa mensagem
Exemplos:
transmitir:João:Maria:Olá Maria, como vai? Aqui é o João
transmitir:Maria:João;José:Vocês terminaram o trabalho de LP2?
transmitir:*:Bom dia para todos!
* O chat irá operar na porta 2424 (escolha do Prof. Ederson !)

Última atualização: quinta, 3 Out 2019, 19:38
    */
    
}
