package myspring.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;
import java.util.Locale;


@Service
public class MessageSourceTest implements MessageSourceAware{

	private MessageSource messageSource;

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}


	public void testMessageSource(){

		System.out.println("消息--英文:");
		System.out.println(messageSource.getMessage("user.login.username", null,Locale.US));
		System.out.println(messageSource.getMessage("user.login.password", null,Locale.US));

		System.out.println("消息--中文:");
		System.out.println(messageSource.getMessage("user.login.username", null, Locale.SIMPLIFIED_CHINESE));
		System.out.println(messageSource.getMessage("user.login.password", null, Locale.CHINESE));

		System.out.println("消息二:");
		System.out.println(messageSource.getMessage("user.login.username",new Object[]{"💊哥"},Locale.SIMPLIFIED_CHINESE));
		System.out.println(messageSource.getMessage("user.login.password",new Object[]{"wenyao"},Locale.SIMPLIFIED_CHINESE));
	}



}
