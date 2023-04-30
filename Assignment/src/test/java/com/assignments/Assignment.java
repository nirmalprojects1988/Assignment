package com.assignments;

import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Assignment {
    private static final HashMap<String, String> CAPITALS_MAP = new HashMap<String, String>() {{
        put("Oslo", "box1");
        put("Madrid", "box7");
        put("Rome", "box6");
        put("Seoul", "box5");
        put("Copenhagen", "box4");
        put("Washington", "box3");
        put("Stockholm", "box2");
    }};

    private static final HashMap<String, String> COUNTRIES_MAP = new HashMap<String, String>() {{
        put("Norway", "box101");
        put("Italy", "box106");
        put("Spain", "box107");
        put("Denmark", "box104");
        put("South Korea", "box105");
        put("Sweden", "box102");
        put("United States", "box103");
    }};
    protected static WebDriver driver;
    protected static WebElement element;
    protected static WebElement element1;

    public static String rgbaToHex(int r, int g, int b, int a) {
        return String.format("#%02X%02X%02X%02X", r, g, b, a);
    }

    public static String rgbaToHex(String rgba) {
        String[] values = rgba.substring(5, rgba.length() - 1).split(",");
        return rgbaToHex(Integer.parseInt(values[0].trim()), Integer.parseInt(values[1].trim()),
                Integer.parseInt(values[2].trim()), Integer.parseInt(values[3].trim()));
    }

    private static void doDragnDropCapitals(String from, String to) {
        element = driver.findElement(By.xpath("//div[@class='dragableBox' and contains(text(),'" + from + "') and @id='" + CAPITALS_MAP.get(from) + "']"));
        element1 = driver.findElement(By.xpath("//div[@id='" + COUNTRIES_MAP.get(to) + "' and contains(text(),'" + to + "')]"));
        Actions actions = new Actions(driver);
        actions.clickAndHold(element).dragAndDrop(element, element1).build().perform();
        Uninterruptibles.sleepUninterruptibly(2000, TimeUnit.MILLISECONDS);
        String value = rgbaToHex(element.getCssValue("background-color"));
        System.out.println("Equivalent color string is " + value);
        if (value.equals("#00FF0001")) {
            System.out.println("Correct drag and drop done: " + from + " to " + to);
        } else {
            System.out.println("InCorrect drag and drop done: " + from + " to " + to);
        }
        System.out.println(element.getCssValue("background-color"));
    }
    private static void moveCapitalsBack() {
        System.out.println("Moving all the capitals back...");
        Actions actions = new Actions(driver);
        element1 = driver.findElement(By.xpath("//div[@id='capitals']"));
        for (String from : CAPITALS_MAP.keySet()) {
            String xpath = "//div[@id='" + CAPITALS_MAP.get(from) + "']";
            element = driver.findElement(By.xpath(xpath));
            actions.clickAndHold(element).dragAndDrop(element, element1).build().perform();
        }
    }
    public static void main(String[] args) {
        driver= new ChromeDriver();
        driver.navigate().to("http://www.dhtmlgoodies.com/scripts/drag-drop-custom/demo-drag-drop-3.html");
        driver.manage().window().maximize();
        doDragnDropCapitals("Oslo","Norway");
        doDragnDropCapitals("Rome","Italy");
        doDragnDropCapitals("Madrid","Spain");
        doDragnDropCapitals("Copenhagen","Denmark");
        doDragnDropCapitals("Seoul","South Korea");
        doDragnDropCapitals("Washington","United States");
        doDragnDropCapitals("Stockholm","Sweden");
        moveCapitalsBack();
        Uninterruptibles.sleepUninterruptibly(2000,TimeUnit.MILLISECONDS);

        //-ve use case
        doDragnDropCapitals("Rome","Sweden");
        doDragnDropCapitals("Stockholm","Spain");
        doDragnDropCapitals("Washington","South Korea");
        doDragnDropCapitals("Seoul","United States");
        doDragnDropCapitals("Oslo","Norway");
        doDragnDropCapitals("Madrid","Denmark");
        doDragnDropCapitals("Copenhagen","Italy");
        moveCapitalsBack();
        Uninterruptibles.sleepUninterruptibly(2000,TimeUnit.MILLISECONDS);
        //+ve and -ve use case
        doDragnDropCapitals("Oslo","Norway");
        doDragnDropCapitals("Stockholm","Sweden");
        doDragnDropCapitals("Washington","United States");
        doDragnDropCapitals("Rome","Italy");
        doDragnDropCapitals("Seoul","South Korea");
        doDragnDropCapitals("Madrid","Denmark");
        doDragnDropCapitals("Copenhagen","Spain");
        moveCapitalsBack();

        driver.quit();
    }
}
