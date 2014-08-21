import ch.sbs.jhyphen.Hyphen;
import ch.sbs.jhyphen.Hyphenator;

import java.io.File;
import java.io.FilenameFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Collection;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.io.FileUtils;
import static org.apache.commons.io.filefilter.FileFilterUtils.asFileFilter;
import static org.apache.commons.io.filefilter.FileFilterUtils.trueFileFilter;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Before;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class TrennfehlerGesamtlisteTest {
	
	@Test
	public void testTrennfehlerGesamtliste()
			throws FileNotFoundException, ParserConfigurationException, IOException, SAXException,
			       UnsupportedCharsetException {
		
		File dictionaryNewSpelling = new File(projectHome, "target/generated-resources/tables/hyph_de_DE.dic");
		File dictionaryOldSpelling = new File(projectHome, "target/generated-resources/tables/hyph_de_OLDSPELL.dic");
		File trennfehlerGesamtliste = new File(projectHome, "src/main/resources/tables/trennfehler_gesamtliste.xml");
		
		final Hyphenator hyphenatorNewSpelling = new Hyphenator(dictionaryNewSpelling);
		final Hyphenator hyphenatorOldSpelling = new Hyphenator(dictionaryOldSpelling);
		
		XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
		
		reader.setContentHandler(new DefaultHandler() {
			
			Stack<String> elements = new Stack<String>();
			
			String wort = null;
			String rechtschreibung = null;
			String ist = null;
			String soll = null;
			
			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
				elements.push(qName);
			}
			
			@Override
			public void endElement(String uri, String localName, String qName) throws SAXException {
				elements.pop();
			}
			
			@Override
			public void characters(char ch[], int start, int length) throws SAXException {
				String text = new String(ch, start, length);
				if ("ss".equals(elements.peek()))
					wort = text;
				else if ("rs".equals(elements.peek()))
					rechtschreibung = text;
				else if ("so".equals(elements.peek())) {
					soll = text;
					ist = ("ALT".equals(rechtschreibung) ?
					           hyphenatorOldSpelling :
					           hyphenatorNewSpelling
					      ).hyphenate(wort, '-', null);
					assertEquals(soll, ist); }
			}
		});
		
		reader.parse(trennfehlerGesamtliste.toURI().toASCIIString());
		
		hyphenatorNewSpelling.close();
		hyphenatorOldSpelling.close();
		
	}
	
	private File projectHome;
	
	@Before
	public void initialize() {
		projectHome = new File(new File(this.getClass().getResource("/").getPath()), "../..");
		Hyphen.setLibraryPath(((Collection<File>)FileUtils.listFiles(
				new File(projectHome, "target/dependency"),
				asFileFilter(new FilenameFilter() {
					public boolean accept(File dir, String fileName) {
						return dir.getName().equals("shared") && fileName.startsWith("libhyphen"); }}),
				trueFileFilter())).iterator().next());
	}
}
