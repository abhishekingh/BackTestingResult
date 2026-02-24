package backTesting;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class DecisionMaking {
    private static final String URL =
            "jdbc:mysql://localhost:3306/trading_analysis";

    private static final String USER = "root";
    private static final String PASSWORD = "Password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
   static List<Double> dl=new ArrayList<>();
    List<List<String>> outputData=new ArrayList<>();
    public List<List<String>> ThreeRowReader(int range,int hour,int minute) throws Exception{
        List<String > al=new ArrayList<>();

        String sql =
                """
                SELECT m2.*
                FROM market_data_raw m1
                JOIN matching mt
                    ON m1.time_iso = mt.time_iso
                JOIN market_data_raw m2
                    ON m2.s_no BETWEEN m1.s_no AND m1.s_no + 2
                ORDER BY m1.time_iso, m2.time_iso
                """;

        try (
                Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            List<MarketDataRaw> list = new ArrayList<>();
            int counter = 0;
            int callSell=0;
            int putSell=0;
            int finalDiff = 0;
            while (rs.next()) {
                counter++;
                String timeIso = rs.getString("time_iso");

                Double openPrice=rs.getObject("open_price",Double.class);
                Double highPrice=rs.getObject("high_price",Double.class);
                Double lowPrice=rs.getObject("low_price",Double.class);
                Double closePrice=rs.getObject("close_price",Double.class);

                Double rsi = rs.getObject("rsi", Double.class);
                Double rsiMa = rs.getObject("rsi_ma", Double.class);

                Double VWap_week=rs.getObject("vwap_week",Double.class);

                Double di_plus = rs.getObject("di_plus", Double.class);
                Double di_minus = rs.getObject("di_minus", Double.class);

                Double adx = rs.getObject("adx", Double.class);

                double dif=Math.abs(rsi-rsiMa);
                /*if(rsi<rsiMa && dif>=1){
                    callSell++;
                }
                if(rsi>rsiMa && dif>=1){
                    putSell++;
                }*/

                if(rsi<rsiMa ){
                    callSell++;
                    if (dif >= 1 && counter>1) {
                        finalDiff = 1;
                    }
                }
                if(rsi>rsiMa){
                    putSell++;
                    if (dif >= 1 && counter>1) {
                        finalDiff = 1;
                    }
                }


                if(callSell==3 && finalDiff==1){
                    callSell=0;
                    putSell=0;
                    counter=0;
                    finalDiff=0;
                    /*System.out.printf(
                            "S_NO=%d TIME=%s RSI=%.2f RSI_MA=%.2f openPrice=%.2f closePrice=%.2f%n",
                            sNo,
                            timeIso,
                            rsi,
                            rsiMa,
                            openPrice,
                            closePrice
                    );*/
                    int  diff=(int)(double)(closePrice-openPrice);
                    if(diff<range){
                        int temp=(int)Math.max(closePrice,openPrice);
                        int rem=temp%range;
                        rem=range-rem;
                        int strike=temp+rem;
                        al=fourDayReader(timeIso,strike,"callSell",hour,minute);
                        //System.out.println("callSell"+" | Strike="+strike+"| "+al);
                        DateOperation dateOperation=new DateOperation();
                        String arr[]=dateOperation.dateConversion(timeIso);
                        List<String> row=new ArrayList<>();
                        if(al.size()==4 && dl.size()==4){
                            row.add(arr[0]);
                            row.add(arr[1]);
                            row.add(arr[2]);
                            row.add(Double.toString(openPrice));
                            row.add(Double.toString(highPrice));
                            row.add(Double.toString(lowPrice));
                            row.add(Double.toString(closePrice));
                            row.add(Double.toString(rsi));
                            row.add(Double.toString(rsiMa));
                            row.add(Double.toString(VWap_week));
                            row.add(Double.toString(adx));
                            row.add(Double.toString(di_plus));
                            row.add(Double.toString(di_minus));
                            row.add("callSell");
                            row.add(Integer.toString(strike));
                            row.add(al.get(0));
                            row.add(al.get(1));
                            row.add(al.get(2));
                            row.add(al.get(3));
                            row.add(Double.toString(dl.get(0)));
                            row.add(Double.toString(dl.get(1)));
                            row.add(Double.toString(dl.get(2)));
                            row.add(Double.toString(dl.get(3)));
                            outputData.add(row);
                        }

                    }else{
                        int temp=(int)(double)(closePrice+openPrice)/2;
                        int rem=temp%range;
                        rem=range-rem;
                        int strike=temp+rem;
                        al=fourDayReader(timeIso,strike,"callSell",hour,minute);
                        //System.out.println("callSell"+" | Strike="+strike+"| "+al);
                        DateOperation dateOperation=new DateOperation();
                        String arr[]=dateOperation.dateConversion(timeIso);
                        List<String> row=new ArrayList<>();
                        if(al.size()==4 && dl.size()==4){
                            row.add(arr[0]);
                            row.add(arr[1]);
                            row.add(arr[2]);
                            row.add(Double.toString(openPrice));
                            row.add(Double.toString(highPrice));
                            row.add(Double.toString(lowPrice));
                            row.add(Double.toString(closePrice));
                            row.add(Double.toString(rsi));
                            row.add(Double.toString(rsiMa));
                            row.add(Double.toString(VWap_week));
                            row.add(Double.toString(adx));
                            row.add(Double.toString(di_plus));
                            row.add(Double.toString(di_minus));
                            row.add("callSell");
                            row.add(Integer.toString(strike));
                            row.add(al.get(0));
                            row.add(al.get(1));
                            row.add(al.get(2));
                            row.add(al.get(3));
                            row.add(Double.toString(dl.get(0)));
                            row.add(Double.toString(dl.get(1)));
                            row.add(Double.toString(dl.get(2)));
                            row.add(Double.toString(dl.get(3)));
                            outputData.add(row);
                        }
                    }
                }
                if(putSell==3 && finalDiff==1){
                    callSell=0;
                    putSell=0;
                    counter=0;
                    finalDiff=0;
                    /*System.out.printf(
                            "S_NO=%d TIME=%s RSI=%.2f RSI_MA=%.2f openPrice=%.2f closePrice=%.2f%n",
                            sNo,
                            timeIso,
                            rsi,
                            rsiMa,
                            openPrice,
                            closePrice
                    );*/
                    int  diff=(int)(double)(closePrice-openPrice);
                    if(diff<range){
                        int temp=(int)Math.min(closePrice,openPrice);
                        int rem=temp%range;
                        int strike=temp-rem;
                        al=fourDayReader(timeIso,strike,"putSell",hour,minute);
                        //System.out.println("putSell"+" | Strike="+strike+"| "+al);
                        DateOperation dateOperation=new DateOperation();
                        String arr[]=dateOperation.dateConversion(timeIso);
                        List<String> row=new ArrayList<>();
                        if(al.size()==4 && dl.size()==4){
                            row.add(arr[0]);
                            row.add(arr[1]);
                            row.add(arr[2]);
                            row.add(Double.toString(openPrice));
                            row.add(Double.toString(highPrice));
                            row.add(Double.toString(lowPrice));
                            row.add(Double.toString(closePrice));
                            row.add(Double.toString(rsi));
                            row.add(Double.toString(rsiMa));
                            row.add(Double.toString(VWap_week));
                            row.add(Double.toString(adx));
                            row.add(Double.toString(di_plus));
                            row.add(Double.toString(di_minus));
                            row.add("putSell");
                            row.add(Integer.toString(strike));
                            row.add(al.get(0));
                            row.add(al.get(1));
                            row.add(al.get(2));
                            row.add(al.get(3));
                            row.add(Double.toString(dl.get(0)));
                            row.add(Double.toString(dl.get(1)));
                            row.add(Double.toString(dl.get(2)));
                            row.add(Double.toString(dl.get(3)));
                            outputData.add(row);
                        }

                    }else{
                        int temp=(int)(double)(closePrice+openPrice)/2;
                        int rem=temp%range;
                        int strike=temp-rem;
                        al=fourDayReader(timeIso,strike,"putSell",hour,minute);
                        //System.out.println("putSell"+" | Strike="+strike+"| "+al);
                        DateOperation dateOperation=new DateOperation();
                        String arr[]=dateOperation.dateConversion(timeIso);
                        List<String> row=new ArrayList<>();
                        if(al.size()==4 && dl.size()==4){
                            row.add(arr[0]);
                            row.add(arr[1]);
                            row.add(arr[2]);
                            row.add(Double.toString(openPrice));
                            row.add(Double.toString(highPrice));
                            row.add(Double.toString(lowPrice));
                            row.add(Double.toString(closePrice));
                            row.add(Double.toString(rsi));
                            row.add(Double.toString(rsiMa));
                            row.add(Double.toString(VWap_week));
                            row.add(Double.toString(adx));
                            row.add(Double.toString(di_plus));
                            row.add(Double.toString(di_minus));
                            row.add("putSell");
                            row.add(Integer.toString(strike));
                            row.add(al.get(0));
                            row.add(al.get(1));
                            row.add(al.get(2));
                            row.add(al.get(3));
                            row.add(Double.toString(dl.get(0)));
                            row.add(Double.toString(dl.get(1)));
                            row.add(Double.toString(dl.get(2)));
                            row.add(Double.toString(dl.get(3)));
                            outputData.add(row);
                        }
                    }
                }
                if(counter==3){
                    callSell=0;
                    putSell=0;
                    counter=0;
                    finalDiff=0;
                }
            }
        }
        return outputData;
    }
   public static List<String> fourDayReader(String strtime,int strike,String tradType,int hour,int minute) throws Exception{
        List<Double> cp=new ArrayList<>();
       List<String> al=new ArrayList<>();
       OffsetDateTime odt = OffsetDateTime.parse(strtime);
       LocalTime time = odt.toLocalTime();

       String targetTime = String.format("%02d:%02d", hour, minute);

       String sql;

       if (time.isBefore(LocalTime.of(hour, minute))) {
           // include same date
           sql = """
          SELECT *
          FROM market_data_raw
          WHERE SUBSTRING(time_iso, 1, 10) >= SUBSTRING(?, 1, 10)
            AND SUBSTRING(time_iso, 12, 5) = ?
          ORDER BY time_iso
          LIMIT 4
          """;
       } else {
           // exclude same date
           sql = """
          SELECT *
          FROM market_data_raw
          WHERE SUBSTRING(time_iso, 1, 10) > SUBSTRING(?, 1, 10)
            AND SUBSTRING(time_iso, 12, 5) = ?
          ORDER BY time_iso
          LIMIT 4
          """;
       }
       try (
               Connection con =getConnection();
               PreparedStatement ps = con.prepareStatement(sql)
       ) {
           ps.setString(1, strtime);
           ps.setString(2,targetTime);

           try (ResultSet rs = ps.executeQuery()) {

               //int count = 0;

               while (rs.next()) {

                   /*++count;

                   int sNo = rs.getInt("s_no");
                   String timeIso = rs.getString("time_iso");
                   double openPrice = rs.getDouble("open_price");*/
                   double closePrice = rs.getDouble("close_price");

                   /*System.out.println(
                           "DAY " + count +
                                   " -> S_NO=" + sNo +
                                   " TIME=" + timeIso +
                                   " openPrice=" + openPrice +
                                   " closePrice=" + closePrice
                   );*/
                   cp.add(closePrice);
                   int temp=(int)(double)closePrice;
                   if(temp>=strike && tradType.equals("putSell")){
                       al.add("profit");
                   }
                   else if(temp<=strike && tradType.equals("callSell")){
                       al.add("profit");
                   }else{
                       al.add("loss");
                   }
               }
               dl=cp;
           }

       }
       return al;
   }
}
