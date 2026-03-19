package view;

import database.dao.ReplicacaoProcessoDAO;
import database.model.TBReplicacaoProcesso;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class TelaReplicacaoProcessoView extends JFrame {

    private enum ModoTela {NENHUM, INSERT, UPDATE}
    private ModoTela modoTela = ModoTela.NENHUM;

    private final Connection connection;
    private final ReplicacaoProcessoDAO dao;

    private JTextField txfId;
    private JTextField txfProcesso;
    private JTextField txfDescricao;
    private JCheckBox chkHabilitado;

    private JButton btnSalvar;
    private JButton btnAdicionar;
    private JButton btnBuscar;
    private JButton btnExcluir;

    public TelaReplicacaoProcessoView(Connection connection) throws SQLException {

        this.connection = connection;
        this.dao = new ReplicacaoProcessoDAO(connection);

        setTitle("Cadastro de Processo");
        setSize(584,300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);


        btnBuscar = new JButton("BUSCAR");
        btnAdicionar = new JButton("ADICIONAR");
        btnSalvar = new JButton("SALVAR");
        btnExcluir = new JButton("EXCLUIR");

        btnBuscar.setBounds(10,10,130,30);
        btnAdicionar.setBounds(150,10,130,30);
        btnSalvar.setBounds(290,10,130,30);
        btnExcluir.setBounds(430,10,130,30);

        getContentPane().add(btnBuscar);
        getContentPane().add(btnAdicionar);
        getContentPane().add(btnSalvar);
        getContentPane().add(btnExcluir);

        JLabel lblId = new JLabel("ID: ");
        lblId.setBounds(10,70,120,25);
        getContentPane().add(lblId);

        txfId = new JTextField();
        txfId.setBounds(140,70,420,25);
        getContentPane().add(txfId);

        JLabel lblProcesso = new JLabel("PROCESSO: ");
        lblProcesso.setBounds(10,105,120,25);
        getContentPane().add(lblProcesso);

        txfProcesso = new JTextField();
        txfProcesso.setBounds(140,105,420,25);
        getContentPane().add(txfProcesso);

        JLabel lblDescricao = new JLabel("DESCRIÇÃO: ");
        lblDescricao.setBounds(10,140,120,25);
        getContentPane().add(lblDescricao);

        txfDescricao = new JTextField();
        txfDescricao.setBounds(140,140,420,25);
        getContentPane().add(txfDescricao);

        chkHabilitado = new JCheckBox("HABILITADO");
        chkHabilitado.setBounds(10,175,120,25);
        getContentPane().add(chkHabilitado);

        txfId.setEnabled(false);
        txfProcesso.setEnabled(false);
        txfDescricao.setEnabled(false);
        chkHabilitado.setEnabled(false);
        btnSalvar.setEnabled(false);
        btnExcluir.setEnabled(false);

        btnAdicionar.addActionListener(e -> {
            modoTela = ModoTela.INSERT;

            txfId.setText("");
            txfProcesso.setText("");
            txfDescricao.setText("");
            chkHabilitado.setSelected(true);

            txfId.setEnabled(true);
            txfProcesso.setEnabled(true);
            txfDescricao.setEnabled(true);
            chkHabilitado.setEnabled(true);
            btnSalvar.setEnabled(true);
            btnExcluir.setEnabled(true);
            });

        btnSalvar.addActionListener(e -> {

            try {
                if (txfProcesso.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(this, "Informe o PROCESSO.");
                }

                TBReplicacaoProcesso p = new TBReplicacaoProcesso();
                p.setProcesso(txfProcesso.getText().trim());
                p.setDescricao(txfDescricao.getText().trim());
                p.setHabilitado(chkHabilitado.isSelected());

                if (modoTela == ModoTela.INSERT){
                    dao.insert(p);
                    JOptionPane.showMessageDialog(null,  "Processo cadastrado.");
                }

                else if (modoTela == ModoTela.UPDATE){
                    if (txfId.getText().trim().isEmpty()){
                        JOptionPane.showMessageDialog(this, "ID não carregado para update.");
                        return;
                    }

                    p.setId(Integer.parseInt(txfId.getText()));
                    dao.update(p);
                    JOptionPane.showMessageDialog(this, "Processo atualizado.");
                }

                else {
                    JOptionPane.showMessageDialog(this, "Clique em ADICIONAR ou BUSCAR antes de SALVAR.");
                    return;
                }

                modoTela = ModoTela.NENHUM;
                txfProcesso.setEnabled(false);
                txfDescricao.setEnabled(false);
                chkHabilitado.setEnabled(false);
                btnSalvar.setEnabled(false);
            }

            catch (Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,  "Erro ao salvar: " +  ex.getMessage());
            }
        });

        btnSalvar.addActionListener(e -> {
            try {
                if (txfId.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(this, "ID não carregado para update.");
                    return;
                }

                int op = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir o registro? ", "Excluir", JOptionPane.YES_NO_OPTION);
                if (op != JOptionPane.YES_OPTION) return;

                long id = Long.parseLong(txfId.getText());
                dao.delete(id);
                JOptionPane.showMessageDialog(null,  "Processo excluído ");

                modoTela = ModoTela.NENHUM;

                txfId.setText("");
                txfProcesso.setText("");
                txfDescricao.setText("");
                chkHabilitado.setSelected(false);

                txfProcesso.setEnabled(false);
                txfDescricao.setEnabled(false);
                chkHabilitado.setEnabled(false);
                btnSalvar.setEnabled(false);
                btnExcluir.setEnabled(false);
            }

            catch (Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,  "Erro ao excluir registro: " +  ex.getMessage());
            }
        });

        btnBuscar.addActionListener(b -> {

            modoTela = ModoTela.UPDATE;
        });

    }
}
