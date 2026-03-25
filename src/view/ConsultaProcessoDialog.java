package view;

import database.dao.ReplicacaoProcessoDAO;
import database.model.TBReplicacaoProcesso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ConsultaProcessoDialog extends JDialog {

    private JTable table;
    private JButton btnSelecionar;
    private JButton btnCancelar;

    private TBReplicacaoProcesso selecionado;

    public ConsultaProcessoDialog(JFrame parent, ReplicacaoProcessoDAO dao) throws Exception{
        super(parent, "Consulta de Processo");
        setSize(700,400);
        setLocationRelativeTo(parent);
        setResizable(false);
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
        scrollPane.setBounds(10,10,680,300);
        add(scrollPane);

        btnSelecionar = new JButton("SELECIONAR");
        btnSelecionar.setBounds(10,320,140,30);
        add(btnSelecionar);

        btnCancelar = new JButton("CANCELAR");
        btnCancelar.setBounds(170,320,150,30);
        add(btnCancelar);

        btnCancelar.addActionListener(e -> {
            selecionado = null;
            dispose();
        });

        btnSelecionar.addActionListener(e -> {
            int row =  table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um registro.");
                return;
            }
                    TBReplicacaoProcesso p = new TBReplicacaoProcesso();
                    p.setId(Integer.parseInt(table.getValueAt(row, 0).toString()));
                    p.setProcesso(table.getValueAt(row, 1).toString());
                    p.setDescricao(table.getValueAt(row, 2).toString());
                    p.setHabilitado(Boolean.parseBoolean(table.getValueAt(row, 3).toString()));


            selecionado = p;
            dispose();
        });

        table.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if (e.getClickCount() ==2){
                    btnSelecionar.doClick();
                }
            }
        });
    }

    public TBReplicacaoProcesso getSelecionado(){
        return selecionado;
    }
}
