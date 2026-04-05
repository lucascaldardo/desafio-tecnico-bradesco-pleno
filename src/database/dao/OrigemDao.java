package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrigemDao {

    private final Connection connection;

    public OrigemDao(Connection connection) {
        this.connection = connection;
    }

    public ResultSet selectComandoOrigem(final String tabela, String where) throws SQLException {
        String sql = "SELECT * FROM " + tabela + " WHERE " + where;
        System.out.println("Select na origem: " + sql);

        PreparedStatement pstSelect = connection.prepareStatement(sql);
        pstSelect.closeOnCompletion();

        return pstSelect.executeQuery();
    }
}