package pl.kubiczak.felix.shark.samples.jersey;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

import org.junit.Test;

import java.util.Set;

public class JerseyApplicationTest {

  @Test
  public void shouldContainSomeClassesForJerseyApplication() throws Exception {
    JerseyApplication tested = new JerseyApplication();
    Set<Class<?>> jerseyApplicationClasses = tested.getClasses();
    assertThat(jerseyApplicationClasses, is(not(empty())));
  }
}
