package com.morgan.robots.acestream;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.ProtocolHandshake;



/**
 * this class aims to retrieve acestream links from redit in order to load them in acestream player
 * 
 * @author ndendele
 */
public class AceStreamRobot{

	private WebDriver driver;
	private JavascriptExecutor jse;
	private List<String> listeMatch= new ArrayList<>();
	private DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();

	/**
	 * this method creates a new  ghost driver with no log displayed in the console
	 * the drivers is connected to the website redit, deletes cookies 
	 * 
	 * @return void
	 */
	public void  invokeDriver() {
		//Remove displayed log 
		String[] phantomArgs = new  String[] {
				"--webdriver-loglevel=NONE"
		};


		capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomArgs);
		capabilities.setCapability("phantomjs.binary.path","./phantomjs.exe"); 
		Logger.getLogger(PhantomJSDriverService.class.getName()).setLevel(Level.OFF);
		Logger.getLogger(ProtocolHandshake.class.getName()).setLevel(Level.OFF);

		driver = new PhantomJSDriver(capabilities);

		driver.manage().deleteAllCookies();
		driver.get("https://www.reddit.com/r/soccerstreams/");
		driver.manage().window().maximize();

	}

	/**
	 * this method selects match listed in homePage
	 * 
	 * @return void
	 */
	public void searchObject() {
		List<WebElement> liste= driver.findElements(By.cssSelector("a.title"));
		List<String> url= new ArrayList<>();
		for(WebElement e: liste) {
			String href = e.getAttribute("href"); 
			if(href.contains("_gmt_")){
				url.add(href);
			}

		}

		// Select all line with "acestream"

		for (String s: url) {

			driver.get(s);

			String txt= (driver.findElement(By.className("commentarea"))).getText();

			//Select the title of the match
			String title= (driver.findElement(By.className("top-matter")).findElement(By.tagName("p"))).getText();
			System.out.println("");


			System.out.println( title.replaceAll(".", "-"));
			System.out.println( title);

			String result[]= txt.split("\n");

			for(String line: result) {
				if (line.contains("acestream://")) {
					System.out.println(" - "+line);
				}
			}
		}


	}
	/**
	 * this method quit the webdriver
	 * 
	 * @return void
	 */
	public void close() {
		driver.quit();

	}
	/**
	 * this method prints welcome message
	 * 
	 * @return void
	 */
	private void intro() {
		System.out.println("  ____    __    ___  _____ ______  ____     ___   ____  ___ ___      ____   ___   ____    ___   ______ \r\n" + 
				" /    |  /  ]  /  _]/ ___/|      ||    \\   /  _] /    ||   |   |    |    \\ /   \\ |    \\  /   \\ |      |\r\n" + 
				"|  o  | /  /  /  [_(   \\_ |      ||  D  ) /  [_ |  o  || _   _ |    |  D  )     ||  o  )|     ||      |\r\n" + 
				"|     |/  /  |    _]\\__  ||_|  |_||    / |    _]|     ||  \\_/  |    |    /|  O  ||     ||  O  ||_|  |_|\r\n" + 
				"|  _  /   \\_ |   [_ /  \\ |  |  |  |    \\ |   [_ |  _  ||   |   |    |    \\|     ||  O  ||     |  |  |  \r\n" + 
				"|  |  \\     ||     |\\    |  |  |  |  .  \\|     ||  |  ||   |   |    |  .  \\     ||     ||     |  |  |  \r\n" + 
				"|__|__|\\____||_____| \\___|  |__|  |__|\\_||_____||__|__||___|___|    |__|\\_|\\___/ |_____| \\___/   |__|  \r\n" + 
				"                                                                                                       ");

	}

	/**
	 * Main program entry
	 * @param args : program arguments (currently un 
	 * @return void
	 */
	public static void main(String[] args) {
		AceStreamRobot robot = new AceStreamRobot();
		robot.intro();
		System.out.println("Please wait, searching for links...");
		robot.invokeDriver();
		robot.searchObject();
		robot.close();
		System.out.println("\nEnjoy your match! \nProvided by Acestream Robot");
	}

}

