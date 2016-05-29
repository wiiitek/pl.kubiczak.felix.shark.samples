package pl.kubiczak.felix.shark.samples.ioc.blueprint.spring.simple;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public class Bean {

	private final String name;

	private Object sampleProperty;

	public Bean(String name) {
		this.name = name;
	}

	public void setSampleProperty(Object sampleProperty) {
		this.sampleProperty = sampleProperty;
	}

	@Override
	public String toString() {
		String namePart = name != null ? name : super.toString();
		String referencedBeanPart = StringUtils.EMPTY;
		if (sampleProperty != null) {
			referencedBeanPart = StringUtils.join(Arrays.asList("[", sampleProperty.toString(), "]"), null);
		}
		return StringUtils.join(Arrays.asList(namePart, referencedBeanPart), null);
	}
}
