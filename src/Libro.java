
public class Libro extends Catalogo {
	private String Autore;
    private String Genere;

	public Libro(String titolo, String annoPubblicazione, int numeroPagine, String autore,
			String genere) {
		super(titolo, annoPubblicazione, numeroPagine);
		Autore = autore;
		Genere = genere;
	}

	@Override
	public String toString() {
		return "Libro [Autore=" + Autore + ", Genere=" + Genere + ", CodiceISBN=" + super.getCodiceISBN() + ", Titolo=" + super.getTitolo()+ ", AnnoPubblicazione=" + super.getAnnoPubblicazione() + ", NumeroPagine=" + super.getNumeroPagine() + "]";
	}

	public String getAutore() {
		return Autore;
	}

	public void setAutore(String autore) {
		Autore = autore;
	}

	public String getGenere() {
		return Genere;
	}

	public void setGenere(String genere) {
		Genere = genere;
	}
	
}
