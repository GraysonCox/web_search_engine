package pa1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a singleton class that serves as the only point of
 * communication between this program and the JSoup library.
 * That's modularity, baby.
 *
 * @author Grayson Cox
 */
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
	 * Downloads the web page with the given URL and returns a list
	 * of all the links contained in the page.
	 *
	 * @param url The URL of the page from which the links will be retrieved
	 * @return A List containing all links found in the page
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
	 * Downloads the web page with the given URL and returns the text
	 * contained in the page's body.
	 *
	 * @param url The URL of the page from which the body will be retrieved
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
	 *
	 * @param url The URL of the page to download
	 * @return The Document object
	 */
	private Document downloadPage(String url) {
		try {
			System.out.println("Connecting to " + url + " ...");
			Document doc = Jsoup.connect(url).get();
			coolDownIfNeeded();
			return doc;
		} catch (Exception e) {
			System.err.println("Couldn't connect to " + url);
			return null;
		}
	}

	/**
	 * Increments requestCount and triggers a cooldown if REQUEST_LIMIT has been reached
	 */
	private void coolDownIfNeeded() {
		requestCount = (requestCount + 1) % REQUEST_LIMIT;
		if (requestCount == 0) { // Cool down after reaching REQUEST_LIMIT
			try {
				System.out.println("Cool down for " + COOL_DOWN_TIME / 1000 + " seconds ...");
				Thread.sleep(COOL_DOWN_TIME);
			} catch (InterruptedException ignore) {
				System.err.println("Cool down interrupted");
			}
		}
	}
}
