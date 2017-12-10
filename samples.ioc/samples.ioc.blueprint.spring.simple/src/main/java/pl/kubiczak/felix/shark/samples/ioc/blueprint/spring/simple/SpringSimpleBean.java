package pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.simple;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public class SpringSimpleBean {

  private final String name;

  private Object property;

  public SpringSimpleBean(String name) {
    this.name = name;
  }

  public void setProperty(Object sampleProperty) {
    this.property = sampleProperty;
  }

  @Override
  public String toString() {
    String namePart = name != null ? name : super.toString();
    String referencedBeanPart = StringUtils.EMPTY;
    if (property != null) {
      referencedBeanPart =
          StringUtils.join(Arrays.asList("[", property.toString(), "]"), null);
    }
    return StringUtils.join(Arrays.asList(namePart, referencedBeanPart), null);
  }
}
