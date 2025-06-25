
# NHS Job Search Automation Test Suite

This project is a functional test automation suite for the NHS Jobs website, built using:
-Java
- Cucumber (BDD)
- Selenium WebDriver
- JUnit
- WebDriverManager

# Project Structure

```
nhs-test/
│
├── src/
│   ├── main/
│   │   └── java/              
│   └── test/
│       ├── java/
│       │   └── stepdefinitions/   # Step definitions for Cucumber
│       └── resources/
│           └── features/         # Cucumber features
│
├── pom.xml                      # Maven config
└── README.md                    
```

# Prerequisites

- Java 21 installed and `JAVA_HOME` configured
- Maven installed (`mvn -v` should work)
- Internet connection (for downloading dependencies)

# Running Tests from Command Line

cd /pom-file-path

# To Run in Chrome(default browser):

mvn clean test

mvn clean test -Dbrowser=chrome


# To Run in Firefox:

mvn clean test -Dbrowser=firefox


# Notes

- Browser drivers are managed automatically via WebDriverManager — no need to manually install or configure ChromeDriver/GeckoDriver.
- The browser window will open, maximize, run the scenario(s), and close on completion.
- The project follows BDD practices using Gherkin syntax in `*.feature` files.
- All tests are implemented in `src/test/java/stepdefinitions/SearchSteps.java`.

# Sample Feature Scenarios

You’ll find these scenarios automated:
- Opening NHS Jobs site
- Searching with filters
- Applying working pattern filters
- Verifying results sorted by closing date
- Verifying results sorted by newest Job Posted date

# Troubleshooting

- If no browser opens or tests fail immediately:
  - Check if `JAVA_HOME` and `Maven` are correctly set.
  - Ensure you're not passing an empty `-Dbrowser=` value.
  - Use `mvn clean test -e` to see detailed errors.

# Author

Vignesh Venkataraman
