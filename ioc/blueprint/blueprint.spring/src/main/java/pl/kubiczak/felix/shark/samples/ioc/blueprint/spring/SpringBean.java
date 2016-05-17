package pl.kubiczak.felix.shark.samples.ioc.blueprint.spring;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import pl.kubiczak.felix.shark.samples.ioc.blueprint.simple.SimpleBean;

public class SpringBean implements Printable {

	private final String name;

	private SimpleBean simpleBean;

	public SpringBean(String name) {
		this.name = name;
	}

	public void setSimpleBean(SimpleBean simpleBean) {
		this.simpleBean = simpleBean;
	}

	@Override
	public String toString() {
		String namePart = name != null ? name : super.toString();
		String referencedBeanPart = simpleBean != null ? simpleBean.toString() : StringUtils.EMPTY;
		return StringUtils.join(Arrays.asList(namePart, "[", referencedBeanPart, "]"), null);
	}
}
