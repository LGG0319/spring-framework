/*
 * Copyright 2002-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.aop.support;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.testfixture.interceptor.NopInterceptor;
import org.springframework.beans.testfixture.beans.ITestBean;
import org.springframework.beans.testfixture.beans.TestBean;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ControlFlowPointcut}.
 *
 * @author Rod Johnson
 * @author Chris Beams
 * @author Sam Brannen
 */
class ControlFlowPointcutTests {

	@Test
	void matches() {
		TestBean target = new TestBean();
		target.setAge(27);
		NopInterceptor nop = new NopInterceptor();
		ControlFlowPointcut cflow = new ControlFlowPointcut(One.class, "getAge");
		ProxyFactory pf = new ProxyFactory(target);
		ITestBean proxied = (ITestBean) pf.getProxy();
		pf.addAdvisor(new DefaultPointcutAdvisor(cflow, nop));

		// Not advised, not under One
		assertThat(proxied.getAge()).isEqualTo(target.getAge());
		assertThat(nop.getCount()).isEqualTo(0);
		assertThat(cflow.getEvaluations()).isEqualTo(1);

		// Will be advised
		assertThat(new One().getAge(proxied)).isEqualTo(target.getAge());
		assertThat(nop.getCount()).isEqualTo(1);
		assertThat(cflow.getEvaluations()).isEqualTo(2);

		// Won't be advised
		assertThat(new One().nomatch(proxied)).isEqualTo(target.getAge());
		assertThat(nop.getCount()).isEqualTo(1);
		assertThat(cflow.getEvaluations()).isEqualTo(3);
	}

	@Test
	void controlFlowPointcutIsExtensible() {
		@SuppressWarnings("serial")
		class CustomControlFlowPointcut extends ControlFlowPointcut {

			CustomControlFlowPointcut(Class<?> clazz, String methodName) {
				super(clazz, methodName);
			}

			@Override
			public boolean matches(Method method, Class<?> targetClass, Object... args) {
				super.incrementEvaluationCount();
				return super.matches(method, targetClass, args);
			}

			Class<?> trackedClass() {
				return super.clazz;
			}

			String trackedMethod() {
				return super.methodName;
			}
		}

		CustomControlFlowPointcut cflow = new CustomControlFlowPointcut(One.class, "getAge");

		assertThat(cflow.trackedClass()).isEqualTo(One.class);
		assertThat(cflow.trackedMethod()).isEqualTo("getAge");

		TestBean target = new TestBean("Jane", 27);
		ProxyFactory pf = new ProxyFactory(target);
		NopInterceptor nop = new NopInterceptor();
		pf.addAdvisor(new DefaultPointcutAdvisor(cflow, nop));
		ITestBean proxy = (ITestBean) pf.getProxy();

		// Not advised: the proxy is not invoked under One#getAge
		assertThat(proxy.getAge()).isEqualTo(target.getAge());
		assertThat(nop.getCount()).isEqualTo(0);
		assertThat(cflow.getEvaluations()).isEqualTo(2); // intentional double increment

		// Will be advised: the proxy is invoked under One#getAge
		assertThat(new One().getAge(proxy)).isEqualTo(target.getAge());
		assertThat(nop.getCount()).isEqualTo(1);
		assertThat(cflow.getEvaluations()).isEqualTo(4); // intentional double increment

		// Won't be advised: the proxy is not invoked under One#getAge
		assertThat(new One().nomatch(proxy)).isEqualTo(target.getAge());
		assertThat(nop.getCount()).isEqualTo(1);
		assertThat(cflow.getEvaluations()).isEqualTo(6); // intentional double increment
	}

	/**
	 * Check that we can use a cflow pointcut only in conjunction with
	 * a static pointcut: e.g. all setter methods that are invoked under
	 * a particular class. This greatly reduces the number of calls
	 * to the cflow pointcut, meaning that it's not so prohibitively
	 * expensive.
	 */
	@Test
	void selectiveApplication() {
		TestBean target = new TestBean();
		target.setAge(27);
		NopInterceptor nop = new NopInterceptor();
		ControlFlowPointcut cflow = new ControlFlowPointcut(One.class);
		Pointcut settersUnderOne = Pointcuts.intersection(Pointcuts.SETTERS, cflow);
		ProxyFactory pf = new ProxyFactory(target);
		ITestBean proxied = (ITestBean) pf.getProxy();
		pf.addAdvisor(new DefaultPointcutAdvisor(settersUnderOne, nop));

		// Not advised, not under One
		target.setAge(16);
		assertThat(nop.getCount()).isEqualTo(0);

		// Not advised; under One but not a setter
		assertThat(new One().getAge(proxied)).isEqualTo(16);
		assertThat(nop.getCount()).isEqualTo(0);

		// Won't be advised
		new One().set(proxied);
		assertThat(nop.getCount()).isEqualTo(1);

		// We saved most evaluations
		assertThat(cflow.getEvaluations()).isEqualTo(1);
	}

	@Test
	void equalsAndHashCode() throws Exception {
		assertThat(new ControlFlowPointcut(One.class)).isEqualTo(new ControlFlowPointcut(One.class));
		assertThat(new ControlFlowPointcut(One.class, "getAge")).isEqualTo(new ControlFlowPointcut(One.class, "getAge"));
		assertThat(new ControlFlowPointcut(One.class, "getAge")).isNotEqualTo(new ControlFlowPointcut(One.class));

		assertThat(new ControlFlowPointcut(One.class)).hasSameHashCodeAs(new ControlFlowPointcut(One.class));
		assertThat(new ControlFlowPointcut(One.class, "getAge")).hasSameHashCodeAs(new ControlFlowPointcut(One.class, "getAge"));
		assertThat(new ControlFlowPointcut(One.class, "getAge")).doesNotHaveSameHashCodeAs(new ControlFlowPointcut(One.class));
	}

	@Test
	void testToString() {
		assertThat(new ControlFlowPointcut(One.class)).asString()
			.isEqualTo(ControlFlowPointcut.class.getName() + ": class = " + One.class.getName() + "; methodName = null");
		assertThat(new ControlFlowPointcut(One.class, "getAge")).asString()
			.isEqualTo(ControlFlowPointcut.class.getName() + ": class = " + One.class.getName() + "; methodName = getAge");
	}


	private static class One {
		int getAge(ITestBean proxied) {
			return proxied.getAge();
		}
		int nomatch(ITestBean proxied) {
			return proxied.getAge();
		}
		void set(ITestBean proxied) {
			proxied.setAge(5);
		}
	}

}
