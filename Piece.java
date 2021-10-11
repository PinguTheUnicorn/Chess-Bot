//clasa ce modeleaza o piesa
public class Piece {
	//numele piesei
	private String nume;
	//culoarea piesei
	private String culoare;

	//constructor
	public Piece(String nume, String culoare) {
		this.nume = nume;
		this.culoare = culoare;
	}

	//geters
	public String getNume() {
		return nume;
	}

	public String getCuloare() {
		return culoare;
	}

	public String toString() {
		return nume + " " + culoare;
	}
}
