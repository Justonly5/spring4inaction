# Spring 事务

## 1、 事务

    事务（Transaction）是并发控制的单位，是用户定义的一个操作序列。这些操作要么都做，要么都不做，是一个不可分割的工作单位。通过事务，能将逻辑相关的一组操作绑定在一起，以便服务器保持数据的完整性。

### 1.1 事务的基本特性：ACID

* 原子性(Atomicity)：事务是一个原子操作,由一系列动作组成。事务的原子性确保动作要么全部完成，要么完全不起作用。

* 一致性(Consistency)：一旦事务完成（不管成功还是失败），系统必须确保它所建模的业务处于一致的状态，而不会是部分完成部分失败。在现实中的数据不应该被破坏。

* 隔离性(Isolation)：可能有许多事务会同时处理相同的数据，因此每个事务都应该与其他事务隔离开来，防止数据损坏。

* 持久性(Durability)：一旦事务完成，无论发生什么系统错误，它的结果都不应该受到影响，这样就能从任何系统崩溃中恢复过来。通常情况下，事务的结果被写到持久化存储器中。

### 1.2 并发事务导致的问题

* 第一类丢失更新：撤销一个事务时，把其他事务已提交的更新数据覆盖。

* 第二类丢失更新：是不可重复读的特殊情况。如果两个事物都读取同一行，然后两个都进行写操作，并提交，第一个事物所做的改变就会丢失。

* 脏读：一个事务读取到另一个事务未提交的更新数据。

* 幻读也叫虚读：一个事务执行两次查询，第二次结果集包含第一次中没有或某些行已经被删除的数据，造成两次结果不一致，这是另一个事务在这两次查询中间插入或删除了数据造成的。

* 不可重复读：一个事务两次读取同一行的数据，结果得到不同状态的结果，中间正好另一个事务更新了该数据，两次结果相异，不可被信任。

### 1.3 事务隔离级别

    在标准 SQL 规范中定义了四种隔离级别：

* Read Uncommited：最低隔离级别。一个事务能读取到另一个事务未提交的数据，很不安全，可能出现丢失更新、脏读、不可重复读、幻读。

* Read Commited：一个事务能读取到别的事务提交的更新数据，不能看到未提交的更新数据，不可能可能出现丢失更新、脏读，但可能出现不可重复读、幻读。

* Repeatable Read：保证同一事务中先后执行的多次查询将返回同一结果，不受其他事务影响，可能可能出现丢失更新、脏读、不可重复读，但可能出现幻读。

* Serializable：最高隔离级别，不允许事务并发执行，而必须串行化执行，最安全，不可能出现更新、脏读、不可重复读、幻读。

    隔离级别越高，数据库事务并发执行性能越差，能处理的操作越少。因此在实际项目开发中为了考虑并发性能一般使用提交读隔离级别，它能避免丢失更新和脏读，尽管不可重复读和幻读不能避免，但可以在可能出现的场合使用悲观锁或乐观锁来解决这些问题。

### 1.4 事务类型

* 数据库事务类型分为本地事务和分布式事务：

  * 本地事务：就是普通事务。能保证单台数据库上的操作的 ACID，被限定在一台数据库上。

  * 分布式事务：涉及两个或多个数据库源的事务，即跨越多台同类或异类数据库的事务（由每台数据库的本地事务组成的），分布式事务旨在保证这些本地事务的所有操作的 ACID，使事务可以跨越多台数据库。

* Java 事务类型有 JDBC 事务和 JTA 事务：

  * JDBC 事务：就是数据库事务类型中的本地事务，通过 Connection 对象的控制来管理事务。

  * JTA 事务：JTA 指 Java 事务 API(Java Transaction API)，是 Java EE 数据库事务规范， JTA只提供了事务管理接口，由应用程序服务器厂商（如WebSphere Application Server）提供实现，JTA 事务比 JDBC 更强大，支持分布式事务。

* Java EE事务类型有本地事务和全局事务：

  * 本地事务：使用 JDBC 编程实现事务。
  * 全局事务：由应用程序服务器提供，使用JTA事务。

* 按是否通过编程实现事务有声明式事务和编程式事务：

  * 声明式事务：通过注解或 XML 配置文件指定事务信息。

  * 编程式事务：通过编写代码实现事务。

## 2、 Spring 事务核心接口

    Spring事务管理中，主要的核心实现为 org.springframework.transaction.PlatformTransactionManager 接口。

![](http://owkf2wv58.bkt.clouddn.com/SouthEast.png)

* TransactionDefinition：规范事务的特定表现形式
* TransactionStatus：事务运转的状态形式
* PlatformTransactionManager：根据给定的规则获取事务现有状态的具体表现，进行具体操作。

## 3、 事务特性

* 传播特性(Propagation)

    事务的传播行为是指，如果在开始当前事务之前，一个事务上下文已经存在，此时有若干选项可以指定一个事务性方法的执行行为。在  TransactionDefinition 定义中包括了如下七个表示传播行为的常量：

  * PROPAGATION_REQUIRED：当前方法必须在事务中运行，如果当前有事务环境就加入当前正在执行的事务环境，如果没有，则新建一个事务。此选项为默认值。

  * PROPAGATION_SUPPORTS：如果当前存在事务环境，该方法就加入事务中执行。如果当前没有事务，就以非事务的方式执行。

  * PROPAGATION_MANDATORY：当前方法必须在事务中运行，如果当前没有事务，就抛出异常。

  * PROPAGATION_REQUIRES_NEW：当前方法总是会为自己发起一个新的事务，如果当前方法已经运行在一个事务中，则原有事务被挂起，创建一个属于自己的事务，直到自身的事务 commit 结束，原有的事务才会恢复执行。

  * PROPAGATION_NOT_SUPPORTED：当前方法总是以非事务方式进行操作。如果当前存在事务，就把当前事务挂起，等该方法以非事务的状态运行完成，再继续原来的事务。

  * PROPAGATION_NEVER：当前方法总是以非事务方式进行操作。如果方法在事务范围内执行，容器则抛出异常。

  * PROPAGATION_NESTED：当前方法执行时，如果已经有一个事务存在，则以嵌套事务的方式运行在当前事务中。子事务可以单独的进行回滚提交操作，不会对父事务造成影响。但是子事务要受父事务的影响。

* 事务的隔离级别(Isolation)

    事务隔离级别，是指若干个并发的事务之间的隔离程度。TransactionDefinition 接口中定义了五个表示隔离级别的常量：

  * ISOLATION_DEFAULT：采用数据库默认隔离级别。

  * ISOLATION_READ_UNCOMMITTED：最低的隔离级别，允许读取尚未提交的数据变更，可能会导致脏读、幻读或不可重复读。

  * ISOLATION_READ_COMMITTED：大多数主流数据库的默认事务等级。保证了一个事务不会读到另一个并行事务已修改但未提交的数据，避免了脏读。该级别适用于大多数系统。

  * ISOLATION_REPEATABLE_READ：保证一个事务不会修改已经由另一个事务读取但为提交（回滚）的数据，也就是对同一字段在同一事务中多次读取的结果都是一致的。避免了脏读和不可重复读的情况，但是带来了更多的性能损耗。

  * ISOLATION_SERIALIZABLE：最严格的级别，事务串行执行，资源消耗最大。

* 事务超时

    事务超时，指的是设置事务执行的最长时间，如果超过时间事务还没有执行完成便立即回滚该事务。在 TransactionDefinition 中以 int 的值来表示超时时间，单位为是秒。

* 事务只读属性

    事务只读，指的是操作数据库时，只进行读取操作，而不会做相应的修改。只读属性并不是强制性的选项，而是一种优化配置。它将只读配置信息传递给数据库，以期待数据库会对其做一些特定化的优化。例如，不安排相应的数据库锁，以减轻事务对数据库的压力等。

* 事务回滚规则

     默认配置下，spring 只有在抛出的异常为运行时 unchecked 异常时才回滚该事务，也就是抛出的异常为 RuntimeException 的子类(Errors也会导致事务回滚)，而抛出 checked 异常则不会导致事务回滚。可以明确的配置在抛出那些异常时回滚事务，包括 checked 异常。

    PS：
    RuntimeException + Error 和其子类都是属于 uncheck exception，
    Exception 类中除了 RuntimeException之外的类都是属于 check exception。

## 4、 事务管理器

    Spring本身并不管理事务，而是提供了许多的事务管理器，这些事务管理器都实现了org.springframework.transaction.PlatformTransactionManager 接口，并且进行个性化的实现，达到事务管理的目的。

* DataSourceTransactionManager

     DataSourceTransactionManager 主要使用于由 JDBC 直接持久化数据的情况，例如 Mybatis 等。它通过调用 java.sql.connection 来管控事务，并且指定相应的 DataSource。但是这并不意味着 Connection 具备事务功能，它仅仅是将 commit，rollback 等命令传递给数据库，由数据库本身的事务进行管理。并且像 commit，rollback 等事务功能和执行SQL的业务功能必须使用同一个 Connection，才能真正实现事务管控的功能。

    ```XML
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
       <property name="dataSource" ref="dataSource"/>
    </bean>
    ```

* HibernateTransactionManager

    HibernateTransactionManager主要使用于由 Hibernate 框架持久化数据的情况。它将事务功能和业务功能分隔开来，由 Hibernate 自身的 Transaction 来管理事务，使职责更为分明清晰。

    ```XML
    <bean id="transactionManager" class="org.springframework.orm.hibernate3.HibernateTransactionManager">
        <property name="sessionFactory" ref="sessionFactory" />
    </bean>
    ```

* JpaTransactionManager

    JpaTransactionManager 主要使用于通过 Java 持久化 API 来持久化数据的情况。它需要装配一个 JPA 实体管理工厂( javax.persistence.EntityManagerFactory 接口的任意实现)，并且 JpaTransactionManager 将与由工厂所产生的 JPA EntityManager 合作来构建事务。

    ```XML
    <bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
        <property name="sessionFactory" ref="sessionFactory" />
    </bean>
    ```

* JtaTransactionManager

    JtaTransactionManager主要使用于包含多个数据源的分布式事务管控。

* @Transactional

    @Transactional 注解可以作用在接口，接口方法，类以及类方法上。当作用在类上时，该类所有的 public 方法都将具有该类型的事务属性。作用在方法上的标注将覆盖类级别的定义。

    注意：在 Spring 中，建议不要在接口或者接口方法上使用该注解。因为 Spring 的代理方式分为基于接口的代理和基于类实现的代理，只有当 Spring 在使用基于接口的代理时，注解才会生效。另外，@Transactional注解应该只被应用到 public 方法上，这是由 Spring AOP 本质决定的。应用在非 public 方法上，注解会被忽略，也不会抛出异常。

    Spring AOP代理对象的生成有两种：JDKProxy 和 Cglib，具体使用哪种方式生成由 AopProxyFactory 根据 AdvisedSupport 对象的配置来决定。默认的策略是如果目标类是接口，则使用 JDK 动态代理技术，否则使用 Cglib 来生成代理。接口中不允许出现 private、protected 方法，同样子类实现中也不会出现 private、protected 等默认可见性方法。所以是无法被AOP所拦截，进而 @Transactional 注解不起效。

```XML
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:aop="http://www.springframework.org/schema/aop"
    xmlns:tx="http://www.springframework.org/schema/tx"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="
    http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd
    http://www.springframework.org/schema/context
    http://www.springframework.org/schema/context/spring-context.xsd
    http://www.springframework.org/schema/aop
    http://www.springframework.org/schema/aop/spring-aop.xsd
    http://www.springframework.org/schema/tx
    http://www.springframework.org/schema/tx/spring-tx.xsd
    ">

    <!-- 此处的dataSource 为数据源配置-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
       <property name="dataSource" ref="dataSource"/>
    </bean>

    <tx:advice id="dataAdvice"transaction-manager="transactionManager">
        <tx:attributes>
            <tx:method name="insert*" propagation="REQUIRED" />
            <tx:method name="delete*" propagation="REQUIRED" />
            <tx:method name="update*" propagation="REQUIRED" />
            <tx:method name="save*" propagation="REQUIRED" />
            <tx:method name="edit*" propagation="REQUIRED" />
            <tx:method name="add*" propagation="REQUIRED" />
            <tx:method name="select*" propagation="SUPPORTS" />
            <tx:method name="query*" propagation="SUPPORTS" />
            <tx:method name="get*" propagation="SUPPORTS" />
        </tx:attributes>
    </tx:advice>

    <aop:config>
        <!-- 表示在com.xxx.manager包以及所有子包下的的任意类的任意方法 -->
        <aop:pointcut id="dataPointcut" expression="execution(* com.XXX.manager..*.*(..))" />
        <aop:advisor pointcut-ref="dataPointcut" advice-ref="dataAdvice" />
    </aop:config>

    <!-- 使用@Transactional 注解必须开启配置-->
    <!--<tx:annotation-driven transaction-manager="transactionManager"/>-->
</beans>
```

## 5、编程式事务

    所谓编程式事务指的是通过编码方式实现事务，即类似于 JDBC 编程实现事务管理。Spring 框架提供一致的事务抽象，因此对于 JDBC 还是 JTA 事务都是采用相同的 API 进行编程。

## 6、声明式事务

* XML 命名空间定义：定义用于事务支持的 tx 命名空间和 AOP 支持的 aop 命名空间。

* 事务相关配置：

  * < tx:advice >：事务通知定义，用于指定事务属性，其中 “transaction-manager” 属性指定事务管理器，并通过 < tx:attributes > 指定具体需要拦截的方法。

```xml
<!-- tx:advice:事务通知定义. transaction-manager 指定事务管理器 -->
<tx:advice id="txAdvice" transaction-manager="txManager">
    <!-- attributes指定具体需要拦截的方法 -->
    <tx:attributes>
        <!-- 表示将拦截以save开头的方法 -->
        <tx:method name="save*" propagation="REQUIRED" isolation="READ_COMMITTED"/>
        <tx:method name="*" propagation="REQUIRED" isolation="READ_COMMITTED" read-only="true"/>
    </tx:attributes>
</tx:advice>
<!-- AOP配置 -->
<aop:config>
    <!-- pointcut:切入点定义,拦截cn包及子包下的chapter9. service包及子包下的任何类的任何方法 -->
    <aop:pointcut id="serviceMethod" expression="execution(* cn..chapter9.service..*.*(..))"/>
    <!-- Advisor定义，其中切入点为serviceMethod，通知为txAdvice -->
    <aop:advisor pointcut-ref="serviceMethod" advice-ref="txAdvice"/>
</aop:config>
```
