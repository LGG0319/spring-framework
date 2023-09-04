package myspring.eventPublish;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;


@Service
public class PublishService implements ApplicationEventPublisherAware {

	// 事件发布者
	private ApplicationEventPublisher publisher;
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}

	// 事件触发
	public void trigger(){
		System.out.println("注册用户中");
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		System.out.println("注册完成！");

		SendMessageEvent event=new SendMessageEvent("注册成功");
		//发布中心发布事件
		publisher.publishEvent(event);
	}
}
