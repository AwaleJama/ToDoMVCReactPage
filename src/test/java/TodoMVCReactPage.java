import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
        return firstToDo.getText();
    }

    public int getLengthOfTodos() {
        return driver.findElements(todoItemsBy).size();
    }
}