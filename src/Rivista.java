
public class Rivista extends Catalogo {
	private Periodicita periodo;

	public Rivista(long CodiceISBN, String titolo, String annoPubblicazione, int numeroPagine, Periodicita periodo) {
		super(CodiceISBN, titolo, annoPubblicazione, numeroPagine);
		this.periodo = periodo;
	}

	public Periodicita getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(Periodicita periodo) {
		this.periodo=periodo;
	}

	@Override
	public String toString() {
		return "Rivista [periodo=" + periodo + ", CodiceISBN=" + super.getCodiceISBN() + ", Titolo=" + super.getTitolo() + ", AnnoPubblicazione=" + super.getAnnoPubblicazione() + ", NumeroPagine=" + super.getNumeroPagine() + "]";
	}
}
