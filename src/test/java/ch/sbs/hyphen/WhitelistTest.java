package ch.sbs.hyphen;

import ch.sbs.jhyphen.Hyphen;
import ch.sbs.jhyphen.Hyphenator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import static org.apache.commons.io.filefilter.FileFilterUtils.asFileFilter;
import static org.apache.commons.io.filefilter.FileFilterUtils.trueFileFilter;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class WhitelistTest {
	
	@Test
	@SuppressWarnings("unchecked")
	public void testWhitelist() throws IOException {
		
		File testRootDir = new File(this.getClass().getResource("/").getPath());
		Hyphen.setLibraryPath(((Collection<File>)FileUtils.listFiles(
				new File(testRootDir, "../dependency"),
				asFileFilter(new FilenameFilter() {
					public boolean accept(File dir, String fileName) {
						return dir.getName().equals("shared") && fileName.startsWith("libhyphen"); }}),
				trueFileFilter())).iterator().next());
		
		File projectHome = new File(testRootDir, "../..");
		File whitelist = new File(projectHome, "src/main/resources/whitelist/whitelist_de_SBS.txt");
		File dictionary = new File(projectHome, "target/generated-resources/tables/hyph_de_DE.dic");
		File origDictionary = new File(projectHome, "src/main/resources/tables/hyph_de_DE.dic");
		
		Hyphenator hyphenator = new Hyphenator(dictionary);
		Hyphenator origHyphenator = new Hyphenator(origDictionary);
		
		List<String> redundantWords = new ArrayList<String>();
		
		Scanner scanner = new Scanner(whitelist, "ISO8859-1");
		String word = null;
		String unhyphenated = null;
		while (scanner.hasNext()) {
			word = scanner.nextLine();
			unhyphenated = word.replaceAll("\\-", "");
			assertEquals(word, hyphenator.hyphenate(unhyphenated, '-', null));
			if(word.equals(origHyphenator.hyphenate(unhyphenated, '-', null)))
				redundantWords.add(unhyphenated); }
		
		System.out.print("The following words are correctly hyphenated without whitelist:\n=> ");
		for (String w : redundantWords)
			System.out.print(w + " ");
		System.out.println();
		
		hyphenator.close();
	}
}
