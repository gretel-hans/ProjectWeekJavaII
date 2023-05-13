
public abstract class Catalogo {
	private static long CodiceISBN=0;
    private String Titolo;
    private String AnnoPubblicazione;
    private int NumeroPagine;
    
	public Catalogo(String titolo, String annoPubblicazione, int numeroPagine) {
		this.Titolo = titolo;
		this.AnnoPubblicazione = annoPubblicazione;
		this.NumeroPagine = numeroPagine;
		this.CodiceISBN=this.getCodiceISBN()+1;
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
