import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ExtReport {

	static WebDriver driver;
	ExtentReports extent = new ExtentReports();
	ExtentSparkReporter spark = new ExtentSparkReporter(
			System.getProperty("user.dir") + "/report/AutomationReport.html" + true);

	@BeforeTest
	public void OpenBrNavTo() {
		spark.config().setTheme(Theme.DARK);
		spark.config().setDocumentTitle("AutomationReport");
		extent.attachReporter(spark);

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.google.com/");
	}

	@AfterTest
	public void endExtentReport() {
		extent.flush();
		driver.quit();
	}

	@Test(priority = 1)
	public void TC_001() {
		ExtentTest test = extent.createTest("Vefify the home page").assignAuthor("Miah")
				.assignCategory("Functional Test Case").assignDevice("Windows");
		test.info("Capturing the page title");

		String pageTitle = driver.getTitle();
		test.info("Capturing page tile as: " + pageTitle);

		if (pageTitle.equals("google")) {
			test.pass("Page title is verified : title captured " + pageTitle);
		} else {
			test.pass("Page title does not match as expected " + pageTitle);
		}
	}

	@Test(priority = 2)
	public void TC_002() throws IOException {
		ExtentTest test = extent.createTest("Vefify the edit field").assignAuthor("Miah2")
				.assignCategory("Functional Test Case").assignDevice("Windows");
		test.info("Capturing the page title");
		try {
			driver.findElement(By.xpath("//input[@name='q']")).clear();
			driver.findElement(By.xpath("//input[@name='q']")).sendKeys("Wells Fargo ATM near me");
			driver.findElement(By.xpath("(//input[@name='btnK'])[2]")).click();
			test.pass("User is on search result page");
		} catch (Exception e) {
			test.fail("unexpected errors in application : " + e.getMessage());
			test.addScreenCaptureFromPath(captureScreenshot(driver));
		}
	}

	@Test(priority = 3)
	public void clickOnSettings() {
		driver.findElement(By.xpath("//body/div[@id='searchform']/div[2]/div[1]/div[1]/div[1]/span[1]/*[1]")).click();
	}

	private String captureScreenshot(WebDriver driver) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File destinationFilePath = new File("src/../images/screenshot" + System.currentTimeMillis() + ".png");
		String absolutepathlocation = destinationFilePath.getAbsolutePath();
		FileUtils.copyFile(scrFile, destinationFilePath);
		return absolutepathlocation;
	}

}

//VDO ref:  https://www.youtube.com/watch?v=rURzzmgSAnI
