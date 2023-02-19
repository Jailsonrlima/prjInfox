package br.com.prjInfox.telas;

import java.sql.*;
import br.com.prjInfox.dal.ModuloConexao;
import javax.swing.JOptionPane;
//A linha baixo importa recursos da biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;
import Atxy2k.CustomTextField.RestrictedTextField;
import java.net.URL;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * @author jailson Rodrigues Lima
 */
public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaCliente() {
        initComponents();
        conexao = ModuloConexao.conector();
        RestrictedTextField valida = new RestrictedTextField(txtCep);
        valida.setOnlyNums(true);
        valida.setLimit(8);
    }
    private void buscaCep(){
    String logradouro = "";
    String tipoLogradouro = "";
    String resultado = null;
    String cep = txtCep.getText();
        try { //URL é a class no java resposavel por ler um conteudo da internet
            URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
            SAXReader xml = new SAXReader();
            Document documento = xml.read(url);
            Element root = documento.getRootElement();
            for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
                Element element = it.next();
                if(element.getQualifiedName().equals("cidade")){
                    txtCidade.setText(element.getText());
                }
                if(element.getQualifiedName().equals("bairro")){
                    txtBairro.setText(element.getText());
                }
                if(element.getQualifiedName().equals("uf")){
                    cboUf.setSelectedItem(element.getText());
                }
                if(element.getQualifiedName().equals("tipo_logradouro")){
                    tipoLogradouro = element.getText();
                }
                if(element.getQualifiedName().equals("logradouro")){
                    logradouro = element.getText();
                }
                if (element.getQualifiedName().equals("resultado")) {
                    resultado = element.getText();
                    if (resultado.equals("1")) {
                        lblSituacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/prjinfox/icones/icons8_checked_16.png")));
                    } else {
                        JOptionPane.showMessageDialog(null, "CEP não encontrado");
                    }
                }
            } //Linha abaixo concatena tipo logradou com logradou e mostra no jtextfiel endereço     
            txtEndereco.setText(tipoLogradouro+" "+logradouro);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
          
        }
    
    
    
    }

    public void Adiciona() {
        String sql = "insert into cliente "
                + "(nome,cpf_cnpj,rg_ie,fone,email,logradouro,numero,complemento,bairro,cidade,uf,cep) "
                + "values (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNome.getText());
            pst.setString(2, txtCnpj.getText());
            pst.setString(3, txtIE.getText());
            pst.setString(4, txtFone.getText());
            pst.setString(5, txtEmail.getText());
            pst.setString(6, txtEndereco.getText());
            pst.setString(7, txtNumero.getText());
            pst.setString(8, txtComplemento.getText());
            pst.setString(9, txtBairro.getText());
            pst.setString(10, txtCidade.getText());
            pst.setString(11, cboUf.getSelectedItem().toString());
            pst.setString(12, txtCep.getText());

            if ((txtNome.getText().isEmpty()) || (txtIE.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatório");
            } else {
                //A linha abaixo atualiza a tabela tbusurio com os dados do formulario
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente Adicionado com Sucesso!");
                        txtNome.setText(null);
                        txtCnpj.setText(null);
                        txtIE.setText(null);
                        txtFone.setText(null);
                        txtEmail.setText(null);
                        txtEndereco.setText(null);
                        txtComplemento.setText(null);
                        txtNumero.setText(null);
                        txtBairro.setText(null);
                        txtCidade.setText(null);
                        cboUf.setSelectedItem(null);
                        txtCep.setText(null);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    //Metodo para pesquisar clientes pelo nome com filtro
    public void Procura() {
        String sql = "select * from cliente where nome like ?";
        try {
            pst = conexao.prepareStatement(sql);
            //a contenação da "%" é a continuação do comado sql.
            pst.setString(1, txtPesquisa.getText() + "%");
            rs = pst.executeQuery();
            //A linha abaixo usar recursos da biblioteca rs2xml.jar para preencher a tabela cliente.
            tblCliente.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Metodo para preencher os campos da tela ao clikar na linha da tabela
    public void Preenche() {
        int preencher = tblCliente.getSelectedRow();
            txtCodigo.setText(tblCliente.getModel().getValueAt(preencher, 0).toString());
            txtNome.setText(tblCliente.getModel().getValueAt(preencher, 1).toString());
            txtCnpj.setText(tblCliente.getModel().getValueAt(preencher, 2).toString());
            txtIE.setText(tblCliente.getModel().getValueAt(preencher, 3).toString());
            txtFone.setText(tblCliente.getModel().getValueAt(preencher, 4).toString());
            txtEmail.setText(tblCliente.getModel().getValueAt(preencher, 5).toString());
            txtEndereco.setText(tblCliente.getModel().getValueAt(preencher, 6).toString());
            txtNumero.setText(tblCliente.getModel().getValueAt(preencher, 7).toString());
            txtComplemento.setText(tblCliente.getModel().getValueAt(preencher, 8).toString());
            txtBairro.setText(tblCliente.getModel().getValueAt(preencher, 9).toString());
            txtCidade.setText(tblCliente.getModel().getValueAt(preencher, 10).toString());
            cboUf.setSelectedItem(tblCliente.getModel().getValueAt(preencher, 11).toString());
            txtCep.setText(tblCliente.getModel().getValueAt(preencher, 12).toString());
            btnNovo.setEnabled(false);
    }

    //Metodo para alterar os dados do cliente na base de dados
    public void Modifica() {
        String sql = "update cliente set "
                + "nome=?,"
                + "cpf_cnpj=?,"
                + "rg_ie=?,"
                + "fone=?,"
                + "email=?,"
                + "logradouro=?,"
                + "numero=?,"
                + "complemento=?,"
                + "bairro=?,"
                + "cidade=?,"
                + "uf=?,"
                + "cep=?"
                + "where idcli=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNome.getText());
            pst.setString(2,txtCnpj.getText());
            pst.setString(3, txtIE.getText());
            pst.setString(4, txtFone.getText());
            pst.setString(5, txtEmail.getText());
            pst.setString(6, txtEndereco.getText());
            pst.setString(7, txtNumero.getText());
            pst.setString(8, txtComplemento.getText());
            pst.setString(9, txtBairro.getText());
            pst.setString(10, txtCidade.getText());
            pst.setString(11, cboUf.getSelectedItem().toString());
            pst.setString(12,txtCep.getText());

            //Validação dos campos obrigatórios
            if ((txtNome.getText().isEmpty()) || (txtIE.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatório");
            } else {
                //A linha abaixo atualiza a tabela cliente com os dados do formulario
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do cliente modificardos com sucesso!");
                        txtCodigo.setText(null);
                        txtNome.setText(null);
                        txtCnpj.setText(null);
                        txtIE.setText(null);
                        txtFone.setText(null);
                        txtEmail.setText(null);
                        txtEndereco.setText(null);
                        txtNumero.setText(null);
                        txtComplemento.setText(null);
                        txtBairro.setText(null);
                        txtCidade.setText(null);
                        cboUf.setSelectedItem(null);
                        txtCep.setText(null);
                        txtPesquisa.setText(null);
                        // Linha Abaixo Habilita o Botão Novo
                        btnNovo.setEnabled(true);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
    //Metodo para apagar os dados cadastrais de um cliente

    public void Apaga() {
        int confirma = JOptionPane.showConfirmDialog(null, "Temcerteza que deseja apagar este cliente?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from cliente where id=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCodigo.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente excluido com Sucesso.");
                        txtCodigo.setText(null);
                        txtNome.setText(null);
                        txtCnpj.setText(null);
                        txtIE.setText(null);
                        txtFone.setText(null);
                        txtEmail.setText(null);
                        txtEndereco.setText(null);
                        txtNumero.setText(null);
                        txtComplemento.setText(null);
                        txtBairro.setText(null);
                        txtCidade.setText(null);
                        cboUf.setSelectedItem(null);
                        txtCep.setText(null);
                        txtPesquisa.setText(null);
                        //Linha abaixo abilita o botão novo
                        btnNovo.setEnabled(true);

                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Exclusão do cliente cancelada.");
                txtCodigo.setText(null);
                txtNome.setText(null);
                txtCnpj.setText(null);
                txtIE.setText(null);
                txtFone.setText(null);
                txtEmail.setText(null);
                txtEndereco.setText(null);
                txtNumero.setText(null);
                txtComplemento.setText(null);
                txtBairro.setText(null);
                txtCidade.setText(null);
                cboUf.setSelectedItem(null);
                txtCep.setText(null);
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

        txtPesquisa = new javax.swing.JTextField();
        lblAviso = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblIE = new javax.swing.JLabel();
        txtIE = new javax.swing.JTextField();
        lblCNPJ = new javax.swing.JLabel();
        txtCnpj = new javax.swing.JTextField();
        lbliEndereco = new javax.swing.JLabel();
        txtEndereco = new javax.swing.JTextField();
        btnNovo = new javax.swing.JButton();
        btniModifica = new javax.swing.JButton();
        btnApaga = new javax.swing.JButton();
        lblNome = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblPesquisa = new javax.swing.JLabel();
        lblCep = new javax.swing.JLabel();
        txtCep = new javax.swing.JTextField();
        lblCidade = new javax.swing.JLabel();
        txtCidade = new javax.swing.JTextField();
        lblBairro = new javax.swing.JLabel();
        txtBairro = new javax.swing.JTextField();
        lblNM = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        cboUf = new javax.swing.JComboBox<>();
        lblUf = new javax.swing.JLabel();
        lblComplemento = new javax.swing.JLabel();
        txtComplemento = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtFone = new javax.swing.JTextField();
        lblFone = new javax.swing.JLabel();
        lblSituacao = new javax.swing.JLabel();
        btnBusca = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCliente = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Cliente");
        setMinimumSize(new java.awt.Dimension(1032, 0));
        setPreferredSize(new java.awt.Dimension(1032, 659));

        txtPesquisa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyReleased(evt);
            }
        });

        lblAviso.setForeground(new java.awt.Color(255, 0, 0));
        lblAviso.setText("* Campos Obrigatorios");

        lblCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCodigo.setText("Cód.");

        txtNome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lblIE.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblIE.setText("*IE");

        txtIE.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtIE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIEActionPerformed(evt);
            }
        });

        lblCNPJ.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCNPJ.setText("*CNPJ");

        txtCnpj.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lbliEndereco.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbliEndereco.setText("*Endereço");

        txtEndereco.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/prjInfox/icones/add.png"))); // NOI18N
        btnNovo.setToolTipText("Novo");
        btnNovo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNovo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btniModifica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/prjInfox/icones/Edit.png"))); // NOI18N
        btniModifica.setToolTipText("Modifica");
        btniModifica.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btniModifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btniModificaActionPerformed(evt);
            }
        });

        btnApaga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/prjInfox/icones/Remove.png"))); // NOI18N
        btnApaga.setToolTipText("Apaga");
        btnApaga.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnApaga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApagaActionPerformed(evt);
            }
        });

        lblNome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNome.setText("*Nome");

        txtCodigo.setBackground(new java.awt.Color(238, 238, 238));
        txtCodigo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCodigo.setBorder(null);
        txtCodigo.setEnabled(false);

        lblPesquisa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPesquisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/prjInfox/icones/Lupa.png"))); // NOI18N
        lblPesquisa.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblCep.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCep.setText("*CEP");

        txtCep.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCepActionPerformed(evt);
            }
        });

        lblCidade.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCidade.setText("Cidade");

        txtCidade.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lblBairro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblBairro.setText("*Bairro");

        txtBairro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtBairro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBairroActionPerformed(evt);
            }
        });

        lblNM.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNM.setText("Nº");

        txtNumero.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNumero.setToolTipText("");

        cboUf.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cboUf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
        cboUf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboUfActionPerformed(evt);
            }
        });

        lblUf.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lblUf.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUf.setText("UF");

        lblComplemento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblComplemento.setText("Comple.:");

        txtComplemento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtComplemento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtComplementoActionPerformed(evt);
            }
        });

        lblEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblEmail.setText("E-Mail");

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtFone.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtFone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFoneActionPerformed(evt);
            }
        });

        lblFone.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblFone.setText("*Fone");

        lblSituacao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btnBusca.setText("Busca");
        btnBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaActionPerformed(evt);
            }
        });

        tblCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NOME", "CPF/CNPJ", "RG/IE", "FONE", "E-MAIL", "ENDERECO", "NM", "COMPLEMENTO", "BAIRRO", "CIDADE", "ESTADO", "CEP"
            }
        ));
        tblCliente.setColumnSelectionAllowed(true);
        jScrollPane2.setViewportView(tblCliente);
        tblCliente.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNome, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtCnpj)
                                        .addGap(160, 160, 160))
                                    .addComponent(txtNome)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lbliEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtEndereco))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCep, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblAviso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                                            .addComponent(txtCep))
                                        .addGap(1, 1, 1)
                                        .addComponent(lblSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtCidade))
                                .addGap(209, 209, 209))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblIE, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtIE)
                                .addGap(160, 160, 160))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPesquisa)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblFone, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEmail)
                                    .addComponent(txtFone)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNM, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblUf, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtBairro, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                                    .addComponent(txtComplemento)
                                    .addComponent(cboUf, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btniModifica, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnApaga, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(63, 63, 63))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIE, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIE, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFone, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbliEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNM, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUf, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboUf, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblCep, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCep, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblAviso, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btniModifica, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnApaga, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(218, 218, 218))
        );

        txtCnpj.getAccessibleContext().setAccessibleName("");
        lblUf.getAccessibleContext().setAccessibleDescription("");

        setBounds(0, 0, 1105, 680);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // Metodo resposavél por inserir dados de cliente no banco de dados. 
        Adiciona();
    }//GEN-LAST:event_btnNovoActionPerformed
    // O evento abaixo exibe o resultado ao digitar.
    private void txtPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyReleased
        //Chama o metodo pesquisa
        Procura();
    }//GEN-LAST:event_txtPesquisaKeyReleased

    private void btniModificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btniModificaActionPerformed
        //Chama o metodo que altera os dados do cliente
        Modifica();
    }//GEN-LAST:event_btniModificaActionPerformed

    private void btnApagaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApagaActionPerformed
        // Chama o metodo que apaga os dados do clientena base de dados
        Apaga();
    }//GEN-LAST:event_btnApagaActionPerformed

    private void txtBairroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBairroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBairroActionPerformed

    private void txtCepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCepActionPerformed

    private void txtComplementoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComplementoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtComplementoActionPerformed

    private void txtIEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIEActionPerformed

    private void cboUfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboUfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboUfActionPerformed

    private void txtFoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoneActionPerformed

    private void btnBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaActionPerformed
        // TODO add your handling code here:
        if (txtCep.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o CEP");
        } else {
          buscaCep();
        }
    }//GEN-LAST:event_btnBuscaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApaga;
    private javax.swing.JButton btnBusca;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btniModifica;
    private javax.swing.JComboBox<String> cboUf;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAviso;
    private javax.swing.JLabel lblBairro;
    private javax.swing.JLabel lblCNPJ;
    private javax.swing.JLabel lblCep;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblComplemento;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFone;
    private javax.swing.JLabel lblIE;
    private javax.swing.JLabel lblNM;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblPesquisa;
    private javax.swing.JLabel lblSituacao;
    private javax.swing.JLabel lblUf;
    private javax.swing.JLabel lbliEndereco;
    private javax.swing.JTable tblCliente;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JTextField txtCep;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JTextField txtCnpj;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtComplemento;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEndereco;
    private javax.swing.JTextField txtFone;
    private javax.swing.JTextField txtIE;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
