import database.model.TBReplicacaoProcesso;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends JFrame {

    private JDesktopPane desktopPane;
    private static Connection connection;

    public Main(){
        setTitle("Sistema de Replicaçõ de Dados");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);

        JMenu meunuSistema = new JMenu("Sistema");

        JMenuItem itemExecutar = new JMenuItem("Executar Replicação");
        itemExecutar.addActionListener(e -> {});
        meunuSistema.add(itemExecutar);

        JMenuItem itemSair = new JMenuItem("Sair");
        itemSair.addActionListener(e -> {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            System.exit(0);
        });
        meunuSistema.add(itemSair);

        JMenu menuCadastro = new JMenu("Cadastro");

        JMenuItem itemProcesso = new JMenuItem("Processos");
        itemProcesso.addActionListener(e -> {});
        menuCadastro.add(itemProcesso);

        JMenuItem itemProcessoTabela = new JMenuItem("Processo x Tabelas");
        itemProcessoTabela.addActionListener(e -> {});
        menuCadastro.add(itemProcessoTabela);

        JMenuItem itemDirecao = new JMenuItem("Direções");
        itemDirecao.addActionListener(e -> {});
        menuCadastro.add(itemDirecao);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(meunuSistema);
        menuBar.add(menuCadastro);
        setJMenuBar(menuBar);

    }

    public static void main(String[] args) {

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/controle","postgres", "123");
            SwingUtilities.invokeLater(() -> new Main().setVisible(true));
        }
        catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Não foi possível conectar com o banco de dados");
            System.exit(0);
        }

    }
}