package backTesting;

import java.util.List;

public class AnalysisTrigger {
    public static void main(String[] args) throws Exception {
        String path="D:\\JavaWorkSpace\\InputExcel\\BTC_1Hr_25RSI&ADX_VWAP.xlsx";
        ExcelOperation eo=new ExcelOperation();
        Sql_market_data_raw data=new Sql_market_data_raw();

        /*List<MarketDataRaw> list=eo.read(path);
        data.insertAll(list);
        data.readWithPrevious();*/

         //puting all resulting data into sql file.................
        /*DecisionMaking dm=new DecisionMaking();
        List<List<String>> dataOutput=dm.ThreeRowReader(200,16,30);

        Sql_market_data_output sq=new Sql_market_data_output();
        sq.insertAll(dataOutput);*/

        //Exporting resulting data from sql to excelSheet
        /*MarketDataExcelExporter mde=new MarketDataExcelExporter();
        String resultPath="D:\\JavaWorkSpace\\OutoutExcel\\temp_BackTestingResult - Copy.xlsx";
        mde.exportToExcel(resultPath);*/
    }
}
