package si.fri.prpo.Group32v1.services.interceptors;

import si.fri.prpo.Group32v1.services.annotations.CallLogger;
import si.fri.prpo.Group32v1.services.annotations.ValidateConsultationDto;
import si.fri.prpo.Group32v1.services.dtos.ConsultationDto;
import si.fri.prpo.Group32v1.services.exceptions.InvalidConsultationDtoException;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

@Interceptor
@ValidateConsultationDto
public class ValidateConsultationDtoInterceptor {

    Logger log = Logger.getLogger(ValidateConsultationDtoInterceptor.class.getName());

    @AroundInvoke
    public Object validateConsultation(InvocationContext context) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        if(context.getParameters().length > 0 && context.getParameters()[0] instanceof ConsultationDto) {

            ConsultationDto consultation = (ConsultationDto) context.getParameters()[0];

            Date start = df.parse(consultation.getTimeStart());
            Date end = df.parse(consultation.getTimeEnd());

            if(start.after(end)) {
                String msg = "Given consultation start time is after end time, please fix the times";
                log.severe(msg);
                throw new InvalidConsultationDtoException(msg);
            }
        }
        return context.proceed();
    }
}
