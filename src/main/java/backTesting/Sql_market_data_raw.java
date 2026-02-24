package backTesting;

import java.sql.*;
import java.util.List;

public class Sql_market_data_raw {
    private static final String URL =
            "jdbc:mysql://localhost:3306/trading_analysis";

    private static final String USER = "root";
    private static final String PASSWORD = "Password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    private static final String TRUNCATE_SQL =
            "TRUNCATE TABLE market_data_raw";
    private static final String INSERT_SQL = """
        INSERT INTO market_data_raw
        (time_iso, open_price, high_price, low_price, close_price,
         vwap_week, volume, rsi, rsi_ma, di_plus, di_minus, mom, on_balance_vol,adx)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)
        """;

    public void insertAll(List<MarketDataRaw> dataList) throws Exception {

        try (Connection conn = getConnection()){
            Statement stmt = conn.createStatement();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL);
            //Truncate existing data
            stmt.execute(TRUNCATE_SQL);
            //Insert all data
            conn.setAutoCommit(false);

            for (MarketDataRaw d : dataList) {

                ps.setString(1, d.getTimeIso());
                ps.setBigDecimal(2, d.getOpen());
                ps.setBigDecimal(3, d.getHigh());
                ps.setBigDecimal(4, d.getLow());
                ps.setBigDecimal(5, d.getClose());
                ps.setBigDecimal(6, d.getVwapWeek());
                ps.setLong(7, d.getVolume());
                ps.setBigDecimal(8, d.getRsi());
                ps.setBigDecimal(9, d.getRsi_ma());
                ps.setBigDecimal(10, d.getDiPlus());
                ps.setBigDecimal(11, d.getDiMinus());
                ps.setBigDecimal(12, d.getMom());
                ps.setBigDecimal(13, d.getOnBalanceVol());
                ps.setBigDecimal(14,d.getAdx());

                ps.addBatch();
            }

            ps.executeBatch();
            conn.commit();
        }
    }
    public void readWithPrevious() throws Exception {
        //selecting data from raw table.............
        String sql = """
        SELECT rsi, rsi_ma, time_iso
        FROM market_data_raw
        WHERE rsi IS NOT NULL
          AND rsi_ma IS NOT NULL
        ORDER BY S_No
    """;

        //truncating matching table............
        String Truncate_Matching =
                "TRUNCATE TABLE matching";
        try(Connection con=getConnection();
            Statement stm= con.createStatement()){
            stm.execute(Truncate_Matching);
        }

        try (Connection conn =getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (!rs.next()) return;

            double currentRsi = rs.getDouble("rsi");
            double currentRSI_MA = rs.getDouble("rsi_ma");
            String currentTime = rs.getString("time_iso");
            while (rs.next()) {
                double nextRsi = rs.getDouble("rsi");
                double nextRSI_Ma = rs.getDouble("rsi_ma");
                String nextTime = rs.getString("time_iso");
                if(currentRsi<currentRSI_MA && nextRsi<nextRSI_Ma){
                    currentRsi = nextRsi;
                    currentRSI_MA = nextRSI_Ma;
                    currentTime = nextTime;
                    continue;
                }else if(currentRsi>currentRSI_MA && nextRsi>nextRSI_Ma){
                    currentRsi = nextRsi;
                    currentRSI_MA = nextRSI_Ma;
                    currentTime = nextTime;
                    continue;
                }else{
                    /*System.out.println(
                            "NEXT    -> " + nextTime +
                                    " RSI=" + nextRsi +
                                    " RSI MA=" + nextRSI_Ma
                    );
                    System.out.println("--------------------------------");*/
                    insertMatchingTimeIso(nextTime);
                }
                currentRsi = nextRsi;
                currentRSI_MA = nextRSI_Ma;
                currentTime = nextTime;
            }
        }
    }
    public static void insertMatchingTimeIso(String timeIso) throws Exception {
        String insertSql = """
        INSERT INTO matching (time_iso)
        VALUES (?)
    """;

        try (Connection conn =getConnection();
             PreparedStatement ps = conn.prepareStatement(insertSql)) {

            ps.setString(1, timeIso);
            ps.executeUpdate();
        }
    }
}
