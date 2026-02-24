package backTesting;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ExcelOperation {
    public List<MarketDataRaw> read(String excelFile) throws Exception {

        List<MarketDataRaw> list = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(excelFile);
             Workbook wb = new XSSFWorkbook(fis)) {

            Sheet sheet = wb.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // skip header
                Row row = sheet.getRow(i);
                if (row == null) continue;

                MarketDataRaw d = new MarketDataRaw();

                d.setTimeIso(getString(row, 0));
                d.setOpen(getDecimal(row, 1));
                d.setHigh(getDecimal(row, 2));
                d.setLow(getDecimal(row, 3));
                d.setClose(getDecimal(row, 4));
                d.setVwapWeek(getDecimal(row, 5));
                d.setVolume(getLong(row, 6));
                d.setRsi(getDecimal(row, 7));
                d.setRsi_ma(getDecimal(row, 8));
                d.setAdx(getDecimal(row,9));
                d.setDiPlus(getDecimal(row, 10));
                d.setDiMinus(getDecimal(row, 11));
                d.setMom(getDecimal(row, 12));
                d.setOnBalanceVol(getDecimal(row, 13));

                list.add(d);
            }
        }
        return list;
    }

    private String getString(Row row, int col) {
        Cell c = row.getCell(col);
        return c == null ? null : c.getStringCellValue();
    }

    private BigDecimal getDecimal(Row row, int col) {
        Cell c = row.getCell(col);
        if (c == null || c.getCellType() != CellType.NUMERIC) return null;
        return BigDecimal.valueOf(c.getNumericCellValue())
                .setScale(2, RoundingMode.HALF_UP);
    }

    private long getLong(Row row, int col) {
        Cell c = row.getCell(col);
        if (c == null || c.getCellType() != CellType.NUMERIC) return 0L;
        return (long) c.getNumericCellValue();
    }
}
