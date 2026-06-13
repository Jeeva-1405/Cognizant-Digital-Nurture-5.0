import java.util.ArrayList;
import java.util.List;

interface Observer {
    void update(String stockName, double price);
}

interface Stock {
    void registerObserver(Observer observer);
    void deregisterObserver(Observer observer);
    void notifyObservers();
}

class StockMarket implements Stock {
    private List<Observer> observers = new ArrayList<>();
    private String stockName;
    private double stockPrice;

    public void setStockPrice(String stockName, double price) {
        this.stockName = stockName;
        this.stockPrice = price;
        notifyObservers();
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
        System.out.println("Observer registered: " + observer.getClass().getSimpleName());
    }

    public void deregisterObserver(Observer observer) {
        observers.remove(observer);
        System.out.println("Observer removed: " + observer.getClass().getSimpleName());
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(stockName, stockPrice);
        }
    }
}

class MobileApp implements Observer {
    private String appName;

    public MobileApp(String appName) {
        this.appName = appName;
    }

    public void update(String stockName, double price) {
        System.out.println("[Mobile App - " + appName + "] Stock update: " + stockName + " is now $" + price);
    }
}

class WebApp implements Observer {
    private String siteName;

    public WebApp(String siteName) {
        this.siteName = siteName;
    }

    public void update(String stockName, double price) {
        System.out.println("[Web App - " + siteName + "] Stock update: " + stockName + " is now $" + price);
    }
}

public class StockMarketTest {
    public static void main(String[] args) {
        StockMarket market = new StockMarket();

        Observer mobileUser = new MobileApp("StockTracker");
        Observer webUser = new WebApp("FinancePortal");

        market.registerObserver(mobileUser);
        market.registerObserver(webUser);

        System.out.println();
        market.setStockPrice("AAPL", 182.50);

        System.out.println();
        market.setStockPrice("GOOGL", 141.80);

        System.out.println();
        market.deregisterObserver(webUser);
        market.setStockPrice("MSFT", 415.20);
    }
}
