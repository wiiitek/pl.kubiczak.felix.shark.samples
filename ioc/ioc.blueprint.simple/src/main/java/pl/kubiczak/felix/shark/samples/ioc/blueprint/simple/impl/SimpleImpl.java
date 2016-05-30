package pl.kubiczak.felix.shark.samples.ioc.blueprint.simple.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.kubiczak.felix.shark.samples.ioc.blueprint.simple.DateFormatter;

/**
 * Formats date returning milliseconds since 1970-01-01
 */
public class SimpleImpl implements DateFormatter {

	private final Logger log = LoggerFactory.getLogger(getClass());

	public String getFormatted(Date date) {
		return Long.toString(date.getTime());
	}

	public void init(){
		log.debug("initialized bean");
	}

	public void destroy(){
		log.debug("destroyed bean");
	}
}
