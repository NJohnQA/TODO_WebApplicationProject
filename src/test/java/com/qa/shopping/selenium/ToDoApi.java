package com.qa.shopping.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;



import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ToDoApi {
	private WebDriver driver;

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1366, 768));
        driver.get("http://localhost:8082/");

    }
    
    @Test
    public void testHome() throws InterruptedException {
        
        assertEquals("Home", driver.getTitle());
        
    }
    @Test
    public void CreateTestPage() throws InterruptedException {
        WebElement createpage = driver.findElement(By.id("Create an item record"));
        createpage.click();
        assertEquals("Create a new record", driver.getTitle());
    }
    
    @Test
    public void UpdateTestPage() throws InterruptedException {
        WebElement updateButton = driver.findElement(By.id("submit"));
        updateButton.click();
        assertEquals("submit", driver.getTitle());
    }
    @Test
    public void CreateTestItem() throws InterruptedException {
        WebElement create = driver.findElement(By.id("Create an Item"));
        create.click();
        WebElement ItemId = driver.findElement(By.id("commentItemId"));
        int id = "1";
        ItemId.sendKeys(id);
        WebElement Cat = driver.findElement(By.id("commentCategory"));
        String cat = "food";
        Cat.sendKeys(cat);
        WebElement item = driver.findElement(By.id("commentItem"));
        String item = "food";
        item.sendKeys(item);
        WebElement quan = driver.findElement(By.id("commentQuantity"));
        int quan = "1";
        quan.sendKeys(quan);
        WebElement submitBut = driver.findElement(By.id("sub"));
        subBut.click();
        driver.switchTo().alert().dismiss();
    }

    @Test
    public void CreateTestShop() throws InterruptedException {
        WebElement create = driver.findElement(By.id("Create a Shop"));
        create.click();
        WebElement shopId = driver.findElement(By.id("commentShopId"));
        int id = "1";
        shopId.sendKeys(id);
        WebElement Cat = driver.findElement(By.id("commentCategory"));
        String cat = "food";
        Cat.sendKeys(cat);
        WebElement shop = driver.findElement(By.id("commentShop"));
        String shop = "asda";
        hop.sendKeys(shop);
        WebElement sub = driver.findElement(By.id("sub"));
        sub.click();
        driver.switchTo().alert().dismiss();
    }
    
    @Test
    public void testUpdateItemTest() throws InterruptedException {
    	WebElement create = driver.findElement(By.id("Create an Item"));
        create.click();
        WebElement ItemId = driver.findElement(By.id("commentItemId"));
        int id = "1";
        ItemId.sendKeys(id);
        WebElement Cat = driver.findElement(By.id("commentCategory"));
        String cat = "game";
        Cat.sendKeys(cat);
        WebElement item = driver.findElement(By.id("commentItem"));
        String item = "COD";
        item.sendKeys(item);
        WebElement quan = driver.findElement(By.id("commentQuantity"));
        int quan = "1";
        quan.sendKeys(quan);
        WebElement submitBut = driver.findElement(By.id("sub"));
        subBut.click();
        driver.switchTo().alert().dismiss();
    }
    
    @Test
    public void testUpdateShopTest() throws InterruptedException {
    	   WebElement create = driver.findElement(By.id("Create a Shop"));
           create.click();
           WebElement shopId = driver.findElement(By.id("commentShopId"));
           int id = "1";
           shopId.sendKeys(id);
           WebElement Cat = driver.findElement(By.id("commentCategory"));
           String cat = "game";
           Cat.sendKeys(cat);
           WebElement shop = driver.findElement(By.id("commentShop"));
           String shop = "argos";
           hop.sendKeys(shop);
           WebElement sub = driver.findElement(By.id("sub"));
           sub.click();
           driver.switchTo().alert().dismiss();
    }
    
    @Test
    public void testReadAll() throws InterruptedException {
        WebElement sub = driver.findElement(By.id("readAllButton"));
        sub.click();
    }
    
    
    @AfterEach
    public void tearDown() {
        driver.close();
    }

}
