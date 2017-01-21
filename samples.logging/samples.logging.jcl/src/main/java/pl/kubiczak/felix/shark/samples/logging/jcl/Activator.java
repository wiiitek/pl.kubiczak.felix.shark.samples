package pl.kubiczak.felix.shark.samples.logging.jcl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

  private static final Log LOG = LogFactory.getLog(Activator.class);

  /**
   * Publish log messages with all possible levels while bundle is started
   * with <code>org.apache.commons.logging.Log</code>.
   */
  @Override
  public void start(BundleContext context) throws Exception {
    String msg = "bundle " + context.getBundle().getSymbolicName() + " START";
    testLogging(msg);
  }

  /**
   * Publish log messages with all possible levels while bundle is stopped
   * with <code>org.apache.commons.logging.Log</code>.
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    String msg = "bundle " + context.getBundle().getSymbolicName() + " STOP";
    testLogging(msg);
  }

  private void testLogging(String msg) {
    LOG.trace("trace: " + msg);
    LOG.debug("debug: " + msg);
    LOG.info("info: " + msg);
    LOG.warn("warn: " + msg);
    LOG.error("error: " + msg);
    LOG.fatal("fatal: " + msg);
  }
}
