import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DownloadDemo {

	WebDriver driver;
	File folder;
	
	

	@BeforeTest
	public void setup() {
		
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		
		folder=new File(UUID.randomUUID().toString());
		folder.mkdir();
		
		Map<String,Object> specs = new HashMap<String,Object>(); 
		specs.put("profile.default_content_settings.popups", 0);
		specs.put("profile.default_directory", folder.getAbsolutePath());
		options.setExperimentalOption("specs",specs);
		
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		
		driver = new ChromeDriver();
	}
	
	
	
	@Test
	public void dwnld() throws InterruptedException {
		
		driver.get("https://the-internet.herokuapp.com/download");
		driver.findElement(By.linkText("text.txt")).click();
		Thread.sleep(3000);
		File allfiles[] = folder.listFiles();
		
		for(File file : allfiles) {
			
			Assert.assertTrue(file.length()>0);
			
		}
	}
	
	
	@AfterTest
	public void tearDown() {
		
		driver.quit();
		
		File allfiles[] = folder.listFiles();
		for(File file : allfiles) {
			
			file.delete();
		}
		
		folder.delete();
	}
	
}
