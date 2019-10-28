package pa1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class WebService {

	private static WebService instance = null;

	private final int REQUEST_LIMIT = 50;
	private final int COOL_DOWN_TIME = 3000;

	private int requestCount;

	private WebService() {
		requestCount = 0;
	}

	public static WebService getInstance() {
		if (instance == null) {
			instance = new WebService();
		}
		return instance;
	}

	/**
	 * Downloads the webpage with the given URL and returns a list
	 * of all the links contained in the page.
	 * @param url
	 * @return A List<String> containing all links found in the page
	 */
	public List<String> getLinksFromPage(String url) {
		Document doc = downloadPage(url);
		if (doc == null) {
			return new ArrayList<>();
		}
		Elements links = doc.select("a[href]");
		return links.eachAttr("abs:href");
	}

	/**
	 * Downloads the webpage with the given URL and returns the text
	 * contained in the page's body.
	 * @param url
	 * @return A String containing all the text from the page's body
	 * or an empty string if the page has no body
	 */
	public String getBodyFromPage(String url) {
		Document doc = downloadPage(url);
		if (doc == null || doc.body() == null) {
			return "";
		}
		return doc.body().text();
	}

	/**
	 * This is the single point of failure for internet access.
	 * This downloads and returns the page at the specified URL and
	 * waits on a cool-down if the request limit has been reached.
	 * @param url
	 * @return The Document object
	 */
	private Document downloadPage(String url) {
		Document doc = null;
		try {
			System.out.println("Connecting to " + url + " ...");
			doc = Jsoup.connect(url).get();
		} catch (Exception e) {
			System.err.println("Couldn't connect to " + url); // TODO: Better error handling
			return null;
		}
		coolDownIfNeeded();
		return doc;
	}

	/**
	 * Increments requestCount and triggers a cooldown if REQUEST_LIMIT has been reached
	 */
	private void coolDownIfNeeded() {
		requestCount = (requestCount + 1) % REQUEST_LIMIT;
		if (requestCount == 0) { // Cool down after reaching REQUEST_LIMIT
			try {
				System.out.println("Cool down for " + COOL_DOWN_TIME/1000 + " seconds ...");
				Thread.sleep(COOL_DOWN_TIME);
			} catch (InterruptedException ignore) {
				System.err.println("Cool down interrupted");
			}
		}
	}
}
