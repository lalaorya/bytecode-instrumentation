package secordexample;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * @Author virtual
 * @Date 2022/6/14 0:26
 * @description：通过MethodDelegation动态的自定义方法的字节码
 * MethodDelegation可以将方法委托给任何的pojo,这个pojo可以使接口实现类，普通类，匿名内部类，但必须是public的
 *
 * 可能的报错：https://stackoverflow.com/questions/34756496/interceptor-class-visibility-in-byte-buddy
 *
 */
public class MethodDelegationExample {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<? extends Function> dynamicType = new ByteBuddy()
                .subclass(Function.class)
                .method(ElementMatchers.named("apply"))
                .intercept(MethodDelegation.to(new MyMethodInterceptor()))
                .make()
                .load(MethodDelegationExample.class.getClassLoader())
                .getLoaded();

        System.out.println(dynamicType.getDeclaredConstructor().newInstance().apply("Byte Buddy"));
    }


    // 这个类必须是public的，不然会有报错 ~ is not visible to class ~
    public static class MyMethodInterceptor{

        public MyMethodInterceptor() {
        }

        public Object greet(Object argument){
            return "hello from " + argument;

        }
    }

}
