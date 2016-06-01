package pl.kubiczak.felix.shark.samples.logging.jcl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private final static Log LOG = LogFactory.getLog(Activator.class);

	public void start(BundleContext context) throws Exception {

		String msg = "bundle " + context.getBundle().getSymbolicName() + " START";

		LOG.trace("trace: " + msg);
		LOG.debug("debug: " + msg);
		LOG.info("info: " + msg);
		LOG.warn("warn: " + msg);
		LOG.error("error: " + msg);
		LOG.fatal("fatal: " + msg);
	}

	public void stop(BundleContext context) throws Exception {

		String msg = "bundle " + context.getBundle().getSymbolicName() + " STOP";

		LOG.trace("trace: " + msg);
		LOG.debug("debug: " + msg);
		LOG.info("info: " + msg);
		LOG.warn("warn: " + msg);
		LOG.error("error: " + msg);
		LOG.fatal("fatal: " + msg);
	}
}
