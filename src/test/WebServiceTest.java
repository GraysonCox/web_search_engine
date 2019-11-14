package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pa1.WebService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WebServiceTest {

	WebService webService;

//	final String testUrl = "http://crawler-test.com/";
//	final String testUrl = "https://isitchristmas.com";
//	final String testUrl = "https://cdn.britannica.com/s:300x300/77/170477-050-1C747EE3/Laptop-computer.jpg";
//	final String testUrl = "BadLink";
	final String testUrl = "https://crawler-test.com/status_codes/status_404";

	@BeforeEach
	void setUp() {
		webService = WebService.getInstance();
	}

	@Test
	void getLinksFromPage() {
		List links = webService.getLinksFromPage(testUrl);
		System.out.println(links);
	}

	@Test
	void getBodyFromPage() {
		String body = webService.getBodyFromPage(testUrl);
		System.out.println(body);
	}
}