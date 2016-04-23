package pl.kubiczak.felix.shark.samples.ioc.blueprint.simple.impl;

import java.util.Date;

import pl.kubiczak.felix.shark.samples.ioc.blueprint.simple.DateFormatter;

public class SimpleImpl implements DateFormatter {

	/**
	 * @param date
	 * @return milliseconds since 1970-01-01
	 */
	public String getFormatted(Date date) {
		return Long.toString(date.getTime());
	}
}
