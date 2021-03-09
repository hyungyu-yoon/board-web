# board-web
Spring quick start

# AOP
AspectJ dependency 추가 
~~~xml
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjrt</artifactId>
        <version>1.9.3</version>
    </dependency>
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjweaver</artifactId>
        <version>1.9.3</version>
    </dependency>
~~~


## AOP 용어 정리
### 조인포인트 (Joinpoint)
* 클라이언트가 호출하는 모든 비즈니스 메서드
* 포인트 컷 대상, 포인트 컷 후보라고도 한다.

### 포인트컷(Pointcut)
* 필터링된 조인포인트
* 수많은 비즈니스 메서드 중에서 원하는 특정 메서드에만 횡단 관심에 해당하는 공통 기능을 수행시키기 위해 포인트컷이 필요
* 패키지, 클래스, 메서드 시그니처까지 정확하게 지정할 수 있다.
~~~xml
<aop:pointcut id="아이디" exepression="execution(표현식)"></aop:pointcut>
~~~
##### 포인트컷 표현식
~~~xml
    [*] [com.multicampus.biz..][*Impl].[*(..)]
  리턴타입       패키지 경로         클래스명  메서드명 및 매개변수
~~~

### 어드바이스(Advice)
* 횡단 관심에 해당하는 공통 기능의 코드
* 독립된 클래스의 메서드로 작성된다.
* 어드바이스로 구현된 메서드가 언제 동작할지 스프링 설정파일로 지정 가능

##### 동작 시점
before, after, after-returning, after-throwing 등 
~~~xml
    <aop:aspect ref="log">
        <aop:before pointcut-ref="getPointcut" method="printLogging"/>
    </aop:aspect>
~~~

### 위빙(Weaving)
* 포인트컷으로 지정된 핵심 관심 메서드가 호출될 때, 어드바이스에 해당하는 횡단 관심 메서드가 삽입되는 과정
* 위빙을 통해 비즈니스 메서드를 수정하지 않고 횡단 관심에 해당하는 기능을 추가하거나 변경 가능

### 애스팩트(Aspect) 또는 어드바이저(Advisor)
* AOP의 핵심은 애스팩트
* 애스팩트는 포인트컷과 어드바이스의 결합
* 어떤 포인트컷 메서드에 대해서 어떤 어드바이스 메서드를 실행할지 결정
* 애스팩트 설정할 때는 \<aop:aspect\>
* 트랜잭션 설정에서는 \<aop:advisor\> 를 사용한다.
* 애스팩트와 어드바이저는 같은 의미이다.

## AOP 엘리먼트
### 1. \<aop:config\> 엘리먼트
* AOP 설정에서 루트 엘리먼트이다.
* 스프링 설정 파일 내에서 여러 번 사용할 수 있다.
* 하위에는 \<aop:pointcut\>, \<aop:aspect\> 엘리먼트가 위치할 수 있다.

### 2. \<aop:pointcut\> 엘리먼트
* 포인트컷을 지정하기 위해 사용한다.
* 여러 개 정의할 수 있으며, 유일한 아이디를 할당하여 애스펙트를 설정할 때 포인트컷을 참조하는 용도로 사용한다.
~~~xml
    <aop:config>
        <aop:pointcut id="allPointcut" expression="execution(* com.springbook.biz..*Impl.*(..))"/>
        <aop:aspect ref="log">
            <aop:before pointcut-ref="allPointcut" method="printLogging"/>
        </aop:aspect>
    </aop:config>
~~~

### 3. \<aop:aspect\> 엘리먼트
* 핵심 관심에 해당하는 포인트컷 메서드와 횡단 관심에 해당하는 어드바이스 메서드를 결합하기 위해 사용한다.
* 애스펙트의 설정에 따라 위빙의 결과가 달라지므로, AOP에서 가장 중요한 설정이다.

### 4. \<aop:advisor\> 엘리먼트
* 트랜잭션 설정 같은 몇몇 특수한 경우는 애스펙트 대신 어드바이저를 사용한다.
* 어드바이스 객체의 아이디를 모르거나 메서드 이름을 확인할 수 없을 때 애스펙트를 설정할 수 없다.
~~~xml
    <bean id="txManager" class="org.springframework.orm.jpa.JpaTransactionManager">
        <property name="entityManagerFactory" ref="entityManagerFactory"></property>
    </bean> 

    <tx:advice id="txAdvice" transaction-manager="txManager">
        <tx:attributes>
            <tx:method name="get*" read-only="true" />
            <tx:method name="*"/>
        </tx:attributes>
    </tx:advice>
    
    <aop:config>
            <aop:pointcut id="allPointcut" expression="execution(* com.springbook.biz..*Impl.*(..))"/>
            <aop:advisor pointcut-ref="allPointcut" advice-ref="txAdvice"/>
    </aop:config>
~~~

## 포인트컷 표현식
~~~xml
    excution([*] [com.multicampus.biz..][*Impl].[*(..)])
            리턴타입       패키지 경로         클래스명  메서드명 및 매개변수
~~~

##### 리턴타입 지정
* \*: 모든 리턴타입 허용
* void: 리턴타입 void 선택
* !void: 리턴타입 void 아닌 메서드 선택

##### 패키지 지정
* com.springbook.biz : 정확히 해당 패키지만 선택
* com.springbook.biz.. : 해당 패키지로 시작하는 모든 패키지 선택
* com.springbook..impl : com.springbook으로 시작하면서 마지막 패키지 이름이 impl로 끝나는 패키지 선택

##### 클래스 지정
* BoardServiceImpl : 정확하게 해당 클래스만 선택
* \*Impl : 클래스 이름이 Impl로 끝나는 클래스 선택
* BoardService+ : 해당 클래스로 부터 파생된 모든 자식 클래스, 인터페이스라면 해당 인터페이스를 구현한 모든 클래스 선택

##### 메서드 지정
* \*(..) : 가장 기본 설정으로 모든 메서드 선택
* get*(..) : get으로 시작하는 모든 메서드 선택

##### 매개변수 지정
* (..) : 매개변수 개수와 타입에 제약이 없음
* (*) : 반드시 1개의 매개변수를 가지는 메서드만 선택
* (com.springbook.user.UserVO) : 매개변수로 UserVO를 갖는 메서드 선택, 패키지 경로 필수
* (!com.springbook.user.UserVO) : UserVO를 갖지 않는 메서드만 선택
* (Integer, ..) : 한 개 이상의 매개변수, 첫 번째 Integer 를 갖는 메서드 선택
* (Integer, *) : 두 개 이상의 매개변수, 첫 번째 Integer 를 갖는 메서드 선택
    
## 어드바이스 동작 시점
* Before : 비즈니스 메서드 실행 전 동작
* After Returning : 비즈니스 메서드가 성공적으로 리턴되면 동작
* After Throwing : 비즈니스 메서드 실행 중 예외가 발생하면 동작
* After : 비즈니스 메서드 실행된 후, 무조건 실행
* Around : 메서드 호출 자체를 가로채 비즈니스 메서드 실행 전후에 처리할 로직을 삽입할 수 있음.
    * ~~~java
        public Object aroundLog(ProceedingJoinPoint pjp) throws Throwable {
          System.out.println("[before] 수행 전 처리");
          Object returnObj = pjp.proceed();
          System.out.println("[after] 수행 후 처리");
        }
      ~~~
    * ProceedingJoinPoint 객체를 매개변수로 받아야 한다.

## JoinPoint와 바인드 변수
* 어드바이스 메서드를 의미 있게 구현하려면 클라이언트가 호출한 비즈니스 메서드의 정보가 필요하다.
* 메서드 이름, 클래스와 패키지 정보 등 정보를 이용할 수 있다.

### JoinPoint 메서드
* Signature getSignature() : 클라이언트가 호출한 메서드의 시그니처(리턴타입, 이름, 매개변수) 정보를 가진 객체
* Object getTarget() : 클라이언트가 호출한 비즈니스 메서드를 포함한 비즈니스 객체 리턴
* Object[] getArgs() : 클라이언트가 메서드를 호출할 때 넘겨준 인자 목록을 Object 배열로 리턴
* Before, After* 어드바이스는 JoinPoint를 사용하고, Around만 ProceedingJoinPoint를 사용한다.

##### Signature 메서드
* String getName() : 메서드 이름
* String toLongString() : 메서드의 리턴타입, 이름, 매개변수를 패키지 경로까지 포함한 리턴
* String toShortString() : 메서드 시그니처를 축약한 문자열로 리턴
 
~~~ java
public class LogAdvice {
    public void printLong(JoinPoint jp) {
        System.out.println("[공통 로그] 비즈니스 로직 수행 전 동작");
    }
}
~~~

## 어노테이션 기반 AOP
xml과 어노테이션 설정을 적절히 혼합하여 사용하면 xml 설정을 최소화하면서 객체들을 효츌적으로 관리할 수 있다.

### 어노테이션 기반 AOP 설정
#### 어노테이션 사용을 위한 스프링 설정
~~~xml
<aop:aspectj-autoproxy></aop:aspectj-autoproxy>
~~~
* 어드바이스 객체를 bean으로 등록하거나 @Service 어노테이션을 사용하여 검색될 수 있도록 해야 한다.

#### 포인트컷 설정
* 참조 메서드를 만들고 @Pointcut 어노테이션을 붙인다.
* 참조 메서드는 메서드 몸체가 비어있는, 구현 로직이 없는 메서드이다. 포인트컷을 식별하는 이름으로만 사용된다.
    * ~~~java
      @Pointcut("execution(* com.springbook.biz..*Impl.*(..))") 
      public void allPointcut() {}
      ~~~

#### 어드바이스 설정
* 어드바이스 클래스에는 횡단 관심에 해당하는 어드바이스 메서드가 구현되어 있다.
* 어드바이스 동작시점은 xml 설정과 마찬가지로 다섯 가지가 제공된다.
* @Before, @AfterReturning, @AfterThrowing, @After, @Around
* 해당 어노테이션에 포인트컷 참조 메서드를 지정한다.
~~~java
@Before("allPointcut()")
public void printlog() {
    System.out.println("[공통 로그] ~~");
}
~~~

#### 애스펙트 설정
* AOP 설정에서 가장 중요한 애스펙트는 @Aspect를 이용하여 설정한다.
* 애스펙트는 포인트컷과 어드바이스의 결합이다.
* @Aspect가 설정된 애스펙트 객체에는 반드시 포인트컷과 어드바이스를 결합시키는 설정이 있어야 한다.
~~~java
@Service
@Aspect // Aspect = Pointcut + Advice
public class LogAdvice {
    @Pointcut("execution(* com.springbook.biz..*Impl.*(..))")
    public void allPointcut() {}

    @Before("allPointcut()")
    public void printLong() {
        System.out.println("[공통 로그] 비즈니스 로직 수행 전 동작");
    }
}
~~~

### 어드바이스 동작 시점
* 위의 방법으로 테스트 한다.
* 공통 포인트컷을 클래스로 분리하여 사용할 수 있다.
 
 