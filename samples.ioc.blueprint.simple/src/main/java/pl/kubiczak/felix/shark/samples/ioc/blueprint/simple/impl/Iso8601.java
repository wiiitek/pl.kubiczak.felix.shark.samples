package pl.kubiczak.felix.shark.samples.ioc.blueprint.simple.impl;

import pl.kubiczak.felix.shark.samples.ioc.blueprint.simple.DateFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Formats date returning String that has correct alphabetical order (ISO-8601).
 */
public class Iso8601 implements DateFormatter {

  private static final String TIMEZONE = "UTC";

  private static final String PATTERN = "yyyy-MM-dd'T'HH:mmZ";

  /**
   * Converts the date to iso-8601 format for UTC timezone.
   *
   * @param date date instance for UTC timezone
   * @return text representing the date in iso-8601 format
   */
  public String getFormatted(Date date) {
    TimeZone tz = TimeZone.getTimeZone(TIMEZONE);
    DateFormat df = new SimpleDateFormat(PATTERN);
    df.setTimeZone(tz);
    return df.format(date);
  }
}
