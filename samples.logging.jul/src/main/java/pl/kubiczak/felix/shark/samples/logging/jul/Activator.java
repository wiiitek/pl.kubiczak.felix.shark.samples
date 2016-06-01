package pl.kubiczak.felix.shark.samples.logging.jul;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.logging.Logger;

public class Activator implements BundleActivator {

	private final static Logger LOG = Logger.getLogger(Activator.class.getName());

	public void start(BundleContext context) throws Exception {

		String msg = "bundle " + context.getBundle().getSymbolicName() + " START";

		LOG.finest("finest: " + msg);
		LOG.finer("finer: " + msg);
		LOG.fine("fine: " + msg);
		LOG.info("info: " + msg);
		LOG.warning("warning: " + msg);
		LOG.severe("severe: " + msg);
	}

	public void stop(BundleContext context) throws Exception {

		String msg = "bundle " + context.getBundle().getSymbolicName() + " STOP";

		LOG.finest("finest: " + msg);
		LOG.finer("finer: " + msg);
		LOG.fine("fine: " + msg);
		LOG.info("info: " + msg);
		LOG.warning("warning: " + msg);
		LOG.severe("severe: " + msg);
	}
}