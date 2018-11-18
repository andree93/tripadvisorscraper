package scraper;

import org.jsoup.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.RecursiveTask;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class Parser extends RecursiveTask<String> {
	
	private String url ="";
	private Document doc;
	private String nomeLocale="";
	private String numeroTel="";
	private String contenuto="";
	private String indirizzo= "";
	private String indirizzoesteso="";
	private String citta= "";
	private String nazione="";
	public static final String NOME_CLASSE_TITOLO ="heading_title";
	public static final String NOME_CLASSE_INFORMAZIONI = "prw_rup prw_common_atf_header_bl headerBL";
	public static final String USER_AGENT ="Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0";
	
	public Parser(String url) {
		super();
		this.url = url;
	}



	public Parser() {
		super();
	}




	public Document getDoc() {
		return doc;
	}


	public void setDoc(Document doc) {
		this.doc = doc;
	}


	public String getNomeLocale() {
		return nomeLocale;
	}


	public void setNomeLocale(String nomeLocale) {
		this.nomeLocale = nomeLocale;
	}


	public String getNumeroTel() {
		return numeroTel;
	}


	public void setNumeroTel(String numeroTel) {
		this.numeroTel = numeroTel;
	}


	public String getIndirizzo() {
		return indirizzo;
	}


	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}


	public String getIndirizzoesteso() {
		return indirizzoesteso;
	}


	public void setIndirizzoesteso(String indirizzoesteso) {
		this.indirizzoesteso = indirizzoesteso;
	}


	public String getCitta() {
		return citta;
	}


	public void setCitta(String citta) {
		this.citta = citta;
	}


	public String getNazione() {
		return nazione;
	}


	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public String getContenuto() {
		return contenuto;
	}


	public String nomeLocale() {
		return nomeLocale;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String u) {
		this.url = u;
	}
	
	
	public void scrape() {
		URL url=null;	
			try {
				url = new URL(this.url);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				this.doc = Jsoup.connect(url.toString()).userAgent(USER_AGENT).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Element info = doc.getElementsByClass(NOME_CLASSE_INFORMAZIONI).first();
			Element titolo = doc.getElementsByClass(NOME_CLASSE_TITOLO).first();
			this.setNomeLocale(titolo.select("#HEADING").text()); //Rimuovo intestazione tripadvisor dal titolo
			this.setNazione(info.select(".country-name").text());
			this.setCitta(info.select(".locality").text().replaceAll(",", ""));
			this.setIndirizzo((info.select(".street-address").text()).replaceAll(",",""));
			this.setNumeroTel(info.select(".blEntry.phone").text());
	}
	
	public String buildCSV() {
		this.scrape();
		StringBuilder csv = new StringBuilder();
		csv.append(this.getNomeLocale()).append(",").append(this.getNazione()).append(",").append(this.getCitta()).append(",").append(this.getIndirizzo()).append(",").append(this.getNumeroTel());
		
		return csv.toString();
	}
	

	@Override
	public String compute() {
		String result = buildCSV().replaceAll("\\+ Aggiungi numero di telefono", "N/A").replaceAll(",,", ",");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
