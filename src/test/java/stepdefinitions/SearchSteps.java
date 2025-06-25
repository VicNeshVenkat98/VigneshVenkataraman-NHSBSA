package stepdefinitions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SearchSteps {

    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void setup() {
        String browser = System.getProperty("browser");
        if (browser == null || browser.isBlank()) {
            browser = "chrome";
        }

        switch (browser.toLowerCase()) {
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            }
            default -> throw new RuntimeException("Unsupported browser: " + browser);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    // @Before
    // public void setup() {
    //     String browser = System.getProperty("browser", "chrome");

    //     if (browser.equalsIgnoreCase("chrome")) {
    //         WebDriverManager.chromedriver().setup();
    //         driver = new ChromeDriver();
    //     } else if (browser.equalsIgnoreCase("firefox")) {
    //         WebDriverManager.firefoxdriver().setup();
    //         driver = new FirefoxDriver();
    //     } else {
    //         throw new RuntimeException("Unsupported browser: " + browser);
    //     }

    //     driver.manage().window().maximize();
    //     driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    //     wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    // }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }    

    // @Before
    // public void setup() {
    //     String browser = System.getProperty("browser", "chrome");
    //     if (browser.equalsIgnoreCase("chrome")) {
    //         WebDriverManager.chromedriver().setup();
    //         driver = new ChromeDriver();
    //         driver.manage().window().maximize();
    //     } else {
    //         WebDriverManager.firefoxdriver().setup();
    //         driver = new FirefoxDriver();
    //     }
    //     driver.manage().window().maximize();
    //     driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    // }

    // @Given("I am on the NHS Jobs search page")
    // public void i_am_on_the_nhs_jobs_search_page() {
    //     driver.get("https://www.jobs.nhs.uk/candidate/search");
    // }

    @Given("I am on the NHS Jobs search page")
    public void i_open_nhs_jobs_website() {
        // WebDriverManager.chromedriver().setup();
        // driver = new ChromeDriver();
        driver.get("https://www.jobs.nhs.uk/candidate/search");
        System.out.println("PAGE TITLE: " + driver.getTitle());
        
    }

    @When("I fill all search fields with:")
    public void i_fill_all_search_fields(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        driver.findElement(By.id("keyword")).sendKeys(data.get("keyword"));
        driver.findElement(By.id("location")).sendKeys(data.get("location"));

        // Select distance from dropdown
        Select distanceDropdown = new Select(driver.findElement(By.id("distance")));
        // distanceDropdown.selectByVisibleText(data.get("distance") + " Miles");
        distanceDropdown.selectByValue(data.get("distance")); 

        // Expand additional search options if collapsed
        driver.findElement(By.id("searchOptionsBtn")).click();

        // Optional fields
        String jobReference = data.get("jobReference");
        if (jobReference != null && !jobReference.isBlank()) {
            driver.findElement(By.id("jobReference")).sendKeys(jobReference);
        }

        driver.findElement(By.id("employer")).sendKeys(data.get("employer"));

        // Handle pay range as checkboxes, based on earlier HTML
        // String pay = data.get("payRange"); // Example: "30000 to 40000"
        // if (pay != null) {
        //     switch (pay) {
        //         case "0 to 10000" -> driver.findElement(By.id("pay-range-0-10")).click();
        //         case "10000 to 20000" -> driver.findElement(By.id("pay-range-10-20")).click();
        //         case "20000 to 30000" -> driver.findElement(By.id("pay-range-20-30")).click();
        //         case "30000 to 40000" -> driver.findElement(By.id("pay-range-30-40")).click();
        //         // Add more cases if needed
        //     }
        // }

        // Handle pay range as a dropdown
        String pay = data.get("payRange"); // Example: "30000 to 40000"
        if (pay != null) {
            WebElement payRangeDropdown = driver.findElement(By.id("payRange"));
            Select select = new Select(payRangeDropdown);

        switch (pay) {
            case "0 to 10000" -> select.selectByValue("0-10");
            case "10000 to 20000" -> select.selectByValue("10-20");
            case "20000 to 30000" -> select.selectByValue("20-30");
            case "30000 to 40000" -> select.selectByValue("30-40");
            case "40000 to 50000" -> select.selectByValue("40-50");
            case "50000 to 60000" -> select.selectByValue("50-60");
            case "60000 to 70000" -> select.selectByValue("60-70");
            case "70000 to 80000" -> select.selectByValue("70-80");
            case "80000 to 90000" -> select.selectByValue("80-90");
            case "90000 to 100000" -> select.selectByValue("90-100");
            case "100000 plus" -> select.selectByValue("100");
            default -> System.out.println("Invalid pay range: " + pay);
        }
}
    }

    @When("I click the search button")
    public void i_click_the_search_button() {
        driver.findElement(By.id("search")).click();
    }

    // @Then("I should see job results listed")
    // public void i_should_see_job_results_listed() {
    //     new WebDriverWait(driver, Duration.ofSeconds(10))
    //         .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".job-listing__details")));

    //     int jobCount = driver.findElements(By.cssSelector(".job-listing__details")).size();
    //     if (jobCount == 0) {
    //         throw new AssertionError("No job results found.");
    //     }
    // }

    @Then("I should see job results listed")
    public void i_should_see_job_results_listed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for either job results OR "no result" message
        try {
            wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[data-test='search-result-job-title']")),
                ExpectedConditions.presenceOfElementLocated(By.id("no-result-title"))
            ));

            // Check if "no result" message is present
            List<WebElement> noResult = driver.findElements(By.id("no-result-title"));
            if (!noResult.isEmpty() && noResult.get(0).isDisplayed()) {
                throw new AssertionError("No job results found. Message displayed: " + noResult.get(0).getText());
            }

            // Else, check for job results
            List<WebElement> jobResults = driver.findElements(By.cssSelector("a[data-test='search-result-job-title']"));
            if (jobResults.isEmpty()) {
                throw new AssertionError("Expected job results, but none were found.");
            } else {
                System.out.println("NUMBER OF JOB RESULTS FOUND: " + jobResults.size());
            }

        } catch (TimeoutException e) {
            throw new AssertionError("Timed out waiting for job results or 'no result' message.");
        }
        }

    @Then("the results should be sorted by newest Date Posted")
    public void the_results_should_be_sorted_by_newest_date_posted() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Select "Date Posted (newest)" from the sort dropdown
        WebElement sortDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("sort")));
        Select select = new Select(sortDropdown);
        select.selectByValue("publicationDateDesc");

        // Allow time for page to reload after sort is applied
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("li[data-test='search-result-publicationDate']")));

        // 2. Collect the posted dates from the search result items
        List<WebElement> dateElements = driver.findElements(By.cssSelector("li[data-test='search-result-publicationDate'] strong"));
        if (dateElements.size() < 2) {
            throw new AssertionError("Not enough job dates found to verify sorting.");
        }

        // 3. Parse the dates into a list of LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.UK);
        List<LocalDate> postedDates = dateElements.stream().map(el -> {
            String dateText = el.getText().trim(); // Example: "5 June 2025"
            try {
                return LocalDate.parse(dateText, formatter);
            } catch (DateTimeParseException e) {
                throw new RuntimeException("Failed to parse date: " + dateText, e);
            }
        }).collect(Collectors.toList());

        // 4. Verify the list is in descending order (newest first)
        List<LocalDate> sortedDates = new ArrayList<>(postedDates);
        sortedDates.sort(Comparator.reverseOrder());

        if (!postedDates.equals(sortedDates)) {
            throw new AssertionError("Job results are not sorted by newest Date Posted.");
        }

        System.out.println("Job results are correctly sorted by newest Date Posted.");
    }


    @When("I click the clear filters button")
    public void i_click_the_clear_filters_button() {
        driver.findElement(By.id("clearFilters")).click();
    }

    @When("I enter {string} in the What field")
    public void enterJobTitle(String jobTitle) {
        driver.findElement(By.id("keyword")).sendKeys(jobTitle);
}

     @When("I enter {string} in the Where field")
    public void i_enter_location(String location) {
        driver.findElement(By.id("location")).sendKeys(location);
    }

    @When("I select {string} from the Distance dropdown")
    public void i_select_distance(String distance) {
        Select select = new Select(driver.findElement(By.id("distance")));
        select.selectByVisibleText("+" + distance + " Miles");
    }

    @When("I enter {string} in the Job Reference field")
    public void i_enter_job_reference(String jobRef) {
        driver.findElement(By.id("jobReference")).sendKeys(jobRef);
    }

    @When("I enter {string} in the Employer field")
    public void i_enter_employer(String employer) {
        driver.findElement(By.id("employer")).sendKeys(employer);
    }

    @When("I select {string} from the Pay Range dropdown")
    public void i_select_pay_range(String payRange) {
        Select select = new Select(driver.findElement(By.id("payRange")));
        select.selectByVisibleText(payRange);
    }

    @When("I click the Search button")
    public void i_click_search() {
        driver.findElement(By.id("search")).click();
    }

    @Then("I should see jobs matching my preferences")
    public void i_should_see_matching_jobs() {
        WebElement resultsHeader = driver.findElement(By.cssSelector("h1"));
        assert resultsHeader.getText().contains("jobs") || resultsHeader.getText().contains("No jobs");
    }

    @Then("I should see the results header")
    public void i_should_see_the_results_header() {
        WebElement header = driver.findElement(By.id("search-results-heading"));
        assert header.isDisplayed();
        System.out.println("Results header displayed: " + header.getText());
}

    @Then("I should see job result titles or a no result message")
    public void i_should_see_job_result_titles_or_a_no_result_message() {
        // Check for job result titles
        List<WebElement> jobTitles = driver.findElements(By.cssSelector("a[data-test='search-result-job-title']"));

        if (!jobTitles.isEmpty()) {
            System.out.println("✅ Job results found: ");
            for (WebElement title : jobTitles) {
                System.out.println(" - " + title.getText());
            }
            assert true; // Pass
        } else {
            // Check for "no results" heading
            WebElement noResultTitle = null;
            try {
                noResultTitle = driver.findElement(By.id("no-result-title"));
            } catch (NoSuchElementException e) {
                // Neither results nor 'no results' message found: Fail
                throw new AssertionError("Neither job results nor 'no result' message found.");
            }

            // Confirm 'no result' message is displayed
            assert noResultTitle.isDisplayed() : "'No result found' message not displayed.";
            System.out.println(" No job results found.");
            System.out.println("Suggestion Tips:");
            
            // Optional: Print out improvement tips
            List<WebElement> suggestions = driver.findElements(By.cssSelector("li[data-test^='bullet-']"));
            for (WebElement tip : suggestions) {
                System.out.println(" - " + tip.getText());
            }
        }
    }

    
    @When("I apply filters like full-time, permanent, and pay range 30-40")
    public void i_apply_filters() {
        // Wait or scroll if needed depending on your environment
        driver.findElement(By.id("working-pattern-full-time")).click();
        driver.findElement(By.id("contract-type-permanent")).click();
        driver.findElement(By.id("pay-range-30-40")).click();
        driver.findElement(By.id("pay-band-4")).click();  // optional
        driver.findElement(By.id("refine-search")).click();
}

    @Then("I should see pagination controls")
    public void i_should_see_pagination() {
        WebElement nextPage = driver.findElement(By.xpath("//span[text()='Next']"));
        WebElement currentPage = driver.findElement(By.cssSelector("span.nhsuk-pagination__page"));
        assert nextPage.isDisplayed() && currentPage.getText().contains("Page");
        System.out.println("Pagination displayed: " + currentPage.getText());
}

    @Then("the results should be within the specified pay range")
    public void verifySalariesWithinSelectedRange() {
    // Find the checked pay range checkbox
        WebElement checkedPayRange = driver.findElement(By.cssSelector("input[name='payRange']:checked"));
        String selectedValue = checkedPayRange.getAttribute("value");  // e.g., "30-40" or "100"

        int lower = 0, upper = Integer.MAX_VALUE;
        if (selectedValue.contains("-")) {
            String[] parts = selectedValue.split("-");
            lower = Integer.parseInt(parts[0]) * 1000;
            upper = Integer.parseInt(parts[1]) * 1000;
        } else if (selectedValue.equals("100")) {
            lower = 100000;
        }

        // Get all salary elements from search results
        List<WebElement> salaryElements = driver.findElements(By.cssSelector("li[data-test='search-result-salary']"));
        if (salaryElements.isEmpty()) {
            throw new AssertionError("No salary information found in job listings.");
        }

        for (WebElement salaryElement : salaryElements) {
            String salaryText = salaryElement.getText();  // e.g., "Salary: £51,883 to £58,544 a year"

            // Clean and extract numbers
            String[] parts = salaryText.replaceAll("[^\\d to]", "").split("to");
            if (parts.length < 2) {
                System.out.println("Skipping unparsable salary: " + salaryText);
                continue;
            }

            try {
                int minSalary = Integer.parseInt(parts[0].trim());
                int maxSalary = Integer.parseInt(parts[1].trim());

                if (maxSalary < lower || minSalary > upper) {
                    throw new AssertionError("Job salary out of range: " + salaryText + ". Expected range: " + selectedValue);
                }
            } catch (NumberFormatException e) {
                System.out.println("Skipping invalid salary format: " + salaryText);
            }
        }

        System.out.println("ALL JOB SALARIES ARE WITHIN THE SELECTED PAY RANGE: " + selectedValue);
    }

    @When("I select working pattern {string}")
    public void i_select_working_pattern(String patternLabel) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Convert label to checkbox value and id suffix
        String value = switch (patternLabel.toLowerCase()) {
            case "full time" -> "full-time";
            case "part time" -> "part-time";
            case "job-share" -> "job-share";
            case "flexible working" -> "flexible-working";
            case "home or remote working" -> "remote-working";
            case "compressed hours" -> "compressed-hours";
            case "term time hours" -> "term-time-hours";
            case "annualised hours" -> "annualised-hours";
            default -> throw new IllegalArgumentException("Unsupported working pattern: " + patternLabel);
        };

        // Expand the "Working pattern" details section if collapsed
        WebElement summary = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("summary[data-test='filter-working-pattern']")
        ));
        if (!Boolean.parseBoolean(summary.getAttribute("aria-expanded"))) {
            js.executeScript("arguments[0].click();", summary);
        }

        // Find the checkbox input by ID
        String checkboxId = "working-pattern-" + value;
        WebElement checkboxInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(checkboxId)));

        // If already selected, skip click
        if (!checkboxInput.isSelected()) {
            // Click the label instead of checkbox for reliability
            WebElement label = driver.findElement(By.cssSelector("label[for='" + checkboxId + "']"));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", label);
            js.executeScript("arguments[0].click();", label);
        }

        System.out.println("SELECTED WORKING PATTERN: " + patternLabel);
    }



    @And("I apply the search filters")
    public void i_apply_the_search_filters() {
        WebElement applyButton = driver.findElement(By.id("refine-search"));
        applyButton.click();
    }

    @Then("I should see only jobs with working pattern {string}")
    public void i_should_see_only_jobs_with_working_pattern(String expectedPattern) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.or(
            ExpectedConditions.presenceOfElementLocated(By.cssSelector("li[data-test='search-result-workingPattern']")),
            ExpectedConditions.presenceOfElementLocated(By.id("no-result-title"))
        ));

        // If "No result" message appears, fail
        List<WebElement> noResult = driver.findElements(By.id("no-result-title"));
        if (!noResult.isEmpty() && noResult.get(0).isDisplayed()) {
            throw new AssertionError("No job results found after filtering. Message: " + noResult.get(0).getText());
        }

        // Validate working patterns in results
        List<WebElement> patterns = driver.findElements(By.cssSelector("li[data-test='search-result-workingPattern']"));
        if (patterns.isEmpty()) {
            throw new AssertionError("No working pattern info found in job listings.");
        }

        for (WebElement patternElement : patterns) {
            String actualPattern = patternElement.getText().replace("Working pattern:", "").trim().toLowerCase();
            if (!actualPattern.contains(expectedPattern.toLowerCase())) {
                throw new AssertionError("Unexpected working pattern found: '" + actualPattern +
                    "'. Expected: '" + expectedPattern + "'");
            }
        }

        System.out.println("ALL JOB LISTINGS MATCH WORKING PATTERN: " + expectedPattern);
    }

    @When("I select sort by {string}")
    public void i_select_sort_by(String sortOption) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement sortDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("sort")));

        Select select = new Select(sortDropdown);
        select.selectByVisibleText(sortOption);

        // Wait for the page to reload after onchange
        wait.until(ExpectedConditions.stalenessOf(sortDropdown));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li[data-test='search-result-closingDate']")));

        System.out.println("SELECTED SORT BY OPTION: " + sortOption);
    }

    @Then("I should see job results sorted by closing date")
    public void i_should_see_job_results_sorted_by_closing_date() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li[data-test='search-result-closingDate']")));

        List<WebElement> closingDateElements = driver.findElements(By.cssSelector("li[data-test='search-result-closingDate'] strong"));

        List<LocalDate> extractedDates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.UK);

        for (WebElement el : closingDateElements) {
            String dateText = el.getText().trim();
            LocalDate date = LocalDate.parse(dateText, formatter);
            extractedDates.add(date);
        }

        List<LocalDate> sorted = new ArrayList<>(extractedDates);
        sorted.sort(Comparator.naturalOrder());

        if (!extractedDates.equals(sorted)) {
            throw new AssertionError("Job results are not sorted by Closing Date (ascending).\nActual: " +
                    extractedDates + "\nExpected: " + sorted);
        }

        System.out.println("JOB RESULTS SORTED BY CLOSING DATE" + extractedDates);
    }

    @Then("I should see job results sorted by Date Posted newest")
    public void i_should_see_job_results_sorted_by_date_posted_newest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li[data-test='search-result-publicationDate']")));

        List<WebElement> datePostedElements = driver.findElements(By.cssSelector("li[data-test='search-result-publicationDate'] strong"));

        List<LocalDate> extractedDates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.UK);

        for (WebElement el : datePostedElements) {
            String dateText = el.getText().trim();
            LocalDate date = LocalDate.parse(dateText, formatter);
            extractedDates.add(date);
        }

        List<LocalDate> sorted = new ArrayList<>(extractedDates);
        sorted.sort(Comparator.reverseOrder()); // Newest first

        if (!extractedDates.equals(sorted)) {
            throw new AssertionError("Job results are not sorted by Date Posted (newest first).\nActual: " +
                    extractedDates + "\nExpected: " + sorted);
        }

        System.out.println("RESULTS VERIFICATION - PASSED : JOB RESULTS ARE SORTED BY DATE POSTED (NEWEST): " + extractedDates);
    }


    
}
