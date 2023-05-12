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
		stampaListaTotale();
		String scritto="";
		try {
			scritto = FileUtils.readFileToString(file, "UTF-8");
		} catch (IOException e) {
			System.out.println("ERRORE"+e);
			scritturaSuFile();
		}
		String risultato[];
		String risultato2[];
		risultato = scritto.split("Libro");
		risultato2 = scritto.split("Rivista");
		for(int i=1;i<risultato.length;i++){
			System.out.println(risultato[i]);
		}
		System.out.println("Ecco le riviste"+risultato2[1]);
		boolean uscita = false;
		do {
			try {
				System.out.println("Inserisci un codice dell'elemento da eliminare ('0'-> per uscire)");
				long codice = Integer.parseInt(sc.nextLine());
				if (codice == 0) {
					System.out.println("Grazie per aver usato il sistema ad eliminazione con codice!");
					uscita = true;
				} else {
					if (eliminazioneConCodice(codice).size() == 0) {
						System.out.println("Elemento non trovato");
					}
				}
			} catch (NumberFormatException e) {
				log.error("ERRORE inserire un numero" + e);
			} catch (Exception e) {
				log.error("ERRORE" + e);
			}
		} while (!uscita);

		stampaListaTotale();

		boolean uscita2 = false;
		do {
			try {
				stampaListaTotale();
				System.out.println("Inserisci il codice dell'elemento da cercare ('0'-> per uscire)");
				long codiceISBN = Integer.parseInt(sc.nextLine());
				if (codiceISBN == 0) {
					System.out.println("Grazie per aver usato il sistema di ricerca con codice!");
					uscita2 = true;
				} else {
					System.out.println("Ecco l'elemento cercato: " + ricercaConCodice(codiceISBN));
				}
			} catch (NumberFormatException e) {
				log.error("ERRORE inserire un numero" + e);
			} catch (Exception e) {
				log.error("ERRORE" + e);
			}
		} while (!uscita2);
		stampaListaTotale();

		boolean uscita3 = false;
		do {
			stampaListaTotale();
			System.out.println("Inserisci l'anno degli elementi da cercare ('q'-> per uscire)");
			String ricercaAnno = sc.nextLine();
			if (ricercaAnno.equals("q")) {
				System.out.println("Grazie per aver usato il sistema di ricerca per anno!");
				uscita3 = true;
			} else {
				if (ricercaAnno.length() != 4) {
					System.out.println("Inserisci un anno valido! ");
				} else {
					ricercaConAnno(ricercaAnno);
				}
			}

		} while (!uscita3);
		stampaListaTotale();

	}

	public static void creazioneElementiCatalogo() {
		listaCompletaCatalogo.add(new Libro(1, "la Bella e la bestia", "2032", 190, "Hans", "Fantasy"));
		listaCompletaCatalogo.add(new Libro(2, "Hansel e Gretel", "1999", 120, "Mario Rossi", "Giallo"));
		listaCompletaCatalogo.add(new Libro(3, "Rosso malpelo", "1980", 190, "Giovanni Verga", "Verista"));
		listaCompletaCatalogo.add(new Rivista(4, "Verissimo Magazine", "1980", 190, Periodicita.Settimanale));

	}

	public static List<Catalogo> eliminazioneConCodice(long codice) {
		List<Catalogo> listaModificataUno = new ArrayList<Catalogo>();
		listaModificataUno.addAll(listaCompletaCatalogo);
		for (int i = 0; i < listaModificataUno.size(); i++) {
			if (listaModificataUno.get(i).getCodiceISBN() == codice) {
				System.out.println("Ecco il ISBN: " + listaModificataUno.get(i).getCodiceISBN());
				System.out.println("È stato eliminato il seguente elemento dal catalogo: " + listaModificataUno.get(i));
				listaModificataUno.remove(listaModificataUno.get(i));
			}
		}
		if (listaModificataUno.size() == listaCompletaCatalogo.size()) {
			System.out.println("Elemento non trovato");
			listaModificataUno.forEach(e -> System.out.println(e));
		}

		return listaModificataUno;
	}

	public static Catalogo ricercaConCodice(long codice) {
		List<Catalogo> listaModificataDue = listaCompletaCatalogo.stream()
				.filter(e -> e.getCodiceISBN() == codice)
				.collect(Collectors.toList());
		if (listaModificataDue.size() == 0) {
			System.out.println("Elemento non trovato");
		}
		return listaModificataDue.get(0);
	}

	public static void stampaListaTotale() {
		System.out.println("Ecco la lista del Catalogo:");
		listaCompletaCatalogo.forEach(e -> System.out.println(e));
	}

	public static void ricercaConAnno(String s) {
		List<Catalogo> listaModificatoTre = new ArrayList<Catalogo>();
		listaCompletaCatalogo.forEach(e -> {
			if (e.getAnnoPubblicazione().equals(s)) {
				listaModificatoTre.add(e);
			}
		});
		if (listaModificatoTre.size() == 0) {
			System.out.println("Elemento non trovato");
		} else {
			System.out
					.println("Ecco la lista degli elementi del catalogo scritti nel " + s + ":\n" + listaModificatoTre);
		}
	}

	public static void scritturaSuFile() {
		listaCompletaCatalogo.forEach(e -> {
			try {
				if (e instanceof Libro) {
					Libro l = ((Libro) e);
					String stringaLibro = ("Libro:" + l.getAutore() + "-" + l.getGenere() + "-" + l.getCodiceISBN()
							+ "-" + l.getTitolo() + "-" + l.getAnnoPubblicazione() + "-" + l.getNumeroPagine());
					FileUtils.writeStringToFile(file, stringaLibro, "UTF-8", true);
				} else if (e instanceof Rivista) {
					Rivista r = ((Rivista) e);
					String stringaRivista = ("Rivista:" + r.getCodiceISBN() + "-" + r.getTitolo() + "-"
							+ r.getAnnoPubblicazione() + "-" + r.getNumeroPagine() + "-" + r.getPeriodo());
					FileUtils.writeStringToFile(file, stringaRivista, "UTF-8", true);
				}
			} catch (IOException h) {
				h.printStackTrace();
			}
		});
	}

}
