package polymorphism;

public class SamsungTV implements TV {
    private Speaker speaker;
    private int price;

    public SamsungTV(){
        System.out.println("===> SamsungTV 객체 생성");
    }

    /**
     * 의존성 주입
     * Setter 인젝션
     */
    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * 의존성 주입
     * 생성자 인젝션(Constructor Injection)
     */
//    public SamsungTV(Speaker speaker, int price) {
//        System.out.println("===> SamsungTV(2) 객체 생성");
//        this.speaker = speaker;
//        this.price = price; // 다중 변수 매핑
//    }



    public void powerOn() {
        System.out.println("SamsungTV---전원 켠다. (가격: " + price +")");
    }

    public void powerOff() {
        System.out.println("SamsungTV---전원 끈다.");
    }

    public void volumeUp() {
//        System.out.println("SamsungTV---소리 올린다.");
        speaker.volumeUp();
    }

    public void volumeDown() {
//        System.out.println("SamsungTV---소리 내린다.");
        speaker.volumeDown();
    }

    private void initMethod() {
        System.out.println("객체 초기화 작업 처리..");
    }

    private void destroyMethod() {
        System.out.println("객체 삭제 전에 처리할 로직 처리....");
    }
}
