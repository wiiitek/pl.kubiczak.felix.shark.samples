package pl.kubiczak.felix.shark.samples.http.resources;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true)
@Service(value = ResourcesRegistration.class)
public class ResourcesRegistration {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Reference(policy = ReferencePolicy.DYNAMIC)
	private volatile HttpService httpService;

	@Activate
	public void start() {
		try {
			httpService.registerResources("/shark/samples/http/resources", "/static", null);
			httpService.registerResources("/shark/samples/http/resources/img", "/static/img", null);
			log.debug("Resources registered");
		} catch (NamespaceException ne) {
			log.warn("Failed to register resources", ne);
		}
	}

	@Deactivate
	public void stop() {
		httpService.unregister("/shark/samples/http/resources");
		httpService.unregister("/shark/samples/http/resources/img");
	}
}
