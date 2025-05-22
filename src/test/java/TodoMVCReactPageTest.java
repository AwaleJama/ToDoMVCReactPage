import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}