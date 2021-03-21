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
 
## 스프링 JDBC
### 스프링 JDBC 개념
* JDBC는 가장 오랫동안 자바 개발자들이 사용한 DB 연동 기술이다.
* JDBC를 이용하여 DB연동 프로그램을 개발하면 데이터베이스에 비종속적인 DB 연동 로직을 구현할 수 있다.
* JDBC 프로그램은 이용하려면 개발자가 작성해야할 코드가 많다.
* 유틸 클래스로 커넥션 연결 해제 로직을 대체하더라도 많은 양의 코드가 필요하다.
* 스프링은 JDBC 기반의 DB 연동 프로그램을 쉽게 개발할 수 있도록 JdbcTemplate 클래스를 지원한다.

### JdbcTemplate 클래스
* JdbcTemplate은 템플릿 메서드 패턴이 적용된 클래스이다.
* 템플릿 메서드 패턴은 복잡하고 반복되는 알고리즘을 캡슐화해서 재사용하는 패턴으로 정의할 수 있다.
* 반복되는 DB 연동 로직은 JdbcTemplate 클래스의 템플릿 메서드가 제공하고, 개발자는 달라지는 SQL 구문과 설정값만 신경쓰면 된다.

### 스프링 JDBC 설정
#### 라이브러리 추가


#### DataSource 설정
~~~xml
    <!-- DataSource 설정  -->
    <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
        <property name="driverClassName" value="org.h2.driver"/>
        <property name="url" value="jdbc:h2:tcp://localhost/~/test"/>
        <property name="username" value="sa"/>
        <property name="password" value=""/>
    </bean>
~~~

#### 프로퍼티 파일을 활용한 DataSource 설정
src/main/resources/confing/
data.properties
~~~properties
jdbc.driver=org.h2.Driver
jdbc.url=jdbc:h2:tcp://localhost/~/test
jdbc.username=sa
jdbc.password=
~~~
applicationContext.xml
~~~xml
<context:property-placeholder location="classpath:config/database.properties"/>
    <!-- DataSource 설정  -->
    <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>
~~~

### JdbcTemplate 메서드
#### update()메서드
* SQL의 ? 값을 
1. int update(String sql, Object... args) 가변변수로 전달
2. int update(String sql, Object[] args) 배열로 전달

#### queryForInt() 메서드
* select 구문으로 검색된 정숫값을 리턴 받으려면 queryForInt() 사용
1. int queryForInt(String sql)
2. int queryForInt(String sql, Object... args)
3. int queryForInt(String sql, Object[] args)

#### queryForObject() 메서드
* select 구문의 실행 결과를 특정 자바 객체로 매핑하여 리턴받을 때 사용
* 검색 결과를 자바 객체로 매핑할 RowMapper 객체를 반드시 지정해야 한다.
1. Object queryForObject(String sql)
2. Object queryForObject(String sql, RowMapper\<T\> rowMapper)
3. Object queryForObject(String sql Object[] args, RowMapper\<T\> rowMapper)

ex) RowMapper
~~~java
public class BoardRowMapper implements RowMapper<BoardVO> {
    @Override
    public BoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        BoardVO board = new BoardVO();
        board.setSeq(rs.getInt("SEQ"));
        // ...
        return board;
    }
}
~~~ 

#### query() 메서드
* query 메서드는 select 문의 실행 결과가 리스트일 때 사용한다.
1. List query(String sql)
2. List query(String sql, RowMapper\<T\> rowMapper)
3. List query(String sql Object[] args, RowMapper\<T\> rowMapper)
* 여러건의 ROW 정보가 검색되고, 검색된 ROW 수많큼 mapRow 메서드가 실행되어 List로 저장되어 리턴한다.

### DAO 클래스 구현
1. 첫 번째 방법: JdbcDaoSupport 클래스 상속
    * DAO 클래스에 JdbcDaoSupport를 상속받는다.
    * 부모의 getJdbcTemplate() 메서드를 사용할 수 있다.
    * getJdbcTemplate 메서드가 JdbcTemplate 객체를 리턴하려면 DataSource 객체가 필요하다.
    * 부모의 setDataSource에 데이터소스 객체를 의존성 주입하면 된다.
    ~~~java
       @Autowired
       public void setSuperDataSource(DataSource dataSource) {
           super.setDataSource(dataSource);
       }
    ~~~
2. 두 번째 방법: JdbcTemplate 클래스 \<bean\> 등록, 의존성 주입
* 일반적으로 이 방법을 사용한다.


## 트랜잭션 처리
* 스프링에서 트랜잭션 처리를 컨테이너가 자동으로 처리할 수 있도록 설정할 수 있다.
* 선언적 트랜잭션 처리라고 한다.
* 트랜잭션은 어드바이저 엘리먼트를 사용해야 한다.

### 트랜잭션 네임스페이스 등
* applicationContext.xml에 tx 네임스페이스 등록

### 트랜잭션 관리자 등록
* 트랜잭션 관련 설정에서 가장 먼저 등록하는 것은 트랜잭션 관리자 클래스이다.
* 스프링은 다양한 트랜잭션 관리자를 지원하고, 어떤 기술을 이용하여 데이터베이스 연동을 처리했느냐에 따라서 트랜잭션 관리자가 달라진다.
* 모든 트랜잭션 관리자는 PlatformTransactionManager 인터페이스를 구현한 클래스들이다.
* 트랜잭션 관리자를 이용하여 트랜잭션을 제어하는 어드바이스를 등록한다.

### 트랜잭션 어드바이스 설정
* \<tx:advice\> 엘리먼트로 어드바이스를 설정한다.
* AOP 관련 설정에 사용한 모든 어드바이스 클래스를 우리가 직접 구현했지만, 트랜잭션 관리 기능의 어드바이스는 직접 구현하지 않는다.
* 스프링 컨테이너가 \<tx:advice\> 설정을 참조하여 자동으로 생성한다.
##### tx:method 엘리먼트가 가질 수 있는 속성
* name: 트랜잭션이 적용될 메소드 이름 지정
* read-only: 읽기 전용 여부 지정 (default:false)
* no-rollback-for: 트랜잭션을 롤백하지 않을 예외 지정
* rollback-for: 트랜잭션을 롤백할 예외 지정

### AOP 설정을 통한 트랜잭션 적용
* 트랜잭션 관리 어드바이스가 동작하도록 AOP 설정만 추가하면 된다.
* 이때 \<aop:advisor\> 를 사용해야 한다.
* 트랜잭션 관리 어드바이스는 직접 클래스 구현을 하지 않고, 스프링 컨테이너가 자동으로 설정하여 이름을 알 수 없다.
* 결국 aop:aspect 엘리먼트를 사용할 수 없다.

##### ex) 트랜잭션 설정
 ~~~xml
<!-- DataSource 설정  -->
    <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>

    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!-- Transaction 설정 -->
    <bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!-- 트랜잭션 어드바이스 설정 -->
    <tx:advice id="txAdvice" transaction-manager="txManager">
        <tx:attributes>
            <tx:method name="get*" read-only="true"/>
            <tx:method name="*"/>
        </tx:attributes>
    </tx:advice>
    
    <!-- 어드바이스 설정-->
    <aop:config>
        <aop:pointcut id="txPointcut" expression="execution(* com.springbook.biz..*(..))"/>
        <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointcut"/>
    </aop:config>
~~~

### 트랜잭션 설정 테스트
* 트랜잭션은 메서드 단위로 관리되므로 예외가 발생하면 메서드의 작업결과는 모두 롤백처리 된다.

## Spring MVC 구조
### Spring MVC 수행 흐름
Client request 요청
1. DispatcherServlet - 클라이언트로부터의 모든 요청을 받는다.
2. HandlerMapping - 요청을 처리할 Controller를 검색한다.
3. Controller - 검색된 Controller를 실행하여 클라이언트의 요청을 처리한다.
4. ModelAndView - 비즈니스 로직 수행 결과로 얻어낸 Model 정보와 보여줄 View 정보를 ModelAndView 객체에 저장하여 리턴
5. ViewResolver - DispatcherServlet은 ModelAndView로부터 View 정보를 추출하고, ViewResolver를 이용하여 응답으로 사용할 View를 얻어낸다.
6. View - DispatcherServlet은 ViewResolver로 찾아낸 View를 실행하여 응답을 전송한다.

### DispatcherServlet 등록 및 스프링 컨테이너 구동
#### DispatcherServlet 등록
* WEB-INF/web.xml에 Spring에서 제공하는 DispatcherServlet으로 변경

#### 스프링 컨테이너 구동
* 서블릿 컨테이너가 DispatcherServlet 객체를 생성하고 나면 재정의된 init() 메서드가 자동으로 실행된다.
* init() 메서드는 스프링 설정 파일(action-servlet.xml)을 로딩하여 XmlWebApplicationContext를 생성한다.
* 스프링 설정 파일에 DispatcherServlet이 사용할 HandlerMapping, Controller, ViewResolver 클래스를 bean으로 등록하면 스프링 컨테이너가 해당 객체들을 생성해준다.

#### 스프링 설정 파일 등록
* WEB-INF/action-servlet.xml 파일을 찾아 로딩을 하므로 해당 위치에 파일을 생성한다.
* 파일 이름은 web.xml에서 등록된 서블릿 이름 뒤에 -servlet.xml을 붙여 스프링 설정 파일을 찾는다.

### 스프링 설정 파일 변경
* 스프링 컨테이너를 위한 설정 파일의 이름과 위치는 서블릿 이름을 기준으로 자동으로 결정된다.
* 하지만 필요에 따라 설정 파일의 이름을 바꾸거나 위치를 변경할 수 있다.

### 인코딩 설정
* 스프링에서는 인코딩 처리를 위해 CharacterEncodingFilter 클래스를 제공하며 web.xml 파일에 CharacterEncodingFilter를 등록하면 모든 클라이언트의 요청에 대해서 일괄적으로 인코딩을 처리할 수 있다. 