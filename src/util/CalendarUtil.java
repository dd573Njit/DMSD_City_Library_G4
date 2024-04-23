package util;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

    public static Date get20DaysAfterDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 20);
        return calendar.getTime();
    }

    public static String getStringFormattedDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        return formatter.format(date);
    }

    public static boolean isCurrentTimeAfter6Pm() {
        LocalTime currentTime = LocalTime.now();
        LocalTime cutoffTime = LocalTime.of(18, 0);
        return currentTime.isAfter(cutoffTime);
    }
}
