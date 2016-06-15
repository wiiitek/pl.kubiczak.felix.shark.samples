package pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.security;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ServletImplTest {

  @Test
  public void datePatternShouldProperlyFormatDate() throws Exception {
    // given
    Calendar cal = GregorianCalendar.getInstance();
    cal.clear();
    cal.set(2016, java.util.Calendar.FEBRUARY, 14, 17, 00);
    Object date = cal.getTime();
    // when
    String actual = String.format(ServletImpl.DATE_PATTERN, date);
    // then
    assertThat(actual, is("2016-02-14 17.00.00.000"));
  }

}
