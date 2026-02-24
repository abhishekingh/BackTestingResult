package backTesting;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.sql.*;
public class MarketDataExcelExporter {
    private static final String URL =
            "jdbc:mysql://localhost:3306/trading_analysis";
    private static final String USER = "root";
    private static final String PASSWORD = "Password";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    private static final String SELECT_SQL =
            "SELECT * FROM market_data_output ORDER BY sr_no";

    public void exportToExcel(String filePath) throws Exception {

        try (Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(SELECT_SQL);
            ResultSet rs = ps.executeQuery();
            Workbook workbook = new XSSFWorkbook()
        ) {

            Sheet sheet = workbook.createSheet("BackTesting Result");

            // ---------------- HEADER ----------------
            String[] headers = {
                    "Sr No", "Date", "Time", "Day",
                    "O", "H", "L", "C",
                    "RSI", "RSIMA", "VWAP-Week", "ADX",
                    "DI+", "DI-", "Trade Type", "Strike",
                    "1st Exp res", "2nd Exp res", "3rd Exp res", "4th Exp res",
                    "1st Exp Close", "2nd Exp Close", "3rd Exp Close", "4th Exp Close"
            };

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            // ---------------- DATA ----------------
            int rowNum = 1;

            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rs.getInt("sr_no"));                     // Sr No
                row.createCell(1).setCellValue(rs.getString("trade_date"));            // Date
                row.createCell(2).setCellValue(rs.getString("trade_time"));            // Time
                row.createCell(3).setCellValue(rs.getString("trade_day"));            // Day
                row.createCell(4).setCellValue(rs.getDouble("open_price"));            // O
                row.createCell(5).setCellValue(rs.getDouble("high_price"));            // H
                row.createCell(6).setCellValue(rs.getDouble("low_price"));            // L
                row.createCell(7).setCellValue(rs.getDouble("close_price"));            // C
                row.createCell(8).setCellValue(rs.getDouble("rsi"));            // RSI
                row.createCell(9).setCellValue(rs.getDouble("rsi_ma"));            // RSIMA
                row.createCell(10).setCellValue(rs.getDouble("vwap_week"));           // VWAP
                row.createCell(11).setCellValue(rs.getDouble("adx"));           // ADX
                row.createCell(12).setCellValue(rs.getDouble("di_plus"));           // DI+
                row.createCell(13).setCellValue(rs.getDouble("di_minus"));           // DI-
                row.createCell(14).setCellValue(rs.getString("trade_type"));           // Trade Type
                row.createCell(15).setCellValue(rs.getInt("strike"));              // Strike
                row.createCell(16).setCellValue(rs.getString("exp_1"));           // Exp1
                row.createCell(17).setCellValue(rs.getString("exp_2"));           // Exp2
                row.createCell(18).setCellValue(rs.getString("exp_3"));           // Exp3
                row.createCell(19).setCellValue(rs.getString("exp_4"));           // Exp4
                row.createCell(20).setCellValue(rs.getDouble("exp_1_close"));           // Exp1 Close
                row.createCell(21).setCellValue(rs.getDouble("exp_2_close"));           // Exp2 Close
                row.createCell(22).setCellValue(rs.getDouble("exp_3_close"));           // Exp3 Close
                row.createCell(23).setCellValue(rs.getDouble("exp_4_close"));           // Exp4 Close
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write file
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }

            System.out.println("Excel exported successfully: " + filePath);
        }
    }
}
