package stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BookingStepdefs {
    public static WebDriver driver;
    private String email;
    private String enteredName;
    private String hotelName;
    private String rating;
    private String selectedStartDates;
    private String selectedEndDates;

    protected String getSaltString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    protected void scrollToMonthContaining(String date) {
        String displayedMonth = driver.findElement(By.xpath("(//div[@class=\"bui-calendar__month\"])[2]")).getText().toLowerCase();
        while (!displayedMonth.contains(date.toLowerCase())) {
            driver.findElement(By.xpath("//div[@data-bui-ref='calendar-next']")).click();
            displayedMonth = driver.findElement(By.xpath("(//div[@class=\"bui-calendar__month\"])[2]")).getText().toLowerCase();
            if (driver.findElement(By.xpath("//div[@data-bui-ref='calendar-next']")).isDisplayed()) {
                break;
            }
        }
    }

    @Given("I am in Sign Up page")
    public void iAmInSignUpPage() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.get("https://account.booking.com/register");
        driver.findElement(By.id("onetrust-accept-btn-handler")).click();
    }

    @When("I enter valid user email")
    public void iEnterValidUserEmail() {
        this.email = getSaltString(10) + "@" + getSaltString(6) + ".com";
        driver.findElement(By.id("login_name_register")).sendKeys(this.email);
    }

    @And("click on GET STARTED button")
    public void clickOnGETSTARTEDButton() {
        driver.findElement(By.xpath("//button[@type='submit']/span")).click();
    }

    @When("I enter valid password")
    public void iEnterValidPassword() {
        String password = getSaltString(10) + "a";
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("confirmed_password")).sendKeys(password);
    }

    @And("click on Create Account button")
    public void clickOnCreateAccountButton() {
        driver.findElement(By.xpath("//button[@type='submit']/span")).click();
        driver.findElement(By.xpath("//button[@title=\"Close\"]")).click();
    }

    @And("main page is opened")
    public void mainPageIsOpened() {
        driver.findElement(By.id("profile-menu-trigger--title")).isDisplayed();
    }

    @And("I click on My Dashboard button under account menu")
    public void iClickOnButtonUnderAccountMenu() {
        driver.findElement(By.xpath("//span[contains(text(),\"Your account\")]")).click();
        driver.findElement(By.xpath("//span[contains(text(),\"Manage account\")]")).click();
        driver.findElement(By.xpath("//a[@data-trackname=\"Dashboard\"]")).click();
        //driver.findElement(By.xpath("//div[@class='profile-menu__item profile_menu__item--mydashboard']/a")).click();
    }

    @Then("My Dashboard page is opened")
    public void myDashboardPageIsOpened() {
        driver.findElement(By.xpath("//ul[@class='profile-area__nav']/li[@class='selected']/a")).isDisplayed();
    }

    @And("correct value is prefilled in email verification placeholder")
    public void correctValueIsPrefilledInEmailVerificationPlaceholder() {
        String displayedEmail = driver.findElement(By.xpath("//div[@class='email-confirm-banner__email']/input")).getAttribute("value");
        Assert.assertEquals(this.email.toUpperCase(), displayedEmail.toUpperCase());
    }

    @Given("I have account created")
    public void iHaveAccountCreated() {
        iAmInSignUpPage();
        iEnterValidUserEmail();
        clickOnGETSTARTEDButton();
        iEnterValidPassword();
        clickOnCreateAccountButton();
    }

    @When("I set up destination as {string}")
    public void iSetUpDestinationAs(String arg0) {
        driver.findElement(By.id("ss")).sendKeys(arg0);
    }

    @And("I set dates {string} - {string}")
    public void iSetDates(String arg0, String arg1) {
        this.selectedStartDates = arg0;
        this.selectedEndDates = arg1;
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        int startDay = Integer.parseInt(arg0.split("-")[0]);
        String startMonth = months[Integer.parseInt(arg1.split("-")[1]) - 1];
        String startYear = arg0.split("-")[2];
        String startDate = startDay + " " + startMonth + " " + startYear;
        int endDay = Integer.parseInt(arg1.split("-")[0]);
        String endMonth = months[Integer.parseInt(arg1.split("-")[1]) - 1];
        String endYear = arg1.split("-")[2];
        String endDate = endDay + " " + endMonth + " " + endYear;

        driver.findElement(By.xpath("//div[@class='xp__dates-inner']")).click();
        scrollToMonthContaining(startYear);
        scrollToMonthContaining(startMonth);
        driver.findElement(By.xpath("//span[@aria-label='" + startDate + "']")).click();
        scrollToMonthContaining(endYear);
        scrollToMonthContaining(endMonth);
        driver.findElement(By.xpath("//span[@aria-label='" + endDate + "']")).click();
    }

    @And("I select {string} adults and {string} children")
    public void iSelectAdultsAndChildren(String arg0, String arg1) {
        driver.findElement(By.id("xp__guests__toggle")).click();
        String currentAmount = driver.findElement(By.xpath("//div[@class=\"sb-group__field sb-group__field-adults\"]/div/div[2]/span")).getText();
        while (Integer.parseInt(currentAmount) < Integer.parseInt(arg0)) {
            driver.findElement(By.xpath("//button[@aria-label='Increase number of Adults']")).click();
            currentAmount = driver.findElement(By.xpath("//div[@class=\"sb-group__field sb-group__field-adults\"]/div/div[2]/span")).getText();
        }
        currentAmount = driver.findElement(By.xpath("(//div[@class='sb-group__field sb-group-children ']/div/div[2]/span)[1]")).getText();
        while (Integer.parseInt(currentAmount) < Integer.parseInt(arg1)) {
            driver.findElement(By.xpath("//button[@aria-label='Increase number of Children']")).click();
            currentAmount = driver.findElement(By.xpath("(//div[@class='sb-group__field sb-group-children ']/div/div[2]/span)[1]")).getText();
        }
    }

    @And("I click on Search button")
    public void iClickOnSearchButton() {
        driver.findElement(By.xpath("//div[@class='sb-searchbox-submit-col -submit-button ']/button")).click();
    }

    @And("I click on Choose your room for fist hotel in the list")
    public void iClickOnChooseYourRoomForFistHotelInTheList() throws Throwable {
        this.hotelName = driver.findElement(By.xpath("(//a[@class=\"js-sr-hotel-link hotel_name_link url\"]/span)[1]")).getText();
        this.rating = driver.findElement(By.xpath("(//div[@class = \"bui-review-score__badge\"])[1]")).getText();
        Thread.sleep(3000);
        driver.findElement(By.xpath("(//div[@class=\"room_details \"]//div[@class=\"sr-cta-button-row\"]//a//div)[1]")).click();

    }

    @And("Hotel Details page is opened for selected hotel")
    public void hotelDetailsPageIsOpenedForSelectedHotel() {
        ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(1));
        boolean isSameRating = this.rating.equals(driver.findElement(By.xpath("(//div[@class = \"bui-review-score__badge\"])[1]")).getText());
        boolean isSameName = this.hotelName.equals(driver.findElement(By.xpath("//h2[@id=\"hp_hotel_name\"]/span")).getText());
        assert isSameRating && isSameName;
    }

    @And("I click on Reserve button for recommended room")
    public void iClickOnReserveButtonForRecommendedRoom() {
        driver.findElement(By.xpath("//div[@id=\"group_recommendation\"]//td[@class=\"submitButton\"]/a")).click();
    }

    @And("I click on I'll Reserve button")
    public void iClickOnILlReserveButton() throws Throwable {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='hprt-reservation-cta']/button")).click();
    }

    @Then("Checkout page is displayed")
    public void checkoutPageIsDisplayed() {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        int startDay = Integer.parseInt(this.selectedStartDates.split("-")[0]);
        String startMonth = months[Integer.parseInt(this.selectedStartDates.split("-")[1]) - 1];
        String startYear = this.selectedStartDates.split("-")[2];
        String startDate = startMonth + " " + startDay + ", " + startYear;
        int endDay = Integer.parseInt(this.selectedEndDates.split("-")[0]);
        String endMonth = months[Integer.parseInt(this.selectedEndDates.split("-")[1]) - 1];
        String endYear = this.selectedEndDates.split("-")[2];
        String endDate = endMonth + " " + endDay + ", " + endYear;

        String displayedStartDate = driver.findElement(By.xpath("(//time[@class=\"bui-date bui-date--large\"]/span[@class=\"bui-date__title\"])[1]")).getText();
        String displayedEndDate = driver.findElement(By.xpath("(//time[@class=\"bui-date bui-date--large\"]/span[@class=\"bui-date__title\"])[2]")).getText();

        assert displayedEndDate.contains(endDate) && displayedStartDate.contains(startDate);
    }

    @And("I enter valid booking information")
    public void iEnterValidBookingInformation() {
        this.enteredName = getSaltString(6);
        driver.findElement(By.id("lastname")).sendKeys(enteredName);
    }

    @And("I click on Next: Final Details button")
    public void iClickOnNextFinalDetailsButton() {
        driver.findElement(By.xpath("//span[contains(text(),\"Final\")]")).click();
    }


    @And("Final Details page is displayed")
    public void finalDetailsPageIsDisplayed() {
        Boolean containsName = driver.findElement(By.xpath("(//div[@class = \"book-form-field field_name_full_name\"]/div)[2]")).getText().contains(this.enteredName);
        Boolean containsEmail = driver.findElement(By.xpath("(//div[@id=\"label_email\"]/div)[2]/ins")).getText().contains(this.email);
        assert containsEmail && containsName;
    }


    @And("I click on Choose your room for the cheapest hotel in the list with a rating above {string} stars")
    public void iClickOnChooseYourRoomForTheCheapestHotelInTheListWithARatingAboveStars(String arg0) throws Throwable {
        driver.findElement(By.xpath("//li[@class=\" sort_category   sort_price \"]/a")).click();
        int stars = Integer.parseInt(arg0);
        while (stars < 5) {
            stars++;
            driver.findElement(By.xpath("//div[@id=\"filter_class\"]/div/a[@data-value='" + stars + "']")).click();
        }
        iClickOnChooseYourRoomForFistHotelInTheList();

    }

    @And("I click on I'll Reserve button for the most expensive available room in the hotel")
    public void iClickOnILlReserveButtonForTheMostExpensiveAvailableRoomInTheHotel(){
        List<WebElement> priceElements = driver.findElements(By.xpath("//table[@id=\"hprt-table\"]//option[@value = \"1\"]"));
        WebElement highestPriceElement = null;
        int highestPrice = 0;
        for (WebElement element : priceElements) {
            int priceOfRoom = Integer.parseInt(element.getText().split(" ")[5].replace(")", ""));
            if (priceOfRoom > highestPrice) {
                highestPriceElement = element;
                highestPrice = priceOfRoom;
            }
        }
        highestPriceElement.click();
        WebElement reserveButton = driver.findElement(By.xpath("(//div[@class='hprt-reservation-cta']/button)[2]"));
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOf(reserveButton));
        wait.until(ExpectedConditions.elementToBeClickable(reserveButton));
        reserveButton.click();
    }

}
