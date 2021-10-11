import java.util.Collections;
import java.util.StringTokenizer;
import java.util.Vector;
import java.lang.Math;

//clasa ce modeleaza tabla de sah

public class Board {
	//matrice pentru stocarea pieselor
	Piece[][] tabla;
	int kingBlackX = 7;
	int kingBlackY = 3;
	int kingWhiteX = 0;
	int kingWhiteY = 3;
	boolean rookRightBlack = true;
	boolean rookLeftBlack = true;
	boolean rookRightWhite = true;
	boolean rookLeftWhite = true;
	boolean kingWhite = true;
	boolean kingBlack = true;

	Board(Board old) {
		this.tabla = new Piece[8][8];
		this.kingBlackX = old.kingBlackX;
		this.kingBlackY = old.kingBlackY;
		this.kingWhiteX = old.kingWhiteX;
		this.kingWhiteY = old.kingWhiteY;

		this.rookLeftBlack = old.rookLeftBlack;
		this.rookLeftWhite = old.rookLeftWhite;
		this.rookRightBlack = old.rookRightBlack;
		this.rookRightWhite = old.rookRightWhite;

		this.kingWhite = old.kingWhite;
		this.kingBlack = old.kingBlack;

		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if (old.tabla[i][j] == null) {
					this.tabla[i][j] = null;
				} else {
					this.tabla[i][j] = new Piece(old.tabla[i][j].getNume(),
												old.tabla[i][j].getCuloare());
				}
			}
		}
	}

	//constructor
	Board() {
		//se aloca memorie pentru matrice
		tabla = new Piece[8][8];

		/* se pun piesele in locul de inceput pe tabla
		   in functie de culoare si tipul piesei */
		tabla[0][0] = new Piece("rook", "w");
		tabla[0][1] = new Piece("knight", "w");
		tabla[0][2] = new Piece("bishop", "w");
		tabla[0][3] = new Piece("king", "w");
		tabla[0][4] = new Piece("queen", "w");
		tabla[0][5] = new Piece("bishop", "w");
		tabla[0][6] = new Piece("knight", "w");
		tabla[0][7] = new Piece("rook", "w");

		for(int i = 0; i < 8; i++) {
			tabla[1][i] = new Piece("pawn", "w");
		}

		for(int i = 2; i < 6; i++) {
			for(int j = 0; j < 8; j++)
				tabla[i][j] = null;
		}

		for(int i = 0; i < 8; i++) {
			tabla[6][i] = new Piece("pawn", "b");
		}

		tabla[7][0] = new Piece("rook", "b");
		tabla[7][1] = new Piece("knight", "b");
		tabla[7][2] = new Piece("bishop", "b");
		tabla[7][3] = new Piece("king", "b");
		tabla[7][4] = new Piece("queen", "b");
		tabla[7][5] = new Piece("bishop", "b");
		tabla[7][6] = new Piece("knight", "b");
		tabla[7][7] = new Piece("rook", "b");
	}

	/* metoda ce muta o piesa pe tabla
	   se presupune ca mutarea este valida */
	public void move(String s) {
		//se face separarea input-ului
		StringTokenizer tok = new StringTokenizer(s, " ");
		//se ia elementul urmator ce reprezinta mutarea in sine
		tok.nextToken();
		/* pentru a putea aplica modificarile mai usor, se face conversia la un
		   array de char-uri */
		char[] mutare = new char[5];
		mutare = tok.nextToken().toCharArray();

		//se calculeaza pozitia initiala si cea finala
		int x1 = 7 - (int)(mutare[0] - 'a');
		int y1 = (int)(mutare[1] - '1');

		int x2 = 7 - (int)(mutare[2] - 'a');
		int y2 = (int)(mutare[3] - '1');

		//se preia piesa
		Piece p = tabla[y1][x1];

		if (p.getNume().equals("king")) {
			if (p.getCuloare().equals("b")) {
				kingBlackX = y2;
				kingBlackY = x2;
				kingBlack = false;
				if (x2 - x1 == 2) {
					tabla[7][5] = p;
					tabla[7][3] = null;
					tabla[7][4] = tabla[7][7];
					tabla[7][7] = null;
					return;
				}
				if (x1 - x2 == 2) {
					tabla[7][1] = p;
					tabla[7][3] = null;
					tabla[7][2] = tabla[7][0];
					tabla[7][0] = null;
					return;
				}
			} else {
				kingWhiteX = y2;
				kingWhiteY = x2;
				kingWhite = false;
				if (x2 - x1 == 2) {
					tabla[0][5] = p;
					tabla[0][3] = null;
					tabla[0][4] = tabla[0][7];
					tabla[0][7] = null;
					return;
				}
				if (x1 - x2 == 2) {
					tabla[0][1] = p;
					tabla[0][3] = null;
					tabla[0][2] = tabla[0][0];
					tabla[0][0] = null;
					return;
				}
			}
		}
		if (p != null && p.getNume().equals("pawn") && tabla[y2][x2] == null &&
				tabla[y1][x2] != null && tabla[y1][x2].getNume().equals("pawn")
				&& !tabla[y1][x2].getCuloare().equals(p.getCuloare())) {
			tabla[y1][x2] = null;
		}
		if (p != null && p.getNume().equals("rook")) {
			if (y1 == 7 && x1 == 0) {
				rookLeftBlack = false;
			}
			if (y1 == 7 && x1 == 7) {
				rookRightBlack = false;
			}
			if (y1 == 0 && x1 == 0) {
				rookLeftWhite = false;
			}
			if (y1 == 0 && x1 == 7) {
				rookRightWhite = false;
			}
		}

		//se fac modificarile necesare pe tabla
		this.tabla[y2][x2] = p;
		this.tabla[y1][x1] = null;
		if (mutare.length == 5) {
			if (mutare[4] == 'q') {
				Piece q = new Piece("queen", this.tabla[y2][x2].getCuloare());
				this.tabla[y2][x2] = q;
			} else if (mutare[4] == 'r') {
				Piece r = new Piece("rook", this.tabla[y2][x2].getCuloare());
				this.tabla[y2][x2] = r;
			} else if (mutare[4] == 'b') {
				Piece b = new Piece("bishop", this.tabla[y2][x2].getCuloare());
				this.tabla[y2][x2] = b;
			} else if (mutare[4] == 'n') {
				Piece k = new Piece("knight", this.tabla[y2][x2].getCuloare());
				this.tabla[y2][x2] = k;
			}
		}
	}

	//metoda ce genereaza o mutare din partea negrului
	public Vector<String> generateMovePawnBlack() {
		//se creaza un vector pentru stocarea pozitiilor pieselor ce pot fi mutate
		Vector<String> v = new Vector<>();

		//se cauta toti pionii care pot face o mutare legala
		for(int i = 6; i > 0; i--) {
			for(int j = 0; j < 8; j++) {

				/* se verifica daca este o piesa ce poate fi mutata(in aceasta
					etapa, pionul) si daca se afla de culoarea bot-ului */
				 if (tabla[i][j] != null && tabla[i][j].getNume().equals("pawn")
							&& tabla[i][j].getCuloare().equals("b")) {

					//se verifica tipul de miscare pe care poate sa o faca
					if (tabla[i-1][j] == null) {
						String mutare = "move ";
						mutare += (char)('h' - j);
						mutare += (char)('1' + i);
						mutare += (char)('h' - j);
						mutare += (char)('1' + (i - 1));
						if (!isCheck(mutare, "b")) {
							if (i - 1 == 0) {
								String mutareQ = mutare + 'q';
								String mutareR = mutare + 'r';
								String mutareN = mutare + 'n';
								String mutareB = mutare + 'b';
								v.add(mutareQ);
								v.add(mutareR);
								v.add(mutareN);
								v.add(mutareB);
							} else {
								v.add(mutare);
							}
						}
					}
					if (i == 6 && tabla[i-1][j] == null && tabla[i-2][j] == null) {
						String mutare = "move ";
						mutare += (char)('h' - j);
						mutare += (char)('1' + i);
						mutare += (char)('h' - j);
						mutare += (char)('1' + (i - 2));
						if (!isCheck(mutare, "b")) {
							v.add(mutare);
						}
					}
					if (j - 1 >= 0 && tabla[i-1][j-1] != null &&
					tabla[i-1][j-1].getCuloare().equals("w") &&
					!tabla[i-1][j-1].getNume().equals("king")) {
						String mutare = "move ";
						mutare += (char)('h' - j);
						mutare += (char)('1' + i);
						mutare += (char)('h' - (j - 1));
						mutare += (char)('1' + (i - 1));
						if (!isCheck(mutare, "b")) {
							if (i - 1 == 0) {
								String mutareQ = mutare + 'q';
								String mutareR = mutare + 'r';
								String mutareN = mutare + 'n';
								String mutareB = mutare + 'b';
								v.add(mutareQ);
								v.add(mutareR);
								v.add(mutareN);
								v.add(mutareB);
							} else {
								v.add(mutare);
							}
						}
					}
					if (j + 1 <= 7 && tabla[i-1][j+1] != null &&
					tabla[i-1][j+1].getCuloare().equals("w") &&
					!tabla[i-1][j+1].getNume().equals("king")) {
						String mutare = "move ";
						mutare += (char)('h' - j);
						mutare += (char)('1' + i);
						mutare += (char)('h' - (j + 1));
						mutare += (char)('1' + (i - 1));
						if (!isCheck(mutare, "b")) {
							if (i - 1 == 0) {
								String mutareQ = mutare + 'q';
								String mutareR = mutare + 'r';
								String mutareN = mutare + 'n';
								String mutareB = mutare + 'b';
								v.add(mutareQ);
								v.add(mutareR);
								v.add(mutareN);
								v.add(mutareB);
							} else {
								v.add(mutare);
							}
						}
					}
				}
			}
		}

		return v;
	}

	/* metoda pentru mutarea pieselor albe, similara celei pentru mutarea
	   pieselor negre */
	public Vector<String> generateMovePawnWhite() {
		Vector<String> v = new Vector<>();

		for(int i = 1; i < 7; i++) {
			for(int j = 0; j < 8; j++) {
				 if (tabla[i][j] != null && tabla[i][j].getNume().equals("pawn")
						&& tabla[i][j].getCuloare().equals("w")) {
					if (tabla[i+1][j] == null) {
						String mutare = "move ";
						mutare += (char)('h' - j);
						mutare += (char)('1' + i);
						mutare += (char)('h' - j);
						mutare += (char)('1' + (i + 1));
						if (!isCheck(mutare, "w")) {
							if (i + 1 == 7) {
								String mutareQ = mutare + 'q';
								String mutareR = mutare + 'r';
								String mutareN = mutare + 'n';
								String mutareB = mutare + 'b';
								v.add(mutareQ);
								v.add(mutareR);
								v.add(mutareN);
								v.add(mutareB);
							} else {
								v.add(mutare);
							}
						}
					}
					if (i == 1 && tabla[i+1][j] == null && tabla[i+2][j] == null) {
						String mutare = "move ";
						mutare += (char)('h' - j);
						mutare += (char)('1' + i);
						mutare += (char)('h' - j);
						mutare += (char)('1' + (i + 2));
						if (!isCheck(mutare, "w")) {
							v.add(mutare);
						}
					}
					if (j - 1 >= 0 && tabla[i+1][j-1] != null &&
					tabla[i+1][j-1].getCuloare().equals("b") &&
					!tabla[i+1][j-1].getNume().equals("king")) {
						String mutare = "move ";
						mutare += (char)('h' - j);
						mutare += (char)('1' + i);
						mutare += (char)('h' - (j - 1));
						mutare += (char)('1' + (i + 1));
						if (!isCheck(mutare, "w")) {
							if (i + 1 == 7) {
								String mutareQ = mutare + 'q';
								String mutareR = mutare + 'r';
								String mutareN = mutare + 'n';
								String mutareB = mutare + 'b';
								v.add(mutareQ);
								v.add(mutareR);
								v.add(mutareN);
								v.add(mutareB);
							} else {
								v.add(mutare);
							}
						}
					}
					if (j + 1 <= 7 && tabla[i+1][j+1] != null &&
					tabla[i+1][j+1].getCuloare().equals("b") &&
					!tabla[i+1][j+1].getNume().equals("king")) {
						String mutare = "move ";
						mutare += (char)('h' - j);
						mutare += (char)('1' + i);
						mutare += (char)('h' - (j + 1));
						mutare += (char)('1' + (i + 1));
						if (!isCheck(mutare, "w")) {
							if (i + 1 == 7) {
								String mutareQ = mutare + 'q';
								String mutareR = mutare + 'r';
								String mutareN = mutare + 'n';
								String mutareB = mutare + 'b';
								v.add(mutareQ);
								v.add(mutareR);
								v.add(mutareN);
								v.add(mutareB);
							} else {
								v.add(mutare);
							}
						}
					}
				}
			}
		}

		return v;
	}

	public Vector<String> generateMoveVertHorz(int x, int y, String culoare) {
		Vector<String> v = new Vector<>(4);

		for(int j = y+1; j <= 7; j++) {
			if (tabla[x][j] == null) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - j);
				mutare += (char)('1' + x);
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
			} else if (!tabla[x][j].getCuloare().equals(culoare) &&
			!tabla[x][j].getNume().equals("king")) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - j);
				mutare += (char)('1' + x);
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
				break;
			} else {
				break;
			}
		}

		for(int i = x-1; i >= 0; i--) {
			if (tabla[i][y] == null) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - y);
				mutare += (char)('1' + i);
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
			} else if (!tabla[i][y].getCuloare().equals(culoare) &&
			!tabla[i][y].getNume().equals("king")) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - y);
				mutare += (char)('1' + i);
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
				break;
			} else {
				break;
			}
		}

		for(int j = y-1; j >= 0; j--) {
			if (tabla[x][j] == null) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - j);
				mutare += (char)('1' + x);
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
			} else if (!tabla[x][j].getCuloare().equals(culoare) &&
			!tabla[x][j].getNume().equals("king")) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - j);
				mutare += (char)('1' + x);
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
				break;
			} else {
				break;
			}
		}

		for(int i = x+1; i <= 7; i++) {
			if (tabla[i][y] == null) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - y);
				mutare += (char)('1' + i);
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
			} else if (!tabla[i][y].getCuloare().equals(culoare) &&
			!tabla[i][y].getNume().equals("king")) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - y);
				mutare += (char)('1' + i);
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
				break;
			} else {
				break;
			}
		}

		return v;
	}

	public Vector<String> moveRook(String culoare) {
		Vector<String> v = new Vector<>();

		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if (tabla[i][j] != null && tabla[i][j].getNume().equals("rook")
				&& tabla[i][j].getCuloare().equals(culoare)) {
					v.addAll(generateMoveVertHorz(i, j, culoare));
				}
			}
		}

		return v;
	}

	public Vector<String> generateMoveDiagonally(int x, int y, String culoare) {
		Vector<String> v = new Vector<>();

		int i = 1;

		while(x - i >= 0 && y - i >= 0) {
			if (tabla[x-i][y-i] == null) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - (y - i));
				mutare += (char)('1' + (x - i));
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
				i++;
			} else if (!tabla[x-i][y-i].getCuloare().equals(culoare) &&
			!tabla[x-i][y-i].getNume().equals("king")) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - (y - i));
				mutare += (char)('1' + (x - i));
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
				break;
			} else {
				break;
			}
		}

		i = 1;

		while(x + i <= 7 && y - i >= 0) {
			if (tabla[x+i][y-i] == null) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - (y - i));
				mutare += (char)('1' + (x + i));
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
				i++;
			} else if (!tabla[x+i][y-i].getCuloare().equals(culoare) &&
			!tabla[x+i][y-i].getNume().equals("king")) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - (y - i));
				mutare += (char)('1' + (x + i));
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
				break;
			} else {
				break;
			}
		}

		i = 1;

		while(x + i <= 7 && y + i <= 7) {
			if (tabla[x+i][y+i] == null) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - (y + i));
				mutare += (char)('1' + (x + i));
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
				i++;
			} else if (!tabla[x+i][y+i].getCuloare().equals(culoare) &&
			!tabla[x+i][y+i].getNume().equals("king")) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - (y + i));
				mutare += (char)('1' + (x + i));
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
				break;
			} else {
				break;
			}
		}

		i = 1;

		while(x - i >= 0 && y + i <= 7) {
			if (tabla[x-i][y+i] == null) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - (y + i));
				mutare += (char)('1' + (x - i));
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
				i++;
			} else if (!tabla[x-i][y+i].getCuloare().equals(culoare) &&
			!tabla[x-i][y+i].getNume().equals("king")) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - (y + i));
				mutare += (char)('1' + (x - i));
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
				break;
			} else {
				break;
			}
		}

		return v;
	}

	public Vector<String> moveBishop(String culoare) {
		Vector<String> v = new Vector<>();

		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if (tabla[i][j] != null && tabla[i][j].getNume().equals("bishop")
				&& tabla[i][j].getCuloare().equals(culoare)) {
					v.addAll(generateMoveDiagonally(i, j, culoare));
				}
			}
		}

		return v;
	}

	public Vector<String> moveQueen(String culoare) {
		Vector<String> v = new Vector<>();

		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if (tabla[i][j] != null && tabla[i][j].getNume().equals("queen")
				&& tabla[i][j].getCuloare().equals(culoare)) {
					v.addAll(generateMoveDiagonally(i, j, culoare));

					v.addAll(generateMoveVertHorz(i, j, culoare));
				}
			}
		}

		return v;
	}

	public Vector<String> moveKnight(String culoare) {
		Vector<String> v = new Vector<>();

		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if (tabla[i][j] != null && tabla[i][j].getNume().equals("knight")
				&& tabla[i][j].getCuloare().equals(culoare)) {
					if (i - 2 >= 0 && j - 1 >= 0 && (tabla[i-2][j-1] == null ||
					(!tabla[i-2][j-1].getCuloare().equals(culoare) &&
					!tabla[i-2][j-1].getNume().equals("king")))) {
						String move = "move ";
						move += (char)('h' - j);
						move += (char)('1' + i);
						move += (char)('h' - (j-1));
						move += (char)('1' + (i-2));
						if (!isCheck(move, culoare)) {
							v.add(move);
						}
					}

					if (i - 2 >= 0 && j + 1 <= 7 && (tabla[i-2][j+1] == null ||
					(!tabla[i-2][j+1].getCuloare().equals(culoare) &&
					!tabla[i-2][j+1].getNume().equals("king")))) {
						String move = "move ";
						move += (char)('h' - j);
						move += (char)('1' + i);
						move += (char)('h' - (j+1));
						move += (char)('1' + (i-2));
						if (!isCheck(move, culoare)) {
							v.add(move);
						}
					}

					if (i - 1 >= 0 && j - 2 >= 0 && (tabla[i-1][j-2] == null ||
					(!tabla[i-1][j-2].getCuloare().equals(culoare) &&
					!tabla[i-1][j-2].getNume().equals("king")))) {
						String move = "move ";
						move += (char)('h' - j);
						move += (char)('1' + i);
						move += (char)('h' - (j-2));
						move += (char)('1' + (i-1));
						if (!isCheck(move, culoare)) {
							v.add(move);
						}
					}

					if (i - 1 >= 0 && j + 2 <= 7 && (tabla[i-1][j+2] == null ||
					(!tabla[i-1][j+2].getCuloare().equals(culoare) &&
					!tabla[i-1][j+2].getNume().equals("king")))) {
						String move = "move ";
						move += (char)('h' - j);
						move += (char)('1' + i);
						move += (char)('h' - (j+2));
						move += (char)('1' + (i-1));
						if (!isCheck(move, culoare)) {
							v.add(move);
						}
					}

					if (i + 1 <= 7 && j - 2 >= 0 && (tabla[i+1][j-2] == null ||
					(!tabla[i+1][j-2].getCuloare().equals(culoare) &&
					!tabla[i+1][j-2].getNume().equals("king")))) {
						String move = "move ";
						move += (char)('h' - j);
						move += (char)('1' + i);
						move += (char)('h' - (j-2));
						move += (char)('1' + (i+1));
						if (!isCheck(move, culoare)) {
							v.add(move);
						}
					}

					if (i + 1 <= 7 && j + 2 <= 7 && (tabla[i+1][j+2] == null ||
					(!tabla[i+1][j+2].getCuloare().equals(culoare) &&
					!tabla[i+1][j+2].getNume().equals("king")))) {
						String move = "move ";
						move += (char)('h' - j);
						move += (char)('1' + i);
						move += (char)('h' - (j+2));
						move += (char)('1' + (i+1));
						if (!isCheck(move, culoare)) {
							v.add(move);
						}
					}

					if (i + 2 <= 7 && j - 1 >= 0 && (tabla[i+2][j-1] == null ||
					(!tabla[i+2][j-1].getCuloare().equals(culoare) &&
					!tabla[i+2][j-1].getNume().equals("king")))) {
						String move = "move ";
						move += (char)('h' - j);
						move += (char)('1' + i);
						move += (char)('h' - (j-1));
						move += (char)('1' + (i+2));
						if (!isCheck(move, culoare)) {
							v.add(move);
						}
					}

					if (i + 2 <= 7 && j + 1 <= 7 && (tabla[i+2][j+1] == null ||
					(!tabla[i+2][j+1].getCuloare().equals(culoare) &&
					!tabla[i+2][j+1].getNume().equals("king")))) {
						String move = "move ";
						move += (char)('h' - j);
						move += (char)('1' + i);
						move += (char)('h' - (j+1));
						move += (char)('1' + (i+2));
						if (!isCheck(move, culoare)) {
							v.add(move);
						}
					}
				}
			}
		}

		return v;
	}

	public Vector<String> EnPassant(String lastMove) {
		Vector<String> v = new Vector<>();
		if (lastMove == null) {
			return v;
		}

		StringTokenizer tok = new StringTokenizer(lastMove, " ");
		tok.nextToken();

		char[] mutare = new char[5];
		mutare = tok.nextToken().toCharArray();

		int y1 = (int)(mutare[1] - '1');

		int x2 = 7 - (int)(mutare[2] - 'a');
		int y2 = (int)(mutare[3] - '1');

		if (tabla[y2][x2].getNume().equals("pawn") && Math.abs(y2 - y1) == 2) {
			if (x2 + 1 <= 7 && tabla[y2][x2+1] != null &&
			tabla[y2][x2+1].getNume().equals("pawn") &&
			!tabla[y2][x2+1].getCuloare().equals(tabla[y2][x2].getCuloare())) {
				String move = "move ";
				move += (char)('h' - (x2 + 1));
				move += (char)('1' + y2);
				move += (char)('h' - x2);
				if (y2 > y1) {
					move += (char)('1' + (y2 - 1));
				} else {
					move += (char)('1' + (y2 + 1));
				}

				if (!isCheck(move, tabla[y2][x2+1].getCuloare())) {
					v.add(move);
				}
			}

			if (x2 - 1 >= 0 && tabla[y2][x2-1] != null &&
			tabla[y2][x2-1].getNume().equals("pawn") &&
			!tabla[y2][x2-1].getCuloare().equals(tabla[y2][x2].getCuloare())) {
				String move = "move ";
				move += (char)('h' - (x2 - 1));
				move += (char)('1' + y2);
				move += (char)('h' - x2);
				if (y2 > y1) {
					move += (char)('1' + (y2 - 1));
				} else {
					move += (char)('1' + (y2 + 1));
				}

				if (!isCheck(move, tabla[y2][x2-1].getCuloare())) {
					v.add(move);
				}
			}
		}

		return v;
	}

	public Vector<String> moveKing(String culoare) {
		int x, y;
		Vector<String> v = new Vector<>();

		if (culoare.equals("b")) {
			x = kingBlackX;
			y = kingBlackY;
		} else {
			x = kingWhiteX;
			y = kingWhiteY;
		}

		//horz vert

		if (x - 1 >= 0) {
			if (tabla[x-1][y] == null) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - y);
				mutare += (char)('1' + (x - 1));
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
			} else if (!tabla[x-1][y].getCuloare().equals(culoare) &&
			!tabla[x-1][y].getNume().equals("king")) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - y);
				mutare += (char)('1' + (x - 1));
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
			}
		}

		if (x + 1 <= 7) {
			if (tabla[x+1][y] == null) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - y);
				mutare += (char)('1' + (x + 1));
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
			} else if (!tabla[x+1][y].getCuloare().equals(culoare) &&
			!tabla[x+1][y].getNume().equals("king")) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - y);
				mutare += (char)('1' + (x + 1));
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
			}
		}

		if (y + 1 <= 7) {
			if (tabla[x][y+1] == null) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - (y + 1));
				mutare += (char)('1' + x);
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
			} else if (!tabla[x][y+1].getCuloare().equals(culoare) &&
			!tabla[x][y+1].getNume().equals("king")) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - (y + 1));
				mutare += (char)('1' + x);
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
			}
		}

		if (y - 1 >= 0) {
			if (tabla[x][y-1] == null) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - (y - 1));
				mutare += (char)('1' + x);
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
			} else if (!tabla[x][y-1].getCuloare().equals(culoare) &&
			!tabla[x][y-1].getNume().equals("king")) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - (y - 1));
				mutare += (char)('1' + x);
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
			}
		}

		//diagonala

		if (y + 1 <= 7 && x - 1 >= 0) {
			if (tabla[x-1][y+1] == null) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - (y + 1));
				mutare += (char)('1' + (x - 1));
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
			} else if (!tabla[x-1][y+1].getCuloare().equals(culoare) &&
			!tabla[x-1][y+1].getNume().equals("king")) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - (y + 1));
				mutare += (char)('1' + (x - 1));
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
			}
		}

		if (y + 1 <= 7 && x + 1 <= 7) {
			if (tabla[x+1][y+1] == null) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - (y + 1));
				mutare += (char)('1' + (x + 1));
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
			} else if (!tabla[x+1][y+1].getCuloare().equals(culoare) &&
			!tabla[x+1][y+1].getNume().equals("king")) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - (y + 1));
				mutare += (char)('1' + (x + 1));
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
			}
		}

		if (y - 1 >= 0 && x + 1 <= 7) {
			if (tabla[x+1][y-1] == null) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - (y - 1));
				mutare += (char)('1' + (x + 1));
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
			} else if (!tabla[x+1][y-1].getCuloare().equals(culoare) &&
			!tabla[x+1][y-1].getNume().equals("king")) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - (y - 1));
				mutare += (char)('1' + (x + 1));
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
			}
		}

		if (y - 1 >= 0 && x - 1 >= 0) {
			if (tabla[x-1][y-1] == null) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - (y - 1));
				mutare += (char)('1' + (x - 1));
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
			} else if (!tabla[x-1][y-1].getCuloare().equals(culoare) &&
			!tabla[x-1][y-1].getNume().equals("king")) {
				String mutare = "move ";
				mutare += (char)('h' - y);
				mutare += (char)('1' + x);
				mutare += (char)('h' - (y - 1));
				mutare += (char)('1' + (x - 1));
				if (!isCheck(mutare, culoare)) {
					v.add(mutare);
				}
			}
		}

		return v;
	}

	public String generateMove(String culoare, String lastMove) {
		Vector<String> v = new Vector<>();

		if (culoare.equals("b")) {
			v.addAll(rocadaBlack());
			if (!v.isEmpty()) {
				return v.get(0);
			}
		} else {
			v.addAll(rocadaWhite());
			if (!v.isEmpty()) {
				return v.get(0);
			}
		}

		if (v.addAll(EnPassant(lastMove))) {
			Collections.shuffle(v);
			return v.get(0);
		}

		if (culoare.equals("b")) {
			v.addAll(generateMovePawnBlack());
		} else {
			v.addAll(generateMovePawnWhite());
		}

		v.addAll(moveQueen(culoare));

		v.addAll(moveKing(culoare));

		v.addAll(moveBishop(culoare));

		v.addAll(moveRook(culoare));

		v.addAll(moveKnight(culoare));
		if (v.isEmpty() || v.size() == 0) {
			String ret = "resign";
			return ret;
		}

		Collections.shuffle(v);
		return v.get(0);
	}

	public boolean isCheck(String mutare, String culoare) {
		int x, y;
		Board copie = new Board(this);
		copie.move(mutare);

		if (culoare.equals("b")) {
			x = copie.kingBlackX;
			y = copie.kingBlackY;
		} else {
			x = copie.kingWhiteX;
			y = copie.kingWhiteY;
		}

		//cal
		if (x - 2 >= 0 && y - 1 >= 0 && copie.tabla[x-2][y-1] != null) {
			if (copie.tabla[x-2][y-1].getNume().equals("knight") &&
			!copie.tabla[x-2][y-1].getCuloare().equals(culoare)) {
				return true;
			}
		}
		if (x - 2 >= 0 && y + 1 <= 7 && copie.tabla[x-2][y+1] != null) {
			if (copie.tabla[x-2][y+1].getNume().equals("knight") &&
			!copie.tabla[x-2][y+1].getCuloare().equals(culoare)) {
				return true;
			}
		}
		if (x - 1 >= 0 && y - 2 >= 0 && copie.tabla[x-1][y-2] != null) {
			if (copie.tabla[x-1][y-2].getNume().equals("knight") &&
			!copie.tabla[x-1][y-2].getCuloare().equals(culoare)) {
				return true;
			}
		}
		if (x - 1 >= 0 && y + 2 <= 7 && copie.tabla[x-1][y+2] != null) {
			if (copie.tabla[x-1][y+2].getNume().equals("knight") &&
			!copie.tabla[x-1][y+2].getCuloare().equals(culoare)) {
				return true;
			}
		}
		if (x + 1 <= 7 && y - 2 >= 0 && copie.tabla[x+1][y-2] != null) {
			if (copie.tabla[x+1][y-2].getNume().equals("knight") &&
			!copie.tabla[x+1][y-2].getCuloare().equals(culoare)) {
				return true;
			}
		}
		if (x + 1 <= 7 && y + 2 <= 7 && copie.tabla[x+1][y+2] != null) {
			if (copie.tabla[x+1][y+2].getNume().equals("knight") &&
			!copie.tabla[x+1][y+2].getCuloare().equals(culoare)) {
				return true;
			}
		}
		if (x + 2 <= 7 && y + 1 <= 7 && copie.tabla[x+2][y+1] != null) {
			if (copie.tabla[x+2][y+1].getNume().equals("knight") &&
			!copie.tabla[x+2][y+1].getCuloare().equals(culoare)) {
				return true;
			}
		}
		if (x + 2 <= 7 && y - 1 >= 0 && copie.tabla[x+2][y-1] != null) {
			if (copie.tabla[x+2][y-1].getNume().equals("knight") &&
			!copie.tabla[x+2][y-1].getCuloare().equals(culoare)) {
				return true;
			}
		}

		//tura regina
		int i = 1;
		while(x - i >= 0) {
			if (copie.tabla[x-i][y] != null) {
				if (!copie.tabla[x-i][y].getCuloare().equals(culoare) &&
				(copie.tabla[x-i][y].getNume().equals("rook") ||
				copie.tabla[x-i][y].getNume().equals("queen"))) {
					return true;
				} else {
					break;
				}
			}
			i++;
		}

		i = 1;

		while (x + i <= 7) {
			if (copie.tabla[x+i][y] != null) {
				if (!copie.tabla[x+i][y].getCuloare().equals(culoare) &&
				(copie.tabla[x+i][y].getNume().equals("rook") ||
				copie.tabla[x+i][y].getNume().equals("queen"))) {
					return true;
				} else {
					break;
				}
			}
			i++;
		}

		i = 1;

		while (y + i <= 7) {
			if (copie.tabla[x][y+i] != null) {
				if (!copie.tabla[x][y+i].getCuloare().equals(culoare) &&
				(copie.tabla[x][y+i].getNume().equals("rook") ||
				copie.tabla[x][y+i].getNume().equals("queen"))) {
					return true;
				} else {
					break;
				}
			}
			i++;
		}

		i = 1;

		while (y - i >= 0) {
			if (copie.tabla[x][y-i] != null) {
				if (!copie.tabla[x][y-i].getCuloare().equals(culoare) &&
				(copie.tabla[x][y-i].getNume().equals("rook") ||
				copie.tabla[x][y-i].getNume().equals("queen"))) {
					return true;
				} else {
					break;
				}
			}
			i++;
		}

		//nebun regina
		i = 1;

		while (x - i >= 0 && y - i >= 0) {
			if (copie.tabla[x-i][y-i] != null) {
				if (!copie.tabla[x-i][y-i].getCuloare().equals(culoare) &&
				(copie.tabla[x-i][y-i].getNume().equals("bishop") ||
				copie.tabla[x-i][y-i].getNume().equals("queen"))) {
					return true;
				} else {
					break;
				}
			}
			i++;
		}

		i = 1;

		while (x + i <= 7 && y - i >= 0) {
			if (copie.tabla[x+i][y-i] != null) {
				if (!copie.tabla[x+i][y-i].getCuloare().equals(culoare) &&
				(copie.tabla[x+i][y-i].getNume().equals("bishop") ||
				copie.tabla[x+i][y-i].getNume().equals("queen"))) {
					return true;
				} else {
					break;
				}
			}
			i++;
		}

		i = 1;

		while (x + i <= 7 && y + i <= 7) {
			if (copie.tabla[x+i][y+i] != null) {
				if (!copie.tabla[x+i][y+i].getCuloare().equals(culoare) &&
				(copie.tabla[x+i][y+i].getNume().equals("bishop") ||
				copie.tabla[x+i][y+i].getNume().equals("queen"))) {
					return true;
				} else {
					break;
				}
			}
			i++;
		}

		i = 1;

		while (x - i >= 0 && y + i <= 7) {
			if (copie.tabla[x-i][y+i] != null) {
				if (!copie.tabla[x-i][y+i].getCuloare().equals(culoare) &&
				(copie.tabla[x-i][y+i].getNume().equals("bishop") ||
				copie.tabla[x-i][y+i].getNume().equals("queen"))) {
					return true;
				} else {
					break;
				}
			}
			i++;
		}

		// pion
		if (culoare.equals("b")) {
			if (x - 1 >= 0 && y - 1 >= 0 && copie.tabla[x-1][y-1] != null &&
			copie.tabla[x-1][y-1].getNume().equals("pawn") &&
			copie.tabla[x-1][y-1].getCuloare().equals("w")) {
				return true;
			}
			if (x - 1 >= 0 && y + 1 <= 7 && copie.tabla[x-1][y+1] != null &&
			copie.tabla[x-1][y+1].getNume().equals("pawn") &&
			copie.tabla[x-1][y+1].getCuloare().equals("w")) {
				return true;
			}
		} else {
			if (x + 1 <= 7 && y - 1 >= 0 && copie.tabla[x+1][y-1] != null &&
			copie.tabla[x+1][y-1].getNume().equals("pawn") &&
			copie.tabla[x+1][y-1].getCuloare().equals("b")) {
				return true;
			}
			if (x + 1 <= 7 && y + 1 <= 7 && copie.tabla[x+1][y+1] != null &&
			copie.tabla[x+1][y+1].getNume().equals("pawn") &&
			copie.tabla[x+1][y+1].getCuloare().equals("b")) {
				return true;
			}
		}

		//rege
		if (x - 1 >= 0 && copie.tabla[x-1][y] != null &&
		copie.tabla[x-1][y].getNume().equals("king")) {
			return true;
		}
		if (y - 1 >= 0 && copie.tabla[x][y-1] != null &&
		copie.tabla[x][y-1].getNume().equals("king")) {
			return true;
		}
		if (x + 1 <= 7 && copie.tabla[x+1][y] != null &&
		copie.tabla[x+1][y].getNume().equals("king")) {
			return true;
		}
		if (y + 1 <= 7 && copie.tabla[x][y+1] != null &&
		copie.tabla[x][y+1].getNume().equals("king")) {
			return true;
		}
		if (x - 1 >= 0 && y - 1 >= 0 && copie.tabla[x-1][y-1] != null &&
		copie.tabla[x-1][y-1].getNume().equals("king")) {
			return true;
		}
		if (x + 1 <= 7 && y - 1 >= 0 && copie.tabla[x+1][y-1] != null &&
		copie.tabla[x+1][y-1].getNume().equals("king")) {
			return true;
		}
		if (x - 1 >= 0 && y + 1 <= 7 && copie.tabla[x-1][y+1] != null &&
		copie.tabla[x-1][y+1].getNume().equals("king")) {
			return true;
		}
		if (x + 1 <= 7 && y + 1 <= 7 && copie.tabla[x+1][y+1] != null &&
		copie.tabla[x+1][y+1].getNume().equals("king")) {
			return true;
		}

		return false;
	}

	public Vector<String> rocadaBlack() {
		Vector<String> v = new Vector<>();
		boolean check = false;
		if (!kingBlack)
			return v;

		int x = kingBlackX;
		int y = kingBlackY;

		String stare = "move ";
		stare += (char)('h' - y);
		stare += (char)('1' + x);
		stare += (char)('h' - y);
		stare += (char)('1' + x);

		if (isCheck(stare, "b")) {
			return v;
		}

		if (rookLeftBlack && tabla[7][0] != null &&
		tabla[7][0].getNume().equals("rook") && tabla[x][y-1] == null
		&& tabla[x][y-2] == null) {
			String left1 = "move ";
			left1 += (char)('h' - y);
			left1 += (char)('1' + x);
			left1 += (char)('h' - (y - 1));
			left1 += (char)('1' + x);

			if (isCheck(left1, "b")) {
				check = true;
			}

			String left2 = "move ";
			left2 += (char)('h' - y);
			left2 += (char)('1' + x);
			left2 += (char)('h' - (y - 2));
			left2 += (char)('1' + x);

			if (isCheck(left2, "b")) {
				check = true;
			}

			if (!check) {
				v.add(left2);
			}
		}

		check = false;

		if (rookRightBlack && tabla[7][7] != null &&
		tabla[7][7].getNume().equals("rook") && tabla[x][y+1] == null &&
		tabla[x][y+2] == null && tabla[x][y+3] == null) {
			String right1 = "move ";
			right1 += (char)('h' - y);
			right1 += (char)('1' + x);
			right1 += (char)('h' - (y + 1));
			right1 += (char)('1' + x);

			if (isCheck(right1, "b")) {
				check = true;
			}

			String right2 = "move ";
			right2 += (char)('h' - y);
			right2 += (char)('1' + x);
			right2 += (char)('h' - (y + 2));
			right2 += (char)('1' + x);

			if (isCheck(right2, "b")) {
				check = true;
			}

			if (!check) {
				v.add(right2);
			}
		}

		return v;
	}

	public Vector<String> rocadaWhite() {
		Vector<String> v = new Vector<>();
		boolean check = false;
		if (!kingWhite)
			return v;

		int x = kingWhiteX;
		int y = kingWhiteY;

		String stare = "move ";
		stare += (char)('h' - y);
		stare += (char)('1' + x);
		stare += (char)('h' - y);
		stare += (char)('1' + x);

		if (isCheck(stare, "w")) {
			return v;
		}

		if (rookLeftWhite && tabla[0][0] != null &&
		tabla[0][0].getNume().equals("rook") && tabla[x][y-1] == null
		&& tabla[x][y-2] == null) {
			String left1 = "move ";
			left1 += (char)('h' - y);
			left1 += (char)('1' + x);
			left1 += (char)('h' - (y - 1));
			left1 += (char)('1' + x);

			if (isCheck(left1, "w")) {
				check = true;
			}

			String left2 = "move ";
			left2 += (char)('h' - y);
			left2 += (char)('1' + x);
			left2 += (char)('h' - (y - 2));
			left2 += (char)('1' + x);

			if (isCheck(left2, "w")) {
				check = true;
			}

			if (!check) {
				v.add(left2);
			}
		}

		check = false;

		if (rookRightWhite && tabla[0][7] != null &&
		tabla[0][7].getNume().equals("rook") && tabla[x][y+1] == null
		&& tabla[x][y+2] == null && tabla[x][y+3] == null) {
			String right1 = "move ";
			right1 += (char)('h' - y);
			right1 += (char)('1' + x);
			right1 += (char)('h' - (y + 1));
			right1 += (char)('1' + x);

			if (isCheck(right1, "w")) {
				check = true;
			}

			String right2 = "move ";
			right2 += (char)('h' - y);
			right2 += (char)('1' + x);
			right2 += (char)('h' - (y + 2));
			right2 += (char)('1' + x);

			if (isCheck(right2, "w")) {
				check = true;
			}

			if (!check) {
				v.add(right2);
			}
		}

		return v;
	}

	//metoda pentru afisarea matricei
	void afisare() {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				System.out.print(tabla[i][j] + " ");
			}
			System.out.println("");
		}
	}
}
