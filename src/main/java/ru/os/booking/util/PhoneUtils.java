package ru.os.booking.util;

import java.util.regex.Pattern;

public final class PhoneUtils {

    private static final Pattern SEPARATORS = Pattern.compile("[\\s()\\-]");
    private static final Pattern PHONE = Pattern.compile("(\\+7|8)\\d{10}");

    private PhoneUtils() {}

    public static String normalize(String raw) {
        var clean = SEPARATORS.matcher(raw).replaceAll("");
        var normalized = PHONE.matcher(clean).matches() ? "+7" + clean.substring(clean.length() - 10) : null;
        return normalized;
    }
}
