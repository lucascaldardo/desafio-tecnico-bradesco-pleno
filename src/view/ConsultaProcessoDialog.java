package view;

import database.dao.ReplicacaoProcessoDAO;
import database.model.TBReplicacaoProcesso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ConsultaProcessoDialog extends JDialog {

    private JTable table;
    private JButton btnSelecionar;
    private JButton btnCancelar;

    private TBReplicacaoProcesso selecionado;

    public ConsultaProcessoDialog(Frame parent, ReplicacaoProcessoDAO dao) throws Exception{
        super(parent, "Consulta - Processo", true);
        setSize(700, 400);
        setLocationRelativeTo(parent);
        setLayout(null);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("PROCESSO");
        model.addColumn("DESCRICAO");
        model.addColumn("HABILITADO");

        ArrayList<TBReplicacaoProcesso> lista = dao.selectAll();
        for (TBReplicacaoProcesso p : lista){
            model.addRow(new Object[]{
                    p.getId(),
                    p.getProcesso(),
                    p.getDescricao(),
                    p.isHabilitado()
            });
        }

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10,10,660,300);
        add(scrollPane);

        btnSelecionar = new JButton("SELECIONAR");
        btnSelecionar.setBounds(10,320,140,30);
        add(btnSelecionar);

        btnCancelar = new JButton("CANCELAR");
        btnCancelar.setBounds(160,320,140,30);
        add(btnCancelar);

        btnCancelar.addActionListener(e -> {
            selecionado = null;
            dispose();
        });

        btnSelecionar.addActionListener(e -> {
            int row =  table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma linha.");
                return;
            }
                    TBReplicacaoProcesso p = new TBReplicacaoProcesso();
            p.setId(Long.parseLong(table.getValueAt(row, 0).toString()));
            p.setProcesso(String.valueOf(table.getValueAt(row, 1)));
            p.setDescricao(String.valueOf(table.getValueAt(row, 2)));
            p.setHabilitado(Boolean.parseBoolean(table.getValueAt(row, 3).toString()));

            selecionado = p;
            dispose();
        });

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    btnSelecionar.doClick();
                }
            }
        });;
    }

    public TBReplicacaoProcesso getSelecionado(){
        return selecionado;
    }
}
