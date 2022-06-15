package firstexample;

import java.lang.instrument.Instrumentation;

/**
 * @Author virtual
 * @Date 2022/5/31 18:37
 * @description：
 */
public class AgentApp {

    // 定义premain方法
    public static void premain(String agentOps, Instrumentation instrumentation){
        System.out.println("enter premain");
        System.out.println(agentOps);
        instrumentation.addTransformer(new Agent());
    }
}
