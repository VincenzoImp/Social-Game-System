import java.util.*;
import java.io.*;
//progetto backup 02.06.19
public class Center {
	static long speed = 1000;//tempo di delay tra un ciclo e l'altro in modalita guarda con la regolazione della velocita
	static long timestart;//tempo inizio ciclo
	static long time;//tempo mancante alla fine del ciclo 
	static int sleep;
	static boolean slow = false;
	static boolean player = false;   //||||||||||||| True = Modalita' Gioca ||||||||||||| False = Modalita' Guarda |||||||||||||
	static boolean isalive = false;
	static boolean p = false;
	static boolean continua = true;
	static int ContatoreMorti = 0;
	static int LimiteFigli = 3;
	static int DivisoreIncrementoUmore = 3;
	static int EtaMassima = 50;
	static int size;
	static int NatiOggi = 0;
	static int MortiOggi = 0;
	static int giorno = 1;
	static int c;
	static int gtot;
	static double ImportanzaIniziale = 2.5;
	static double UmoreIniziale = 50;
	static Giocatore io;
	static HashMap<String, Integer> contCaratteri = new HashMap<String, Integer>();
	static HashSet<Giocatore> MortiViventi = new HashSet<Giocatore>();
	static HashSet<Giocatore> appenaNati = new HashSet<Giocatore>();
	static HashSet<Tupla> amicizieRotte = new HashSet<Tupla>();
	static LinkedList<Giocatore> ins = new LinkedList<Giocatore>();
	static LinkedList<Giocatore> cimitero = new LinkedList<Giocatore>();
	static LinkedList<String> l = new LinkedList<String>();
	static LinkedList<String> Nomi = new LinkedList<String>();
	static LinkedList<String> Cognomi = new LinkedList<String>();
	static LinkedList<String> caratteri = new LinkedList<String>();
	static Random random = new Random();
	static Scanner inputScanner = new Scanner(System.in);
	static Interfaccia2 frame;

	public static void main(String[] args) throws InterruptedException {
		frame = new Interfaccia2();
		frame.setVisible(true);
		while(c > 0 && ins.size() > 1) {
			timestart = System.currentTimeMillis();
			NuoveAmicizie();
			VitaSociale();
			aggiornaInsCimitero();
			Controlli();
			if (player) {
				if (!isalive) break;
				StampaFinePlayer();
			}
			Stampa();
			c--;
			if (c!=0) giorno++;
			frame.Giorno.setText(Integer.toString(giorno));
			frame.morti.setText(Integer.toString(ContatoreMorti));
			frame.vivi.setText(Integer.toString(ins.size()));
			if (player==false && slow==false) {
				time = System.currentTimeMillis()-(timestart+speed);
				if (time>0) Thread.sleep(time);
			}
			else Thread.sleep(sleep);
		}

	}
	
	static void Controlli() {
		if (ins.size()>10000) {
			DivisoreIncrementoUmore = 4;
			EtaMassima = 30;
			ImportanzaIniziale = 2;
		}
		else if (ins.size()<100) {
			DivisoreIncrementoUmore = 2;
			EtaMassima = 60;
			ImportanzaIniziale = 3;
		}
		else {
			DivisoreIncrementoUmore = 3;
			EtaMassima = 50;
			ImportanzaIniziale = 2.5;
		}
	}

	static double Round(double numero) { 
		numero *= 100;
		float parteintera = (float)numero;
		int uscita = Math.round(parteintera);
		return (double)uscita/100;
	}

	static void Crea_io() {
		p=true;
		isalive=true;
		size--;
		l.add("player");
		io=new Giocatore(l);
		ins.add(io);
		c = io.giorno_morte; 
	}

	static void Start() throws IOException {
		Genera_liste();
		for (int i=0; i<size; i++) {
			ins.add(new Giocatore(caratteri));
			contCaratteri.replace(ins.getLast().carattere.carattere, contCaratteri.get(ins.getLast().carattere.carattere)+1);
		}
		if (player) size++;
	}

	static void Genera_liste() throws IOException {
		String fileName = "src\\listanomi.txt";
		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
		BufferedReader br = new BufferedReader(isr);
		String line;
		while((line = br.readLine()) != null){
			Nomi.add(line);
		}
		br.close();
		String fileName1 = "src\\listacognomi.txt";
		File file1 = new File(fileName1);
		FileInputStream fis1 = new FileInputStream(file1);
		InputStreamReader isr1 = new InputStreamReader(fis1,"UTF-8");
		BufferedReader br1 = new BufferedReader(isr1);

		String line1;
		while((line1 = br1.readLine()) != null){
			Cognomi.add(line1);
		}
		br1.close();
		for (String carattere: caratteri) contCaratteri.put(carattere, 0);
	}

	static void NuoveAmicizie() throws InterruptedException {
		Collections.shuffle(ins);
		int n = ((ins.size()*60)/100);
		if (n%2!=0) n+=1;
		for (int i=0; i<n/2; i++) {
			ins.get(i).Crea_Amicizia(ins.get(i+(n/2)));
		}
	}

	static void aggiornaInsCimitero() throws InterruptedException {
		NatiOggi = appenaNati.size();
		MortiOggi = MortiViventi.size();
		ContatoreMorti += MortiOggi;
		for (Tupla tupla: amicizieRotte) {
			if (tupla.g1.conoscenti.contains(tupla.g2)) {
				tupla.g1.conoscenti.remove(tupla.g2);
				tupla.g1.interazioni.remove(tupla.g2);
			}
			if (tupla.g2.conoscenti.contains(tupla.g1)) {
				tupla.g2.conoscenti.remove(tupla.g1);
				tupla.g2.interazioni.remove(tupla.g1);
			}
		}
		for (Giocatore iGiocatore: MortiViventi) {
			ins.remove(iGiocatore);
			cimitero.add(iGiocatore);
			if (!iGiocatore.carattere.carattere.equals("player")) contCaratteri.replace(iGiocatore.carattere.carattere, contCaratteri.get(iGiocatore.carattere.carattere)-1);
			for (Giocatore conoscente: iGiocatore.conoscenti) {
				if (conoscente==io) {
					frame.textArea.append("Il tuo caro amico " + iGiocatore.nome + " " + iGiocatore.cognome + " e' morto! Condoglianze (Il tuo umore scende del 5%)\n");
					Thread.sleep(sleep);
				}
				conoscente.umore-=5;
				conoscente.interazioni.remove(iGiocatore);
				conoscente.conoscenti.remove(iGiocatore);
			}
		}
		for (Giocatore iGiocatore: appenaNati) {
			ins.add(iGiocatore);
			contCaratteri.replace(iGiocatore.carattere.carattere, contCaratteri.get(iGiocatore.carattere.carattere)+1);
		}
		MortiViventi.clear();
		appenaNati.clear();
		amicizieRotte.clear();
	}

	static class Tupla {
		Giocatore g1;
		Giocatore g2;
		Tupla(Giocatore g1, Giocatore g2){
			this.g1 = g1;
			this.g2 = g2;
		}
	}

	static void VitaSociale() throws InterruptedException {
		Collections.shuffle(ins);
		for (Giocatore iGiocatore: ins) {
			if (iGiocatore.conoscenti.isEmpty()) {
				iGiocatore.umore -= 5;
				if (iGiocatore.carattere.carattere.equals("player")) {
					frame.textArea.append("Oggi non hai conosciuto nessuno! Il tuo umore scende del 5%...\n");
					frame.umore.setText(Double.toString(Round(iGiocatore.umore)));
					Thread.sleep(sleep);
				}
				if (slow) {
					frame.textArea.append(iGiocatore.nome + " " + iGiocatore.cognome + " non conosce ancora nessuno e il suo umore scende del 5%\n");
					Thread.sleep(sleep);
				}
			}
			if (iGiocatore.AggiornaGiocatore() == false) continue;
			Collections.shuffle(iGiocatore.conoscenti);
			for (Giocatore conoscente: iGiocatore.conoscenti) {
				if (MortiViventi.contains(conoscente)) continue;
				iGiocatore.ScambiaMessaggio(conoscente);
				if (iGiocatore.AggiornaGiocatore() == false) break;
				iGiocatore.AggiornaImportanzaConoscente(conoscente);
				if (conoscente.AggiornaGiocatore()) iGiocatore.ControllaAmicizia(conoscente);
				if (iGiocatore.ControllaAmicizia(conoscente)==false && iGiocatore.carattere.carattere.equals("player")) {
					System.out.println("Hai rotto l'amicizia con " + conoscente.nome + " " + conoscente.cognome + ", qualcosa non andava!\n");
					Thread.sleep(sleep);
				}
				if (iGiocatore.AggiornaGiocatore() == false) break;
			}
			iGiocatore.giorni_attivi+=1;
			if (MortiViventi.contains(iGiocatore) == false) iGiocatore.AggiornaGiocatore();
		}
	}

	static void Stampa() throws InterruptedException {
		frame.textArea.append("Giorno:"+ giorno + "   Nati oggi:" + NatiOggi + "   Morti oggi:"+ MortiOggi + "   Vivi:"+ ins.size()+" "+"   Morti:" + ContatoreMorti + "\n");
		frame.textArea.append(contCaratteri.toString() + "\n\n");
	}


	static void StampaFinePlayer() throws InterruptedException {
		for (Giocatore conoscente: io.conoscenti) {
			frame.textArea.append("Nome conoscente: " + conoscente.nome + " " + conoscente.cognome + " | Importanza: " + io.interazioni.get(conoscente).importanza + " | Carattere: " + conoscente.carattere.carattere + " | Umore: " + Round(conoscente.umore) + "\n"); 
			Thread.sleep(sleep);
		}
	}	
}