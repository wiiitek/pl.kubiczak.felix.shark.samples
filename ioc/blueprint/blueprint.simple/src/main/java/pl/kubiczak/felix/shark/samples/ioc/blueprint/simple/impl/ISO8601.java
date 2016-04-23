package pl.kubiczak.felix.shark.samples.ioc.blueprint.simple.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import pl.kubiczak.felix.shark.samples.ioc.blueprint.simple.DateFormatter;

public class ISO8601 implements DateFormatter {

	private static final String TIMEZONE = "UTC";

	private static final String PATTERN = "yyyy-MM-dd'T'HH:mmZ";

	public String getFormatted(Date date) {
		TimeZone tz = TimeZone.getTimeZone(TIMEZONE);
		DateFormat df = new SimpleDateFormat(PATTERN);
		df.setTimeZone(tz);
		return df.format(date);
	}
}
