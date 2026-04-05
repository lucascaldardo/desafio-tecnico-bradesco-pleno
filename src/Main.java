import database.model.TBReplicacaoProcesso;
import service.ReplicacaoExecutar;
import view.TelaReplicacaoDirecaoView;
import view.TelaReplicacaoProcessoTabelaView;
import view.TelaReplicacaoProcessoView;

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
        itemExecutar.addActionListener(e -> {

            new Thread(new Runnable() {
                @Override
                public void run() {
                   while (!Thread.currentThread().isInterrupted()){
                        new ReplicacaoExecutar(connection);
                       try {
                           Thread.sleep(6000);
                       } catch (InterruptedException ex) {
                           throw new RuntimeException(ex);
                       }
                   }
                }
            }).start();

        });
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
        itemProcesso.addActionListener(e -> {
            abrirTelaInternaProcesso();
        });
        menuCadastro.add(itemProcesso);

        JMenuItem itemProcessoTabela = new JMenuItem("Processo x Tabelas");
        itemProcessoTabela.addActionListener(e -> {
            abrirTelaInternaProcessoTabela();
        });
        menuCadastro.add(itemProcessoTabela);

        JMenuItem itemDirecao = new JMenuItem("Direções");
        itemDirecao.addActionListener(e -> {
            abrirTelaInternaDirecoes();
        });
        menuCadastro.add(itemDirecao);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(meunuSistema);
        menuBar.add(menuCadastro);
        setJMenuBar(menuBar);

    }

    private void abrirTelaInternaProcesso(){
        try {
            TelaReplicacaoProcessoView tela = new TelaReplicacaoProcessoView(connection);

            JInternalFrame internalFrame = new JInternalFrame("Processos", true, true, true, true);
            internalFrame.setSize(650, 360);
            internalFrame.setLayout(new BorderLayout());
            internalFrame.add(tela.getContentPane(), BorderLayout.CENTER);
            internalFrame.setVisible(true);
            desktopPane.add(internalFrame);
            internalFrame.setSelected(true);
        }
        catch (Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao abrir tela: " + ex.getMessage());
        }
    }

    private void abrirTelaInternaDirecoes() {
        try {
            TelaReplicacaoDirecaoView tela = new TelaReplicacaoDirecaoView(connection);

            JInternalFrame internalFrame = new JInternalFrame("Cadastro de Direções", true, true, true, true);
            internalFrame.setSize(820, 520);
            internalFrame.setLayout(new BorderLayout());
            internalFrame.add(tela.getContentPane(), BorderLayout.CENTER);
            internalFrame.setVisible(true);
            desktopPane.add(internalFrame);
            internalFrame.setSelected(true);
        }
        catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao abrir tela" + e.getMessage());
        }
    }

    private void abrirTelaInternaProcessoTabela(){
        try {
            TelaReplicacaoProcessoTabelaView tela = new TelaReplicacaoProcessoTabelaView(connection);

            JInternalFrame internalFrame = new JInternalFrame("Cadastro de Processos x Tabela", true, true, true, true);
            internalFrame.setSize(720, 500);
            internalFrame.setLayout(new BorderLayout());
            internalFrame.add(tela.getContentPane(), BorderLayout.CENTER);
            internalFrame.setVisible(true);
            desktopPane.add(internalFrame);
            internalFrame.setSelected(true);
        }
        catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao abrir tela" + e.getMessage());
        }
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