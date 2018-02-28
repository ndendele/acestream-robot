package com.morgan.robots.acestream;

import java.util.ArrayList;
import java.util.List;
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
	
	/**
	 * Main program entry
	 * 	 
	 */
	public static void main(String args[]){

		System.out.println("  ____    __    ___  _____ ______  ____     ___   ____  ___ ___      ____   ___   ____    ___   ______ \r\n" + 
				" /    |  /  ]  /  _]/ ___/|      ||    \\   /  _] /    ||   |   |    |    \\ /   \\ |    \\  /   \\ |      |\r\n" + 
				"|  o  | /  /  /  [_(   \\_ |      ||  D  ) /  [_ |  o  || _   _ |    |  D  )     ||  o  )|     ||      |\r\n" + 
				"|     |/  /  |    _]\\__  ||_|  |_||    / |    _]|     ||  \\_/  |    |    /|  O  ||     ||  O  ||_|  |_|\r\n" + 
				"|  _  /   \\_ |   [_ /  \\ |  |  |  |    \\ |   [_ |  _  ||   |   |    |    \\|     ||  O  ||     |  |  |  \r\n" + 
				"|  |  \\     ||     |\\    |  |  |  |  .  \\|     ||  |  ||   |   |    |  .  \\     ||     ||     |  |  |  \r\n" + 
				"|__|__|\\____||_____| \\___|  |__|  |__|\\_||_____||__|__||___|___|    |__|\\_|\\___/ |_____| \\___/   |__|  \r\n" + 
				"                                                                                                       ");
		System.out.println("\nPlease wait a few seconds...");		

		new AceStreamRobot().start();

		System.out.println("\nEnjoy your match! \nProvided by Acestream Robot");		
	}
	
	/**
	 * Default constructor
	 * 	 
	 */
	AceStreamRobot() {
		Logger.getLogger(ProtocolHandshake.class.getName()).setLevel(Level.OFF);	   
		System.setProperty("webdriver.chrome.args", "--disable-logging");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--headless");
		driver = new ChromeDriver(chromeOptions);
	}
	
	/**
	 * Get the main page and search for match links
	 * 	 
	 */
	void start() {	

		driver.get("https://www.reddit.com/r/soccerstreams/");

		List<WebElement> liste= driver.findElements(By.cssSelector("a.title"));
		List<String> url= new ArrayList<>();

		for(WebElement e: liste) {
			String href = e.getAttribute("href"); 
			if(href.contains("_gmt_")){
				url.add(href);
			}
		}

		for (String s: url) {
			scanPage(s);
		}

		driver.quit();

	}
	
	/**
	 * Retrieve ace-stream links from the match page
	 * 	 
	 */
	void scanPage(String s) {

		driver.get(s);

		String txt= (driver.findElement(By.className("commentarea"))).getText();

		//Select the title of the match
		String title= (driver.findElement(By.className("top-matter")).findElement(By.tagName("p"))).getText().replaceAll("\n","");
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