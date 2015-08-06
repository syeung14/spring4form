package com.foliofn.verification.repositories.ldap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * LdapFormats provides methods for parsing and formatting standard LDAP data types.
 */
public class LdapFormats {

    public static final String X208_GENERALIZED_TIME_FORMAT = "yyyyMMddHHmmss'Z'";

    /**
     * Parses an X.208 Generalized Time string and converts from UTC to the local time zone.
     * 
     * @param str
     *            the X.208 formatted date/time string.
     * @return
     * @throws ParseException
     *             if the provided string is not a valid X.208 date/time string.
     */
    public static Date parseGeneralizedTime(String str) throws ParseException {
        Date date;
        SimpleDateFormat fmt;

        fmt = new SimpleDateFormat(X208_GENERALIZED_TIME_FORMAT);
        fmt.setTimeZone(TimeZone.getTimeZone("UTC"));
        date = fmt.parse(str);

        return date;
    }

    public static String formatGeneralizedTime(Date date) {
        String x208Str;
        SimpleDateFormat fmt;

        if (date == null)
            throw new NullPointerException("The date to format is null, provide a fully initialized date instance.");

        fmt = new SimpleDateFormat(X208_GENERALIZED_TIME_FORMAT);
        fmt.setTimeZone(TimeZone.getTimeZone("UTC"));
        x208Str = fmt.format(date);

        return x208Str;
    }

    /**
     * Uses Generalized Time for timestamps.
     */
    public static String formatTimestamp(Date date) {
        return formatGeneralizedTime(date);
    }

    /**
     * Uses Generalized Time for timestamps.
     */
    public static Date parseTimestamp(String str) throws ParseException {
        return parseGeneralizedTime(str);
    }

    /**
     * Parses a date/time in ISO 8601 format, a less restrictive version of the Generalized Time
     * format used by LDAP. See IETF RFC 4517.
     * 
     * @param str
     *            the date in string format.
     * @return the number of milliseconds since the UTC epoch represented by the string.
     */
    // public static long parseIso8601DateTime(String str) {
    // long millis;
    // DatatypeFactory fac;
    // XMLGregorianCalendar cal;
    //
    // fac = newDatatypeFactory();
    // cal = fac.newXMLGregorianCalendar(str);
    // millis = cal.toGregorianCalendar().getTimeInMillis();
    //
    // return millis;
    // }

    // private static DatatypeFactory newDatatypeFactory() {
    // DatatypeFactory fac;
    //
    // try {
    // fac = DatatypeFactory.newInstance();
    // } catch (DatatypeConfigurationException e) {
    // throw new
    // RuntimeException("Fatal system error encountered attempting to load datatype factory.", e);
    // }
    //
    // return fac;
    // }
    //
    // public static String toIso8601DateTime(long millis) {
    // return toIso8601DateTime(new Date(millis));
    // }
    //
    // public static String toIso8601DateTime(Date date) {
    // String iso8601;
    // // SimpleDateFormat fmt;
    // GregorianCalendar cal = new GregorianCalendar();
    //
    // cal.setTime(date);
    // iso8601 = newDatatypeFactory().newXMLGregorianCalendar(cal).toXMLFormat();
    //
    // // fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    // // iso8601 = fmt.format(date);
    //
    // return iso8601;
    // }
}