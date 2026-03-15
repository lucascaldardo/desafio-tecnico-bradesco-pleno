package database.dao;

import database.model.TBReplicacaoDirecao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DirecaoDAO {

    private Connection connection;

    private static final String SQL_SELECT_BY_PROCESSO_HABILITADO =
            "SELECT * FROM TB_REPLICACAO_DIRECAO WHERE PROCESSO_ID = ? AND HABILITADO = TRUE";

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM TB_REPLICACAO_DIRECAO";

    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM TB_REPLICACAO_DIRECAO WHERE ID = ?";

    private static final String SQL_INSERT =
            "INSERT INTO TB_REPLICACAO_DIRECAO (PROCESSO_ID, DIRECAO_ORIGEM, DIRECAO_DESTINO, USUARIO_ORIGEM, USUARIO_DESTINO,  SENHA_ORIGEM, SENHA_DESTINO, HABILITADO) VALUES (?, ?, ?, ? ,? ,?, ?, ?)";

    private static final String SQL_UPDATE =
            "UPDATE TB_REPLICACAO_DIRECAO SET PROCESSO_ID = ? DIRECAO_ORIGEM = ? ,DIRECAO_DESTINO = ? ,USUARIO_ORIGEM = ? ,USUARIO_DESTINO = ? ,SENHA_ORIGEM = ? ,SENHA_DESTINO = ? ,HABILITADO = ? ,WHERE ID = ?";

    private static final String SQL_DELETE =
            "DELETE FROM TB_REPLICACAO_DIRECAO WHERE ID = ?";

    private PreparedStatement pstSelectByProcessoHabilitado;
    private PreparedStatement pstSelectAll;
    private PreparedStatement pstSelectById;
    private PreparedStatement pstInsert;
    private PreparedStatement pstUpdate;
    private PreparedStatement pstDelete;

    public DirecaoDAO(Connection connection) throws SQLException {
        this.connection = connection;
        this.pstSelectByProcessoHabilitado = connection.prepareStatement(SQL_SELECT_BY_PROCESSO_HABILITADO);
        this.pstSelectAll = connection.prepareStatement(SQL_SELECT_ALL);
        this.pstSelectById = connection.prepareStatement(SQL_SELECT_BY_ID);
        this.pstInsert = connection.prepareStatement(SQL_INSERT);
        this.pstUpdate = connection.prepareStatement(SQL_UPDATE);
        this.pstDelete = connection.prepareStatement(SQL_DELETE);
    }

    private ArrayList<TBReplicacaoDirecao> selectByProcessoHabilitado(long processoId) throws SQLException {
        ArrayList<TBReplicacaoDirecao> lista = new ArrayList<>();
        pstSelectByProcessoHabilitado.setLong(1, processoId);
        try(ResultSet rs = pstSelectByProcessoHabilitado.executeQuery()){
            while (rs.next()){
                lista.add(map(rs));
            }
        }
        return lista;
    }


    public ArrayList<TBReplicacaoDirecao> selectAll() throws SQLException{
        ArrayList<TBReplicacaoDirecao> lista = new ArrayList<>();
        try (ResultSet rs = pstSelectAll.executeQuery()) {
            while (rs.next()) {
                lista.add(map(rs));
            }
        }
        return lista;
    }

    public TBReplicacaoDirecao selectById(long id) throws SQLException {
        pstSelectById.setLong(1, id);
        try (ResultSet rs = pstSelectById.executeQuery()) {
            return rs.next() ? map(rs) : null;
        }
    }

    public void insert(TBReplicacaoDirecao tb) throws SQLException {
        pstInsert.setLong(1, tb.getProcessoId());
        pstInsert.setString(2, tb.getDirecaoOrigem());
        pstInsert.setString(3, tb.getDirecaoDestino());
        pstInsert.setString(4, tb.getUsuarioOrigem());
        pstInsert.setString(5, tb.getUsuarioDestino());
        pstInsert.setString(6, tb.getSenhaOrigem());
        pstInsert.setString(7, tb.getSenhaDestino());
        pstInsert.setBoolean(8, tb.isHabilitado());
        pstInsert.executeUpdate();
    }

    public void update(TBReplicacaoDirecao tb) throws SQLException {
        pstUpdate.setLong(1, tb.getProcessoId());
        pstUpdate.setString(2, tb.getDirecaoOrigem());
        pstUpdate.setString(3, tb.getDirecaoDestino());
        pstUpdate.setString(4, tb.getUsuarioOrigem());
        pstUpdate.setString(5, tb.getUsuarioDestino());
        pstUpdate.setString(6, tb.getSenhaOrigem());
        pstUpdate.setString(7, tb.getSenhaDestino());
        pstUpdate.setBoolean(8, tb.isHabilitado());
        pstUpdate.setLong(9, tb.getId());
    }

    public void delete(Long id) throws SQLException {
        pstDelete.setLong(1, id);
        pstDelete.executeUpdate();
    }

    private TBReplicacaoDirecao map (ResultSet rs) throws SQLException{
        TBReplicacaoDirecao tb = new TBReplicacaoDirecao();
        tb.setId(rs.getLong("ID"));
        tb.setProcessoId(rs.getLong("PROCESSO_ID"));
        tb.setDirecaoOrigem(rs.getString("DIRECAO_ORIGEM"));
        tb.setDirecaoDestino(rs.getString("DIRECAO_DESTINO"));
        tb.setUsuarioOrigem(rs.getString("USUARIO_ORIGEM"));
        tb.setUsuarioDestino(rs.getString("USUARIO_DESTINO"));
        tb.setSenhaOrigem(rs.getString("SENHA_ORIGEM"));
        tb.setSenhaDestino(rs.getString("SENHA_DESTINO"));
        tb.setHabilitado(rs.getBoolean("HABILITADO"));
        return tb;
    }
}
