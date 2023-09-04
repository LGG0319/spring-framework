package myspring.eventPublish;


import org.springframework.context.ApplicationEvent;

import java.io.Serial;


public class SendMessageEvent extends ApplicationEvent {

	@Serial
	private static final long serialVersionUID = 12434242243593L;
	public SendMessageEvent(String source) {
		super(source);
	}
}
