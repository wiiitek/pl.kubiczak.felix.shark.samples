package pl.kubiczak.felix.shark.samples.jersey;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

public class JerseyApplication extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> result = new HashSet<>();
    result.add(SampleResource.class);
    return result;
  }

}
