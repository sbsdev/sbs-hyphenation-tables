import ch.sbs.jhyphen.Hyphen;
import ch.sbs.jhyphen.Hyphenator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static org.apache.commons.io.filefilter.FileFilterUtils.asFileFilter;
import static org.apache.commons.io.filefilter.FileFilterUtils.trueFileFilter;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class JHyphenTest {
	
	@Test
	public void testHyphenation() {
		
		String unhyphenatedWord = "Dampfschiff, Zucker, Schiffahrt";
		String hyphenatedWord = "Dampf=schiff, Zu=cker, Schif=fahrt";
		
		assertEquals(hyphenatedWord, hyphenator.hyphenate(unhyphenatedWord, '=', '='));
	}
	
	@Test
	public void testHardHyphen() {
		
		Map<String,String> words = new HashMap<String,String>();
		
		words.put("bla-bla", "bla-=bla");
		words.put("bla-", "bla-");
		words.put("-bla", "-bla");
		words.put(":-)", ":-)");
		words.put("3-jährig", "3-=jäh=rig");
		words.put("3-für-2-Aktion", "3-=für-=2-=Ak=ti=on");
		words.put("von 14-16 Uhr", "von 14-=16 Uhr");
		
		for (Entry<String,String> entry : words.entrySet()) {
			assertEquals(entry.getValue(), hyphenator.hyphenate(entry.getKey(), '=', '='));
		}
	}
	
	@Test
	public void testExceptionWords() {
		
		Map<String,String> words = new HashMap<String,String>();
		words.put("angestarrt", "an=ge=starrt");
		
		for (Entry<String,String> entry : words.entrySet()) {
			assertEquals(entry.getValue(), hyphenator.hyphenate(entry.getKey(), '=', '='));
		}
	}
	
	@Test
	public void testCapital() {
		
		Map<String,String> words = new HashMap<String,String>();
		words.put("schlampe", "schlam=pe");
		words.put("Schlampe", "Schlam=pe");
		
		for (Entry<String,String> entry : words.entrySet()) {
			assertEquals(entry.getValue(), hyphenator.hyphenate(entry.getKey(), '=', '='));
		}
	}
	
	private File projectHome;
	private Hyphenator hyphenator;
	
	@Before
	public void initialize() throws UnsupportedCharsetException, FileNotFoundException {
		projectHome = new File(new File(this.getClass().getResource("/").getPath()), "../..");
		Hyphen.setLibraryPath(((Collection<File>)FileUtils.listFiles(
				new File(projectHome, "target/dependency"),
				asFileFilter(new FilenameFilter() {
					public boolean accept(File dir, String fileName) {
						return dir.getName().equals("shared") && fileName.startsWith("libhyphen"); }}),
				trueFileFilter())).iterator().next());
		hyphenator = new Hyphenator(new File(projectHome, "target/generated-resources/tables/hyph_de_DE.dic"));
	}
	
	@After
	public void close() {
		hyphenator.close();
	}
}
