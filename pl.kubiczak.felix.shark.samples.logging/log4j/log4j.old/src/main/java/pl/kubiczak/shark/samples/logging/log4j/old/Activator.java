package pl.kubiczak.shark.samples.logging.log4j.old;


import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static final Logger LOG = Logger.getLogger(Activator.class);

	public void start(BundleContext context) throws Exception {

		String msg = "bundle " + context.getBundle().getSymbolicName() + " START";

		LOG.debug("debug: " + msg);
		LOG.info("info: " + msg);
		LOG.warn("warn: " + msg);
		LOG.error("error: " + msg);
	}

	public void stop(BundleContext context) throws Exception {

		String msg = "bundle " + context.getBundle().getSymbolicName() + " STOP";

		LOG.debug("debug: " + msg);
		LOG.info("info: " + msg);
		LOG.warn("warn: " + msg);
		LOG.error("error: " + msg);
	}
}