package top.dannystone.staticAbstract.normalWay;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2019/6/15 9:09 AM
 */
public abstract class Animal {
    public void animalCall() {
        openMonth();
        makeSound();
    }

    public abstract void makeSound();


    private void openMonth() {
        System.out.println("opening month.");
        System.out.println("month are open.");
    }
}
