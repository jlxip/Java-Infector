import java.io.IOException;

public class template {
	public static void main(String[] args) {
		// System.out.println("THE CODE HAS BEEN INJECTED!");
		String[] espaces = new String[] {"calc.exe"};
		try {
			new ProcessBuilder(espaces).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/* --- TEMPLATE --- */
	}
}
