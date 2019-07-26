package com.example.demo.util;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class DateUtils {
    public static final String SIMPLE_DATE_FORMAT = "MM月dd日";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATETIME_FORMAT1 = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATETIME_FORMAT2 = "yyyy-MM-dd HH:mm";
    public static final String DEFAULT_DATETIME_FORMAT3 = "yyyy/MM/dd HH:mm:ss";
    public static final String DEFAULT_DATETIME_FORMAT4 = "yyyy-MM-dd HH24:mm:ss";
    public static final String DEFAULT_DATETIME_FORMAT5 = "yyyy-MM-dd HH24:mm";
    public static final String DEFAULT_DATETIME_FORMAT6 = "YYYY-MM-DD:HH24:MI:SS";
    public static final String DEFAULT_DATETIME_FORMAT7 = "yyyy/MM/dd";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    public static final String OTHER_TIME_FORMAT = "HH:mm";
    private static final long HOURS_PER_DAY = 24L;
    private static final long MINUTES_PER_HOUR = 60L;
    private static final long SECONDS_PER_MINUTE = 60L;
    private static final long MILLIONSECONDS_PER_SECOND = 1000L;
    private static final long MILLIONSECONDS_PER_MINUTE = 60000L;
    private static final long MILLIONSECONDS_SECOND_PER_DAY = 86400000L;
    public static TimeZone TIMEZONE_UTC = TimeZone.getTimeZone("UTC");
    private static final SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
    private static final SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private DateUtils() {
    }

    public static Date getDate(String date) {
        return getDate(date, "yyyy-MM-dd", (Date)null);
    }

    public static Date getMaxDate() {
        return getDate("9999-12-31", "yyyy-MM-dd", (Date)null);
    }

    public static Date getDateTime(String date) {
        if (StringUtils.isNotBlank(date)) {
            date = date.replaceAll("/", "-");
            return getDate(date, "yyyy-MM-dd HH:mm:ss", (Date)null);
        } else {
            return null;
        }
    }

    public static long getDateMilles(Date date, String format) {
        String formateDate = (new SimpleDateFormat(format)).format(date);

        try {
            return (new SimpleDateFormat(format)).parse(formateDate).getTime();
        } catch (ParseException var4) {
            var4.printStackTrace();
            return 0L;
        }
    }

    public static Date getDate(String date, String format) {
        return getDate(date, format, (Date)null);
    }

    public static Date getDate(String date, String format, Date defVal) {
        Date d;
        try {
            d = (new SimpleDateFormat(format)).parse(date);
        } catch (ParseException var5) {
            d = defVal;
        }

        return d;
    }

    /**
     * yyyy-MM-dd
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd", (String)null);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String forDatetime(Date date) {
        return date != null ? formatDate(date, "yyyy-MM-dd HH:mm:ss", (String)null) : null;
    }

    /**
     * HH:mm:ss
     * @param date
     * @return
     */
    public static String formatTime(Date date) {
        return formatDate(date, "HH:mm:ss", (String)null);
    }

    public static String formatTime(Date date, String format) {
        return formatDate(date, format, (String)null);
    }

    public static String formatDate(Date date, String format) {
        return formatDate(date, format, (String)null);
    }

    public static String formatDateTimeZone(Date date, String format, TimeZone timeZone) {
        String ret = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setTimeZone(timeZone);
            ret = sdf.format(date);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return ret;
    }

    public static String formatDate(Date date, String format, String defVal) {
        String ret;
        try {
            ret = (new SimpleDateFormat(format)).format(date);
        } catch (Exception var5) {
            ret = defVal;
        }

        return ret;
    }

    public static Date plusDays(Date date, int days) {
        if (date == null) {
            date = getToday();
        }

        return changeDays(date, days);
    }

    public static Date plusHours(Date date, int hours) {
        if (date == null) {
            date = getToday();
        }

        return changeHours(date, hours);
    }

    public static Date plusMinute(Date date, int minutes) {
        if (date == null) {
            date = getToday();
        }

        return changeMinute(date, minutes);
    }

    public static Date plusMonth(Date date, int months) {
        if (date == null) {
            date = getToday();
        }

        return changeMonth(date, months);
    }

    public static Date plusYear(Date date, int years) {
        if (date == null) {
            date = getToday();
        }

        return changeYear(date, years);
    }

    public static Date getToday() {
        return new Date();
    }

    public static long currentTimeMillis() {
        return getToday().getTime();
    }

    public static java.sql.Date getTodaySqlDate() {
        return new java.sql.Date(getToday().getTime());
    }

    public static String getTodayStr(Date date, String format) {
        if (date == null) {
            date = getToday();
        }

        if (StringUtils.isBlank(format)) {
            format = "yyyy-MM-dd";
        }

        return formatDate(date, format);
    }

    public static int intervalDay(Date d1, Date d2) {
        if (d1 == null) {
            d1 = getToday();
        }

        long intervalMillSecond = setToDayStartTime(d1).getTime() - setToDayStartTime(d2).getTime();
        return (int)(intervalMillSecond / 86400000L);
    }

    public static int intervalMinutes(Date date1, Date date2) {
        long intervalMillSecond = date1.getTime() - date2.getTime();
        return (int)(intervalMillSecond / 60000L + (long)(intervalMillSecond % 60000L > 0L ? 1 : 0));
    }

    public static int intervalSeconds(Date date1, Date date2) {
        long intervalMillSecond = date1.getTime() - date2.getTime();
        return (int)(intervalMillSecond / 1000L + (long)(intervalMillSecond % 1000L > 0L ? 1 : 0));
    }

    public static Date setToDayStartTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }

    public static Date setToDayEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        calendar.set(14, 0);
        return calendar.getTime();
    }

    public static String getDateStatus() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(11);
        if (hour >= 6 && hour < 12) {
            return "morning";
        } else if (hour >= 12 && hour < 18) {
            return "noon";
        } else {
            return hour >= 18 && hour < 24 ? "evning" : "midnight";
        }
    }

    public static int getAge(Date birthday) {
        Calendar now = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthday);
        int year = birth.get(1);
        int age = now.get(1) - year;
        now.set(1, year);
        age = now.before(birth) ? age - 1 : age;
        return age;
    }

    public static boolean isSameDate(Date d1, Date d2) {
        if (d1 != null && d2 != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTimeInMillis(d1.getTime());
            Calendar c2 = Calendar.getInstance();
            c2.setTimeInMillis(d2.getTime());
            return c1.get(1) == c2.get(1) && c1.get(2) == c2.get(2) && c1.get(5) == c2.get(5);
        } else {
            return false;
        }
    }

    public static boolean isContinueDay(Date d1, Date d2) {
        if (d1 != null && d2 != null) {
            return intervalDay(d1, d2) == 1;
        } else {
            return false;
        }
    }

    public static Date truncDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        return c.getTime();
    }

    public static Date truncDateHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(12, 0);
        c.set(13, 0);
        return c.getTime();
    }

    public static String getCnDecade(Date input) {
        String day = formatDate(input);
        String decade = day.replaceAll("01日", "上旬").replaceAll("11日", "中旬").replaceAll("21日", "下旬");
        return decade;
    }

    public static Date getTodayZero() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        return c.getTime();
    }

    public static Date getTheDayBefore(Date date) {
        return new Date(date.getTime() - 86400000L);
    }

    public static Date[] getTenDayBefore() {
        Date[] ret = new Date[2];
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        int day = c.get(5);
        if (day < 10) {
            c.set(5, 1);
            ret[1] = new Date(c.getTime().getTime());
            c.setTime(getTheDayBefore(c.getTime()));
            c.set(5, 21);
            ret[0] = new Date(c.getTime().getTime());
        } else if (10 < day && day <= 20) {
            c.set(5, 1);
            ret[0] = new Date(c.getTime().getTime());
            c.set(5, 11);
            ret[1] = new Date(c.getTime().getTime());
        } else {
            c.set(5, 11);
            ret[0] = new Date(c.getTime().getTime());
            c.set(5, 21);
            ret[1] = new Date(c.getTime().getTime());
        }

        return ret;
    }

    public static Date[] getCurrentTenDay(Date input) {
        Date[] ret = new Date[2];
        Calendar c = Calendar.getInstance();
        c.setTime(input);
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        int day = c.get(5);
        if (day < 10) {
            c.set(5, 1);
            ret[0] = new Date(c.getTime().getTime());
            c.set(5, 11);
            ret[1] = new Date(c.getTime().getTime());
        } else if (10 < day && day <= 20) {
            c.set(5, 11);
            ret[0] = new Date(c.getTime().getTime());
            c.set(5, 21);
            ret[1] = new Date(c.getTime().getTime());
        } else {
            c.set(5, 21);
            ret[0] = new Date(c.getTime().getTime());
            ret[1] = getNextMonthFirst(c.getTime());
        }

        return ret;
    }

    public static Date getNextMonthFirst(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        c.add(2, 1);
        c.set(5, 1);
        return c.getTime();
    }

    public static Date[] getTheMonthBefore(Date date) {
        Date[] ret = new Date[2];
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        c.set(5, 1);
        ret[1] = new Date(c.getTime().getTime());
        c.setTime(getTheDayBefore(c.getTime()));
        c.set(5, 1);
        ret[0] = new Date(c.getTime().getTime());
        return ret;
    }

    public static Integer getCurrentQuarter() {
        int month = Integer.parseInt(formatDate(new Date(), "MM"));
        int quarter = 0;
        if (month >= 1 && month <= 3) {
            quarter = 1;
        } else if (month >= 4 && month <= 6) {
            quarter = 2;
        } else if (month >= 7 && month <= 9) {
            quarter = 3;
        } else if (month >= 10 && month <= 12) {
            quarter = 4;
        }

        return Integer.valueOf(quarter);
    }

    public static Map<String, String> getQuarterToYearMonthDay(Integer year, Integer quarter) {
        if (year != null && year.intValue() > 0 && quarter != null && quarter.intValue() > 0) {
            Map<String, String> map = new HashMap<>();
            if (quarter.intValue() == 1) {
                map.put("startTime", year + "-01-" + getMonthDays(year, Integer.valueOf(1)) + " 00:00:00");
                map.put("endTime", year + "-03-" + getMonthDays(year, Integer.valueOf(3)) + " 23:59:59");
            } else if (quarter.intValue() == 2) {
                map.put("startTime", year + "-04-" + getMonthDays(year, Integer.valueOf(4)) + " 00:00:00");
                map.put("endTime", year + "-06-" + getMonthDays(year, Integer.valueOf(6)) + " 23:59:59");
            } else if (quarter.intValue() == 3) {
                map.put("startTime", year + "-07-" + getMonthDays(year, Integer.valueOf(7)) + " 00:00:00");
                map.put("endTime", year + "-09-" + getMonthDays(year, Integer.valueOf(9)) + " 23:59:59");
            } else if (quarter.intValue() == 4) {
                map.put("startTime", year + "-10-" + getMonthDays(year, Integer.valueOf(10)) + " 00:00:00");
                map.put("endTime", year + "-12-" + getMonthDays(year, Integer.valueOf(12)) + " 23:59:59");
            }

            return map;
        } else {
            return null;
        }
    }

    public static Integer getMonthDays(Integer year, Integer month) {
        if (year != null && year.intValue() > 0 && month != null && month.intValue() > 0) {
            Calendar c = Calendar.getInstance();
            c.set(1, year.intValue());
            c.set(2, month.intValue());
            c.set(5, 1);
            c.add(5, -1);
            return c.get(5);
        } else {
            return Integer.valueOf(0);
        }
    }

    public static String getTimeDiffText(Date date1, Date date2) {
        long diff = Math.abs(date1.getTime() - date2.getTime()) / 1000L;
        long minuteSeconds = 60L;
        long hourSeconds = minuteSeconds * 60L;
        long daySeconds = hourSeconds * 24L;
        long weekSeconds = daySeconds * 7L;
        Date min = date1.compareTo(date2) < 0 ? date1 : date2;
        if (diff >= weekSeconds) {
            return formatDate(min);
        } else if (diff >= daySeconds) {
            return diff / daySeconds + "天前";
        } else if (diff >= hourSeconds) {
            return diff / hourSeconds + "小时前";
        } else {
            return diff >= minuteSeconds ? diff / minuteSeconds + "分钟前" : diff + "秒前";
        }
    }

    public static int getWeek(Date dt) {
        int[] week = new int[]{7, 1, 2, 3, 4, 5, 6};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(7) - 1;
        if (w < 0) {
            w = 0;
        }

        return week[w];
    }

    public static Date getCurrentDate(String datePattern) {
        try {
            return (new SimpleDateFormat(datePattern)).parse(getCurrentDateByString(datePattern));
        } catch (ParseException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static String getCurrentDateByString(String datePattern) {
        return (new SimpleDateFormat(datePattern)).format(System.currentTimeMillis());
    }

    public static String getCurrentDateByString(Date date, String datePattern) {
        return (new SimpleDateFormat(datePattern)).format(date);
    }

    public static boolean beforeDate(Date date1, Date date2) {
        return date1.before(date2);
    }

    public static boolean beforeDate(String date1, String date2) {
        Date dt1 = null;
        Date dt2 = null;
        dt1 = getDateTime(date1);
        dt2 = getDateTime(date2);
        return beforeDate(dt1, dt2);
    }

    public static boolean betweenDateScope(String date, String from, String end) {
        if (date != null && from != null && end != null) {
            return !beforeDate(date, from) && beforeDate(date, end);
        } else {
            return false;
        }
    }

    public static boolean checkTimeRange(String time, String startRange, String endRange) {
        String[] s = startRange.split(":");
        int totalStart = Integer.parseInt(s[0]) * 3600 + Integer.parseInt(s[1]) * 60 + Integer.parseInt(s[2]);
        String[] e = endRange.split(":");
        int totalEnd = Integer.parseInt(e[0]) * 3600 + Integer.parseInt(e[1]) * 60 + Integer.parseInt(e[2]);
        String[] t = time.split(":");
        int timeTotal = Integer.parseInt(t[0]) * 3600 + Integer.parseInt(t[1]) * 60 + Integer.parseInt(t[2]);
        return timeTotal >= totalStart && timeTotal <= totalEnd;
    }

    private static Date changeMinute(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(12, minutes);
        return cal.getTime();
    }

    private static Date changeHours(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(11, hours);
        return cal.getTime();
    }

    private static Date changeDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(6, days);
        return cal.getTime();
    }

    private static Date changeYear(Date date, int years) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(1, years);
        return cal.getTime();
    }

    private static Date changeMonth(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(2, months);
        return cal.getTime();
    }

    public static Date getCurrentDayBegin() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        return calendar.getTime();
    }

    public static Date getCurrentDayEnd() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        return calendar.getTime();
    }

    public static Date getLastDayBegin() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.add(5, -1);
        return calendar.getTime();
    }

    public static Date getLastDayEnd() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        calendar.add(5, -1);
        return calendar.getTime();
    }

    public static Date getBeforeYesterdayBegin() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.add(5, -2);
        return calendar.getTime();
    }

    public static Date BeforeYesterdayEnd() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        calendar.add(5, -2);
        return calendar.getTime();
    }

    public static Date getCurrentMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(2, 0);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(5, 1);
        return calendar.getTime();
    }

    public static Date getLastMonthDayBegin() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.add(5, -30);
        return calendar.getTime();
    }

    public static Date getBeginTime() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.add(1, -5);
        return calendar.getTime();
    }

    public static String getYear() {
        return sdfYear.format(new Date());
    }

    public static String getDay() {
        return sdfDay.format(new Date());
    }

    public static String getDays() {
        return sdfDays.format(new Date());
    }

    public static String getTime() {
        return sdfTime.format(new Date());
    }

    public static boolean compareDate(String s, String e) {
        if (fomatDate(s) != null && fomatDate(e) != null) {
            return fomatDate(s).getTime() >= fomatDate(e).getTime();
        } else {
            return false;
        }
    }

    public static boolean compareDate(Date d1, Date d2) {
        return d1.getTime() >= d2.getTime();
    }

    public static Date fomatDate(String date) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return fmt.parse(date);
        } catch (ParseException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static boolean isValidDate(String s) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        try {
            fmt.parse(s);
            return true;
        } catch (Exception var3) {
            return false;
        }
    }

    public static int getDiffYear(String startTime, String endTime) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        try {
            int years = (int)((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / 86400000L / 365L);
            return years;
        } catch (Exception var4) {
            return 0;
        }
    }

    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0L;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException var8) {
            var8.printStackTrace();
        }

        day = (endDate.getTime() - beginDate.getTime()) / 86400000L;
        return day;
    }

    public static String getAfterDayDate(String days) {
        int daysInt = Integer.parseInt(days);
        return getAfterDayDate(daysInt);
    }

    public static String getAfterDayDate(int daysInt) {
        Calendar canlendar = Calendar.getInstance();
        canlendar.add(5, daysInt);
        Date date = canlendar.getTime();
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        return dateStr;
    }

    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);
        Calendar canlendar = Calendar.getInstance();
        canlendar.add(5, daysInt);
        Date date = canlendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    public static String getSearchBeginDate(String date, DateUtils.SearchDateBuff buff) {
        StringBuilder builder = new StringBuilder(date);
        builder.append(" ").append(buff.toString());
        return builder.toString();
    }

    public static int getCurrentTime() {
        return (int)(System.currentTimeMillis() / 1000L);
    }

    public static String getSpecifiedDayBefore(String specifiedDay, String format, String defaultStr) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        String dayBefore = "";

        try {
            date = (new SimpleDateFormat(format)).parse(specifiedDay);
            c.setTime(date);
            int day = c.get(5);
            c.set(5, day - 1);
            dayBefore = (new SimpleDateFormat(format)).format(c.getTime());
            return dayBefore;
        } catch (Exception var7) {
            return defaultStr;
        }
    }

    public static enum SearchDateBuff {
        SEARCH_BEGIN_TIME("00:00:00"),
        SEARCH_END_TIME("23:59:59");

        private String buff;

        private SearchDateBuff(String buff) {
            this.buff = buff;
        }

        @Override
        public String toString() {
            return this.buff;
        }
    }
}