package org.example;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.nio.file.Path;
import java.util.regex.Pattern;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Playwright.CreateOptions;
import com.microsoft.playwright.options.AriaRole;

public class Demo {

	public static void main(String[] args) {
		CreateOptions createoptions = new CreateOptions();
		Playwright playwright = Playwright.create(createoptions);
		BrowserType browserType = playwright.chromium();
		//		Browser browser = browserType.launch();
		Browser browser = browserType
				.launch(new BrowserType.LaunchOptions().setHeadless(false).setChannel("chrome").setSlowMo(3000));
		Page page = browser.newPage();
		//		Page page = browser.newContext().newPage();

		page.navigate("https://playwright.dev/");
		page.screenshot(new Page.ScreenshotOptions()
				.setPath(Path.of(System.getProperty("user.dir") + "/screenshot/navigate playwright example.png")));

		// get current url
		//		System.out.println("***********" + page.url());
		//		// get the whole html body
		//		System.out.println("***********" + page.content());
		//		// get title
		System.out.println("***********" + page.title());

		//		page.getByPlaceholder("Search Google or type a URL").fill("Gaurav");
		//		page.locator("Stay signed out").click();
		//		page.getByTestId("APjFqb").fill("Guarav");

		assertThat(page).hasURL(Pattern.compile("playwright"));

		Locator getStarted = page.locator("text=Get Started");
		assertThat(getStarted).hasAttribute("href", "/docs/intro");
		getStarted.click();
		page.screenshot(new Page.ScreenshotOptions()
				.setPath(Path.of(System.getProperty("user.dir") + "/screenshot/get started example.png")));

		Locator loc2 = page.locator("//a[text()='Writing tests']");
		loc2.click();
		page.screenshot(new Page.ScreenshotOptions()
				.setPath(Path.of(System.getProperty("user.dir") + "/screenshot/Writing tests example.png")));

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		page.close();
		browser.close();
		playwright.close();
		System.out.println("END");

	}

	public static void runSamplePlaywrightCode() {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium().launch();
			Page page = browser.newPage();
			page.navigate("http://playwright.dev");

			// Expect a title "to contain" a substring.
			assertThat(page).hasTitle(Pattern.compile("Playwright"));

			// create a locator
			Locator getStarted = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Get Started"));

			// Expect an attribute "to be strictly equal" to the value.
			assertThat(getStarted).hasAttribute("href", "/docs/intro");

			// Click the get started link.
			getStarted.click();

			// Expects page to have a heading with the name of Installation.
			assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Installation")))
			.isVisible();
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ END @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		}
	}
}