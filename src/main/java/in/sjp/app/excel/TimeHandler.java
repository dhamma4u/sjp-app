package in.sjp.app.excel;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TimeHandler {

    public static List<String> extractTimeRanges( String startTimeRange, String endTimeRange ) throws ParseException {
        List<String> textFormattedTimeRanges = new ArrayList<>();
        DateFormat sdf = new SimpleDateFormat("HH:mm");

        // start Time
        Calendar rangeStartTimeCalendar = Calendar.getInstance();
        rangeStartTimeCalendar.setTime(sdf.parse(startTimeRange));


        // End time
        Calendar rangeEndTimeCalendar = Calendar.getInstance();
        rangeEndTimeCalendar.setTime(sdf.parse(endTimeRange));

        List<Time> intervals = new ArrayList<>();
        // These constructors are deprecated and are used only for example

        intervals.add(new Time(rangeStartTimeCalendar.getTimeInMillis()));

        while (rangeStartTimeCalendar.getTime().before(rangeEndTimeCalendar.getTime())) {
            rangeStartTimeCalendar.add(Calendar.MINUTE, 1); // One Minute as interval
            intervals.add(new Time(rangeStartTimeCalendar.getTimeInMillis()));
        }

        for (Time time : intervals) {
            //System.out.println(sdf.format(time));
            textFormattedTimeRanges.add(sdf.format(time));
        }
        return textFormattedTimeRanges;
    }


}
