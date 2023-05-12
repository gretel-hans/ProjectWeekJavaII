import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MainProject {
	private static Logger log = LoggerFactory.getLogger(MainProject.class);
	private static String path = "dati/catalogo.txt";
	private static File file = new File(path);
	private static List<Catalogo> listaCompletaCatalogo = new ArrayList<Catalogo>();
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		creazioneElementiCatalogo();
		listaCompletaCatalogo.forEach(e -> System.out.println(e));
		boolean uscita = false;
		do {
			try {
				System.out.println("Inserisci un codice dell'elemento da eliminare ('0'-> per uscire)");
				long codice = Integer.parseInt(sc.nextLine());
				if (codice == 0) {
					System.out.println("Grazie per aver usato il sistema ad eliminazione con codice!");
					uscita = true;
				} else {
					if(eliminazioneConCodice(codice).size()==0){
						System.out.println("Elemento non trovato");
					}
					
				}
			} catch (NumberFormatException e) {
				log.error("ERRORE inserire un numero" + e);
			} catch (Exception e) {
				log.error("ERRORE" + e);
			}
		} while (!uscita);
		listaCompletaCatalogo.forEach(e -> System.out.println(e));


		System.out.println("Inserisci il codice dell'elemento da cercare ('0'-> per uscire)");
		long codiceISBN = Integer.parseInt(sc.nextLine());
		System.out.println("Ecco l'elemento cercato: "+ricercaConCodice(codiceISBN));

		/*
		 * try {
		 * FileUtils.writeStringToFile(file,"Ciaoo","UTF-8",true);
		 * } catch (IOException e) {
		 * e.printStackTrace();
		 * }
		 */
	}

	public static void creazioneElementiCatalogo() {
		listaCompletaCatalogo.add(new Libro(1, "la Bella e la bestia", "2032", 190, "Hans", "Fantasy"));
		listaCompletaCatalogo.add(new Libro(2, "Hansel e Gretel", "1999", 120, "Mario Rossi", "Giallo"));
		listaCompletaCatalogo.add(new Libro(3, "Rosso malpelo", "1980", 190, "Giovanni Verga", "Verista"));
		listaCompletaCatalogo.add(new Rivista(4, "Verissimo Magazine", "1980", 190, Periodicita.Settimanale));
	}

	public static List<Catalogo> eliminazioneConCodice(long codice) {
		List<Catalogo> listaModificataUno=new ArrayList<Catalogo>();
		listaModificataUno.addAll(listaCompletaCatalogo);
		for (int i = 0; i < listaModificataUno.size(); i++) {
			if (listaModificataUno.get(i).getCodiceISBN() == codice) {
				System.out.println("Ecco il ISBN: "+listaModificataUno.get(i).getCodiceISBN());
				System.out.println("È stato eliminato il seguente elemento dal catalogo: " + listaModificataUno.get(i));
				listaModificataUno.remove(listaModificataUno.get(i));
			}
		}
		listaModificataUno.forEach(e -> System.out.println(e));
		return listaModificataUno;
	}

	public static Catalogo ricercaConCodice(long codice){
		List<Catalogo> listaModificataDue=listaCompletaCatalogo.stream()
		.filter(e->e.getCodiceISBN()==codice)
		.collect(Collectors.toList());
		return listaModificataDue.get(0);
	}

}
