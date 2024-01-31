package myspring.propertySource;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class MyPropertySource extends AnnotationConfigApplicationContext {

	public MyPropertySource(){
	}

	public MyPropertySource(Class<?>... componentClasses){
		super(componentClasses);
	}

	@Override
	protected void initPropertySources() {
		super.initPropertySources();
		getEnvironment().setRequiredProperties("NODE_PATH");
	}
}
