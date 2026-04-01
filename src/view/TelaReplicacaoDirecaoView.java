package view;

import database.dao.DirecaoDAO;
import database.dao.ReplicacaoProcessoDAO;
import database.model.TBReplicacaoDirecao;
import database.model.TBReplicacaoProcesso;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class TelaReplicacaoDirecaoView extends JFrame {

    private enum ModoTela {NENHUM, INSERT, UPDATE}
    private ModoTela modoTela = ModoTela.NENHUM;

    private final Connection connection;
    private final DirecaoDAO dao;
    private final ReplicacaoProcessoDAO daoProcesso;

    private JTextField  txtId;
    private JComboBox<TBReplicacaoProcesso> cbProcesso;

    private JTextField txtDirecaoOrigem;
    private JTextField txtUsuarioOrigem;
    private JPasswordField txtSenhaOrigem;

    private JTextField txtDirecaoDestino;
    private JTextField txtUsuarioDestino;
    private JPasswordField txtSenhaDestino;

    private JCheckBox chkHabilitado;

    private JButton btnBuscar;
    private JButton btnAdicionar;
    private JButton btnSalvar;
    private JButton btnExcluir;

    public TelaReplicacaoDirecaoView(Connection connection) throws SQLException {

        this.connection = connection;
        this.dao = new DirecaoDAO(connection);
        this.daoProcesso = new ReplicacaoProcessoDAO(connection);

        setTitle("TB_REPLICACAO_DIRECAO");
        setSize(760, 460);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        add(panel);

        btnBuscar = new JButton("BUSCAR");
        btnBuscar.setBounds(10, 10, 170, 30);
        panel.add(btnBuscar);

        btnAdicionar = new JButton("ADICIONAR");
        btnAdicionar.setBounds(190, 10, 170, 30);
        panel.add(btnAdicionar);

        btnSalvar = new JButton("SALVAR");
        btnSalvar.setBounds(370, 10, 170, 30);
        panel.add(btnSalvar);

        btnExcluir = new JButton("EXCLUIR");
        btnExcluir.setBounds(550, 10, 170, 30);
        panel.add(btnExcluir);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(10, 70, 140, 25);
        panel.add(lblId);

        txtId = new JTextField();
        txtId.setBounds(160, 70, 220, 25);
        panel.add(txtId);

        JLabel lblProcesso = new JLabel("PROCESSO:");
        lblProcesso.setBounds(10, 105, 140, 25);
        panel.add(lblProcesso);

        cbProcesso = new JComboBox<>();
        cbProcesso.setBounds(160, 105, 320, 25);
        panel.add(cbProcesso);

        JLabel lblHabilitado = new JLabel("HABILITADO:");
        lblHabilitado.setBounds(500, 105, 120, 25);
        panel.add(lblHabilitado);

        chkHabilitado = new JCheckBox("Sim");
        chkHabilitado.setBounds(620, 105, 80, 25);
        panel.add(chkHabilitado);

        JLabel lblOrigemTitulo = new JLabel("ORIGEM");
        lblOrigemTitulo.setBounds(10, 150, 200, 25);
        lblOrigemTitulo.setFont(lblOrigemTitulo.getFont().deriveFont(java.awt.Font.BOLD));
        panel.add(lblOrigemTitulo);

        JLabel lblDirecaoOrigem = new JLabel("DIRECAO_ORIGEM:");
        lblDirecaoOrigem.setBounds(10, 185, 140, 25);
        panel.add(lblDirecaoOrigem);

        txtDirecaoOrigem = new JTextField();
        txtDirecaoOrigem.setBounds(160, 185, 560, 25);
        panel.add(txtDirecaoOrigem);

        JLabel lblUsuarioOrigem = new JLabel("USUARIO_ORIGEM:");
        lblUsuarioOrigem.setBounds(10, 220, 140, 25);
        panel.add(lblUsuarioOrigem);

        txtUsuarioOrigem = new JTextField();
        txtUsuarioOrigem.setBounds(160, 220, 560, 25);
        panel.add(txtUsuarioOrigem);

        JLabel lblSenhaOrigem = new JLabel("SENHA_ORIGEM:");
        lblSenhaOrigem.setBounds(10, 255, 140, 25);
        panel.add(lblSenhaOrigem);

        txtSenhaOrigem = new JPasswordField();
        txtSenhaOrigem.setBounds(160, 255, 560, 25);
        panel.add(txtSenhaOrigem);

        JLabel lblDestinoTitulo = new JLabel("DESTINO");
        lblDestinoTitulo.setBounds(10, 300, 200, 25);
        lblDestinoTitulo.setFont(lblDestinoTitulo.getFont().deriveFont(java.awt.Font.BOLD));
        panel.add(lblDestinoTitulo);

        JLabel lblDirecaoDestino = new JLabel("DIRECAO_DESTINO:");
        lblDirecaoDestino.setBounds(10, 335, 140, 25);
        panel.add(lblDirecaoDestino);

        txtDirecaoDestino = new JTextField();
        txtDirecaoDestino.setBounds(160, 335, 560, 25);
        panel.add(txtDirecaoDestino);

        JLabel lblUsuarioDestino = new JLabel("USUARIO_DESTINO:");
        lblUsuarioDestino.setBounds(10, 370, 140, 25);
        panel.add(lblUsuarioDestino);

        txtUsuarioDestino = new JTextField();
        txtUsuarioDestino.setBounds(160, 370, 280, 25);
        panel.add(txtUsuarioDestino);

        JLabel lblSenhaDestino = new JLabel("SENHA_DESTINO:");
        lblSenhaDestino.setBounds(450, 370, 120, 25);
        panel.add(lblSenhaDestino);

        txtSenhaDestino = new JPasswordField();
        txtSenhaDestino.setBounds(570, 370, 150, 25);
        panel.add(txtSenhaDestino);

        cbProcesso.removeAllItems();
        ArrayList<TBReplicacaoProcesso> processos = daoProcesso.selectAll();
        for (TBReplicacaoProcesso p : processos) {
            cbProcesso.addItem(p);
        }

        txtId.setEnabled(false);
        cbProcesso.setEnabled(false);
        chkHabilitado.setEnabled(false);

        txtDirecaoOrigem.setEnabled(false);
        txtUsuarioOrigem.setEnabled(false);
        txtSenhaOrigem.setEnabled(false);

        txtDirecaoDestino.setEnabled(false);
        txtUsuarioDestino.setEnabled(false);
        txtSenhaDestino.setEnabled(false);

        btnSalvar.setEnabled(false);
        btnExcluir.setEnabled(false);

        btnAdicionar.addActionListener(e -> {
            modoTela = ModoTela.INSERT;

            txtId.setText("");
            if (cbProcesso.getItemCount() > 0) cbProcesso.setSelectedIndex(0);

            chkHabilitado.setSelected(true);

            txtDirecaoOrigem.setText("");
            txtUsuarioOrigem.setText("");
            txtSenhaOrigem.setText("");

            txtDirecaoDestino.setText("");
            txtUsuarioDestino.setText("");
            txtSenhaDestino.setText("");

            cbProcesso.setEnabled(true);
            chkHabilitado.setEnabled(true);

            txtDirecaoOrigem.setEnabled(true);
            txtUsuarioOrigem.setEnabled(true);
            txtSenhaOrigem.setEnabled(true);

            txtDirecaoDestino.setEnabled(true);
            txtUsuarioDestino.setEnabled(true);
            txtSenhaDestino.setEnabled(true);

            btnSalvar.setEnabled(true);
            btnExcluir.setEnabled(false);
        });

        btnSalvar.addActionListener(e -> {
            try {
                if (cbProcesso.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um PROCESSO.");
                    return;
                }
                if (txtDirecaoOrigem.getText().trim().isEmpty() || txtDirecaoDestino.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Informe DIRECAO_ORIGEM e DIRECAO_DESTINO.");
                    return;
                }

            TBReplicacaoProcesso pSel = (TBReplicacaoProcesso) cbProcesso.getSelectedItem();

             // poderia alterar da 221 até 229

            TBReplicacaoDirecao d = new TBReplicacaoDirecao();
            d.setProcessoId(pSel.getId());
            d.setHabilitado(chkHabilitado.isSelected());

            d.setDirecaoOrigem(txtDirecaoOrigem.getText().trim());
            d.setUsuarioOrigem(txtUsuarioOrigem.getText().trim());
            d.setSenhaOrigem(new String(txtSenhaOrigem.getPassword()));

            d.setDirecaoDestino(txtDirecaoDestino.getText().trim());
            d.setUsuarioDestino(txtUsuarioDestino.getText().trim());
            d.setSenhaDestino(new String(txtSenhaDestino.getPassword()));

                if (modoTela == ModoTela.INSERT) {
                    dao.insert(d);
                    JOptionPane.showMessageDialog(this, "Inserido com sucesso.");
                } else if (modoTela == ModoTela.UPDATE) {
                    if (txtId.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "ID não carregado para update.");
                        return;
                    }
                    d.setId(Long.parseLong(txtId.getText().trim()));
                    dao.update(d);
                    JOptionPane.showMessageDialog(this, "Atualizado com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(this, "Clique em ADICIONAR ou BUSCAR antes de salvar.");
                    return;
                }

            modoTela = ModoTela.NENHUM;

            cbProcesso.setEnabled(false);
            chkHabilitado.setEnabled(false);

            txtDirecaoOrigem.setEnabled(false);
            txtUsuarioOrigem.setEnabled(false);
            txtSenhaOrigem.setEnabled(false);

            txtDirecaoDestino.setEnabled(false);
            txtUsuarioDestino.setEnabled(false);
            txtSenhaDestino.setEnabled(false);

            btnSalvar.setEnabled(false);

            }
            catch (Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex.getMessage());
            }
        });

        btnExcluir.addActionListener(e -> {
            try {
                if (txtId.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(this, "ID não encontrado para exclusão");
                    return;
                }

                int op = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir o registro? ", "Excluir",
                        JOptionPane.YES_NO_OPTION);

                if (op != JOptionPane.YES_OPTION) return;

                long id = Long.parseLong(txtId.getText());
                dao.delete(id);

                JOptionPane.showMessageDialog(this, "Excluida com sucesso");

                modoTela = ModoTela.NENHUM;

                txtId.setText("");
                if (cbProcesso.getItemCount() > 0) cbProcesso.setSelectedIndex(0);
                chkHabilitado.setSelected(false);

                txtDirecaoOrigem.setText("");
                txtUsuarioOrigem.setText("");
                txtSenhaOrigem.setText("");

                txtDirecaoDestino.setText("");
                txtUsuarioDestino.setText("");
                txtSenhaDestino.setText("");

                cbProcesso.setEnabled(false);
                chkHabilitado.setEnabled(false);

                txtDirecaoOrigem.setEnabled(false);
                txtUsuarioOrigem.setEnabled(false);
                txtSenhaOrigem.setEnabled(false);

                txtDirecaoDestino.setEnabled(false);
                txtUsuarioDestino.setEnabled(false);
                txtSenhaDestino.setEnabled(false);

                btnSalvar.setEnabled(false);
                btnExcluir.setEnabled(false);
            }
            catch (Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
            }
        });

        btnBuscar.addActionListener(e -> {
            try {
                ConsultaDirecaoDialog dlg = new ConsultaDirecaoDialog(this, dao);
                dlg.setVisible(true);

                TBReplicacaoDirecao sel = dlg.getSelecionado();
                if (sel == null) return;

                modoTela = ModoTela.UPDATE;

                txtId.setText(String.valueOf(sel.getId()));
                chkHabilitado.setSelected(sel.isHabilitado());

                txtDirecaoOrigem.setText(sel.getDirecaoOrigem());
                txtUsuarioOrigem.setText(sel.getUsuarioOrigem());
                txtSenhaOrigem.setText(sel.getSenhaOrigem());

                txtDirecaoDestino.setText(sel.getDirecaoDestino());
                txtUsuarioDestino.setText(sel.getUsuarioDestino());
                txtSenhaDestino.setText(sel.getSenhaDestino());

                long pid = sel.getProcessoId();
                for (int i = 0; i <cbProcesso.getItemCount(); i++){
                    TBReplicacaoProcesso item = cbProcesso.getItemAt(i);
                    if (item != null && item.getId() == pid) {
                        cbProcesso.setSelectedIndex(i);
                        break;
                    }
                }

                cbProcesso.setEnabled(true);
                chkHabilitado.setEnabled(true);
                txtDirecaoOrigem.setEnabled(true);
                txtUsuarioOrigem.setEnabled(true);
                txtSenhaOrigem.setEnabled(true);
                txtDirecaoDestino.setEnabled(true);
                txtUsuarioDestino.setEnabled(true);
                txtSenhaDestino.setEnabled(true);
                btnSalvar.setEnabled(true);
                btnExcluir.setEnabled(true);

            } catch (Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "ID não encontrado" + ex.getMessage());
            }

        });


    }
}
