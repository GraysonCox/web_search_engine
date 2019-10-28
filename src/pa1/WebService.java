package pa1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

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
			return null;
		}
		Elements links = doc.select("a[href]");
		return links.eachAttr("abs:href");
	}

	/**
	 * Downloads the webpage with the given URL and returns the text
	 * contained in the page's body.
	 * @param url
	 * @return A String containing all the text from the page's body
	 */
	public String getBodyFromPage(String url) {
		Document doc = downloadPage(url);
		if (doc == null || doc.body() == null) {
			return null;
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
			doc = Jsoup.connect(url).get();
		} catch (Exception e) {
//			e.printStackTrace(); // TODO: Better error handling
			return null;
		}
		coolDownIfNeeded();
		return doc;
	}

	private void coolDownIfNeeded() {
		requestCount = (requestCount + 1) % REQUEST_LIMIT;
		if (requestCount == 0) { // Cool down after reaching REQUEST_LIMIT
			try {
				Thread.sleep(COOL_DOWN_TIME);
			} catch (InterruptedException ignore) {

			}
		}
	}
}
