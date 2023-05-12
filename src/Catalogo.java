
public abstract class Catalogo {
	private long CodiceISBN;
    private String Titolo;
    private String AnnoPubblicazione;
    private int NumeroPagine;
    
	public Catalogo(long codiceISBN, String titolo, String annoPubblicazione, int numeroPagine) {
		this.CodiceISBN = codiceISBN;
		this.Titolo = titolo;
		this.AnnoPubblicazione = annoPubblicazione;
		this.NumeroPagine = numeroPagine;
	}

	public long getCodiceISBN() {
		return CodiceISBN;
	}

	public void setCodiceISBN(long codiceISBN) {
		CodiceISBN = codiceISBN;
	}

	public String getTitolo() {
		return Titolo;
	}

	public void setTitolo(String titolo) {
		Titolo = titolo;
	}

	public String getAnnoPubblicazione() {
		return AnnoPubblicazione;
	}

	public void setAnnoPubblicazione(String annoPubblicazione) {
		AnnoPubblicazione = annoPubblicazione;
	}

	public int getNumeroPagine() {
		return NumeroPagine;
	}

	public void setNumeroPagine(int numeroPagine) {
		NumeroPagine = numeroPagine;
	}
	
}
