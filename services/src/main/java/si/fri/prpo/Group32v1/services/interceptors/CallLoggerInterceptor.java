package si.fri.prpo.Group32v1.services.interceptors;

import si.fri.prpo.Group32v1.services.annotations.CallLogger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Interceptor
@CallLogger
public class CallLoggerInterceptor{

    Logger log = Logger.getLogger(CallLoggerInterceptor.class.getName());
    Map<String, Integer> calls = new HashMap<>();

    @AroundInvoke
    public Object logCall(InvocationContext context) throws Exception{
        String callingMethod = context.getMethod().getName();

        if(calls.get(callingMethod) == null) {
            calls.put(callingMethod, 1);
            log.info(callingMethod + " -> 1");
        }
        else {
            calls.put(callingMethod, calls.get(callingMethod) + 1);
            log.info(callingMethod + " -> " + calls.get(callingMethod));
        }

        return context.proceed();
    }
}
