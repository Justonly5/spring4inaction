# 装配Bean

## 1. Spring配置的可选方式

* 在XML中显式配置
* 在Java中显式配置
* 隐式的Bean发现机制和自动装配

Tips:建议是尽可能地使用自动配置的机制，显式配置越少越好.当必须要显式配置bean的时候（比如，有些源码不是由你来维护的，而当你需要为这些代码配置bean的时候），推荐使用类型安全并且比XML更加强大的JavaConfig。最后，只有当想要使用便利的XML命名空间，并且在JavaConfig中没有同样的实现时，才应该使用XML。

## 2. 自动化装配Bean

Spring 从两个角度实现自动化装配:

* 组件扫描(component-scanning)

    Spring会自动发现应用上下文所创建的bean.

* 自动装配(autowiring)

    Spring自动满足Bean之间的依赖.

### 2.1 创建可被发现的Bean

使用注解表明该类会作为组件类，并告知Spring要为这个类创建bean.组件扫描默认是不启用的。我们还需要显式配置一Spring，从而命令它去寻找带有注解的类，并为其创建bean。

@ComponentScan 注解启用了组件扫描，等价于XML配置的<context:component-scan >

@Configuration 注解表明该类为一个配置类

```java
/*
 * @ComponentScan:设置组件扫描的基础包
 * value 属性用于指定扫描的包.
 * basePackages={"package1"，"package2"}
 * basePackageClasses = {Class1.class，Class2.class}
 *
 */
@Configuration
@ComponentScan("com.spring.soundsystem.impl")
public class CDPlayerConfig {

}
```

* @Component

* @Controller

* @Service

* @Repository

* @Bean 用在方法上，告诉Spring容器，你可以从下面这个方法中拿到一个Bean

### 2.2 为组件扫描的Bean命名

Spring应用上下文中所有的bean都会给定一个ID，默认名称为类名首字母小写.可以使用@Component("id")指定bean对应的ID.

### 2.3 设置组件扫描的基础包

@ComponentScan:设置组件扫描的基础包.

value 属性用于指定扫描的包.

basePackages={"package1"，"package2"}

basePackageClasses = {Class1.class，Class2.class}

### 2.4 通过为Bean添加注解实现自动装配

## 3. 通过Java代码装配Bean

### 3.1 创建配置类

创建一个普通Java类，为其添加@Configuration注解，表明这个类是个注解类.

```java
@Configuration
public class CDPlayerConfig {

}
```

### 3.2 声明简单的bean

要在JavaConfig中声明一个bean，需要创建一个方法，该方法返回一个所需类型的实例，并为该方法添加@Bean注解.Spring将会拦截所有对它的调用，并确保直接返回该方法所创建的bean，而不是每次都对其进行实际的调用.例如:

```java
@Bean
public CompactDisc sgtPappers(){
    return new SgtPappers();
}
```

默认情况下返回的bean的ID和方法名称一致，可以通过name属性指定bean的ID.

### 3.3 借助JavaConfig实现注入

```java
@Bean
public CDPlayer cdPlayer() {
    return new CDPlayer(sgtPappers());
}

@Bean
public CDPlayer cdPlayer(CompactDisc cd) {
    return new CDPlayer(cd);
}
```

以上两种方法都可以实现依赖注入.

* 方法一通过调用sgtPeppers()实现注入.看起来，CompactDisc是通过调用sgtPeppers()得到的，但情况并非完全如此。因为sgtPeppers()方法上添加了@Bean注解，Spring将会拦截所有对它的调用，并确保直接返回该方法所创建的bean，而不是每次都对其进行实际的调用。

* 方法二通过请求一个CompactDisc作为参数实现注入.当Spring调用该方法的时候会自动装配一个CompactDisc的实例到该方法中.

方法二通常是最佳选择,因为它对于注入的bean的来源没有任何要求,只要是容器内已声明的bean都可以被注入.

## 4. 通过XML装配Bean

## 5. 导入和混合配置

* 在一个配置类中可以使用@Import(Class1.class,Class2.class) 引入另外配置类,也可以使用@ImportResource("classpath:cd-config.xml")引入另外的XML配置文件.

* 在XML配置文件中可以使用< bean class="com.spring.config.CDConfig"> 引入配置类.或者使用< import resource="cdplayer-config.xml"> 引入其他xml配置文件.
