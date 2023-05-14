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
	private static int cil;
	private static int cir;
	private static long codiceISBN;
	private static long codiceISBNRivista;
	private static boolean codiceISBNCorretto;
	private static boolean codiceISBNRivistaCorretto;

	public static void main(String[] args) {

		creazioneElementiCatalogo();
		//stampaListaTotale();
/* 
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
*/
	}

	public static void creazioneElementiCatalogo() {
		boolean uscitaCreazione = false;
		do {
			System.out.println(
					"Cosa vuoi creare?\n'libro' -> per creare un libro - 'rivista' -> per creare una rivista 'q' -> per uscire ");
			String scelta = sc.nextLine();
			if (scelta.equals("libro")) {
				codiceISBNCorretto = false;
				do {
					try {
						System.out.println("Inserisci il codice ISBN del libro ('0' -> Non valido)");
						codiceISBN = Long.parseLong(sc.nextLine());
						cil = 0;
						if (listaCompletaCatalogo.size() > 0 && codiceISBN > 0) {
							listaCompletaCatalogo.forEach(e -> {
								if (e.getCodiceISBN() == codiceISBN) {
									System.out
											.println(
													"ERRORE l'elemento è già nel catalogo inserire un altro codice ISBN");
								} else if (e.getCodiceISBN() != codiceISBN) {
									cil++;
									if (cil == listaCompletaCatalogo.size()) {
										codiceISBNCorretto = true;
									}
								}
							});
						} else if (listaCompletaCatalogo.size() == 0 && codiceISBN != 0) {
							codiceISBNCorretto = true;
						}
					} catch (NumberFormatException e) {
						log.error("Inserisci un numero! ");
					} catch (Exception e) {
						log.error("ERRORE! ");
					}
				} while (!codiceISBNCorretto);
				System.out.println("Inserisci il titolo del libro");
				String titolo = sc.nextLine();
				boolean numeroCorretto = false;
				String anno;
				int pagine = 0;
				do {
					System.out.println("Inserisci l'anno di pubblicazione");
					anno = sc.nextLine();
					if (anno.length() != 4) {
						System.out.println("Inserisci un anno valido! ");
					} else if (anno.length() == 4) {
						numeroCorretto = true;
					}
				} while (!numeroCorretto);
				boolean numeroGiusto = false;
				do {
					try {
						System.out.println("Inserisci il numero di pagine");
						pagine = Integer.parseInt(sc.nextLine());
						if (pagine > 0) {
							numeroGiusto = true;
						}
					} catch (NumberFormatException e) {
						System.out.println("Inserisci un numero valido! ");
					}
				} while (!numeroGiusto);
				System.out.println("Inserisci il nome dell'autore");
				String nomeAutore = sc.nextLine();
				System.out.println("Inserisci il nome del genere");
				String nomeGenere = sc.nextLine();
				listaCompletaCatalogo.add(new Libro(codiceISBN, titolo, anno, pagine, nomeAutore, nomeGenere));

			} else if (scelta.equals("rivista")) {
				codiceISBNRivistaCorretto = false;
				do {
					try {
						System.out.println("Inserisci il codice ISBN della rivista ('0' -> Non valido)");
						codiceISBNRivista = Long.parseLong(sc.nextLine());
						if (listaCompletaCatalogo.size() > 0) {
							listaCompletaCatalogo.forEach(e -> {
								if (e.getCodiceISBN() == codiceISBNRivista) {
									System.out
											.println(
													"ERRORE l'elemento è già nel catalogo inserire un altro codice ISBN");
								} else if (e.getCodiceISBN() != codiceISBNRivista) {
									cir++;
									if (cir == listaCompletaCatalogo.size()) {
										codiceISBNRivistaCorretto = true;
									}
								}
							});
						} else if (listaCompletaCatalogo.size() == 0 && codiceISBNRivista != 0) {
							codiceISBNRivistaCorretto = true;
						}
					} catch (NumberFormatException e) {
						log.error("Inserisci un numero! ");
					} catch (Exception e) {
						log.error("ERRORE! ");
					}
				} while (!codiceISBNRivistaCorretto);
				System.out.println("Inserisci il titolo della rivista");
				String titoloRivista = sc.nextLine();
				boolean numeroCorrettoRivista = false;
				String annoRivista;
				int pagineRivista = 0;
				do {
					System.out.println("Inserisci l'anno di pubblicazione");
					annoRivista = sc.nextLine();
					if (annoRivista.length() != 4) {
						System.out.println("Inserisci un anno valido! ");
					} else if (annoRivista.length() == 4) {
						numeroCorrettoRivista = true;
					}
				} while (!numeroCorrettoRivista);
				boolean numeroGiustoRivista = false;
				do {
					try {
						System.out.println("Inserisci il numero di pagine");
						pagineRivista = Integer.parseInt(sc.nextLine());
						if (pagineRivista > 0) {
							numeroGiustoRivista = true;
						}
					} catch (NumberFormatException e) {
						System.out.println("Inserisci un numero valido! ");
					}
				} while (!numeroGiustoRivista);
				boolean periodicitaValida = false;
				do {
					System.out.println(
							"Inserisci la periodicità della rivista ('settimanale' - 'mensile' - 'semestrale')");
					String periodicitaRivista = sc.nextLine();
					switch (periodicitaRivista) {
						case "settimanale":
							Periodicita settimanale = Periodicita.Settimanale;
							periodicitaValida = true;
							listaCompletaCatalogo
									.add(new Rivista(codiceISBNRivista, titoloRivista, annoRivista, pagineRivista,
											settimanale));
							break;
						case "mensile":
							Periodicita mensile = Periodicita.Mensile;
							periodicitaValida = true;
							listaCompletaCatalogo.add(
									new Rivista(codiceISBNRivista, titoloRivista, annoRivista, pagineRivista, mensile));
							break;
						case "semestrale":
							Periodicita semestrale = Periodicita.Semestrale;
							periodicitaValida = true;
							listaCompletaCatalogo
									.add(new Rivista(codiceISBNRivista, titoloRivista, annoRivista, pagineRivista,
											semestrale));
							break;
						default:
							System.out.println("Inserisci una periodicità valida! ");
							break;
					}
				} while (!periodicitaValida);
			} else if (scelta.equals("q")) {
				System.out.println("Grazie per aver creato dei nuovi prodotti nel catalogo! ");
				uscitaCreazione = true;
				if (listaCompletaCatalogo.size() > 0) {
					scritturaSuFile();
					System.out.println("Ho scritto sul file!");
				} else {
					System.out.println("Non ho scritto sul file!");
				}
			}
		} while (!uscitaCreazione);
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
					String stringaLibro = ("#"+"Libro:" + l.getCodiceISBN() + "@" + l.getTitolo() + "@"
							+ l.getAnnoPubblicazione()
							+ "@" + l.getNumeroPagine() + "@" + l.getAutore() + "@" + l.getGenere());
					FileUtils.writeStringToFile(file, stringaLibro, "UTF-8", true);
				} else if (e instanceof Rivista) {
					Rivista r = ((Rivista) e);
					String stringaRivista = ("#"+"Rivista:" + r.getCodiceISBN() + "-r1" + r.getTitolo() + "-r2"
							+ r.getAnnoPubblicazione() + "-r3" + r.getNumeroPagine() + "-r4" + r.getPeriodo());
					FileUtils.writeStringToFile(file, stringaRivista, "UTF-8", true);
				}
				letturaSuFile();
			} catch (IOException h) {
				h.printStackTrace();
			}
		});
	}

	public static void letturaSuFile() throws IOException {
		String scritto = FileUtils.readFileToString(file, "UTF-8");
		
		String[] risultatoCatalogo = scritto.split("#");
		for (int i = 1; i < risultatoCatalogo.length; i++) {
			String[] libro=risultatoCatalogo[i].split("Libro");
			System.out.println("Risultato libro:" +libro[1]);
			String []datiLibro=libro[1].split("@");
			System.out.println("CodiceIsbn "+datiLibro[0] + "\nTitolo " + datiLibro[1] + "\nAnnoPubblicazione "+datiLibro[2] +"\nNumeroPagine "+datiLibro[3]+"\nAutore "+datiLibro[4]+"\nGenere: "+datiLibro[5]);
			Libro l= new Libro(Long.parseLong(datiLibro[0]), scritto, scritto, i, scritto, scritto);
			//String []titolo=codiceIsbn[1].split("-l1");
			//System.out.println("Titolo: "+titolo[0]);
			//System.out.println("Risultato" +risultatoCatalogo[i]);
		}
		/*for (int i = 1; i < risultatoCatalogo.length; i++) {
			String[] libro=risultatoCatalogo[i].split("Libro");
			System.out.println("dopo Libro" +libro[1]);
		}
		for (int i = 0; i < risultatoRiviste.length; i++) {
			System.out.println("Ecco la rivista" + risultatoRiviste[i]);
		}*/
	}

}
