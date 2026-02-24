package backTesting;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

public class Sql_market_data_output {
    private static final String URL =
            "jdbc:mysql://localhost:3306/trading_analysis";

    private static final String USER = "root";
    private static final String PASSWORD = "Password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    private static final String TRUNCATE_SQL =
            "TRUNCATE TABLE market_data_output";
    private static final String INSERT_SQL = """
        INSERT INTO market_data_output
        (trade_date,trade_time,trade_day, open_price, high_price, low_price, close_price,
          rsi, rsi_ma, vwap_week, adx, di_plus, di_minus, trade_type, strike, exp_1,
          exp_2, exp_3, exp_4, exp_1_close, exp_2_close, exp_3_close, exp_4_close )
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

    public void insertAll(List<List<String>> dataList) throws Exception {

        try (Connection conn = getConnection()){
            Statement stmt = conn.createStatement();
            PreparedStatement ps = conn.prepareStatement(INSERT_SQL);
            //Truncate existing data
            stmt.execute(TRUNCATE_SQL);
            //Insert all data
            for (List<String> row : dataList) {
                ps.setString(1,row.get(0));
                ps.setString(2, row.get(1));
                ps.setString(3,row.get(2));
                ps.setBigDecimal(4,new BigDecimal(row.get(3)));
                ps.setBigDecimal(5,new BigDecimal(row.get(4)));
                ps.setBigDecimal(6,new BigDecimal(row.get(5)));
                ps.setBigDecimal(7,new BigDecimal(row.get(6)));
                ps.setBigDecimal(8,new BigDecimal(row.get(7)));
                ps.setBigDecimal(9,new BigDecimal(row.get(8)));
                ps.setBigDecimal(10,new BigDecimal(row.get(9)));
                ps.setBigDecimal(11,new BigDecimal(row.get(10)));
                ps.setBigDecimal(12,new BigDecimal(row.get(11)));
                ps.setBigDecimal(13,new BigDecimal(row.get(12)));
                ps.setString(14,row.get(13));
                ps.setInt(15,Integer.parseInt(row.get(14)));
                ps.setString(16,row.get(15));
                ps.setString(17, row.get(16));
                ps.setString(18,row.get(17));
                ps.setString(19,row.get(18));
                ps.setBigDecimal(20,new BigDecimal(row.get(19)));
                ps.setBigDecimal(21,new BigDecimal(row.get(20)));
                ps.setBigDecimal(22,new BigDecimal(row.get(21)));
                ps.setBigDecimal(23,new BigDecimal(row.get(22)));
                ps.addBatch();
            }

            // 3. Execute batch
            ps.executeBatch();

        }
    }
}
