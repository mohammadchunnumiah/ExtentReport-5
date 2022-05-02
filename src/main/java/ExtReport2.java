import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ExtReport2 {

	private static final String CODE1 = "{\n    \"theme\": \"standard\",\n    \"encoding\": \"utf-8\n}";
	private static final String CODE2 = "{\n    \"protocol\": \"HTTPS\",\n    \"timelineEnabled\": false\n}";

	@Test
	public static void openBrNavTo() throws ClassNotFoundException {
		ExtentReports extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter(System.getProperty("user.dir")+"/report/AutomationReport.html");
		extent.attachReporter(spark);

		extent.createTest("ScreenCapture").addScreenCaptureFromPath("extent.png")
				.pass(MediaEntityBuilder.createScreenCaptureFromPath("extent.png").build());

		extent.createTest("LogLevels").info("info").pass("pass").warning("warn").skip("skip").fail("fail");

		extent.createTest("CodeBlock").generateLog(Status.PASS, MarkupHelper.createCodeBlock(CODE1, CODE2));

		extent.createTest("ParentWithChild").createNode("Child")
				.pass("This test is created as a toggle as part of a child test of 'ParentWithChild'");

		extent.createTest("Tags").assignCategory("MyTag")
				.pass("The test 'Tags' was assigned by the tag <span class='badge badge-primary'>MyTag</span>");

		extent.createTest("Authors").assignAuthor("Miah")
				.pass("This test 'Authors' was assigned by a special kind of author tag.");

		extent.createTest("Devices").assignDevice("Desktop")
				.pass("This test 'Devices' was assigned by a special kind of devices tag.");

		extent.createTest("Exception! <i class='fa fa-frown-o'></i>")
				.fail(new RuntimeException("A runtime exception occurred!"));

		extent.flush();

	}

}
