package test;

import api.TaggedVertex;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class TestUtils {

	public static void printTaggedVertexList(List<TaggedVertex<String>> list) {
		for (TaggedVertex v : list) {
			System.out.println(v.getTagValue() + " " + v.getVertexData());
		}
	}

	public static void writeToPropertiesFile(List<TaggedVertex<String>> list, String fileName) {
		Properties properties = new Properties();
		for (TaggedVertex<String> v : list) {
			properties.setProperty(v.getVertexData(), v.getTagValue() + "");
		}
		try {
			FileWriter writer = new FileWriter(fileName);
			properties.store(writer, null);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<TaggedVertex<String>> readFromPropertiesFile(String fileName) {
		List<TaggedVertex<String>> list = new ArrayList<>();
		try {
			FileReader fileReader = new FileReader(fileName);
			Properties properties = new Properties();
			properties.load(fileReader);

			Enumeration urls = properties.propertyNames();
			String key;
			while (urls.hasMoreElements()) {
				key = (String) urls.nextElement();
				list.add(new TaggedVertex<>(key, Integer.parseInt(properties.getProperty(key))));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
