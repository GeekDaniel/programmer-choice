package top.dannystone.validation.classLoader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * Description:双亲委派模型test case
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/4/5 12:17 AM
 */
public class TestGetClass {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {

        TestGetClass testGetClass = new TestGetClass();
        //return AppClassLoader when load class from classPath
        System.out.println(testGetClass.getClass().getClassLoader());
        //class load correctly if loader is not null
        System.out.println(testGetClass.getClass().getClassLoader().loadClass("top.dannystone.validation.classLoader.TestGetClass"));
        //class resource can be read through Class<?> instance when classLoader not null
        InputStream stream = testGetClass.getClass().getResourceAsStream("TestGetClass.class");
        System.out.println(stream);

        //same class path loaded by same loader then do newInstance return same class instance
        Object o = testGetClass.getClass().getClassLoader().loadClass("top.dannystone.validation.classLoader.TestGetClass").newInstance();
        System.out.println(o instanceof TestGetClass);

        //return null to represent the bootstrap class loader
        Integer integer = new Integer(1);
        System.out.println(integer.getClass().getClassLoader());
        //class resource can be read through Class<?> instance when even classLoader is null
        InputStream stream2 = testGetClass.getClass().getResourceAsStream("TestGetClass.class");
        System.out.println(stream2);

        Object o1 = testGetClass.loadSameClassByDifferentLoaderThenNewInstance();
        //same class path loaded by different loader then do newInstance return different class instance
        System.out.println(o1 instanceof TestGetClass);


    }

    private Object loadSameClassByDifferentLoaderThenNewInstance() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                InputStream stream = getClass().getResourceAsStream(fileName);
                if (stream == null) {
                    return super.loadClass(name);
                }
                try {
                    byte[] b = new byte[stream.available()];
                    // 将流写入字节数组b中
                    stream.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return super.loadClass(name);
            }
        };
        Object obj = classLoader.loadClass("top.dannystone.validation.classLoader.TestGetClass").newInstance();
        return obj;
    }
}
