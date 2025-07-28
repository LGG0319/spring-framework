/*
 * Copyright 2002-present the original author or authors.
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

package org.springframework.aot;

import org.springframework.core.NativeDetector;
import org.springframework.core.NativeDetector.Context;
import org.springframework.core.SpringProperties;

/**
 * Utility for determining if AOT-processed optimizations must be used rather
 * than the regular runtime. Strictly for internal use within the framework.
 *
 * @author Stephane Nicoll
 * @author Sebastien Deleuze
 * @since 6.0
 */
public abstract class AotDetector {

	/**
	 * System property that indicates the application should run with AOT
	 * generated artifacts. If such optimizations are not available, it is
	 * recommended to throw an exception rather than fall back to the regular
	 * runtime behavior.
	 */
	public static final String AOT_ENABLED = "spring.aot.enabled";

	private static final boolean inNativeImage = NativeDetector.inNativeImage(Context.RUN, Context.BUILD);


	/**
	 * Determine whether AOT optimizations must be considered at runtime. This
	 * is mandatory in a native image but can be triggered on the JVM using
	 * the {@value #AOT_ENABLED} Spring property.
	 * @return whether AOT optimizations must be considered
	 * 确定是否必须在运行时考虑AOT优化。这在本机映像中是强制性的，但可以在JVM上使用“spring.aot.enabled”spring属性触发。
	 * 是否必须考虑AOT优化
	 */
	public static boolean useGeneratedArtifacts() {
		return (inNativeImage || SpringProperties.getFlag(AOT_ENABLED));
	}

}
