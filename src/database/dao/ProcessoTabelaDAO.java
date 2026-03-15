package database.dao;

import database.model.TBReplicacaoProcesso;
import database.model.TBReplicacaoProcessoTabela;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProcessoTabelaDAO {

    private Connection connection;

    private static final String SQL_SELECT_BY_PROCESSO_HABILITADO =
            "SELECT * FROM TB_REPLICACAO_PROCESSO_TABELA WHERE PROCESSO_ID = ? AND HABILITADO = TRUE ORDER BY ORDEM";

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM TB_REPLICACAO_PROCESSO_TABELA";

    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM TB_REPLICACAO_PROCESSO_TABELA WHERE ID = ?";

    private static final String SQL_INSERT =
            "INSERT INTO TB_REPLICACAO_PROCESSO_TABELA (PROCESSO_ID, TABELA_ORIGEM, TABELA_DESTINO, ORDEM, HABILITADO, DS_WHERE) VALUES (?, ?, ?, ? ,? ,?)";

    private static final String SQL_UPDATE =
            "UPDATE TB_REPLICACAO_PROCESSO_TABELA SET PROCESSO_ID = PROCESSO_ID = ? ,TABELA_ORIGEM = ? , TABELA_DESTINO = ? ,ORDEM = ? ,HABILITADO = ? ,DS_WHERE = ? WHERE ID = ?";

    private static final String SQL_DELETE =
            "DELETE FROM TB_REPLICACAO_PROCESSO_TABELA WHERE ID = ?";

    private PreparedStatement pstSelectByProcessoHabilitado;
    private PreparedStatement pstSelectAll;
    private PreparedStatement pstSelectById;
    private PreparedStatement pstInsert;
    private PreparedStatement pstUpdate;
    private PreparedStatement pstDelete;

    public ProcessoTabelaDAO(Connection connection) throws SQLException {
        this.connection = connection;
        this.pstSelectByProcessoHabilitado = connection.prepareStatement(SQL_SELECT_BY_PROCESSO_HABILITADO);
        this.pstSelectAll = connection.prepareStatement(SQL_SELECT_ALL);
        this.pstSelectById = connection.prepareStatement(SQL_SELECT_BY_ID);
        this.pstInsert = connection.prepareStatement(SQL_INSERT);
        this.pstUpdate = connection.prepareStatement(SQL_UPDATE);
        this.pstDelete = connection.prepareStatement(SQL_DELETE);
    }

    public ArrayList<TBReplicacaoProcessoTabela> selectByProcessoHabilitado(long processoId) throws SQLException{
        ArrayList<TBReplicacaoProcessoTabela> lista = new ArrayList<>();
        pstSelectByProcessoHabilitado.setLong(1, processoId);
        try (ResultSet rs = pstSelectByProcessoHabilitado.executeQuery()){
            while (rs.next()){
                lista.add(map(rs));
            }
        }
        return lista;
    }

    public ArrayList<TBReplicacaoProcessoTabela> selectAll() throws SQLException{
        ArrayList<TBReplicacaoProcessoTabela> lista = new ArrayList<>();
        try(ResultSet rs = pstSelectAll.executeQuery()){
            while (rs.next()){
                lista.add(map(rs));
            }
        }
        return lista;
    }

    public TBReplicacaoProcessoTabela selectById(long id) throws SQLException {
        pstSelectById.setLong(1, id);
        try (ResultSet rs = pstSelectById.executeQuery()) {
            return rs.next() ? map(rs) : null;
        }
    }

    public void insert(TBReplicacaoProcessoTabela tb) throws SQLException{
        pstInsert.setLong(1, tb.getProcessoId());
        pstInsert.setString(2, tb.getTabelaOrigem());
        pstInsert.setString(3, tb.getTabelaDestino());
        pstInsert.setInt(4, tb.getOrdem());
        pstInsert.setBoolean(5, tb.isHabilitado());
        pstInsert.setString(6, tb.getDsWhere());
        pstInsert.executeUpdate();
    }

    public void update(TBReplicacaoProcessoTabela tb) throws SQLException{
        pstUpdate.setLong(1, tb.getProcessoId());
        pstUpdate.setString(2, tb.getTabelaOrigem());
        pstUpdate.setString(3, tb.getTabelaDestino());
        pstUpdate.setInt(4, tb.getOrdem());
        pstUpdate.setBoolean(5, tb.isHabilitado());
        pstUpdate.setString(6, tb.getDsWhere());
        pstUpdate.setLong(7, tb.getId());
        pstUpdate.executeUpdate();
    }

    public void delete(Long id) throws SQLException{
        pstDelete.setLong(1, id);
        pstDelete.executeUpdate();
    }


    private TBReplicacaoProcessoTabela map(ResultSet rs) throws SQLException{
        TBReplicacaoProcessoTabela tb = new TBReplicacaoProcessoTabela();
        tb.setId(rs.getLong("ID"));
        tb.setProcessoId(rs.getLong("PROCESSO_ID"));
        tb.setTabelaOrigem(rs.getString("TABELA_ORIGEM"));
        tb.setTabelaDestino(rs.getString("TABELA_DESTINO"));
        tb.setOrdem(rs.getInt("ORDEM"));
        tb.setHabilitado(rs.getBoolean("HABILITADO"));
        tb.setDsWhere(rs.getString("DS_WHERE"));
        return tb;
    }
}
