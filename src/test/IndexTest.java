package test;

import api.TaggedVertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pa1.Index;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IndexTest {

	Index index;

	String testUrls[] = {
			"https://www.spacejam.com/archive/spacejam/movie/jam.html",
			"https://www.spacejam.com/archive/spacejam/movie/cmp/pressbox/pressboxframes.html",
			"https://www.spacejam.com/archive/spacejam/movie/cmp/jamcentral/jamcentralframes.html",
			"https://www.spacejam.com/archive/spacejam/movie/cmp/bball/bballframes.html",
			"https://www.spacejam.com/archive/spacejam/movie/cmp/tunes/tunesframes.html",
			"https://www.spacejam.com/archive/spacejam/movie/cmp/lineup/lineupframes.html",
			"https://www.spacejam.com/archive/spacejam/movie/cmp/jump/jumpframes.html",
			"https://www.spacejam.com/archive/spacejam/movie/cmp/junior/juniorframes.html",
			"https://www.spacejam.com/archive/spacejam/movie/cmp/studiostoreframes.html",
			"https://www.spacejam.com/archive/spacejam/movie/cmp/souvenirs/souvenirsframes.html",
			"https://www.spacejam.com/archive/spacejam/movie/cmp/sitemap.html",
			"https://www.spacejam.com/archive/spacejam/movie/cmp/behind/behindframes.html",
			"https://policies.warnerbros.com/privacy/",
			"http://policies.warnerbros.com/terms/en-us/",
			"http://policies.warnerbros.com/terms/en-us/#accessibility",
			"https://policies.warnerbros.com/privacy/en-us/#adchoices",
			"https://www.spacejam.com/archive/spacejam/movie/index.html",
			"https://www.spacejam.com/archive/spacejam/movie/cmp/jamcentral/filmmakersframes.html",
			"https://www.spacejam.com/archive/spacejam/movie/cmp/jamcentral/prodnotesframes.html",
			"https://www.spacejam.com/archive/spacejam/movie/cmp/jamcentral/photosframes.html",
			"https://www.spacejam.com/archive/spacejam/movie/cmp/jamcentral/trailerframes.html",
			"https://www.spacejam.com/archive/spacejam/movie/cmp/behind/chardevelopmentframes.html",
			"https://www.spacejam.com/archive/spacejam/movie/cmp/behind/sketchesframes.html",
			"https://www.spacejam.com/archive/spacejam/movie/cmp/behind/brollframes.html",
			"https://www.spacejam.com/archive/spacejam/movie/cmp/behind/techframes.html"
	};

	int testInDegrees[] = {
			1,
			1,
			2,
			1,
			1,
			1,
			1,
			1,
			1,
			1,
			1,
			2,
			1,
			1,
			1,
			1,
			1,
			1,
			1,
			1,
			1,
			1,
			1,
			1,
			1
	};


	@BeforeEach
	void setUp() {
		List<TaggedVertex<String>> list = new ArrayList<>();
		for (int i = 0; i < testUrls.length; i++) {
			list.add(new TaggedVertex<>(testUrls[i], testInDegrees[i]));
		}
		index = new Index(list);
		index.makeIndex();
	}

	@Test
	void makeIndex() {
	}

	@Test
	void search() {

	}

	@Test
	void searchWithAnd() {
	}

	@Test
	void searchWithOr() {
	}

	@Test
	void searchAndNot() {
	}
}