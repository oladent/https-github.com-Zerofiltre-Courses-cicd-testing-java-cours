package tech.zerofiltre.testing.calcul.e2e;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MultiplicationJourneyE2ETest {

  @LocalServerPort
  private int port;

  private WebDriver webDriver;
  private String baseUrl;

  @BeforeAll
  static void setUpFireFoxDriver() {
    WebDriverManager.firefoxdriver().setup();
  }

  @BeforeEach
  void setUpWebDriver() {
    webDriver = new FirefoxDriver();
    baseUrl = "http://localhost:" + port + "/calculator";

  }

  @AfterEach
  void quitWebDriver() {
    if (webDriver != null) {
      webDriver.quit();
    }
  }

  @Test
  void multiplyTwoBySixteenMustReturn32(){

    //GIVEN
    webDriver.get(baseUrl);
    WebElement leftField = webDriver.findElement(By.id("left"));
    WebElement typeDropDown = webDriver.findElement(By.id("type"));
    WebElement rightField = webDriver.findElement(By.id("right"));
    WebElement submitButton = webDriver.findElement(By.id("submit"));

    //WHEN
    leftField.sendKeys("2");
    typeDropDown.sendKeys("x");
    rightField.sendKeys("16");
    submitButton.click();

    //THEN
    WebDriverWait waiter = new WebDriverWait(webDriver,5);
    WebElement solutionElement = waiter.until(ExpectedConditions.presenceOfElementLocated(By.id("solution")));
    String solution = solutionElement.getText();
    assertThat(solution).isEqualTo("32");
  }


}
