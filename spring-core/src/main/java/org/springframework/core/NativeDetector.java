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

package org.springframework.core;

import org.jspecify.annotations.Nullable;

/**
 * A common delegate for detecting a GraalVM native image environment.
 * GraalVM是 Oracle 开源的一款通用虚拟机产品，官方称之为 Universal GraalVM，是新一代的通用多语言高性能虚拟机。
 * 它可以运行多种编程语言，如Java、JavaScript、Python等，并提供了即时编译（JIT）和AOT编译（AOT）的支持。
 * GraalVM还支持在不同语言之间互相调用，以及嵌入到其他应用程序中使用。
 * <a href="https://blog.csdn.net/truelove12358/article/details/131214457">GraalVM</a>
 * @author Sebastien Deleuze
 * @since 5.3.4
 * 用于检测GraalVM本机映像环境的常见委托
 */
public abstract class NativeDetector {

	// See https://github.com/oracle/graal/blob/master/sdk/src/org.graalvm.nativeimage/src/org/graalvm/nativeimage/ImageInfo.java
	private static final @Nullable String imageCode = System.getProperty("org.graalvm.nativeimage.imagecode");

	private static final boolean inNativeImage = (imageCode != null);


	/**
	 * Returns {@code true} if running in a native image context (for example
	 * {@code buildtime}, {@code runtime}, or {@code agent}) expressed by setting the
	 * {@code org.graalvm.nativeimage.imagecode} system property to any value.
	 */
	public static boolean inNativeImage() {
		return inNativeImage;
	}

	/**
	 * Returns {@code true} if running in any of the specified native image context(s).
	 * @param contexts the native image context(s)
	 * @since 6.0.10
	 */
	public static boolean inNativeImage(Context... contexts) {
		for (Context context: contexts) {
			if (context.key.equals(imageCode)) {
				return true;
			}
		}
		return false;
	}


	/**
	 * Native image context as defined in GraalVM's
	 * <a href="https://github.com/oracle/graal/blob/master/sdk/src/org.graalvm.nativeimage/src/org/graalvm/nativeimage/ImageInfo.java">ImageInfo</a>.
	 * @since 6.0.10
	 */
	public enum Context {

		/**
		 * The code is executing in the context of image building.
		 */
		BUILD("buildtime"),

		/**
		 * The code is executing at image runtime.
		 */
		RUN("runtime");

		private final String key;

		Context(final String key) {
			this.key = key;
		}

		@Override
		public String toString() {
			return this.key;
		}
	}

}
