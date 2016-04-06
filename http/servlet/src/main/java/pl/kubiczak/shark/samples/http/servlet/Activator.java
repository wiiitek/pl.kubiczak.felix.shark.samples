package pl.kubiczak.shark.samples.http.servlet;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

import java.util.Dictionary;
import java.util.Hashtable;

public final class Activator implements BundleActivator {

	public void start(BundleContext bundleContext) throws Exception {

		final Dictionary<String, Object> servletContextProps = new Hashtable<String, Object>();
		servletContextProps.put(HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME, WhiteboardServletContext.CONTEXT_NAME);
		servletContextProps.put(HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH, WhiteboardServletContext.CONTEXT_PATH);

		final WhiteboardServletContext whiteboardServletContext = new WhiteboardServletContext(bundleContext.getBundle());
		bundleContext.registerService(ServletContextHelper.class, whiteboardServletContext, servletContextProps);
	}


	public void stop(BundleContext context)
			throws Exception {
		// nothing to do, services are unregistered automatically
	}
}
