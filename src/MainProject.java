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
	private static boolean erroreFile = false;

	public static void main(String[] args) {

		try {
			letturaSuFile();
		} catch (IOException e) {
			log.error("ERRORE Crea nuovi dati per salvarli prima di poterli leggere dall'archivio ");
			erroreFile = true;
		}
		try {
			creazioneElementiCatalogo();
		} catch (IOException e) {
			e.printStackTrace();
		}

		stampaListaTotale();
		boolean uscita = false;
		do {
			try {
				System.out.println("Inserisci il codice dell'elemento da eliminare ('0'-> per uscire)");
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

		boolean uscita2 = false;
		do {
			try {

				stampaListaTotale();
				System.out.println("\nInserisci il codice dell'elemento da cercare ('0'-> per uscire)");
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

		boolean uscita3 = false;
		do {

			stampaListaTotale();
			System.out.println("\nInserisci l'anno degli elementi da cercare ('q'-> per uscire)");
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

		boolean uscita4 = false;
		do {

			stampaListaTotale();
			System.out.println("\nInserisci il nome dell'autore degli elementi da cercare ('q'-> per uscire)");
			String ricercaNomeAutore = sc.nextLine();
			if (ricercaNomeAutore.equals("q")) {
				System.out.println("Grazie per aver usato il sistema di ricerca per anno!");
				uscita4 = true;
			} else {
				ricercaConAutore(ricercaNomeAutore);
			}

		} while (!uscita4);

	}

	public static void creazioneElementiCatalogo() throws IOException {
		boolean uscitaCreazione = false;
		do {
			if (erroreFile) {
				System.out.println(
						"Cosa vuoi creare?\n'libro' -> per creare un libro - 'rivista' -> per creare una rivista 'q' -> per uscire ");
			} else {
				System.out.println(
						"Vuoi creare nuovi elementi da aggiungere al catalogo o visualizzare i già esistenti?\n'libro' -> per creare un libro - 'rivista' -> per creare una rivista 'q' -> per uscire ");
			}
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
				Libro l = new Libro(codiceISBN, titolo, anno, pagine, nomeAutore, nomeGenere);
				listaCompletaCatalogo.add(l);
				String stringaLibro = ("#" + "Libro" + l.getCodiceISBN() + "@" + l.getTitolo() + "@"
						+ l.getAnnoPubblicazione()
						+ "@" + l.getNumeroPagine() + "@" + l.getAutore() + "@" + l.getGenere());
				FileUtils.writeStringToFile(file, stringaLibro, "UTF-8", true);

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
							Rivista r = new Rivista(codiceISBNRivista, titoloRivista, annoRivista, pagineRivista,
									settimanale);
							listaCompletaCatalogo.add(r);
							String stringaRivista = ("#" + "Rivista" + r.getCodiceISBN() + "&" + r.getTitolo() + "&"
									+ r.getAnnoPubblicazione() + "&" + r.getNumeroPagine() + "&" + r.getPeriodo());
							FileUtils.writeStringToFile(file, stringaRivista, "UTF-8", true);
							break;
						case "mensile":
							Periodicita mensile = Periodicita.Mensile;
							periodicitaValida = true;
							Rivista rm = new Rivista(codiceISBNRivista, titoloRivista, annoRivista, pagineRivista,
									mensile);
							listaCompletaCatalogo.add(rm);
							String stringaRivistaM = ("#" + "Rivista" + rm.getCodiceISBN() + "&" + rm.getTitolo() + "&"
									+ rm.getAnnoPubblicazione() + "&" + rm.getNumeroPagine() + "&" + rm.getPeriodo());
							FileUtils.writeStringToFile(file, stringaRivistaM, "UTF-8", true);
							break;
						case "semestrale":
							Periodicita semestrale = Periodicita.Semestrale;
							periodicitaValida = true;
							Rivista rs = new Rivista(codiceISBNRivista, titoloRivista, annoRivista, pagineRivista,
									semestrale);
							listaCompletaCatalogo.add(rs);
							String stringaRivistaS = ("#" + "Rivista" + rs.getCodiceISBN() + "&" + rs.getTitolo() + "&"
									+ rs.getAnnoPubblicazione() + "&" + rs.getNumeroPagine() + "&" + rs.getPeriodo());
							FileUtils.writeStringToFile(file, stringaRivistaS, "UTF-8", true);
							break;
						default:
							System.out.println("Inserisci una periodicità valida! ");
							break;
					}
				} while (!periodicitaValida);
			} else if (scelta.equals("q")) {
				System.out.println("Grazie per il sistema di creazione di nuovi prodotti nel catalogo! ");
				uscitaCreazione = true;
			}
		} while (!uscitaCreazione);
	}

	public static List<Catalogo> eliminazioneConCodice(long codice) throws IOException {
		List<Catalogo> listaModificataUno = new ArrayList<Catalogo>();
		listaModificataUno.addAll(listaCompletaCatalogo);
		for (int i = 0; i < listaModificataUno.size(); i++) {
			if (listaModificataUno.get(i).getCodiceISBN() == codice) {
				// System.out.println("Ecco il ISBN: " +
				// listaModificataUno.get(i).getCodiceISBN());
				System.out.println("È stato eliminato il seguente elemento dal catalogo: " + listaModificataUno.get(i));
				listaModificataUno.remove(listaModificataUno.get(i));
			}
		}
		if (listaModificataUno.size() == listaCompletaCatalogo.size()) {
			System.out.println("Elemento non trovato");
		} else {
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
		System.out.println("\nEcco la lista del Catalogo:");
		listaCompletaCatalogo.forEach(e -> System.out.println(e));
		// System.out.println("grandezza "+listaCompletaCatalogo.size());
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
			System.out.println("Ecco la lista degli elementi del catalogo scritti nel " + s);
			listaModificatoTre.forEach(e -> System.out.println(e));
		}
	}

	public static void ricercaConAutore(String c) {
		List<Libro> listaModificatoQuattro = new ArrayList<Libro>();
		listaCompletaCatalogo.forEach(e -> {
			if (e instanceof Libro) {
				// listaModificatoQuattro.add
				if (((Libro) (e)).getAutore().equals(c)) {
					listaModificatoQuattro.add((Libro) e);
				}
			}
		});
		if (listaModificatoQuattro.size() == 0) {
			System.out.println("Elemento non trovato");
		} else {
			System.out
					.println("Ecco la lista dei libri del catalogo scritti da " + c + ":");
			listaModificatoQuattro.forEach(e -> System.out.println(e));
		}
	}

	/*
	 * public static void scritturaSuFile() throws IOException {
	 * 
	 * stampaListaTotale();
	 * listaCompletaCatalogo.forEach(e -> {
	 * try {
	 * if (e instanceof Libro) {
	 * Libro l = ((Libro) e);
	 * String stringaLibro = ("#"+"Libro" + l.getCodiceISBN() + "@" + l.getTitolo()
	 * + "@"
	 * + l.getAnnoPubblicazione()
	 * + "@" + l.getNumeroPagine() + "@" + l.getAutore() + "@" + l.getGenere());
	 * FileUtils.writeStringToFile(file, stringaLibro, "UTF-8", true);
	 * } else if (e instanceof Rivista) {
	 * Rivista r = ((Rivista) e);
	 * String stringaRivista = ("#"+"Rivista" + r.getCodiceISBN() + "&" +
	 * r.getTitolo() + "&"
	 * + r.getAnnoPubblicazione() + "&" + r.getNumeroPagine() + "&" +
	 * r.getPeriodo());
	 * FileUtils.writeStringToFile(file, stringaRivista, "UTF-8", true);
	 * }
	 * } catch (IOException h) {
	 * h.printStackTrace();
	 * }
	 * });
	 * letturaSuFile();
	 * }
	 */
	public static void letturaSuFile() throws IOException {
		String scritto = FileUtils.readFileToString(file, "UTF-8");

		String[] risultatoCatalogo = scritto.split("#");
		for (int i = 1; i < risultatoCatalogo.length; i++) {
			if (risultatoCatalogo[i].startsWith("Libro")) {
				String[] libro = risultatoCatalogo[i].split("Libro");
				// System.out.println("Risultato libro:" +libro[1]);
				String[] datiLibro = libro[1].split("@");
				// System.out.println("CodiceIsbn "+datiLibro[0] + "\nTitolo " + datiLibro[1] +
				// "\nAnnoPubblicazione "+datiLibro[2] +"\nNumeroPagine "+datiLibro[3]+"\nAutore
				// "+datiLibro[4]+"\nGenere: "+datiLibro[5]);
				listaCompletaCatalogo.add(new Libro(Long.parseLong(datiLibro[0]), datiLibro[1], datiLibro[2],
						Integer.parseInt(datiLibro[3]), datiLibro[4], datiLibro[5]));
			} else if (risultatoCatalogo[i].startsWith("Rivista")) {
				String[] rivista = risultatoCatalogo[i].split("Rivista");
				// System.out.println("Risultato rivista:" +rivista[1]);
				String[] datiRivista = rivista[1].split("&");
				// System.out.println("CodiceIsbn "+datiRivista[0] + "\nTitolo " +
				// datiRivista[1] );
				Periodicita periodo = Periodicita.Mensile;
				switch (datiRivista[4]) {
					case "Mensile":
						periodo = Periodicita.Mensile;
						break;
					case "Settimanale":
						periodo = Periodicita.Settimanale;
						break;
					case "Semestrale":
						periodo = Periodicita.Semestrale;
						break;
					default:
						break;
				}
				listaCompletaCatalogo.add(new Rivista(Long.parseLong(datiRivista[0]), datiRivista[1], datiRivista[2],
						Integer.parseInt(datiRivista[3]), periodo));
			}
		}

	}

}
