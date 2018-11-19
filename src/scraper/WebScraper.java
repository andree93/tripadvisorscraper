package scraper;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

public class WebScraper {

	public static void main(String[] args) {
		WebScraper scraper = new WebScraper();
		scraper.startScraper();
	} 
	
	public void startScraper() {
		String u="";
		String nomeFile="";
		boolean append=true;
		String risposta="";
		StringBuilder str = new StringBuilder();
		List<String> listalink = new ArrayList<String>();  // All restaurant links list
		List<String> allRestaurantinfo = new ArrayList<String>(); // All Restaurant info list
		ListParser listparser = new ListParser();
		
		System.out.println("Inserisci URL contenente la prima pagina della lista dei ristoranti presenti nella località d'interesse");
		System.out.println("L'URL dovrà essere nel formato: \"https://www.tripadvisor.it/Restaurants-g12345678-abc_defg.html\"");
		System.out.print("URL: ");
		u = new Scanner(System.in).nextLine();
		if (!ScraperUtil.checkUrl(u)) { // check url
			System.out.println("URL non corretto! Programma terminato.");
			System.exit(0);
		}
		
		System.out.print("Se presente, si desidera sovrascrivere il file esistente? (Y/N): ");
		new Scanner(System.in).nextLine();
		if (risposta.equalsIgnoreCase("Y") || risposta.equalsIgnoreCase("YES")|| risposta.equalsIgnoreCase("SI")){
			append = false;
		}
		else if (risposta.equalsIgnoreCase("N")|| risposta.equalsIgnoreCase("NO")){
			append = true;
		}
		System.out.print("Inserisci nome file: ");
		nomeFile=new Scanner(System.in).nextLine()+".csv";
		
		System.out.println("Sto estrapolando la lista dei link di tutti le attivita presenti nella zona. Attendere...");
		
		listparser.getAllLinks(u, listalink);
		
		System.out.println("Attivita trovate: "+listalink.size());
		
		ForkJoinPool pool = new ForkJoinPool();
		
		for (String link : listalink) {		//start new scrape thread for every activity URL contained in the list
			allRestaurantinfo.add(pool.invoke(new Parser(link)));
		}
		pool.shutdown();
		
		System.out.println("Sto salvando le informazioni su File...");
		
		{
			str.append("NOME ATTIVITA").append(",").append("NAZIONE").append(",").append("CITTA").append(",").append("INDIRIZZO").append(",").append("NUMERO").append("\n");
		for (String restaurant : allRestaurantinfo) {
			str.append(restaurant).append("\n");
		}
		
		}
		
		ScraperUtil.writeToFile(str.toString(), nomeFile, append); //Write to file
		System.out.println("Dati scritti correttamente! ");
		System.out.println("Esecuzione programma terminata!");
		
	}
	

}
