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
public class SpringBeanTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void springBeanAInitialization() {
        Bean bean = applicationContext.getBean("beanA", Bean.class);
        assertThat(bean, is(notNullValue()));

        String string = bean.toString();
        assertThat(string, is("Bean A[Sample Value]"));
    }
}
