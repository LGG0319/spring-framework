package myspring.service;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component(value = "rlas")
public class ResourceLoaderAwareService implements ResourceLoaderAware {
	private  ResourceLoader resourceLoader;
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {

       this.resourceLoader = resourceLoader;
	}

	public void getResource(){
		Resource resource = resourceLoader.getResource("a.txt");
		try (InputStream inputStream = resource.getInputStream()) {
			Properties properties = new Properties();
			properties.load(inputStream);
			String val = (String)properties.get("val");
			System.out.println(val);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
