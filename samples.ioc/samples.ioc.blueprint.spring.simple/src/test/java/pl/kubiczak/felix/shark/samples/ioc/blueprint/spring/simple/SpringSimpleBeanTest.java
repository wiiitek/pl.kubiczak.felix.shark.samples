package pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.simple;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:OSGI-INF/blueprint/spring-context.xml")
public class SpringSimpleBeanTest {

  @Autowired
  private ApplicationContext applicationContext;

  @Test
  public void springSimpleBeanShouldBeCreated() {
    SpringSimpleBean springSimpleBean = applicationContext.getBean("springSimpleBean",
        SpringSimpleBean.class);
    assertThat(springSimpleBean, is(notNullValue()));

    String string = springSimpleBean.toString();
    assertThat(string, is("Bean A[Sample Value]"));
  }

  @Test
  public void anotherInstanceShouldAlsoBeCreated() {
    SpringSimpleBean springSimpleBean = applicationContext.getBean("another",
        SpringSimpleBean.class);
    assertThat(springSimpleBean, is(notNullValue()));

    String string = springSimpleBean.toString();
    assertThat(string, is("Bean B"));
  }
}
