package pl.kubiczak.shark.samples.logging.log4j;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static final Logger LOG = LogManager.getLogger(Activator.class);

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