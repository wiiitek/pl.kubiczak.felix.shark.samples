package pl.kubiczak.felix.shark.samples.logging.slf4j;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class Activator implements BundleActivator {

  private static final Logger LOG = LoggerFactory.getLogger(Activator.class);

  /**
   * Publish log messages with all possible levels while bundle is started
   * with <code>org.slf4j.Logger</code>.
   */
  public void start(BundleContext context) throws Exception {

    String msg = "bundle " + context.getBundle().getSymbolicName() + " START";

    MDC.put("userId", "log4j test");

    LOG.trace("trace: {}", msg);
    LOG.debug("debug: {}", msg);
    LOG.info("info: {}", msg);
    LOG.warn("warn: {}", msg);
    LOG.error("error: {}", msg);
  }

  /**
   * Publish log messages with all possible levels while bundle is stopped
   * with <code>org.slf4j.Logger</code>.
   */
  public void stop(BundleContext context) throws Exception {

    String msg = "bundle " + context.getBundle().getSymbolicName() + " STOP";

    LOG.trace("trace: {}", msg);
    LOG.debug("debug: {}", msg);
    LOG.info("info: {}", msg);
    LOG.warn("warn: {}", msg);
    LOG.error("error: {}", msg);

    MDC.clear();
  }
}
