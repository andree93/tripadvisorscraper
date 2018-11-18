package scraper;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.HttpURLConnection;
import java.net.URL;


public class ScraperUtil {
	
	public static void writeToFile(String stringToWrite, String path, boolean append) {
		PrintWriter p = null;
		File f = new File(path);		
		try {
			p = new PrintWriter(new FileOutputStream(f, append));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			p.println(stringToWrite);
		}
		finally {
			p.flush();
			p.close();
		}
	}
	
	
	public static BufferedReader readTextFromFile(String path) throws IOException {
		BufferedReader bf = null;
		File f = new File(path);
		if (f.exists()&& f.canRead()){
			try {
				bf = new BufferedReader(new FileReader(f));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else {
			throw new IOException("Impossibile leggere il file!");
		}
		
		return bf;
	}
	
	public static boolean checkUrl(String u)
	{
		int respCode = -1;
		try {
		URL url = new URL(u);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.connect();
		respCode = conn.getResponseCode();
		}
		catch (IOException e) {
		e.printStackTrace();
		}
		String regexUrl = "(^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[0-9]{6}+[-].*)";
		Pattern urlcheck = Pattern.compile(regexUrl);
		Matcher matcher = urlcheck.matcher(u);
		return (matcher.matches()) && (respCode == 200);
	}
	
	
	public static Scanner scanTextFromFile(String path) throws IOException {
		Scanner s = null;
		File f = new File(path);
		if (f.exists()&& f.canRead()) {
			try {
				s = new Scanner(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			throw new IOException("Impossibile leggere il file!");
			} 
		
		return s;
	}
	
	
	

}
