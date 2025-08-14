package team.safe.escape.common.util;

import java.time.format.DateTimeFormatter;

public abstract class DateTimeUtils {
    private DateTimeUtils() {
    }

    public static final DateTimeFormatter YYYY_MM_DD_HH_MM = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
}
