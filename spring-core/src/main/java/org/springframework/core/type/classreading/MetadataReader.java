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

package org.springframework.core.type.classreading;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;

/**
 * Simple facade for accessing class metadata,
 * as read by an ASM {@link org.springframework.asm.ClassReader}.
 *
 * @author Juergen Hoeller
 * @since 2.5
 * 该接口对Class的元数据做了一个get方法的封装，方便一次性获取所有需要的数据。
 */
public interface MetadataReader {

	/**
	 * Return the resource reference for the class file.
	 * 返回此Class来自的资源（创建的时候需要指定此资源，然后交给`AnnotationMetadataReadingVisitor`去处理）
	 */
	Resource getResource();

	/**
	 * Read basic class metadata for the underlying class.
	 * ClassMeta，实现为通过`AnnotationMetadataReadingVisitor`中获取
	 */
	ClassMetadata getClassMetadata();

	/**
	 * Read full annotation metadata for the underlying class,
	 * including metadata for annotated methods.
	 * 注解元信息 也是通过`AnnotationMetadataReadingVisitor`获取
	 */
	AnnotationMetadata getAnnotationMetadata();

}
