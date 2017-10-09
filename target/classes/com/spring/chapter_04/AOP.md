# Spring AOP

面向方面编程(AOP)：也可称为面向切面编程，是一种编程范式，提供从另一个角度来考虑程序结构从而完善面向对象编程(OOP)。

## AOP 能干什么

* 用于横切关注点的分离和织入横切关注点到系统；比如上边提到的日志等等；

* 完善OOP；

* 降低组件和模块之间的耦合性；

* 使系统容易扩展；

* 而且由于关注点分离从而可以获得组件的更好复用。

## AOP 相关术语

* Joinpoint：连接点，表示需要在程序中插入横切关注点的扩展点，连接点可能是类初始化、方法执行、方法调用、字段调用或处理异常等等，Spring 只支持方法执行连接点，在 AOP 中表示为“在哪里干”；

* Pointcut：切入点，选择一组相关连接点的模式，即可以认为连接点的集合，Spring 支持 perl5 正则表达式和 AspectJ 切入点模式，Spring 默认使用AspectJ语法，在 AOP 中表示为“在哪里干的集合”；

* Advice：通知，在连接点上执行的行为，通知提供了在 AOP 中需要在切入点所选择的连接点处进行扩展现有行为的手段；包括前置通知（before advice）、后置通知(after advice)、环绕通知（around advice），在 Spring 中通过代理模式实现 AOP，并通过拦截器模式以环绕连接点的拦截器链织入通知；在 AOP中表示为“干什么”；

* Aspect：切面，横切关注点的模块化，比如上边提到的日志组件。可以认为是通知、引入和切入点的组合；在 Spring 中可以使用 Schema 和 @AspectJ 方式进行组织实现；在 AOP 中表示为“在哪干和干什么集合”；

* inter-type declaration：引入，也称为内部类型声明，为已有的类添加额外新的字段或方法，Spring 允许引入新的接口（必须对应一个实现）到所有被代理对象（目标对象）, 在 AOP 中表示为“干什么（引入什么）”；

* Target Object：目标对象，需要被织入横切关注点的对象，即该对象是切入点选择的对象，需要被通知的对象，从而也可称为“被通知对象”；由于 Spring AOP 通过代理模式实现，从而这个对象永远是被代理对象，在 AOP 中表示为“对谁干”；

* AOP Proxy：AOP 代理，AOP 框架使用代理模式创建的对象，从而实现在连接点处插入通知（即应用切面），就是通过代理来对目标对象应用切面。在 Spring 中，AOP 代理可以用 JDK 动态代理或 CGLIB 代理实现，而通过拦截器模型应用切面。

* Weaving：织入，织入是一个过程，是将切面应用到目标对象从而创建出 AOP 代理对象的过程，织入可以在编译期、类装载期、运行期进行。

## 通知类型

* 前置通知(Before Advice)：在切入点选择的连接点处的方法之前执行的通知，该通知不影响正常程序执行流程（除非该通知抛出异常，该异常将中断当前方法链的执行而返回）。

* 后置通知(After Advice)：在切入点选择的连接点处的方法之后执行的通知，包括如下类型的后置通知：

  * 后置返回通知（After returning Advice）:在切入点选择的连接点处的方法正常执行完毕时执行的通知，必须是连接点处的方法没抛出任何异常正常返回时才调用后置通知。

  * 后置异常通知（After throwing Advice）: 在切入点选择的连接点处的方法抛出异常返回时执行的通知，必须是连接点处的方法抛出任何异常返回时才调用异常通知。

  * 后置最终通知（After finally Advice）: 在切入点选择的连接点处的方法返回时执行的通知，不管抛没抛出异常都执行，类似于 Java 中的 finally 块。

* 环绕通知（Around Advice）：环绕着在切入点选择的连接点处的方法所执行的通知，环绕通知可以在方法调用之前和之后自定义任何行为，并且可以决定是否执行连接点处的方法、替换返回值、抛出异常等等。

## AOP 代理

AOP 代理就是 AOP 框架通过代理模式创建的对象，Spring 使用 JDK动态代理或 CGLIB 代理来实现，Spring 缺省使用 JDK 动态代理来实现，从而任何接口都可别代理，如果被代理的对象实现不是接口将默认使用 CGLIB 代理，不过 CGLIB 代理当然也可应用到接口。

AOP 代理的目的就是将切面织入到目标对象。

## AOP 配置

* 声明切面

    切面就是包含切入点和通知的对象，在 Spring 容器中将被定义为一个Bean，Schema 方式的切面需要一个切面支持 Bean，该支持 Bean 的字段和方法提供了切面的状态和行为信息，并通过配置方式来指定切入点和通知实现。
    切面使用< aop:aspect> 标签指定，ref 属性用来引用切面支持 Bean。

    ```XML
    <bean id="aspectSupportBean" class="……"/>
    <aop:config>
        <aop:aspect id="aspectId" ref="aspectSupportBean">
        ……
        </aop:aspect>
    </aop:config>
    ```

* 声明切入点

    切入点在Spring中也是一个Bean，Bean定义方式可以有很三种方式：

  * 在< aop:config>标签下使用< aop:pointcut>声明一个切入点Bean。

    该切入点可以被多个切面使用，对于需要共享使用的切入点最好使用该方式，该切入点使用 id 属性指定 Bean 名字，在通知定义时使用 pointcut-ref 属性通过该id引用切入点，expression 属性指定切入点表达式。

    ```XML
    <aop:config>
        <aop:pointcut id="pointcut" expression="execution(* cn.javass..*.*(..))"/>
        <aop:aspect ref="aspectSupportBean">
            <aop:before pointcut-ref="pointcut" method="before"/>
        </aop:aspect>
    </aop:config>
    ```

  * 在< aop:aspect>标签下使用< aop:pointcut>声明一个切入点Bean。

    该切入点可以被多个切面使用，但一般该切入点只被该切面使用，当然也可以被其他切面使用，但最好不要那样使用，该切入点使用id属性指定Bean名字，在通知定义时使用 pointcut-ref 属性通过该id引用切入点，expression 属性指定切入点表达式。

    ```XML
    <aop:config>
        <aop:aspect ref="aspectSupportBean">
        <aop:pointcut id=" pointcut" expression="execution(* cn.javass..*.*(..))"/>
            <aop:before pointcut-ref="pointcut" method="before"/>
        </aop:aspect>
    </aop:config>
    ```
  * 匿名切入点Bean。

    可以在声明通知时通过pointcut属性指定切入点表达式，该切入点是匿名切入点，只被该通知使用。

    ```XML
    <aop:config>
        <aop:aspect ref="aspectSupportBean">
            <aop:after pointcut="execution(* cn.javass..*.*(..))" method="afterFinallyAdvice"/>
        </aop:aspect>
    </aop:config>
    ```

* 声明通知

  * 前置通知：在切入点选择的方法之前执行，通过 < aop:aspect> 标签下的< aop:before> 标签声明：
    * pointcut 和 pointcut-ref：二者选一，指定切入点；

    * method：指定前置通知实现方法名，如果是多态需要加上参数类型，多个用“，”隔开，如 beforeAdvice(java.lang.String)；

    * arg-names：指定通知实现方法的参数名字，多个用“，”分隔，可选，类似于【3.1.2 构造器注入】中的参数名注入限制：在class文件中没生成变量调试信息是获取不到方法参数名字的，因此只有在类没生成变量调试信息时才需要使用 arg-names 属性来指定参数名，如 arg-names="param" 表示通知实现方法的参数列表的第一个参数名字为 “param”。

    ```XML
    <aop:before pointcut="切入点表达式" pointcut-ref="切入点Bean的引用" method="前置通知实现方法名" arg-names="前置通知实现方法参数列表参数名字"/>
    ```
  * 后置返回通知：在切入点选择的方法正常返回时执行，通过 < aop:aspect> 标签下的< aop:after-returning> 标签声明：

    * pointcut 和 pointcut-ref：同前置通知同义；

    * method：同前置通知同义；

    * arg-names：同前置通知同义；

    * returning：定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法执行正常返回后，将把目标方法返回值传给通知方法；returning 限定了只有目标方法返回值匹配与通知方法相应参数类型时才能执行后置返回通知，否则不执行，对于 returning 对应的通知方法参数为 Object 类型将匹配任何目标返回值。

    ```xml
    <aop:after-returning pointcut="execution(* cn.javass..*.sayAfterReturning(..))" method="afterReturningAdvice" arg-names="retVal" returning="retVal"/>
    ```

  * 后置异常通知：在切入点选择的方法抛出异常时执行，通过 < aop:aspect> 标签下的 < aop:after-throwing> 标签声明：

    * pointcut 和 pointcut-ref：同前置通知同义；

    * method：同前置通知同义；

    * arg-names：同前置通知同义；

    * throwing：定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法抛出异常返回后，将把目标方法抛出的异常传给通知方法；throwing 限定了只有目标方法抛出的异常匹配与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行，对于 throwing 对应的通知方法参数为 Throwable 类型将匹配任何异常。

    ```xml
    <aop:after-throwing pointcut="切入点表达式"  pointcut-ref="切入点Bean引用" method="后置异常通知实现方法名" arg-names="后置异常通知实现方法参数列表参数名字" throwing="将抛出的异常赋值给的通知实现方法参数名"/>
    ```
  * 后置最终通知：在切入点选择的方法返回时执行，不管是正常返回还是抛出异常都执行，通过 < aop:aspect> 标签下的 < aop:after > 标签声明：

    * pointcut 和 pointcut-ref：同前置通知同义；

    * method：同前置通知同义；

    * arg-names：同前置通知同义；

    ```xml
    <aop:after pointcut="切入点表达式"  pointcut-ref="切入点Bean引用" method="后置最终通知实现方法名" arg-names="后置最终通知实现方法参数列表参数名字"/>
    ```
  * 环绕通知：环绕着在切入点选择的连接点处的方法所执行的通知，环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值，可通过 < aop:aspect> 标签下的 < aop:around > 标签声明：

    * pointcut 和 pointcut-ref：同前置通知同义；

    * method：同前置通知同义；

    * arg-names：同前置通知同义；

    ```xml
    <aop:around pointcut="切入点表达式"  pointcut-ref="切入点Bean引用" method="后置最终通知实现方法名" arg-names="后置最终通知实现方法参数列表参数名字"/>
    ```

```XML
<bean id="aspect" class="cn.javass.spring.chapter6.aop.HelloWorldAspect"/>
<!-- AOP 配置 -->
<aop:config>
    <!-- 指定切入点 
        expression：切入点表达式,用于定义切入点的模式
    -->
    <aop:pointcut id="pointcut" expression="execution(* cn.javass..*.*(..))"/>
    <!-- 切面定义
        ref:用来引用切面支持类
    -->
    <aop:aspect ref="aspect">
        <!-- 前置通知
            pointcut-ref:用于引用切入点Bean
            method:用来引用切面通知实现类中的方法，该方法就是通知实现，即在目标类方法执行之前调用的方法.
        -->
        <aop:before pointcut-ref="pointcut" method="beforeAdvice"/>
        <!-- 后置通知 
            切入点除了使用pointcut-ref属性来引用已经存在的切入点，也可以使用pointcut属性来定义.
        -->
        <aop:after pointcut="execution(* cn.javass..*.*(..))" method="afterFinallyAdvice"/>
    </aop:aspect>
</aop:config>
```