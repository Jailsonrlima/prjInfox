package br.com.prjInfox.telas;
import java.sql.*;      
import br.com.prjInfox.dal.ModuloConexao;
import javax.swing.JOptionPane;
//A linha baixo importa recursos da biblioteca rs2xml.jar
//import net.proteanit.sql.DbUtils;
/**
 * @author jailson R Lima
 */
public class TelaOS extends javax.swing.JInternalFrame {
    // Declaração de Variaveis
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    // Declaração de uma variavel para armazenar o texto de acordo com Radion Button
    private String tipo;
    
    public TelaOS() {
        initComponents();
        conexao = ModuloConexao.conector();
    }
    
    private void ProcuraCliente() {
        String sql = "select "
                + "id as Código,"
                + "nome as Nome,"
                + "fone as Fone "
                + "from cliente "
                + "where nome like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliPesquisa.getText() + "%");
            tblCliente.setVisible(true);
            rs = pst.executeQuery();
//            tblCliente.setModel(DbUtils.resultSetToTableModel(rs));
            
            txtOsNum.setText(null);
            txtOsData.setText(null);
            txtCliId.setText(null);
            txtOsEquipamento.setText(null);
            txtOsDefeito.setText(null);
            txtOsServico.setText(null);
            txtOsTecnico.setText(null);
            txtOsTotal.setText(null);
            btnOsNovo.setEnabled(true);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void PreencheOS() {
        int preenche = tblCliente.getSelectedRow();
        txtCliId.setText(tblCliente.getModel().getValueAt(preenche, 0).toString());
        txtCliPesquisa.setText(null);
    }
    //Metodo para inserir uma OS no banco de dados 
    private void EmiteOS() {
        String sql = "insert into ordemservico "
                + "(tipo,"
                + "situacao,"
                + "equipamento,"
                + "defeito,"
                + "servico,"
                + "tecnico,"
                + "valor,"
                + "id)"
                + "values (?,?,?,?,?,?,?,?)";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1,tipo);
            pst.setString(2,cboOSSituacao.getSelectedItem().toString());
            pst.setString(3,txtOsEquipamento.getText());
            pst.setString(4,txtOsDefeito.getText());
            pst.setString(5,txtOsServico.getText());
            pst.setString(6,txtOsTecnico.getText());
            pst.setString(7,txtOsTotal.getText().replace(",", ".")); //replace troca a virgula por um ponto.
            pst.setString(8,txtCliId.getText());

            if ((txtCliId.getText().isEmpty() || txtOsEquipamento.getText().isEmpty() || txtOsDefeito.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de serviço emitida com sucesso.");
                    txtCliId.setText(null);
                    txtOsEquipamento.setText(null);
                    txtOsDefeito.setText(null);
                    txtOsServico.setText(null);
                    txtOsTecnico.setText(null);
                    txtOsTotal.setText(null);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    // Metodo para pesquisar um ordem de serviço
    private void ProcuraOS() {
        String numOs = JOptionPane.showInputDialog("Número da OS"); // Caixa de entrada do tipo JOptionPane
        String sql = "Select * from ordemservico where idos =" + numOs;

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtOsNum.setText(rs.getString(1));
                txtOsData.setText(rs.getString(2));
                // criar variavel para armazenar o conteudo dos Radion Button
                String rbtTipo = rs.getString(3);
                if (rbtTipo.equals("os")) {
                    rbtOsServico.setSelected(true);
                    tipo = "os";
                } else {
                    rbtOsOrcamento.setSelected(true);
                    tipo = "orcamento";
                }
                cboOSSituacao.setSelectedItem(rs.getString(4));
                txtOsEquipamento.setText(rs.getString(5));
                txtOsDefeito.setText(rs.getString(6));
                txtOsServico.setText(rs.getString(7));
                txtOsTecnico.setText(rs.getString(8));
                txtOsTotal.setText(rs.getString(9));
                txtCliId.setText(rs.getString(10));
                //Evitar que o usuário duplique a os acidentalmente
                btnOsNovo.setEnabled(false);
//                txtCliPesquisa.setText(null);
//                txtCliPesquisa.setEditable(false);
//                txtCliPesquisa.setFocusable(false);
                  tblCliente.setVisible(false);
            }
        } catch (java.sql.SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "OS invalida!\n Verifique o Número Digitado.");

        } catch (SQLException erroSQL) {
            JOptionPane.showMessageDialog(null, erroSQL);
        }
    }
    private void ModificaOS(){
    String sql = "update ordemservico set tipo = ?, situacao = ?, equipamento = ?, defeito = ?,                          servico = ?, tecnico = ?,valor = ? where idos = ?";
    try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1,tipo);
            pst.setString(2,cboOSSituacao.getSelectedItem().toString());
            pst.setString(3,txtOsEquipamento.getText());
            pst.setString(4,txtOsDefeito.getText());
            pst.setString(5,txtOsServico.getText());
            pst.setString(6,txtOsTecnico.getText());
            pst.setString(7,txtOsTotal.getText().replace(",", ".")); //replace troca a virgula por um ponto.
            pst.setString(8,txtOsNum.getText());

            if ((txtCliId.getText().isEmpty() || txtOsEquipamento.getText().isEmpty() || txtOsDefeito.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de serviço modificada com sucesso.");
                    txtOsNum.setText(null);
                    txtOsData.setText(null);
                    txtCliId.setText(null);
                    txtOsEquipamento.setText(null);
                    txtOsDefeito.setText(null);
                    txtOsServico.setText(null);
                    txtOsTecnico.setText(null);
                    txtOsTotal.setText(null);
                    txtCliPesquisa.setEditable(true);
                    txtCliPesquisa.setFocusable(true);
                    tblCliente.setVisible(true);
                    btnOsNovo.setEnabled(true);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //Metodo para excluir uma OS.
    private void ApagaOS() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar está Ordem de serviço", "Atenção", JOptionPane.YES_NO_OPTION);
        String sql = "delete from ordemservico where idos = ?";
        if (confirma == JOptionPane.YES_NO_OPTION) {
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtOsNum.getText());
                int apagado = pst.executeUpdate();

                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de serviço apagada com sucesso!");
                    txtOsNum.setText(null);
                    txtOsData.setText(null);
                    txtCliId.setText(null);
                    txtOsEquipamento.setText(null);
                    txtOsDefeito.setText(null);
                    txtOsServico.setText(null);
                    txtOsTecnico.setText(null);
                    txtOsTotal.setText(null);
                    txtCliPesquisa.setEditable(true);
                    txtCliPesquisa.setFocusable(true);
                    tblCliente.setVisible(true);
                    btnOsNovo.setEnabled(true);

                }
            } catch (SQLException erroSQL) {
                JOptionPane.showMessageDialog(null, erroSQL);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Order de serviço não apagada!");
            txtOsNum.setText(null);
            txtOsData.setText(null);
            txtCliId.setText(null);
            txtOsEquipamento.setText(null);
            txtOsDefeito.setText(null);
            txtOsServico.setText(null);
            txtOsTecnico.setText(null);
            txtOsTotal.setText(null);
            txtCliPesquisa.setEditable(true);
            txtCliPesquisa.setFocusable(true);
            tblCliente.setVisible(true);
            btnOsNovo.setEnabled(true);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        lblOsNum = new javax.swing.JLabel();
        lblOsData = new javax.swing.JLabel();
        txtOsNum = new javax.swing.JTextField();
        txtOsData = new javax.swing.JTextField();
        rbtOsOrcamento = new javax.swing.JRadioButton();
        rbtOsServico = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        JScrollPane = new javax.swing.JScrollPane();
        tblCliente = new javax.swing.JTable();
        txtCliPesquisa = new javax.swing.JTextField();
        lblCliLupa = new javax.swing.JLabel();
        lblCliId = new javax.swing.JLabel();
        txtCliId = new javax.swing.JTextField();
        cboOSSituacao = new javax.swing.JComboBox<>();
        lblOsSituacao = new javax.swing.JLabel();
        lblOsEquipamento = new javax.swing.JLabel();
        lblOsDefeito = new javax.swing.JLabel();
        lblOsServico = new javax.swing.JLabel();
        lblOsTecnico = new javax.swing.JLabel();
        txtOsEquipamento = new javax.swing.JTextField();
        txtOsDefeito = new javax.swing.JTextField();
        txtOsServico = new javax.swing.JTextField();
        txtOsTecnico = new javax.swing.JTextField();
        lblOsTotal = new javax.swing.JLabel();
        txtOsTotal = new javax.swing.JTextField();
        btnOsImprimi = new javax.swing.JButton();
        btnOsApaga = new javax.swing.JButton();
        btnOsModifica = new javax.swing.JButton();
        btnOsPesquisa = new javax.swing.JButton();
        btnOsNovo = new javax.swing.JButton();
        lblAviso = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Ordem de Serviços");
        setPreferredSize(new java.awt.Dimension(750, 659));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblOsNum.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblOsNum.setText("NM.OS");

        lblOsData.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblOsData.setText("Data");

        txtOsNum.setEditable(false);
        txtOsNum.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtOsNum.setFocusable(false);

        txtOsData.setEditable(false);
        txtOsData.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtOsData.setFocusable(false);

        buttonGroup1.add(rbtOsOrcamento);
        rbtOsOrcamento.setText("Orçamento");
        rbtOsOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOsOrcamentoActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtOsServico);
        rbtOsServico.setText("Ordem de Serviço");
        rbtOsServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOsServicoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtOsNum, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblOsNum, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(rbtOsOrcamento)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtOsData)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblOsData, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rbtOsServico))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOsNum, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOsData, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOsNum, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOsData, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbtOsOrcamento)
                    .addComponent(rbtOsServico))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        JScrollPane.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        tblCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Fone"
            }
        ));
        tblCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClienteMouseClicked(evt);
            }
        });
        JScrollPane.setViewportView(tblCliente);

        txtCliPesquisa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCliPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisaKeyReleased(evt);
            }
        });

        lblCliLupa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/prjInfox/icones/Lupa.png"))); // NOI18N
        lblCliLupa.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblCliId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCliId.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCliId.setText("ID");

        txtCliId.setEditable(false);
        txtCliId.setFocusable(false);
        txtCliId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliIdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtCliPesquisa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblCliLupa)
                        .addGap(18, 18, 18)
                        .addComponent(lblCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblCliLupa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCliPesquisa)
                    .addComponent(txtCliId)
                    .addComponent(lblCliId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(JScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                .addContainerGap())
        );

        cboOSSituacao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cboOSSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Em Avaliação", "Aguardando Aprovação", "Orçamento Aprovado", "Orçamento Reprovado", "Aguardando Peças", "Abandonado  pelo Cliente", "Na Bancada", "Retornou", "Entregue", " ", " " }));

        lblOsSituacao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblOsSituacao.setText("Situação");

        lblOsEquipamento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblOsEquipamento.setText("*Equipamento");

        lblOsDefeito.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblOsDefeito.setText("*Defeito");

        lblOsServico.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblOsServico.setText("Serviço");

        lblOsTecnico.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblOsTecnico.setText("Técnico");

        txtOsEquipamento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtOsDefeito.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtOsServico.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtOsTecnico.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtOsTecnico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOsTecnicoActionPerformed(evt);
            }
        });

        lblOsTotal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblOsTotal.setText("Valor Total");

        txtOsTotal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtOsTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtOsTotal.setText("0,00");
        txtOsTotal.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtOsTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOsTotalActionPerformed(evt);
            }
        });

        btnOsImprimi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/prjInfox/icones/print.png"))); // NOI18N
        btnOsImprimi.setToolTipText("Imprime OS");
        btnOsImprimi.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnOsApaga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/prjInfox/icones/Exclui.png"))); // NOI18N
        btnOsApaga.setToolTipText("Apaga OS");
        btnOsApaga.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnOsApaga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsApagaActionPerformed(evt);
            }
        });

        btnOsModifica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/prjInfox/icones/Altera.png"))); // NOI18N
        btnOsModifica.setToolTipText("Modifica OS");
        btnOsModifica.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnOsModifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsModificaActionPerformed(evt);
            }
        });

        btnOsPesquisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/prjInfox/icones/Pesquisa.png"))); // NOI18N
        btnOsPesquisa.setToolTipText("Procura OS");
        btnOsPesquisa.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnOsPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsPesquisaActionPerformed(evt);
            }
        });

        btnOsNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/prjInfox/icones/Inclui.png"))); // NOI18N
        btnOsNovo.setToolTipText("Emite OS");
        btnOsNovo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnOsNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsNovoActionPerformed(evt);
            }
        });

        lblAviso.setForeground(new java.awt.Color(255, 0, 0));
        lblAviso.setText("*Campos Obrigatorios");
        lblAviso.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblOsSituacao)
                                .addGap(13, 13, 13)
                                .addComponent(cboOSSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblOsServico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblOsDefeito, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtOsServico)
                                        .addComponent(txtOsDefeito, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblOsTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtOsTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(70, 70, 70)
                                    .addComponent(lblOsTotal)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtOsTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblAviso, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(btnOsNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(btnOsPesquisa)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(btnOsModifica, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(btnOsApaga)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(btnOsImprimi, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(22, 22, 22))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblOsEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtOsEquipamento, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
                                .addGap(6, 6, 6)))
                        .addGap(16, 16, 16))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboOSSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblOsSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOsEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOsEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOsDefeito, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOsDefeito, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOsServico, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOsServico, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOsTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOsTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOsTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOsTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnOsApaga, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOsModifica, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOsNovo)
                    .addComponent(btnOsPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOsImprimi, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addComponent(lblAviso, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setBounds(0, 0, 1105, 680);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCliIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliIdActionPerformed

    private void txtOsTecnicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOsTecnicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOsTecnicoActionPerformed

    private void txtOsTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOsTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOsTotalActionPerformed

    private void txtCliPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisaKeyReleased
        // Chamando o metodo Procura
        ProcuraCliente();
    }//GEN-LAST:event_txtCliPesquisaKeyReleased

    private void tblClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClienteMouseClicked
        // TODO add your handling code here:
        PreencheOS();
    }//GEN-LAST:event_tblClienteMouseClicked

    private void rbtOsOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOsOrcamentoActionPerformed
        // adiciona o nome do Radion Button a variavél tipo
       tipo = "orcamento";
    }//GEN-LAST:event_rbtOsOrcamentoActionPerformed

    private void rbtOsServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOsServicoActionPerformed
        // adiciona o nome do Radion Button a variavél tipo
        tipo = "os";
    }//GEN-LAST:event_rbtOsServicoActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // Ao carregar o form seçeciona o Radion Button orcamento e passa o texto para variavel
        rbtOsOrcamento.setSelected(true);
        tipo = "orcamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnOsNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsNovoActionPerformed
        // Chama o metodo emite OS
        EmiteOS();
    }//GEN-LAST:event_btnOsNovoActionPerformed

    private void btnOsPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsPesquisaActionPerformed
        // Executa o metodo que pesquisa uma OS.
        ProcuraOS();
    }//GEN-LAST:event_btnOsPesquisaActionPerformed

    private void btnOsModificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsModificaActionPerformed
        // Executa o metodo que altera uma OS.
        
        ModificaOS();
    }//GEN-LAST:event_btnOsModificaActionPerformed

    private void btnOsApagaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsApagaActionPerformed
        // Executa o metodo que apaga a ordem de srviço
        ApagaOS();
    }//GEN-LAST:event_btnOsApagaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane JScrollPane;
    private javax.swing.JButton btnOsApaga;
    private javax.swing.JButton btnOsImprimi;
    private javax.swing.JButton btnOsModifica;
    private javax.swing.JButton btnOsNovo;
    private javax.swing.JButton btnOsPesquisa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboOSSituacao;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblAviso;
    private javax.swing.JLabel lblCliId;
    private javax.swing.JLabel lblCliLupa;
    private javax.swing.JLabel lblOsData;
    private javax.swing.JLabel lblOsDefeito;
    private javax.swing.JLabel lblOsEquipamento;
    private javax.swing.JLabel lblOsNum;
    private javax.swing.JLabel lblOsServico;
    private javax.swing.JLabel lblOsSituacao;
    private javax.swing.JLabel lblOsTecnico;
    private javax.swing.JLabel lblOsTotal;
    private javax.swing.JRadioButton rbtOsOrcamento;
    private javax.swing.JRadioButton rbtOsServico;
    private javax.swing.JTable tblCliente;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliPesquisa;
    private javax.swing.JTextField txtOsData;
    private javax.swing.JTextField txtOsDefeito;
    private javax.swing.JTextField txtOsEquipamento;
    private javax.swing.JTextField txtOsNum;
    private javax.swing.JTextField txtOsServico;
    private javax.swing.JTextField txtOsTecnico;
    private javax.swing.JTextField txtOsTotal;
    // End of variables declaration//GEN-END:variables
}
