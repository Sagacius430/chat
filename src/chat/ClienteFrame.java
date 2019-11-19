package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Lincoln CG3000001 & Carlos CG1502751
 */
public class ClienteFrame extends javax.swing.JFrame {

    private PrintWriter escrever;
    private BufferedReader leitor;
    private String nomeCliente;
    private ImageIcon imgEnviar;

    /**
     * Creates new form ClienteFrame
     */
    public ClienteFrame() {        
        initComponents();
//        setIcone();
        setVisible(true);
        String[] usuarios = new String[]{"Lincoln","Carlos"};
        prencherListaUsuarios(usuarios);        
    }
    
    //Preenche lista de usuários
    private void prencherListaUsuarios(String[] usuarios) {
        DefaultListModel modelo = new DefaultListModel();
        listaUsuarios.setModel(modelo);
        for (String usuario : usuarios) {
            modelo.addElement(usuario);
        }
    }
    
    private void iniciarEscritor() {
        //Escrevendo para o servidor
        //Esperando digitação           
        String mensagemTerminal = enviarTexto.getText();
        
        if (mensagemTerminal.isEmpty()) {
            JOptionPane.showMessageDialog(ClienteFrame.this, "Mensagem vazia");
            return;
        }
//        DefaultListModel usuarios = getListaUsuarios();
        Object usuario = listaUsuarios.getSelectedValue();
        if (usuario != null) {
//            receberTexto.append(enviarTexto.getText()+":::");//pega o texto escrito
//            receberTexto.append(enviarTexto.getText());//mandar para visor de mensagens
//            receberTexto.append("\n");
            iconeAvatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/chat/imagens/avatar2.png")));            
            exibirDados.setText(usuario.toString());
//            escrever.println(" enviou mensagem para: "+usuario);            
//            escrever.println(" disse: "+enviarTexto.getText()+"<-");
            escrever.println("mensagem:"+usuario+":"+enviarTexto.getText());
            
            //limpando editor
            enviarTexto.setText(" ");
            
            
        } else {
//            if (receberTexto.equals("sair")) {
//                System.exit(0);
//            }
            iconeAvatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/chat/imagens/avatarGrupo.png")));
            escrever.println("mensagem:"+"*"+":"+enviarTexto.getText());
            enviarTexto.setText(" ");
//            JOptionPane.showMessageDialog(ClienteFrame.this, "Selecione um usuário");
//            return;
        }        
    }
    
     public void iniciarChat() {
        try {
            //O socket se conectará ao servidor com IP e porta
            final Socket cliente = new Socket("localhost", 2424);
            //Ouvindo o que o servidor ou outro cliente está escrevendo
            escrever = new PrintWriter(cliente.getOutputStream(), true);

            leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

        } catch (IOException e) {
            System.err.println("Servidor fora do ar");
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        receberTexto = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        enviarTexto = new javax.swing.JTextArea();
        botaoEnviar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        botaoSair = new javax.swing.JButton();
        exibirDados = new javax.swing.JLabel();
        iconeAvatar = new javax.swing.JButton();
        botaoAnexar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaUsuarios = new javax.swing.JList();
        botaoAtualizar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        nomeLogin = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat");
        setBackground(new java.awt.Color(212, 228, 249));

        jPanel1.setBackground(new java.awt.Color(212, 228, 249));

        receberTexto.setEditable(false);
        receberTexto.setBackground(new java.awt.Color(224, 235, 249));
        receberTexto.setColumns(20);
        receberTexto.setRows(5);
        jScrollPane2.setViewportView(receberTexto);

        enviarTexto.setColumns(20);
        enviarTexto.setRows(5);
        jScrollPane3.setViewportView(enviarTexto);

        botaoEnviar.setBackground(new java.awt.Color(199, 220, 249));
        botaoEnviar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/chat/imagens/enviar.png"))); // NOI18N
        botaoEnviar.setToolTipText("Enviar");
        botaoEnviar.setBorder(null);
        botaoEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEnviarActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(212, 228, 252));

        botaoSair.setBackground(new java.awt.Color(199, 220, 249));
        botaoSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/chat/imagens/logout.png"))); // NOI18N
        botaoSair.setToolTipText("Sair");
        botaoSair.setBorder(null);
        botaoSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSairActionPerformed(evt);
            }
        });

        exibirDados.setText("Destinatario Logado");

        iconeAvatar.setBackground(new java.awt.Color(212, 228, 249));
        iconeAvatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/chat/imagens/avatarGrupo.png"))); // NOI18N
        iconeAvatar.setBorder(null);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(iconeAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exibirDados)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botaoSair, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botaoSair, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(exibirDados, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(iconeAvatar, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        botaoAnexar.setBackground(new java.awt.Color(199, 220, 249));
        botaoAnexar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/chat/imagens/anexo.png"))); // NOI18N
        botaoAnexar.setToolTipText("Anexar");
        botaoAnexar.setBorder(null);
        botaoAnexar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAnexarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botaoAnexar, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botaoEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(botaoAnexar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                        .addComponent(botaoEnviar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(212, 228, 249));

        listaUsuarios.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listaUsuarios);

        botaoAtualizar.setBackground(new java.awt.Color(199, 220, 249));
        botaoAtualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/chat/imagens/atualizar.png"))); // NOI18N
        botaoAtualizar.setToolTipText("Atualizar");
        botaoAtualizar.setBorder(null);
        botaoAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAtualizarActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(171, 201, 242));

        nomeLogin.setText("Nome logado");

        jToggleButton1.setBackground(new java.awt.Color(171, 201, 242));
        jToggleButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/chat/imagens/avatar.png"))); // NOI18N
        jToggleButton1.setToolTipText("Usuário Logado");
        jToggleButton1.setAutoscrolls(true);
        jToggleButton1.setBorder(null);
        jToggleButton1.setBorderPainted(false);
        jToggleButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nomeLogin)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nomeLogin)
                .addContainerGap())
        );

        nomeLogin.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(0, 9, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botaoAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoAtualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addContainerGap())
        );

        jMenu1.setText("Contato");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ferramentas");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Ajuda");
        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
  
    private void botaoEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEnviarActionPerformed
        imgEnviar = new ImageIcon("seng.png");
//        JButton botaoEnviar = new JButton("Sair",imgEnviar);
        iniciarEscritor();        
    }//GEN-LAST:event_botaoEnviarActionPerformed

    private void botaoAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAtualizarActionPerformed
        atualizarListaUsuarios();
    }//GEN-LAST:event_botaoAtualizarActionPerformed

    private void botaoSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSairActionPerformed
        System.exit(0);
        this.receberTexto.setEditable(false);
        this.enviarTexto.setEditable(false);
        
    }//GEN-LAST:event_botaoSairActionPerformed

    private void botaoAnexarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAnexarActionPerformed
        botaoAnexar.setToolTipText("Anexar");
    }//GEN-LAST:event_botaoAnexarActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoAnexar;
    private javax.swing.JButton botaoAtualizar;
    private javax.swing.JButton botaoEnviar;
    private javax.swing.JButton botaoSair;
    private javax.swing.JTextArea enviarTexto;
    private javax.swing.JLabel exibirDados;
    private javax.swing.JButton iconeAvatar;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JList listaUsuarios;
    private javax.swing.JLabel nomeLogin;
    private javax.swing.JTextArea receberTexto;
    // End of variables declaration//GEN-END:variables
   
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClienteFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClienteFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClienteFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClienteFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new ClienteFrame().setVisible(true);                
                ClienteFrame cliente = new ClienteFrame();//.setVisible(true);
                //corrigir a visibilidade da janela                
                cliente.iniciarChat(); 
//                cliente.setVisible(true);               
//                cliente.atualizarListaUsuarios();   
                cliente.iniciarLeitor();
                cliente.iniciarEscritor(); 
//            }
//        });
    }
    
    
    
    private void atualizarListaUsuarios(){
        escrever.println("lista_usuarios:");
    }     
    
    private void iniciarLeitor(){
        //lendo mensagens do servidor
        try {
            while(true){
                String mensagem = leitor.readLine();
                
                if(mensagem == null || mensagem.isEmpty())
                    continue;
                //recebe o texto
                if(mensagem.equals("lista_usuarios:")){
                    //verificar a listagem de usuários. talvez o leitor.readline para mensagem
                    String[] usuarios = leitor.readLine().split(";");//.substring(15, mensagem.length());
//                    String[] usuarios;// = new String[5];
//                    usuarios = lista.split(";");
                    prencherListaUsuarios(usuarios);                    
                }
                else if(mensagem.equals("login:")){
                    String login = JOptionPane.showInputDialog("login:");
                    escrever.println(login);                                        
                }
                else if(mensagem.equals("login: false")){
                    JOptionPane.showMessageDialog(ClienteFrame.this, "Digite um nome para logar no chat.");                    
                }
                else if(mensagem.equals("login: true")){
                    nomeLogin.setText(leitor.readLine()); //setar o nome de login aqui                    
                    atualizarListaUsuarios(); 
                }
                else{                
                    receberTexto.append(mensagem);
                    receberTexto.append("\n");
                    receberTexto.setCaretPosition(receberTexto.getDocument().getLength());                    
                }
            }
        } catch (IOException e) {
            System.out.println("Impossível ler mensagem do servidor");
        }
    }

    
    private DefaultListModel getListaUsuarios() {
        return (DefaultListModel) listaUsuarios.getModel();
    }

    //apagar isso, caso não use
    public void setIcone() {
        
//        setIconImage(Toolkit.//.getDefaultToolkit().getImage(getClass().getResource("avatar.png")));
//        URL caminhoIcone = getClass().getResource("/chat/imagens.avatar.png");
//        Image iconeTitulo = Toolkit.getImageAccessor();
//        this.setIconImage(iconeTitulo);
    }
    
}
