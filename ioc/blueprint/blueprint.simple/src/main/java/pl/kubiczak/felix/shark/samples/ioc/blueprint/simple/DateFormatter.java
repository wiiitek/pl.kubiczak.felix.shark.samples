package pl.kubiczak.felix.shark.samples.ioc.blueprint.simple;

import java.util.Date;

public interface DateFormatter {

	/**
	 * @param date date instance for UTC timezone
	 * @return implementation specific representation of date
	 */
	String getFormatted(Date date);
}
