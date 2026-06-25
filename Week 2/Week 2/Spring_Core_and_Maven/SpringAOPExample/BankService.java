import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
class BankService {
    public double withdraw(String account, double amount) {
        System.out.println("Withdrawing Rs." + amount + " from account " + account);
        return amount;
    }

    public void transfer(String fromAccount, String toAccount, double amount) {
        System.out.println("Transferring Rs." + amount + " from " + fromAccount
                + " to " + toAccount);
    }
}

@Aspect
@Component
class LoggingAspect {
    @Around("execution(* BankService.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("[BEFORE] " + methodName + " invoked");

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsed = System.currentTimeMillis() - start;

        System.out.println("[AFTER] " + methodName + " completed in " + elapsed + "ms");
        return result;
    }
}

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackageClasses = BankService.class)
class BankConfig {
}

public class BankServiceTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(BankConfig.class);

        BankService bank = context.getBean(BankService.class);

        bank.withdraw("ACC-1001", 5000.00);
        System.out.println();
        bank.transfer("ACC-1001", "ACC-2002", 2500.00);

        context.close();
    }
}
