package myspring.eventPublish;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

// 监听者
@Service
public class SendMessageListenter implements ApplicationListener<SendMessageEvent> {
	@Override
	public void onApplicationEvent(SendMessageEvent event) {
		System.out.println(event.getSource());
	}
}
