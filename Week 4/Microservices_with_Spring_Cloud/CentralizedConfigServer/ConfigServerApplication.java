import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}

@RestController
@RefreshScope
class FeatureFlagController {

    @Value("${feature.discount-engine.enabled:false}")
    private boolean discountEngineEnabled;

    @Value("${feature.max-loan-amount:500000}")
    private double maxLoanAmount;

    @GetMapping("/config/feature-flags")
    public String getFeatureFlags() {
        return "discountEngineEnabled=" + discountEngineEnabled
            + ", maxLoanAmount=" + maxLoanAmount;
    }
}
