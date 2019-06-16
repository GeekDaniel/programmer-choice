package top.dannystone.staticAbstract.staticWay;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2019/6/15 9:09 AM
 */
public abstract class Animal {
    public static void bark(Class<? extends Animal> clazz) {
        takeABreathIn();
        try {
            Method method = clazz.getMethod("");
            try {
                method.invoke(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

//    public static abstract void specificBark();


    //some common logic
    private static void takeABreathIn() {

    }
}
