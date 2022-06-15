package firstexample;

//import javassist.* ;
import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @Author virtual
 * @Date 2022/6/5 11:01
 * @description：
 */
public class Agent implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            // 字节码的全类名使用/而不是.进行分隔，所以这里需要转换回来
            String loadClassName = className.replaceAll("/", ".");
            CtClass ctClass = ClassPool.getDefault().get(loadClassName);
            CtMethod ctMethod = ctClass.getDeclaredMethod("hello");
            // 增强hello方法
            if(ctMethod != null){
                ctMethod.addLocalVariable("_begin", CtClass.longType);
                ctMethod.insertBefore("_begin = System.nanoTime()");
                ctMethod.insertAfter("System.out.println(System.nanoTime()-_begin);");
                System.out.println(className);
                return ctClass.toBytecode();

            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classfileBuffer;

    }
}
