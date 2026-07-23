import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.HashMap;
import java.util.Map;

class LoanEligibilityRequest {
    private int age;
    private double income;
    private int creditScore;

    public int getAge() { return age; }
    public double getIncome() { return income; }
    public int getCreditScore() { return creditScore; }

    public void setAge(int age) { this.age = age; }
    public void setIncome(double income) { this.income = income; }
    public void setCreditScore(int creditScore) { this.creditScore = creditScore; }
}

public class LoanEligibilityLambdaHandler
        implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final int MIN_AGE = 21;
    private static final double MIN_INCOME = 25000;
    private static final int MIN_CREDIT_SCORE = 700;

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        context.getLogger().log("Received request: " + event.getBody());

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        response.setHeaders(headers);

        try {
            LoanEligibilityRequest request = parseRequestBody(event.getBody());
            String result = evaluateEligibility(request);

            response.setStatusCode(200);
            response.setBody("{\"eligibility\":\"" + result + "\"}");
        } catch (Exception e) {
            context.getLogger().log("Error processing request: " + e.getMessage());
            response.setStatusCode(400);
            response.setBody("{\"error\":\"" + e.getMessage() + "\"}");
        }

        return response;
    }

    private LoanEligibilityRequest parseRequestBody(String body) {
        LoanEligibilityRequest request = new LoanEligibilityRequest();
        String[] pairs = body.replaceAll("[{}\"]", "").split(",");

        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            if (key.equals("age")) request.setAge(Integer.parseInt(value));
            if (key.equals("income")) request.setIncome(Double.parseDouble(value));
            if (key.equals("creditScore")) request.setCreditScore(Integer.parseInt(value));
        }

        return request;
    }

    private String evaluateEligibility(LoanEligibilityRequest request) {
        if (request.getAge() < MIN_AGE) return "REJECTED - Underage";
        if (request.getIncome() < MIN_INCOME) return "REJECTED - Insufficient income";
        if (request.getCreditScore() < MIN_CREDIT_SCORE) return "REJECTED - Low credit score";
        return "APPROVED";
    }
}
