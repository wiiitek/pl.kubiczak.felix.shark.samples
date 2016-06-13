package pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.messages;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:OSGI-INF/blueprint/spring-context.xml")
public class SpringBeanTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void messageSourceShouldBeInserted() {
        Bean bean = applicationContext.getBean("beanId", Bean.class);
        assertThat(bean, is(notNullValue()));

        MessageSource messageSource = bean.getMessageSource();
        assertThat(messageSource, is(notNullValue()));
    }

    @Test
    public void injectedMessageSourceDefaultMessages() {
        Bean bean = applicationContext.getBean("beanId", Bean.class);
        MessageSource messageSource = bean.getMessageSource();

        Locale irrelevantLocale = new Locale("en");
        String retrievedMessage = messageSource.getMessage(
                "irrelevant.key", new String[]{"irrelevant", "arguments"}, "Default message", irrelevantLocale);

        assertThat(retrievedMessage, is("Default message"));
    }

}
