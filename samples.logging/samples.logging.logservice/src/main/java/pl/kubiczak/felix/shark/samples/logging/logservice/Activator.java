package pl.kubiczak.felix.shark.samples.logging.logservice;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {

  private ServiceTracker logServiceTracker;
  private LogService logService;

  /**
   * Publish log messages with all possible levels while bundle is started
   * with <code>org.osgi.service.log.LogService</code>.
   * Service is retrieved with <code>ServiceTracker</code>.
   */
  @Override
  public void start(BundleContext context) throws Exception {
    // create a tracker and track the log service
    logServiceTracker = new ServiceTracker(context, LogService.class.getName(), null);
    logServiceTracker.open();
    // grab the service
    logService = (LogService) logServiceTracker.getService();
    if (logService != null) {
      String msg = "bundle " + context.getBundle().getSymbolicName() + " START";
      testLogging(logService, msg);
    }
  }

  /**
   * Publish log messages with all possible levels while bundle is stopped
   * with <code>org.osgi.service.log.LogService</code>.
   * Service is retrieved with <code>ServiceTracker</code>.
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    if (logService != null) {
      String msg = "bundle " + context.getBundle().getSymbolicName() + " STOP";
      testLogging(logService, msg);
    }
    // close the service tracker
    logServiceTracker.close();
    logServiceTracker = null;
  }

  private void testLogging(LogService logService, String msg) {
    logService.log(LogService.LOG_DEBUG, ": " + msg);
    logService.log(LogService.LOG_INFO, ": " + msg);
    logService.log(LogService.LOG_WARNING, ": " + msg);
    logService.log(LogService.LOG_ERROR, ": " + msg);
  }
}
