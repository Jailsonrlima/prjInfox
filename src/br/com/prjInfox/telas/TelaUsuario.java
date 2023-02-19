package br.com.prjInfox.telas;
import java.sql.*;
import br.com.prjInfox.dal.ModuloConexao;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;

/**
 * @author Jailson R Lima
 */
public class TelaUsuario extends javax.swing.JInternalFrame {
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null; 
    
    public TelaUsuario() {
        initComponents();
        conexao = ModuloConexao.conector();
    }
    
    // Metodo para pesquisar usuário
    public void Procura(){
        String sql = "select * from usuario where id =? or usuario like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuCodido.getText());
            pst.setString(2,txtUsuNome.getText()+"%");
            rs = pst.executeQuery();
            if (rs.next()) {
                txtUsuNome.setText(rs.getString(2));
                txtUsuLogin.setText(rs.getString(3));
                txtUsuSenha.setText(rs.getString(4));
                txtUsuTelefone.setText(rs.getString(5));
                //A linha abaixo refere-se ao combobox
                cboUsuPerfil.setSelectedItem(rs.getString(6));

            } else {
                JOptionPane.showMessageDialog(null, "Usuário não Cadastrado.");
                txtUsuNome.setText(null);
                txtUsuTelefone.setText(null);
                txtUsuLogin.setText(null);
                txtUsuSenha.setText(null);
                cboUsuPerfil.setSelectedItem(null);
                   }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //Metodo para incluir um novo usuário
    private void Adiciona() {
        String sql = "insert into usuario"
                + "(id,nome,usuario,senha,fone,perfil)"
                + "values"
                + "(?,?,?,?,?,?) ";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuCodido.getText());
            pst.setString(2, txtUsuNome.getText());
            pst.setString(3, txtUsuLogin.getText());
            pst.setString(4, txtUsuSenha.getText());
            pst.setString(5, txtUsuTelefone.getText());
            //update de um campo combobox requer  conversão para String, ja que o java não sabe qual o tipo de dados vem do CBO.
            pst.setString(6, cboUsuPerfil.getSelectedItem().toString());
            //Validação dos campos obrigatórios
            if ((txtUsuCodido.getText().isEmpty()) || (txtUsuNome.getText().isEmpty()) || (txtUsuLogin.getText().isEmpty()) || (txtUsuSenha.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatório");
            } else {
                //A linha abaixo atualiza a tabela tbusurio com os dados do formulario
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário Adicionado com Sucesso!");
                    txtUsuCodido.setText(null);
                    txtUsuNome.setText(null);
                    txtUsuTelefone.setText(null);
                    txtUsuLogin.setText(null);
                    txtUsuSenha.setText(null);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void Modifica() {
        String sql = "update usuario set usuario =?,usuario=?,senha=?,fone=?,perfil=? where id=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1,txtUsuNome.getText());
            pst.setString(2,txtUsuTelefone.getText());
            pst.setString(3, txtUsuLogin.getText());
            pst.setString(4, txtUsuSenha.getText());
            pst.setString(5, cboUsuPerfil.getSelectedItem().toString());
            pst.setString(6, txtUsuCodido.getText());
            //Validação dos campos obrigatórios
            if ((txtUsuCodido.getText().isEmpty()) || (txtUsuNome.getText().isEmpty()) || (txtUsuLogin.getText().isEmpty()) || (txtUsuSenha.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatório");
            } else {
                //A linha abaixo atualiza a tabela tbusurio com os dados do formulario
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do usuário modificardos com sucesso!");
                    txtUsuCodido.setText(null);
                    txtUsuNome.setText(null);
                    txtUsuTelefone.setText(null);
                    txtUsuLogin.setText(null);
                    txtUsuSenha.setText(null);
                }
            }
            
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //Metodo responsavél por remover o usuário
    private void Apaga() {
        int confirma = JOptionPane.showConfirmDialog(null, "Temcerteza que deseja apagar este usuário?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from usuario where id = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUsuCodido.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário Apagado com Sucesso.");
                    txtUsuCodido.setText(null);
                    txtUsuNome.setText(null);
                    txtUsuTelefone.setText(null);
                    txtUsuLogin.setText(null);
                    txtUsuSenha.setText(null);

                }

            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
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

        lblUsuId = new javax.swing.JLabel();
        lblUsoNome = new javax.swing.JLabel();
        lblUsuFone = new javax.swing.JLabel();
        lblUsuLogin = new javax.swing.JLabel();
        lblUsuSenha = new javax.swing.JLabel();
        lblUsuPerfil = new javax.swing.JLabel();
        txtUsuCodido = new javax.swing.JTextField();
        txtUsuNome = new javax.swing.JTextField();
        txtUsuTelefone = new javax.swing.JTextField();
        txtUsuLogin = new javax.swing.JTextField();
        txtUsuSenha = new javax.swing.JTextField();
        cboUsuPerfil = new javax.swing.JComboBox<>();
        btnUsuNovo = new javax.swing.JButton();
        btnUsuProcura = new javax.swing.JButton();
        btnUsuModifica = new javax.swing.JButton();
        btnUsuApaga = new javax.swing.JButton();
        txtUsuAviso = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Usuário");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setPreferredSize(new java.awt.Dimension(750, 659));

        lblUsuId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUsuId.setText("*Código");

        lblUsoNome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUsoNome.setText("*Nome");

        lblUsuFone.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUsuFone.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsuFone.setText("Telefone");

        lblUsuLogin.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUsuLogin.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblUsuLogin.setText("*Usuário");

        lblUsuSenha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUsuSenha.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblUsuSenha.setText("*Senha");

        lblUsuPerfil.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUsuPerfil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsuPerfil.setText("*Perfil");

        txtUsuCodido.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtUsuNome.setToolTipText("Digite o nome e sobreno. ");
        txtUsuNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuNomeActionPerformed(evt);
            }
        });

        txtUsuTelefone.setToolTipText("Digite o número do telefone de contato");
        txtUsuTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuTelefoneActionPerformed(evt);
            }
        });

        txtUsuLogin.setToolTipText("Digite o nome do usuário.");

        txtUsuSenha.setToolTipText("");
        txtUsuSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuSenhaActionPerformed(evt);
            }
        });

        cboUsuPerfil.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboUsuPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "user", "Administrador" }));
        cboUsuPerfil.setToolTipText("Escolha o perfil do usuário ");

        btnUsuNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/prjInfox/icones/Inclui.png"))); // NOI18N
        btnUsuNovo.setToolTipText("Click aqui, Para cadastrar um novo usuário.");
        btnUsuNovo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUsuNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuNovoActionPerformed(evt);
            }
        });

        btnUsuProcura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/prjInfox/icones/Pesquisa.png"))); // NOI18N
        btnUsuProcura.setToolTipText("Click aqui, para procurar um usuário cadastrado.");
        btnUsuProcura.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUsuProcura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuProcuraActionPerformed(evt);
            }
        });

        btnUsuModifica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/prjInfox/icones/Altera.png"))); // NOI18N
        btnUsuModifica.setToolTipText("Click aqui, para modificar um usuário cadastrado.");
        btnUsuModifica.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUsuModifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuModificaActionPerformed(evt);
            }
        });

        btnUsuApaga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/prjInfox/icones/Exclui.png"))); // NOI18N
        btnUsuApaga.setToolTipText("Click aqui, para apagar um usuário cadastrado.");
        btnUsuApaga.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUsuApaga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuApagaActionPerformed(evt);
            }
        });

        txtUsuAviso.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtUsuAviso.setForeground(new java.awt.Color(255, 0, 0));
        txtUsuAviso.setText("* Campos Obrigatorios");
        txtUsuAviso.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                txtUsuAvisoAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblUsuId, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtUsuCodido, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(txtUsuSenha)
                                        .addGap(45, 45, 45)
                                        .addComponent(lblUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(cboUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblUsoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtUsuNome))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblUsuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(txtUsuLogin)
                                        .addGap(45, 45, 45)
                                        .addComponent(lblUsuFone, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(txtUsuTelefone)))
                                .addGap(103, 103, 103))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUsuAviso, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnUsuNovo)
                                .addGap(10, 10, 10)
                                .addComponent(btnUsuModifica)
                                .addGap(10, 10, 10)
                                .addComponent(btnUsuApaga)
                                .addGap(14, 14, 14)
                                .addComponent(btnUsuProcura)
                                .addGap(93, 93, 93)))))
                .addGap(27, 27, 27))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(txtUsuAviso, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsuCodido, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblUsoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUsuFone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsuTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUsuNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuModifica, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuApaga, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuProcura, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        setBounds(0, 0, 1105, 680);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsuNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuNomeActionPerformed

    private void txtUsuTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuTelefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuTelefoneActionPerformed

    private void txtUsuSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuSenhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuSenhaActionPerformed

    private void btnUsuNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuNovoActionPerformed
        //Chama o metodo adiciona informações ao banco de dados 
        Adiciona();
    }//GEN-LAST:event_btnUsuNovoActionPerformed

    private void btnUsuProcuraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuProcuraActionPerformed
        //Chama o metodo que pesquisa informações no banco de dados  
        Procura();
    }//GEN-LAST:event_btnUsuProcuraActionPerformed

    private void txtUsuAvisoAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_txtUsuAvisoAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuAvisoAncestorAdded

    private void btnUsuModificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuModificaActionPerformed
        // Chama o metodo para atualizar os dados do usuario no bancode dados
        Modifica();
    }//GEN-LAST:event_btnUsuModificaActionPerformed

    private void btnUsuApagaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuApagaActionPerformed
        // Chama o metodo remover
        Apaga();
    }//GEN-LAST:event_btnUsuApagaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUsuApaga;
    private javax.swing.JButton btnUsuModifica;
    private javax.swing.JButton btnUsuNovo;
    private javax.swing.JButton btnUsuProcura;
    private javax.swing.JComboBox<String> cboUsuPerfil;
    private javax.swing.JLabel lblUsoNome;
    private javax.swing.JLabel lblUsuFone;
    private javax.swing.JLabel lblUsuId;
    private javax.swing.JLabel lblUsuLogin;
    private javax.swing.JLabel lblUsuPerfil;
    private javax.swing.JLabel lblUsuSenha;
    private javax.swing.JLabel txtUsuAviso;
    private javax.swing.JTextField txtUsuCodido;
    private javax.swing.JTextField txtUsuLogin;
    private javax.swing.JTextField txtUsuNome;
    private javax.swing.JTextField txtUsuSenha;
    private javax.swing.JTextField txtUsuTelefone;
    // End of variables declaration//GEN-END:variables
}
