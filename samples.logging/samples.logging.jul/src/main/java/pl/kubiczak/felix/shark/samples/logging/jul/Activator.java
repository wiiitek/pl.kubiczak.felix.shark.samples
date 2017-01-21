package pl.kubiczak.felix.shark.samples.logging.jul;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.logging.Level;
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
    testLogging(msg);
  }

  /**
   * Publish log messages with all possible levels while bundle is stopped
   * with <code>java.util.logging.Logger</code>.
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    String msg = "bundle " + context.getBundle().getSymbolicName() + " STOP";
    testLogging(msg);
  }

  private void testLogging(String msg) {
    // changed from LOG.info("severe: " + msg); because sonar reports performance issues
    LOG.log(Level.FINEST, "finest: {0}", msg);
    LOG.log(Level.FINER, "finer: {0}", msg);
    LOG.log(Level.FINE, "fine: {0}", msg);
    LOG.log(Level.INFO, "info: {0}", msg);
    LOG.log(Level.WARNING, "warning: {0}", msg);
    LOG.log(Level.SEVERE, "severe: {0}", msg);
  }
}
