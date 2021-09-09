import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
//progetto backup 02.06.19
public class Giocatore extends Center {
	int giorno_nascita;
	int giorni_attivi;
	int giorno_morte;
	int figli;
	double umore;
	Carattere carattere;
	HashMap<Integer, Integer> vocabolario;
	HashMap<Integer, LinkedList<Integer>> dizionario_out;
	HashMap<Giocatore, ValoreInterazione> interazioni;
	LinkedList<Giocatore> conoscenti;
	LinkedList<String> ListaCarattereDominante;
	String nome;
	String cognome;


	public Giocatore(LinkedList<String> lista_caratteri) {
		//costruttore di un oggetto di tipo di tipo giocatore
		if (p) {
			giorno_nascita = giorno;
			giorni_attivi = 0;
			giorno_morte = (random.nextInt(20)+EtaMassima);
			umore = UmoreIniziale;
			figli = 0;
			vocabolario = new HashMap<Integer, Integer>();
			dizionario_out = new HashMap<Integer, LinkedList<Integer>>();
			interazioni = new HashMap<Giocatore, ValoreInterazione>();
			conoscenti = new LinkedList<Giocatore>();
			carattere = new Carattere(lista_caratteri);
			ListaCarattereDominante = new LinkedList<String>();
			CompilaListe();
			p=false;
		}
		else {
			giorno_nascita = giorno;
			giorni_attivi = 0;
			giorno_morte = (random.nextInt(20)+EtaMassima);
			umore = UmoreIniziale;
			figli = 0;
			nome = Nomi.get(random.nextInt(Nomi.size()));
			cognome = Cognomi.get(random.nextInt(Cognomi.size()));
			vocabolario = new HashMap<Integer, Integer>();
			dizionario_out = new HashMap<Integer, LinkedList<Integer>>();
			interazioni = new HashMap<Giocatore, ValoreInterazione>();
			conoscenti = new LinkedList<Giocatore>();
			carattere = new Carattere(lista_caratteri);
			ListaCarattereDominante = new LinkedList<String>();
			CompilaListe();
		}
	}

	static class ValoreInterazione {
		double importanza;
		int data_ultimo_mex_ricevuto;
		LinkedList<Integer> storico;

		public ValoreInterazione() {
			this.importanza = ImportanzaIniziale;
			this.data_ultimo_mex_ricevuto = giorno-1;
			this.storico = new LinkedList<Integer>();
		}
	}

	public void AggiornaImportanzaConoscente(Giocatore conoscente) throws InterruptedException {
		if (interazioni.get(conoscente).storico.size() >= 5 && interazioni.get(conoscente).storico.size() % 5 == 0) {
			double oldimportanza = 0;
			if (slow) oldimportanza = interazioni.get(conoscente).importanza;
			carattere.AggiornaImportanza(conoscente, this);
			if (slow) {
				frame.textArea.append(nome + " " + cognome + " aggiorna l'importanza di " + conoscente.nome + " " + conoscente.cognome + ", da " + Round(oldimportanza) + " a " + Round(interazioni.get(conoscente).importanza) + "\n");
				Thread.sleep(sleep);
			}
		}
	}

	public boolean AggiornaGiocatore() throws InterruptedException {
		if (umore>=100) {
			if (figli < LimiteFigli) Nascita();
			if (io==this && figli == LimiteFigli) {
				frame.textArea.append("Sei molto felice, ma l'eta' avanza e non puoi più avere figli!\n");
				umore -= 50;
				Center.frame.umore.setText(Double.toString(Round(io.umore)));
				Thread.sleep(sleep);
			}
			else umore -= 50;
		}
		else if (umore<=0 || giorni_attivi==giorno_morte) {Morte(); return false;}
		return true;
	}

	private void Morte() throws InterruptedException {
		if (this.carattere.carattere.equals("player")) {
			if (!MortiViventi.contains(this)) {
				if (umore<=0) frame.textArea.append("Mi dispiace " + this.nome + " " + this.cognome + ", sei morto prematuramente di tristezza! Il gioco termina qui\n");
				else frame.textArea.append("Mi dispiace " + this.nome + " " + this.cognome + ", la tua ora e' giunta, sei morto! Il gioco termina qui\n");
			}
			isalive=false;
		}
		if (slow && !MortiViventi.contains(this)) {
			frame.textArea.append(nome + " " + cognome + " e' morto\n");
			Thread.sleep(sleep);
		}
		MortiViventi.add(this);
	}

	private void Nascita() throws InterruptedException {
		//crea un altro giocatore che ha come vocabolario e dizionario_out quelli del padre e conosce il padre
		Giocatore figlio;
		if (slow) {
			frame.textArea.append(nome + " " + cognome + " fa nascere un figlio\n");
			Thread.sleep(sleep);
		}
		if (cimitero.isEmpty()) {
			if (carattere.carattere.equals("player")) {
				figlio = new Giocatore(caratteri);
				Nascitafiglio(figlio);
			}
			else figlio = new Giocatore(ListaCarattereDominante);
		}
		else {
			figlio = cimitero.pollFirst();
			if (carattere.carattere.equals("player")) Nascitafiglio(figlio);
			else {
				Collections.shuffle(ListaCarattereDominante);
				figlio.carattere.carattere = ListaCarattereDominante.get(0);
				figlio.giorno_nascita = giorno;
				figlio.giorni_attivi = 0;
				figlio.giorno_morte = (random.nextInt(20)+EtaMassima);
				figlio.umore = UmoreIniziale;
				figlio.figli = 0;
				figlio.nome = Nomi.get(random.nextInt(Nomi.size()));
				figlio.cognome = Cognomi.get(random.nextInt(Cognomi.size()));
				figlio.vocabolario.clear();
				figlio.dizionario_out.clear();
				figlio.interazioni.clear();
				figlio.conoscenti.clear();
				figlio.ListaCarattereDominante.clear();
				figlio.CompilaListe();
			}

		}
		figlio.vocabolario.putAll(vocabolario);
		figlio.dizionario_out.putAll(dizionario_out);
		appenaNati.add(figlio);
		figli++;
	}

	private void Nascitafiglio(Giocatore figlio) {
		Collections.shuffle(caratteri);
		figlio.carattere.carattere = caratteri.get(0);
		figlio.giorno_nascita = giorno;
		figlio.giorni_attivi = 0;
		figlio.giorno_morte = (random.nextInt(20)+EtaMassima);
		figlio.umore = UmoreIniziale;
		figlio.figli = 0;
		frame.textArea.append("Complimenti, hai avuto un figlio!\n");
		frame.InputDati.setText("Come lo vuoi chiamare?");
		figlio.nome=inputScanner.nextLine();
		figlio.cognome = this.cognome;
		frame.textArea.append("Ora " + figlio.nome + " " + cognome + " fa parte della società!\n");
		figlio.vocabolario.clear();
		figlio.dizionario_out.clear();
		figlio.interazioni.clear();
		figlio.conoscenti.clear();
		figlio.ListaCarattereDominante.clear();
		figlio.CompilaListe();
	}
	
	public void ScambiaMessaggio(Giocatore conoscente) throws InterruptedException {
		//se oggi non si sono ancora scambiati messaggi allora il destinatario riceve dal mittente e poi il mittente riceve dal destinatario
		if (interazioni.get(conoscente).data_ultimo_mex_ricevuto != giorno) {
			if (slow) {
				frame.textArea.append(nome +  " " + cognome + " (umore: " + Round(umore) + " carattere: " + carattere.carattere + ") incontra " + conoscente.nome + " " + conoscente.cognome + " (umore: " + Round(conoscente.umore) + " carattere: " + conoscente.carattere.carattere + ")\n");
				Thread.sleep(sleep);
			}
			conoscente.Assimila_messaggio(Produci_messaggio(conoscente), this);
			if (conoscente.AggiornaGiocatore()) Assimila_messaggio(conoscente.Produci_messaggio(this), conoscente);
			if (slow) {
				frame.textArea.append(nome +  " " + cognome + " (umore: " + Round(umore) + " carattere: " + carattere.carattere + ") " + conoscente.nome + " " + conoscente.cognome + " (umore: " + Round(conoscente.umore) + " carattere: " + conoscente.carattere.carattere + ")\n");
				Thread.sleep(sleep);
			}
		}
	}

	private int Produci_messaggio(Giocatore destinatario) throws InterruptedException {
		int messaggio = carattere.output(destinatario, this);
		if (slow) {
			frame.textArea.append(nome + " " + cognome + " invia " + messaggio + " (per lui significa " + vocabolario.get(messaggio) + ")\n");
			Thread.sleep(sleep);
		}
		return messaggio;
	}

	private void Assimila_messaggio(int messaggio, Giocatore mittente) throws InterruptedException {
		interazioni.get(mittente).data_ultimo_mex_ricevuto = giorno;
		interazioni.get(mittente).storico.addFirst(carattere.input(messaggio, mittente, this));
		if (slow) {
			frame.textArea.append(nome + " " + cognome + " assimila " + messaggio + " (per lui significa " + vocabolario.get(messaggio) + ") come " + interazioni.get(mittente).storico.get(0) + "\n");
			Thread.sleep(sleep);
		}
		umore += interazioni.get(mittente).storico.get(0) * interazioni.get(mittente).importanza / DivisoreIncrementoUmore;
		if (this==io) frame.umore.setText(Double.toString(Round(umore)));
	}

	public void Crea_Amicizia(Giocatore giocatore) throws InterruptedException {
		int a = random.nextInt(11)-5;
		int b = random.nextInt(11)-5;
		if (a >= 0 && b >= 0) {
			if (interazioni.containsKey(giocatore) == false) {
				if (giocatore.carattere.carattere.equals("player")) frame.textArea.append("Hai fatto amicizia con " + this.nome + " " + this.cognome + ", e' un tipo " + this.carattere.carattere + "\n");
				if (this.carattere.carattere.equals("player")) frame.textArea.append("Hai fatto amicizia con " + giocatore.nome + " " + giocatore.cognome + ", e' un tipo " + giocatore.carattere.carattere + "\n");
				if (player && (this==io || giocatore==io)) Thread.sleep(sleep);
				conoscenti.add(giocatore);
				interazioni.put(giocatore, new ValoreInterazione());
				giocatore.conoscenti.add(this);
				giocatore.interazioni.put(this, new ValoreInterazione());
				if (slow) {
					frame.textArea.append(giocatore.nome + " " + giocatore.cognome + " ha stretto amicizia con " + this.nome + " " + this.cognome + "\n");
					Thread.sleep(sleep);
				}
			}
		}
	}

	private void CompilaListe() {
		int parola;
		ListaCarattereDominante.addAll(caratteri);
		for (int i=0; i<caratteri.size()/4; i++) ListaCarattereDominante.add(carattere.carattere);
		for (int i=-5; i<6; i++) {
			dizionario_out.put(i, new LinkedList<Integer>());
			while(true) {
				parola = random.nextInt(101);
				if (vocabolario.containsKey(parola)) continue;
				else vocabolario.put(parola, i); break;
			}
			dizionario_out.get(i).add(parola);
		}
	}

	public boolean ControllaAmicizia(Giocatore conoscente) throws InterruptedException {
		if (interazioni.get(conoscente).importanza<1) {
			amicizieRotte.add(new Tupla(this, conoscente));
			if (slow) {
				frame.textArea.append(nome + " " + cognome + " rompe l'amicizia con " + conoscente.nome + " " + conoscente.cognome + "\n");
				Thread.sleep(sleep);
			}
			return false;
		}
		return true;
	}
}