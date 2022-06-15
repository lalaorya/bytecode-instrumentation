package secordexample;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationTargetException;

/**
 * @Author virtual
 * @Date 2022/6/6 0:09
 * @description：创建一个Object的子类，并且重写它的toString方法，方法默认返回hellobytebuddy
 */
public class HelloByteBuddy {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        // 入口一定是new ByteBuddy
        Class<?> dynamicClass = new ByteBuddy()
                .subclass(Object.class)
                // ElementMatchers：元素匹配器
                .method(ElementMatchers.named("toString"))
                // 拦截该方法，传入方法实现Implementation
                // 如果想修改或者定义一个方法，可以实现Implementation接口
                // 通过实现该接口，我们可以自定义方法的字节码，这是一个很强大的功能
                // 但是通常情况下我们不会这样使用，bytebuddy给我们提供了更加简单的方式：MethodDelegation（看example2）
                .intercept(FixedValue.value("Hello ByteBuddy"))
                .make()
                .load(HelloByteBuddy.class.getClassLoader())
                .getLoaded();
        // 返回的Class对象已经是被修改过的Class对象了,可以用debug默认看看当前Class对象与其他类的Class对象有何不同

        System.out.println(dynamicClass.getDeclaredConstructor().newInstance().toString());


    }
}
