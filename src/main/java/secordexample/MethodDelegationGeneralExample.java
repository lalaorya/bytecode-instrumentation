package secordexample;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * @Author virtual
 * @Date 2022/6/14 9:38
 * @description：使用MethodDelegation动态的自定义一个通用方法的字节码，可用于拦截所有函数
 */
public class MethodDelegationGeneralExample {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<? extends Function> dynamicType = new ByteBuddy()
                .subclass(Function.class)
                .method(ElementMatchers.named("apply"))
                .intercept(MethodDelegation.to(new GeneralMethodInterceptor()))
                // 方式二：匿名内部类,这种方式会报错，因为匿名内部类默认是private私有的，可以通过接口实现解决
//                .intercept(MethodDelegation.to(new Object(){
//                    @RuntimeType
//                    public Object intercept(@Origin Method m, @AllArguments Object[] argument){
//                        return null;
//                    }
//                }))
                .make()
                .load(MethodDelegationExample.class.getClassLoader())
                .getLoaded();

        System.out.println(dynamicType.getDeclaredConstructor().newInstance().apply("Byte Buddy"));
    }

    public static class GeneralMethodInterceptor{
        @RuntimeType
        public Object intercept(@Origin Method m, @AllArguments Object[] argument){
            return null;
        }
    }
}
