package service;

import database.dao.DirecaoDAO;
import database.dao.OrigemDao;
import database.dao.ProcessoTabelaDAO;
import database.dao.ReplicacaoProcessoDAO;
import database.model.TBReplicacaoDirecao;
import database.model.TBReplicacaoProcesso;
import database.model.TBReplicacaoProcessoTabela;

import java.sql.*;
import java.util.ArrayList;

public class ReplicacaoExecutar {

    private Connection connControle;
    private Connection connOrigem;
    private Connection connDestino;

    public ReplicacaoExecutar(Connection connControle){
        this.connControle = connControle;
        System.out.println("Replicacao iniciada. Acompanhe pelo Log");
        ReplicacaoIniciar();
        System.out.println("Replicacao finalizada. Aguardando inicio da proxima replicacao");
    }

    private void ReplicacaoIniciar(){

        ReplicacaoProcessoDAO processo = null;

        try {
            processo = new ReplicacaoProcessoDAO(connControle);

            ArrayList<TBReplicacaoProcesso> arlProcessos = processo.selectAll();
            if (arlProcessos != null && !arlProcessos.isEmpty()){
                for (TBReplicacaoProcesso p : arlProcessos){
                    if (p != null && p.isHabilitado()){

                        DirecaoDAO direcao = new DirecaoDAO(connControle);
                        ArrayList<TBReplicacaoDirecao> arlDirecao = direcao.selectByProcessoHabilitado(p.getId());

                        for (TBReplicacaoDirecao d : arlDirecao){
                            if (d != null && d.isHabilitado()){
                                connOrigem = DriverManager.getConnection(d.getDirecaoOrigem(), d.getUsuarioOrigem(), d.getSenhaOrigem());
                                if (connOrigem == null){
                                    System.out.println("Falha ao conectar no banco origem");
                                    continue;
                                }

                                connDestino = DriverManager.getConnection(d.getDirecaoDestino(), d.getUsuarioDestino(), d.getSenhaDestino());
                                if (connDestino == null){
                                    System.out.println("Falha ao conectar no banco destino");
                                }

                                ProcessoTabelaDAO tabela = new ProcessoTabelaDAO(connControle);
                                OrigemDao daoOrigem = new OrigemDao(connOrigem);

                                ArrayList<TBReplicacaoProcessoTabela> arlTabelas = tabela.selectByProcessoHabilitado(p.getId());

                                for (TBReplicacaoProcessoTabela t: arlTabelas){
                                    if (t != null && t.isHabilitado()){
                                        System.out.println("Origem: " +d.getDirecaoOrigem()+"<-->"+d.getDirecaoDestino()+"- Tabela: " + t.getTabelaOrigem());
                                        ResultSet resultado = daoOrigem.selectComandoOrigem(t.getTabelaOrigem(),t.getDsWhere());
                                        if (resultado != null){
                                            ResultSetMetaData metaData = resultado.getMetaData();
                                        }
                                    }
                                    else {
                                        System.out.println("Nenhuma tabela habilitada para replicar");

                                    }                                }
                            }
                            else {
                                System.out.println("Nenhum direcao habilitada para replicar");
                            }
                        }

                    }
                    else {
                        System.out.println("Nenhum processo habilitado");
                    }
                }

            }
            else {
                System.out.println("Nenhum processo encontrado");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
