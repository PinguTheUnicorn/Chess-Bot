import java.util.*;
//clasa ce interpreteaza comenzile de la xboard

public class Main {
	//obiect de tip Scanner pentru a primi informatiile de la xboard constant
	static Scanner scanner = new Scanner(System.in);
	//obiect Board pentru simularea tablei
	static Board tabla = new Board();

	static String lastMove;

	//functie de initializare a jocului
	static void initial() {
		//se citesc primele comenzi(xboard, protover N)
		scanner.next();
		scanner.next();
		scanner.next();

		//se trimit feature-urile
		System.out.println("feature name=\"giumale\" sigint=0 " +
		"san=0 usermove=1");
	}

	//metoda de interpretare a comenzilor de joc
	static void game() {
		String s; //variabile pentru primirea/trimiterea comenzilor
		boolean think = true; //variabila pentru verificarea activarii bot-ului
		String c = "b"; //variabila pentru verificarea culorii bot-ului

		//se asteapta comenzi de la xboard
		while(true) {
			s = scanner.nextLine();
			if (!s.equals("accepted usermove") && s.contains("usermove") &&
																	think) {
				/* daca se primeste mutare si bot-ul trebuie sa gandeasca
				   se aplica mutarea lui */
				lastMove = s;
				tabla.move(s);

				/* in functie de culoarea bot-ului, se calculeaza miscarea
				   acestuia */
				if (c.equals("b")) {
					s = tabla.generateMove("b", lastMove);
				}
				else {
					s = tabla.generateMove("w", lastMove);
				}

				/*  daca nu se pot face miscari valide(resign), se trimite mesaj
					si se asteapta urmatoarea comanda */
				if (s.equals("resign")) {
					System.out.println("resign");
					continue;
				}

				/* daca s-a gasit mutare valida, se aplica pe tabla si se
				   continua la urmatorul semnal de la xboard */
				if (s.equals("resign")) {
					System.out.println("muie");
				}
				tabla.move(s);
				System.out.println(s);
				continue;
			}
			if (!s.equals("accepted usermove") && s.contains("usermove") &&
																	!think) {
				//daca bot-ul este oprit, se inregistreaza miscarea
				lastMove = s;
				tabla.move(s);

				/* se modifica culoarea bot-ului pentru a fi pregatit
				   urmatorul tur */
				if (c.equals("b"))
					c = "w";
				else
					c = "b";
				continue;
			}
			if (s.equals("new")) {
				/* daca s-a primit mesajul de new, se reseteaza tabla
				   bot-ul se pune pe culoarea neagra si incepe sa gandeasca */
				tabla = new Board();
				lastMove = null;
				c = "b";
				think = true;
				//apoi se asteapta urmatoarea comanda
				continue;
			}
			if (s.equals("quit")) {
				//daca s-a primit mesajul de iesire, atunci se iese din functie
				return;
			}
			if (s.equals("force")) {
				//daca s-a primit "force", bot-ul se opreste din gandire
				think = false;

				//si isi schimba culoarea pentru a astepta sa inceapa turul urmator
				if (c.equals("b"))
					c = "w";
				else
					c = "b";
					continue;

			}
			if (s.equals("go")) {
				/* daca bot-ul deja gandeste(adica se da "go" in timp ce
				   ruleaza jocul) acesta isi schimba culoarea pentru a muta cu
				   cel al carui rand este acum */
				if (think)
					if (c.equals("b"))
						c = "w";
					else
						c = "b";

				//ne asiguram ca bot-ul porneste
				think = true;

				/* se genereaza miscarea acestuia in functie de culoarea
				   lui de joc */
				if (c.equals("b")) {
					s = tabla.generateMove("b", lastMove);
				}
				else {
					s = tabla.generateMove("w", lastMove);
				}

				/* la fel ca la o miscare normala, daca nu se gasesc miscari
				   valide, se trimite "resign" */
				if (s.equals("resign"))
					break;

				//se stocheaza mutarea pe tabla
				tabla.move(s);
				//se trimite mutarea
				System.out.println(s);
			}
		}
	}

	public static void main(String[] args) {
		initial(); //se initializeaza jocul
		game(); //se porneste joc-ul
		scanner.close(); //se inchide canalul de comunicare cu xboard-ul
	}
}
