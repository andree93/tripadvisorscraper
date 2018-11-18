package scraper;

import org.jsoup.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ListParser {
	public static final String USER_AGENT ="Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0";
	private String url ="";
	private Elements elementi =null;
	private Document doc;
	
	public ListParser() {
		super();
	}
	
	
	public ListParser(String url) {
		super();
		this.url = url;
	}


	public Elements getElementi() {
		return elementi;
	}


	public void setElementi(Elements elementi) {
		this.elementi = elementi;
	}


	public Document getDoc() {
		return doc;
	}


	public void setDoc(Document doc) {
		this.doc = doc;
	}



	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	public void parseList(String pageListRestaurant, List<String> linkRistorante) {
		URL url=null;
		try {
			url = new URL(pageListRestaurant);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		try {
			this.doc = Jsoup.connect(url.toString()).userAgent(USER_AGENT).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		elementi = doc.getElementsByClass("property_title");
		synchronized(linkRistorante) {
		for (Element e : elementi) {
			linkRistorante.add(e.attr("abs:href"));
		}
		
		}
	}
	
	public static int contaPagineLista(String url) {
		int pagine = 1;
		String u = url;
		Document doc=null;
		try {
			doc = Jsoup.connect(u).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			pagine = Integer.parseInt(doc.select(".pageNum.taLnk").last().text());
		} catch (NumberFormatException|NullPointerException e) {
			return pagine;
		}
		
		return pagine;
	}
	
	
	public String[] linkBuilder(String firstPagesListUrl, int pagine) {
		String[] elencolink = new String[pagine];
		final int offset=47; //First URL part, till "-gXXXXXXX-"
		final String prefix = "oa";
		if (pagine==1) {
			elencolink[0]=firstPagesListUrl;
			return elencolink;
		}
		
		for ( int i=0; i< elencolink.length; i++) {
			String part1 = firstPagesListUrl.substring(0, offset);
			String part2 = part1+prefix+(i*30)+"-"+ firstPagesListUrl.substring(offset);
			elencolink[i]=part2;
		}
		return elencolink;
	}

	public void getAllLinks(String firstPage, List<String> linkRistorante) {
		int pages = contaPagineLista(firstPage);
		String[] elencolink = new String[pages];
		elencolink = linkBuilder(firstPage, pages);
		for (int i = 0; i<pages; i++) {
			parseList(elencolink[i], linkRistorante);
			
		}
		
	}
	
}