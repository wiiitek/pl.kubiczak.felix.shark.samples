package pl.kubiczak.felix.shark.samples.http.resources;

import org.apache.felix.scr.annotations.*;
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
			httpService.registerResources("/shark/samples/http/resources", "/", null);
			httpService.registerResources("/shark/samples/http/resources/img", "/img", null);
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
