package database.dao;

import database.model.TBReplicacaoProcesso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReplicacaoProcessoDAO {

    private Connection connection;

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM TB_REPLICACAO_PROCESSO";

    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM TB_REPLICACAO_PROCESSO WHERE ID = ?";

    private static final String SQL_INSERT =
            "INSERT INTO TB_REPLICACAO_PROCESSO (PROCESSO, DESCRICAO, HABILITADO) VALUES (?, ?, ?)";

    private static final String SQL_UPDATE =
            "UPDATE TB_REPLICACAO_PROCESSO SET PROCESSO = ? ,DESCRICAO = ? ,HABILITADO = ? WHERE ID = ?";

    private static final String SQL_DELETE =
            "DELETE FROM TB_REPLICACAO_PROCESSO WHERE ID = ?";

    private PreparedStatement pstSelectAll;
    private PreparedStatement pstSelectById;
    private PreparedStatement pstInsert;
    private PreparedStatement pstUpdate;
    private PreparedStatement pstDelete;

    public ReplicacaoProcessoDAO(Connection connection) throws SQLException {
        this.connection = connection;
        this.pstSelectAll = connection.prepareStatement(SQL_SELECT_ALL);
        this.pstSelectById = connection.prepareStatement(SQL_SELECT_BY_ID);
        this.pstInsert = connection.prepareStatement(SQL_INSERT);
        this.pstUpdate = connection.prepareStatement(SQL_UPDATE);
        this.pstDelete = connection.prepareStatement(SQL_DELETE);
    }

    public ArrayList<TBReplicacaoProcesso> selectAll() throws SQLException{
        ArrayList<TBReplicacaoProcesso> lista = new ArrayList<>();
        try(ResultSet rs = pstSelectAll.executeQuery()){
            while (rs.next()){
                lista.add(map(rs));
            }
        }
        return lista;
    }

    public TBReplicacaoProcesso selectBYId(long id) throws SQLException {
        pstSelectById.setLong(1, id);
        try(ResultSet rs = pstSelectById.executeQuery()){
            return rs.next() ? map(rs) : null;
        }
    }

    public void insert(TBReplicacaoProcesso tb) throws SQLException {
        pstInsert.setString(1, tb.getProcesso());
        pstInsert.setString(2, tb.getDescricao());
        pstInsert.setBoolean(3, tb.isHabilitado());
        pstInsert.executeUpdate();
    }

    public void update(TBReplicacaoProcesso processo) throws SQLException {
        pstUpdate.setString(1, processo.getProcesso());
        pstUpdate.setString(2, processo.getDescricao());
        pstUpdate.setBoolean(3, processo.isHabilitado());
        pstUpdate.setLong(4, processo.getId());
        pstUpdate.executeUpdate();
    }

    public void delete(Long id) throws SQLException {
        pstDelete.setLong(1, id);
        pstDelete.executeUpdate();
    }

    private TBReplicacaoProcesso map(ResultSet rs) throws SQLException {
        TBReplicacaoProcesso tb = new TBReplicacaoProcesso();
        tb.setId(rs.getLong("ID"));
        tb.setProcesso(rs.getString("PROCESSO"));
        tb.setDescricao(rs.getString("DESCRICAO"));
        tb.setHabilitado(rs.getBoolean("HABILITADO"));
        return tb;
        }
    }


