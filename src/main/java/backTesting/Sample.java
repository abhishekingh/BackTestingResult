package backTesting;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class Sample {
    public static void main(String[] args) throws Exception {

        /*String filePath = "data.xlsx";   // your excel file
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            for (Row row : sheet) {
                for (Cell cell : row) {

                    Object value = readCell(cell, formatter);
                    System.out.print(value + "\t");
                }
                System.out.println();
            }
        }*/
        double d=50.21;
        double d2=51.23;
        double d3=Math.abs(d-d2);
        double rounded = Math.round(d3 * 100.0) / 100.0;
        System.out.println(rounded);
    }

    public static Object readCell(Cell cell, DataFormatter formatter) {

        if (cell == null) return "";

        switch (cell.getCellType()) {

            case STRING:
                return parseDateTimeIfNeeded(cell.getStringCellValue());

            case NUMERIC:
                return roundToTwoDecimals(cell.getNumericCellValue());

            case BOOLEAN:
                return cell.getBooleanCellValue();

            case FORMULA:
                return handleFormula(cell, formatter);

            case BLANK:
                return "";

            default:
                return formatter.formatCellValue(cell);
        }

    }
    public static Object parseDateTimeIfNeeded (String value){
        try {
            // Handles: 2023-01-01T05:30:00+05:30
            OffsetDateTime odt = OffsetDateTime.parse(value);

            String date = odt.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
            String day = odt.format(DateTimeFormatter.ofPattern("EEE"));
            String time = odt.format(DateTimeFormatter.ofPattern("HH:mm"));

            return date + " | " + day + " | " + time;
        } catch (Exception e) {
            return value; // normal string
        }
    }
    public static Object handleFormula (Cell cell, DataFormatter formatter){
        try {
            return roundToTwoDecimals(cell.getNumericCellValue());
        } catch (Exception e) {
            return formatter.formatCellValue(cell);
        }
    }
    public static double roundToTwoDecimals ( double value){
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
