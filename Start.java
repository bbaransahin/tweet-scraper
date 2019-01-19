package tweet_scraper;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Start {
	public static void main(String[] args) throws InterruptedException {
		int wanted_tweets = 1000;
		System.setProperty("webdriver.gecko.driver", "/home/baran/Downloads/selenium-webdriver/geckodriver");
		WebDriver drv = new FirefoxDriver();
		drv.get("https://twitter.com/trthaber");
		int count_of_tweets = 0;
		while (count_of_tweets<wanted_tweets+5) {
			drv = scroll_down(drv);
			List<WebElement> data = get_tweets(drv);
			count_of_tweets = data.size();
			System.out.println(data.size());
		}
		List<WebElement> data = get_tweets(drv);
		System.out.println(data.size());
		List<String> owner_list = get_owners(data);
		List<String> time_list = get_times(data);
		List<String> text_list = get_texts(data);
		drv.close();
	}
	public static List<WebElement> get_tweets(WebDriver drv) {
		List<WebElement> data = drv.findElements(By.className("content"));
		return data;
	}
	public static List<String> get_owners(List<WebElement> main_data) {
		List<String> data = new ArrayList<>();
		int count = 0;
		for (WebElement i : main_data) {
			if (count == main_data.size()-5) {
				break;
			}
			WebElement object1 = i.findElements(By.tagName("a")).get(0);
			WebElement object2 = object1.findElement(By.tagName("b"));
			data.add(object2.getText());
			count++;
		}
		return data;
	}
	public static List<String> get_times(List<WebElement> main_data) {
		List<String> data = new ArrayList<>();
		int count = 0;
		for (WebElement i : main_data) {
			if(count == main_data.size()-5) {
				break;
			}
			WebElement object1 = i.findElement(By.tagName("small"));
			WebElement object2 = object1.findElement(By.tagName("a"));
			data.add(object2.getAttribute("title"));
			count++;
		}
		return data;
	}
	public static List<String> get_texts(List<WebElement> main_data) {
		List<String> data = new ArrayList<>();
		int count = 0;
		for (WebElement i : main_data) {
			if (count == main_data.size()-5) {
				break;
			}
			WebElement object1 = i.findElement(By.className("js-tweet-text-container"));
			data.add(object1.getText());
			count++;
		}
		return data;
	}
	public static WebDriver scroll_down(WebDriver drv) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) drv;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(500);
		return drv;
	}
}
