package myspring.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;

@WebFilter(urlPatterns = "/*",filterName = "bFilter")
public class BFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(BFilter.class);
	public BFilter() {
		logger.info("实例化filter");
	}



	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		logger.info("进入过滤器，但是不做拦截-----------------b");
		chain.doFilter(request,response);
	}
}