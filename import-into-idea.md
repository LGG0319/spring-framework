The following has been tested against IntelliJ IDEA 2016.2.2

## Steps

_Within your locally cloned spring-framework working directory:_

1. Precompile `spring-oxm` with `./gradlew :spring-oxm:compileTestJava`
2. Import into IntelliJ (File -> New -> Project from Existing Sources -> Navigate to directory -> Select build.gradle)
3. When prompted exclude the `spring-aspects` module (or after the import via File-> Project Structure -> Modules)
4. Code away

## Known issues

1. `spring-core` and `spring-oxm` should be pre-compiled due to repackaged dependencies.
See `*RepackJar` tasks in the build and https://youtrack.jetbrains.com/issue/IDEA-160605).
2. `spring-aspects` does not compile due to references to aspect types unknown to
IntelliJ IDEA. See https://youtrack.jetbrains.com/issue/IDEA-64446 for details. In the meantime, the
'spring-aspects' can be excluded from the project to avoid compilation errors.
3. While JUnit tests pass from the command line with Gradle, some may fail when run from
IntelliJ IDEA. Resolving this is a work in progress. If attempting to run all JUnit tests from within
IntelliJ IDEA, you will likely need to set the following VM options to avoid out of memory errors:
    -XX:MaxPermSize=2048m -Xmx2048m -XX:MaxHeapSize=2048m
4. If you invoke "Rebuild Project" in the IDE, you'll have to generate some test
resources of the `spring-oxm` module again (`./gradlew :spring-oxm:compileTestJava`)    


## Tips

In any case, please do not check in your own generated .iml, .ipr, or .iws files.
You'll notice these files are already intentionally in .gitignore. The same policy goes for eclipse metadata.

## FAQ

Q. What about IntelliJ IDEA's own [Gradle support](https://www.jetbrains.com/help/idea/gradle.html)?

A. Keep an eye on https://youtrack.jetbrains.com/issue/IDEA-53476

## 1. java: 找不到符号
    符号:   类 InstrumentationSavingAgent
    位置: 程序包 org.springframework.instrument
    删除项目包中aop、aspect 中main包位置配置
    

## 2. Kotlin: Return type mismatch: expected 'T?', actual 'java.lang.Class<T>'.
    修改 设置 -> 编译器 -> java 编译器 中 所有项目的jdk 版本与项目jdk 版本一致

## 3. Kotlin: Overload resolution ambiguity between candidates:
    fun <T : Any> registerBean(beanName: @Nullable() String?, beanClass: Class<T>, vararg constructorArgs: Any): Unit
    fun <T : Any> registerBean(beanName: @Nullable() String?, beanClass: Class<T>, supplier: @Nullable() Supplier<T>?, vararg customizers: BeanDefinitionCustomizer): Unit
    
    重新设置 项目包内文件位置

## 4.  For more information, please refer to https://docs.gradle.org/8.8/userguide/validation_problems.html#input_file_does_not_exist in the Gradle documentation.
    
    
## 5. java: 找不到符号
    符号:   类 JtaAnnotationTransactionAspect
    位置: 类 org.springframework.transaction.aspectj.AspectJJtaTransactionManagementConfiguration
    
    解决方式：删除aspect main 下的包文件

## 6. java: 程序包org.springframework.ui.freemarker不存在
    重新编译 spring-webmvc

## 7. java: java.lang.ExceptionInInitializerError
    com.sun.tools.javac.code.TypeTag :: UNKNOWN

## 8. kotlin 报错
    重新将tx 模块下的包文件挂载

## 9. java: 程序包org.springframework.oxm不存在



