package pl.kubiczak.felix.shark.samples.logging.log4j.old;


import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Publish log messages for old log4j logging.
 */
public class Activator implements BundleActivator {

  private static final Logger LOG = Logger.getLogger(Activator.class);

  /**
   * Publish log messages with all possible levels while bundle is started
   * with <code>org.apache.log4j.Logger</code>.
   */
  @Override
  public void start(BundleContext context) throws Exception {
    testLogging(context, "START");
  }

  /**
   * Publish log messages with all possible levels while bundle is stopped
   * with <code>org.apache.log4j.Logger</code>.
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    testLogging(context, "STOP");
  }

  private void testLogging(BundleContext context, String action) {
    String msg = "bundle " + context.getBundle().getSymbolicName() + " " + action;

    LOG.debug("debug: " + msg);
    LOG.info("info: " + msg);
    LOG.warn("warn: " + msg);
    LOG.error("error: " + msg);
  }
}
