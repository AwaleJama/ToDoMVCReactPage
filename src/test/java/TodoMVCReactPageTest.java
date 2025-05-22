import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TodoMVCReactPageTest {
    private static ChromeDriver driver;

    @BeforeAll
    static void launchBrowser() {
        driver = new ChromeDriver();
    }

    @AfterAll
    static void closeBrowser() {
        driver.quit();
    }

    @Test
    // TC-1
    public void shouldAddItemToTheToDoList() {
        TodoMVCReactPage searchPage = new TodoMVCReactPage(driver);
        searchPage.navigate();
        searchPage.inputToDo("Buy Sweets");
        assertEquals(searchPage.getFirstToDoItem(), "Buy Sweets");
    }

    @Test
    // TC-2
    public void shouldAddEmptyItemToTheToDoList() {
        TodoMVCReactPage searchPage = new TodoMVCReactPage(driver);
        searchPage.navigate();
        searchPage.inputToDo("");
        assertEquals(searchPage.getLengthOfTodos(), 0);
    }

    @Test
    // TC-3
    public void shouldAddTwoCharactersToTheToDoList() {
        TodoMVCReactPage searchPage = new TodoMVCReactPage(driver);
        searchPage.navigate();
        searchPage.inputToDo("AB");
        assertEquals(searchPage.getFirstToDoItem(), "AB");

    }

    @Test
    // TC-4
    public void shouldAddOneCharacterToTheToDoList() {
        TodoMVCReactPage searchPage = new TodoMVCReactPage(driver);
        searchPage.navigate();
        searchPage.inputToDo("A");
        assertEquals(searchPage.getLengthOfTodos(), 0);

    }

    @Test
    // TC-5
    // & symbol is a bug
    public void shouldAddSpecialCharactersToTheToDoList() {
        TodoMVCReactPage searchPage = new TodoMVCReactPage(driver);
        searchPage.navigate();
        searchPage.inputToDo("@£%*!^?~");
        assertEquals(searchPage.getFirstToDoItem(), "@£%*!^?~");

    }

    @Test
    // TC-6
    // Had to use Basic Multilingual Plane (BMP) safe keys, failed when using non-BMP emojis
    public void shouldAddEmojisToTheToDoList() {
        TodoMVCReactPage searchPage = new TodoMVCReactPage(driver);
        searchPage.navigate();
        searchPage.inputToDo("☺☺");
        assertEquals(searchPage.getFirstToDoItem(), "☺☺");
    }

    @Test
    // TC-7
    public void shouldAddMultipleItemsToTheToDoList() {
        TodoMVCReactPage searchPage = new TodoMVCReactPage(driver);
        searchPage.navigate();
        searchPage.inputToDo("Buy Sweets");
        searchPage.inputToDo("Eat Sweets");
        searchPage.inputToDo("Throw away wrapper");
        searchPage.inputToDo("Remove wrapper from Food bin");
        searchPage.inputToDo("Throw wrapper into Recycling bin");
        assertEquals(searchPage.getLengthOfTodos(), 5);
    }

    @Test
    // TC-8
    public void shouldEditExistingToDoItem() {
        TodoMVCReactPage page = new TodoMVCReactPage(driver);
        page.navigate();

        // Add initial item
        page.inputToDo("Hello");
        assertEquals("Hello", page.getFirstToDoItem());

        // Edit the item
        page.editToDo("HelloThere", 1);

        // Ensure window is still open
        try {
            driver.getTitle();
        } catch (Exception e) {
            fail("Browser window closed unexpectedly: " + e.getMessage());
        }

        // Verify edit
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        assertEquals("HelloThere", page.getFirstToDoItem()); // Direct assertion (editToDo already waited)
    }

    @Test
    // TC-12
    public void shouldDeleteitemFromToDoList() {
        TodoMVCReactPage searchPage = new TodoMVCReactPage(driver);
        searchPage.navigate();
        searchPage.inputToDo("Buy Sweets");
        assertEquals(searchPage.getFirstToDoItem(), "Buy Sweets");
        WebElement todoItem = driver.findElement(By.cssSelector(".todo-list li"));
        new Actions(driver).moveToElement(todoItem).perform();
        WebElement deleteButton = driver.findElement(By.cssSelector(".todo-list li .destroy"));
        deleteButton.click();
    }

    @Test
    // TC-25
    public void shouldAddAccentedCharactersToTheToDoList() {
        TodoMVCReactPage searchPage = new TodoMVCReactPage(driver);
        searchPage.navigate();
        searchPage.inputToDo("éñ");
        assertEquals(searchPage.getFirstToDoItem(), "éñ");

    }
}