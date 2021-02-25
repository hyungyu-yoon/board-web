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

