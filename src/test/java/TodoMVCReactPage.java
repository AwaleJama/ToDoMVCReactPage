import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class TodoMVCReactPage {
    protected WebDriver driver;
    private By todoBoxBy = By.id("todo-input");
    private By firstTodoBy = By.xpath("//*[@class=\"todo-list\"]/li[1]/div/label");
    private By todoItemsBy = By.xpath("//*[@class=\"todo-list\"]/li/div/label");

    public TodoMVCReactPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigate() {
        driver.get("https://todomvc.com/examples/react/dist/");
    }

    public void inputToDo(String toDoItem) {
        WebElement todoBox = driver.findElement(todoBoxBy);
        todoBox.sendKeys(toDoItem);
        todoBox.sendKeys(Keys.ENTER);
    }

    public String getFirstToDoItem() {
        WebElement firstToDo = driver.findElement(firstTodoBy);
        return getFirstToDoElement().getText();
    }

    public int getLengthOfTodos() {
        return driver.findElements(todoItemsBy).size();
    }

    public WebElement getFirstToDoElement() {
        return driver.findElement(By.cssSelector(".todo-list li:first-child label"));
    }


    public void editToDo(String newToDoItem, int itemNumber) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        try {
            // Find and Double-click item
            List<WebElement> todoItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector(".todo-list label")));
            WebElement todoItem = todoItems.get(itemNumber - 1);
            new Actions(driver).doubleClick(todoItem).perform();

            // Wait for and interact with the edit field
            WebElement editInput = wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("todo-input")));
            editInput.clear();
            editInput.sendKeys(newToDoItem);
            editInput.sendKeys(Keys.ENTER);

            // Wait for the edit to complete
            wait.until(ExpectedConditions.textToBePresentInElement(
                    getFirstToDoElement(), newToDoItem));
        } catch (Exception e) {
            throw new RuntimeException("Failed to edit todo: " + e.getMessage(), e);
        }
    }
}