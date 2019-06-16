package top.dannystone.staticAbstract.normalWay;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2019/6/15 9:19 AM
 */
public class Test {

    public static void main(String[] args) {
        Animal animal= new Dog();
        animal.animalCall();
        Animal animal2= new Duck();
        animal2.animalCall();
    }


}
