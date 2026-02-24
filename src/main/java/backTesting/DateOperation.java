package backTesting;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class DateOperation {
    String[] dateConversion(String dateFromExcel){
        OffsetDateTime dateTime = OffsetDateTime.parse(dateFromExcel);

        // Formatters
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        DateTimeFormatter dayFormatter  = DateTimeFormatter.ofPattern("EEE");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String arr[]=new String[3];
        // Formatting
        arr[0] = dateTime.format(dateFormatter);
        arr[1]  = dateTime.format(dayFormatter);
        arr[2] = dateTime.format(timeFormatter);

        /*System.out.println("Date : " + date);
        System.out.println("Day  : " + day);
        System.out.println("Time : " + timeStr);*/
        return arr;
    }
}
