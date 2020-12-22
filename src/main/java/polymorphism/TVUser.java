package polymorphism;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class TVUser {
    public static void main(String[] args) {
        /*
        // 1. Spring 컨테이너를 구동한다. resources의 application.xml을 읽는다.
        AbstractApplicationContext factory =
                new GenericXmlApplicationContext("applicationContext.xml");

        // 2. Spring 컨테이너로부터 필요한 객체를 요청(Lookup)한다.
        TV tv = (TV) factory.getBean("tv");
        tv.powerOn();
        tv.powerOff();
        tv.volumeUp();
        tv.volumeDown();

        // 3. Spring 컨테이너를 종료한다.
        factory.close();
        */

        // 어노테이션 방식 컨테이너
        AbstractApplicationContext factory =
                new GenericXmlApplicationContext("applicationContext.xml");

        // @Componet 방식으로 사용시 id를 미지정하게 되는데 이때 클래스 이름에서 첫 글자만 소문자로 바꾼 후 사용한다.
        TV tv = (TV)factory.getBean("tv");
        tv.volumeUp();
        tv.volumeDown();

    }
}
