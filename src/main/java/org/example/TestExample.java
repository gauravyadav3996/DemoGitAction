package org.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.Parameterized.AfterParam;
import org.junit.runners.Parameterized.BeforeParam;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class TestExample {
	// Shared between all tests in this class.
	static Playwright playwright;
	static Browser browser;

	// New instance for each test method.
	BrowserContext context;
	Page page;

	@Before
	public void before() {
		context = browser.newContext();
		page = context.newPage();
		System.out.println("Before");
	}

	@BeforeClass
	public static void BeforeClass() {
		playwright = Playwright.create();
		browser = playwright.chromium().launch();

		System.out.println("BeforeClass");
	}

	@BeforeParam
	public void BeforeParam() {
		System.out.println("BeforeParam");
	}

	// @Test
	// public void test() {
	// System.out.println("Test");
	// }

	@Test
	public void shouldClickButton() {
		page.navigate("data:text/html,<script>var result;</script><button onclick='result=\"Clicked\"'>Go</button>");
		page.locator("button").click();
		assertEquals("Clicked", page.evaluate("result"));
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ shouldClickButton");
	}

	@Test
	public void shouldCheckTheBox() {
		page.setContent("<input id='checkbox' type='checkbox'></input>");
		page.locator("input").check();
		assertTrue((Boolean) page.evaluate("() => window['checkbox'].checked"));
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ shouldCheckTheBox");
	}

	@Test
	public void shouldSearchWiki() {
		page.navigate("https://www.wikipedia.org/");
		page.locator("input[name=\"search\"]").click();
		page.locator("input[name=\"search\"]").fill("playwright");
		page.locator("input[name=\"search\"]").press("Enter");
		assertEquals("https://en.wikipedia.org/503.html", page.url());
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ shouldSearchWiki");

	}

	@After
	public void After() {
		context.close();
		System.out.println("After");
	}

	@AfterClass
	public static void AfterClass() {
		playwright.close();
		System.out.println("AfterClass");
	}

	@AfterParam
	public void AfterParam() {
		System.out.println("AfterParam");
	}
}