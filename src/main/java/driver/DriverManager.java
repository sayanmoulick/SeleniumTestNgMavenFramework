package driver;

import org.openqa.selenium.WebDriver;

public class DriverManager {
	private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

	public static WebDriver getDriver() {
		return threadLocalDriver.get();
	}

	public static void setDriver(WebDriver driver) {
		DriverManager.threadLocalDriver.set(driver);
	}
	
	public static void unload() {
		threadLocalDriver.remove();
	}
	
	public static void quit() {
        if (DriverManager.getDriver() != null){
            DriverManager.getDriver().quit();
        }
    }
}
