package pl.kubiczak.felix.shark.samples.logging.jul;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.logging.Logger;

public class Activator implements BundleActivator {

  private static final Logger LOG = Logger.getLogger(Activator.class.getName());

  /**
   * Publish log messages with all possible levels while bundle is started
   * with <code>java.util.logging.Logger</code>.
   */
  @Override
  public void start(BundleContext context) throws Exception {

    String msg = "bundle " + context.getBundle().getSymbolicName() + " START";

    LOG.finest("finest: " + msg);
    LOG.finer("finer: " + msg);
    LOG.fine("fine: " + msg);
    LOG.info("info: " + msg);
    LOG.warning("warning: " + msg);
    LOG.severe("severe: " + msg);
  }

  /**
   * Publish log messages with all possible levels while bundle is stopped
   * with <code>java.util.logging.Logger</code>.
   */
  @Override
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
