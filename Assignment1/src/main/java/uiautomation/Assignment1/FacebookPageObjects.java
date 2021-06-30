package uiautomation.Assignment1;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class FacebookPageObjects {

	static final int EXPLICIT_WAIT_TIMEOUT = 10;
	static final int EXPLICIT_POLLING_TIMEOUT = 1;
	static WebDriverWait w;
	static WebDriver driver;
	static String targeturl = "https://www.facebook.com/login/";
	static String userid = "qautomation35@gmail.com";
	static String password = "Test@123#";
	static String postcontent = "Hello World";

	public static void main(String[] args) throws FileNotFoundException, InterruptedException {

		// Code for handling notification prompts
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting_values.notifications", 2);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);

		// Initialize driver
		System.setProperty("webdriver.chrome.driver", "resources//chromedriver.exe");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get(targeturl);
		System.out.println(driver.getTitle());

		// login to facebook
		driver.findElement(By.cssSelector("#email")).sendKeys(userid);
		driver.findElement(By.cssSelector("#pass")).sendKeys(password);
		driver.findElement(By.cssSelector("#loginbutton")).click();
		

		// goto homepage
		WebElement fbhome = waitToClick(By.cssSelector("a[aria-label='Facebook']"));
		fbhome.click();
		System.out.println(driver.getTitle());
		
		// create post
		WebElement createpost = waitToClick(
				By.cssSelector("div[aria-label='Create a post'] > div:nth-of-type(1)>div:nth-of-type(1)"));
		createpost.click();
		WebElement writepost = waitToClick(
				By.cssSelector("div[aria-label*=\"What's on your mind\"] div[class='_1mf _1mj']"));
		writepost.sendKeys(postcontent);
		WebElement post = waitToClick(By.cssSelector("div[aria-label='Post']"));
		post.click();

		// wait till the post is submitted
		waitForvisblitiy(createpost);

		// add post related validations here
		System.out.println("Post Created");

		// loging out of facebook
		driver.findElement(By.cssSelector("div[aria-label='Account']")).click();
		driver.findElement(By.xpath("//span[contains(text(),'Log Out')]")).click();

		// cleanup 
		FacebookPageObjects fbo = new FacebookPageObjects();
		fbo.deletePost(); 
		driver.quit();
	}

	public static WebElement waitForvisblitiy(WebElement element) {
		w = new WebDriverWait(driver, 5);

		WebElement waitelement = w.withTimeout(Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT))
				.pollingEvery(Duration.ofSeconds(EXPLICIT_POLLING_TIMEOUT)).ignoring(NoSuchElementException.class)
				.until(ExpectedConditions.visibilityOf(element));
		return waitelement;

	}

	// fluentwait method
	public static WebElement waitToClick(By by) {

		Wait<WebDriver> fwait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT))
				.pollingEvery(Duration.ofSeconds(EXPLICIT_POLLING_TIMEOUT)).ignoring(NoSuchElementException.class);
		WebElement element = fwait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver webDriver) {
				return webDriver.findElement(by);
			}
		});
		return element;

	}
	public void deletePost() {
		// post deletion code 
	}

}
