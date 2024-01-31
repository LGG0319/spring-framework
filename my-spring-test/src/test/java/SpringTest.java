import myspring.Message.MessageSourceTest;
import myspring.config.AppConfig;
import myspring.eventPublish.PublishService;
import myspring.propertySource.MyPropertySource;
import myspring.service.ResourceLoaderAwareService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import myspring.service.HelloService;


public class SpringTest {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext ac = new MyPropertySource();

		ac.addBeanFactoryPostProcessor(new BeanDefinitionRegistryPostProcessor() {
			@Override
			public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
				System.out.println("----------扩展点--启动时添加postProcessBeanDefinitionRegistry-------------");
			}

			@Override
			public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
				System.out.println(beanFactory.getBeanDefinitionCount());
				System.out.println("----------扩展点--启动时添加BeanFactoryPostProcessor-------------");
			}
		});
		ac.register(AppConfig.class);
		ac.refresh();
		HelloService helloService = ac.getBean(HelloService.class);

		helloService.Hello();
		ResourceLoaderAwareService rlas = ac.getBean(ResourceLoaderAwareService.class);
		rlas.getResource();

		PublishService publishService = ac.getBean(PublishService.class);
		publishService.trigger();


		MessageSourceTest bean = ac.getBean(MessageSourceTest.class);
		bean.testMessageSource();


	}
}
