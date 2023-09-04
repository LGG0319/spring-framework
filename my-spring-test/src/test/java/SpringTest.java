import myspring.Message.MessageSourceTest;
import myspring.config.AppConfig;
import myspring.eventPublish.PublishService;
import myspring.service.ResourceLoaderAwareService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import myspring.service.HelloService;


public class SpringTest {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

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
