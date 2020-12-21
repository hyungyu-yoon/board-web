package collection;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class CollectionBeanClient {
    public static void main(String[] args) {
        AbstractApplicationContext factory = new GenericXmlApplicationContext("applicationContext.xml");

        CollectionBean bean = (CollectionBean) factory.getBean("collectionBean");
//        List<String> addressList = bean.getAddressList();
//        addressList.stream().forEach(System.out::println);

//        Set<String> addressSet = bean.getAddressList();
//        addressSet.stream().forEach(System.out::println);

//        Map<String, String> addressMap = bean.getAddressList();
//        System.out.println(addressMap.get("고길동"));
//        System.out.println(addressMap.get("마이콜"));

        Properties addressProperties = bean.getAddressList();
        System.out.println(addressProperties.getProperty("고길동"));
        System.out.println(addressProperties.getProperty("마이콜"));
    }
}
