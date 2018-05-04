package com.morgan.robots.acestream;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.ProtocolHandshake;


/**
 * this class aims to retrieve acestream links from redit in order to load them in acestream player
 * 
 * @author ndendele
 */
public class AceStreamRobot{

	private final WebDriver driver;
	private static final boolean DEBUG= false;
	private static final Logger LOGGER =Logger.getLogger(AceStreamRobot.class.getName());

	/**
	 * Main program entry
	 * 	 
	 */
	public static void main(String args[]){

		LOGGER.info("  ____    __    ___  _____ ______  ____     ___   ____  ___ ___      ____   ___   ____    ___   ______ \r\n" + 
				" /    |  /  ]  /  _]/ ___/|      ||    \\   /  _] /    ||   |   |    |    \\ /   \\ |    \\  /   \\ |      |\r\n" + 
				"|  o  | /  /  /  [_(   \\_ |      ||  D  ) /  [_ |  o  || _   _ |    |  D  )     ||  o  )|     ||      |\r\n" + 
				"|     |/  /  |    _]\\__  ||_|  |_||    / |    _]|     ||  \\_/  |    |    /|  O  ||     ||  O  ||_|  |_|\r\n" + 
				"|  _  /   \\_ |   [_ /  \\ |  |  |  |    \\ |   [_ |  _  ||   |   |    |    \\|     ||  O  ||     |  |  |  \r\n" + 
				"|  |  \\     ||     |\\    |  |  |  |  .  \\|     ||  |  ||   |   |    |  .  \\     ||     ||     |  |  |  \r\n" + 
				"|__|__|\\____||_____| \\___|  |__|  |__|\\_||_____||__|__||___|___|    |__|\\_|\\___/ |_____| \\___/   |__|  \r\n" + 
				"                                                                                                       ");
		LOGGER.info("\nPlease wait a few seconds...");		

		new AceStreamRobot().start();

		LOGGER.info("\nEnjoy your match! \nProvided by Acestream Robot");		
	}

	/**
	 * Default constructor
	 * 	this method does the following:
	 * - set the log off
	 * - prevent the display of the driver (behave like phantom.js) 
	 */
	AceStreamRobot() {
		Logger.getLogger(ProtocolHandshake.class.getName()).setLevel(Level.OFF);	   
		ChromeOptions chromeOptions = new ChromeOptions();
		if (!DEBUG) {
			System.setProperty("webdriver.chrome.args", "--disable-logging");
			System.setProperty("webdriver.chrome.silentOutput", "true");	
			chromeOptions.addArguments("--headless");
			chromeOptions.addArguments("--incognito");

		}
		driver = new ChromeDriver(chromeOptions);
		
	}

	/**
	 * Get the main page and search for match links
	 * 	 
	 */
	void start() {	

		//Go to the site reddit
		driver.get("https://www.reddit.com/r/soccerstreams/");
				
		LOGGER.info("Page opened");
		//Find the elements linked to the href
		List<WebElement> urlWebElementList= driver.findElements(By.tagName("a"));
			
		LOGGER.info(urlWebElementList.size()+" WebElements candidates for a link");
		//Get the url from urlWebElementList
		Set<String> urlList= new HashSet<>();
		for(WebElement e: urlWebElementList) {
			String href = e.getAttribute("href"); 
			if(href!=null && href.contains("_gmt_")){
				urlList.add(href);

			}
		}

		LOGGER.info("Number of url found: "+ urlList.size());
		for (String s: urlList) {
			scanPage(s);
		}

		driver.manage().deleteAllCookies();
		driver.close();
		driver.quit();

	}

	/**
	 * Retrieve ace-stream links from the match page
	 * 	 
	 */
	void scanPage(String url) {

		
		driver.get(url);

		String txt= (driver.findElement(By.tagName("body"))).getText();

		//Select the title of the match
	
		System.out.println("");

		System.out.println("Match: "+driver.getTitle());

		String result[]= txt.split("\n");

		for(String line: result) {
			if (line.contains("acestream://")) {
				System.out.println(" - "+line);
			}
		}

	}
	
	
}