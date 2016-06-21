package jlxip.JavaInfector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Decompress {
	public Decompress(File FtoInfect, String DESTDIR) {
		System.out.print("Decompressing... ");
		try {
			JarFile JFtoInfect = new JarFile(FtoInfect);
			Enumeration enumEntries = JFtoInfect.entries();
			while(enumEntries.hasMoreElements()) {
				JarEntry file = (JarEntry) enumEntries.nextElement();
				File f = new File(DESTDIR + File.separator + file.getName());
				if (file.isDirectory()) {
			        f.mkdir();
			        continue;
			    }
				InputStream is = JFtoInfect.getInputStream(file);
			    FileOutputStream fos = new FileOutputStream(f);
			    while (is.available() > 0) {
			        fos.write(is.read());
			    }
			    fos.close();
			    is.close();
			}
			System.out.println("DONE!");
		} catch (IOException e) {
			System.out.println("FAILED!");
			e.printStackTrace();
		}
	}
}
