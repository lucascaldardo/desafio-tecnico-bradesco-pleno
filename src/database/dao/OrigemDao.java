package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrigemDao {

    private final Connection connection;

    private PreparedStatement pstSelect;

    public OrigemDao(Connection connection) {
        this.connection = connection;
    }

    public ResultSet selectComandoOrigem(final String tabela, String where) throws SQLException {
        pstSelect = connection.prepareStatement("SELECT * FROM " + tabela + " WHERE " + where);
        System.out.println("Select na origem: " + "SELECT * FROM " + tabela + " WHERE " + where);
        return pstSelect.executeQuery();

    }
}
