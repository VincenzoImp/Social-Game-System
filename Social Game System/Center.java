import java.util.*;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.style.PieStyler.AnnotationType;
import org.knowm.xchart.style.Styler.ChartTheme;

import java.awt.Color;
import java.io.*;
//progetto backup 02.06.19
public class Center {
	static PieChart chart2;
	static SwingWrapper<PieChart> sw2;
	static XYChart chart;
	static double[][] xyData; 
	static SwingWrapper<XYChart> sw;
	static XYChart chartumore;
	static double[][] xyumoreData; 
	static SwingWrapper<XYChart> swumore;
	static int contatoresbalziumore=0;
	static long speed;//tempo di delay tra un giorno e l'altro in modalita guarda con la regolazione della velocita
	static long timestart;//tempo inizio ciclo
	static long time;//tempo mancante alla fine del ciclo 
	static int sleep;//tempo di delay tra un avvenimento e unaltro in modalita slow e player
	static boolean slow = false;//settata a true la modalita di gioco e' slow
	static boolean player = false;//settata a true la modalita di cioco e' Gioca altrimenti e' Guarda
	static boolean isalive = false;//variabile per il funzionamento di player
	static boolean p = false;//variabile per il funzionamento di player
	static boolean continua = true;//variabile per controllare la correttezza degli input
	static int ContatoreMorti = 0;
	static int LimiteFigli = 3;
	static int DivisoreIncrementoUmore = 3;
	static int EtaMassima = 50;
	static int size;
	static int NatiOggi = 0;
	static int MortiOggi = 0;
	static int giorno = 1;
	static int c;//giorni mancanti alla fine del mondo
	static double ImportanzaIniziale = 2.5;
	static double UmoreIniziale = 50;
	static Giocatore io;
	static HashMap<String, Integer> contCaratteri = new HashMap<String, Integer>();
	static HashSet<Giocatore> MortiViventi = new HashSet<Giocatore>();
	static HashSet<Giocatore> appenaNati = new HashSet<Giocatore>();
	static HashSet<Tupla> amicizieRotte = new HashSet<Tupla>();
	static LinkedList<Giocatore> ins = new LinkedList<Giocatore>();
	static LinkedList<Giocatore> cimitero = new LinkedList<Giocatore>();
	static LinkedList<String> Nomi = new LinkedList<String>();
	static LinkedList<String> Cognomi = new LinkedList<String>();
	static LinkedList<String> caratteri = new LinkedList<String>();
	static Random random = new Random();
	static Scanner inputScanner = new Scanner(System.in);

	public static void main(String[] args) throws InterruptedException, IOException {
		Start();
		while(c > 0 && ins.size() > 1) {
			timestart = System.currentTimeMillis();
			NuoveAmicizie();
			VitaSociale();
			aggiornaInsCimitero();
			Controlli();
			AggiornaGrafici();
			if (player) {
				if (!isalive) break;
				StampaFinePlayer();
			}
			Stampa();
			c--;
			if (c!=0) giorno++;
			if (player==false && slow==false) {
				time = -(System.currentTimeMillis()-(timestart+speed));
				if (time>0) Thread.sleep((int)time);
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
		LinkedList<String> l = new LinkedList<String>();
		l.add("player");
		p=true;
		isalive=true;
		size--;
		io=new Giocatore(l);
		ins.add(io);
		c = io.giorno_morte; 
	}

	static void Start() throws IOException {
		int modalita=0;
		continua=true;
		do {
			try {
				System.out.println("Modalita': Gioca(1) | Guarda(2)");
				modalita = inputScanner.nextInt();
				if (modalita!=1 && modalita!=2) throw new InputMismatchException();
				continua=false;
			} catch (InputMismatchException e) {
				System.out.print("Errore! ");
			}
			inputScanner.nextLine(); 
		} while (continua);
		if (modalita==1) player=true;
		continua=true;
		do {
			try {
				System.out.println("Numero di giocatori:");
				size = inputScanner.nextInt();
				if (size<2) throw new InputMismatchException();
				continua=false;
			} catch (InputMismatchException e) {
				System.out.print("Errore! ");
			}
			inputScanner.nextLine(); 
		} while (continua);
		
		if (player) {
			Crea_io();
			continua=true;
			do {
				try {
					System.out.println("Decidi velocita' di ogni avvenimento in millisecondi:");
					sleep = inputScanner.nextInt();
					if (sleep<0) throw new InputMismatchException();
					continua=false;
				} catch (InputMismatchException e) {
					System.out.print("Errore! ");
				}
				inputScanner.nextLine(); 
			} while (continua);
		}
		else {
			continua=true;
			do {
				try {
					System.out.println("Modalita' guarda: Slow(1) | Normale(2)");
					modalita = inputScanner.nextInt();
					if (modalita!=1 && modalita!=2) throw new InputMismatchException();
					continua=false;
				} catch (InputMismatchException e) {
					System.out.print("Errore! ");
				}
				inputScanner.nextLine(); 
			} while (continua);
			
			
			continua=true;
			do {
				try {
					System.out.println("Numero di giorni:");
					c = inputScanner.nextInt();
					if (c<1) throw new InputMismatchException();
					continua=false;
				} catch (InputMismatchException e) {
					System.out.print("Errore! ");
				}
				inputScanner.nextLine(); 
			} while (continua);
			
			if(modalita==1) slow=true;
			if (slow) {
				continua=true;
				do {
					try {
						System.out.println("Decidi velocita' di ogni avvenimento in millisecondi:");
						sleep = inputScanner.nextInt();
						if (sleep<0) throw new InputMismatchException();
						continua=false;
					} catch (InputMismatchException e) {
						System.out.print("Errore! ");
					}
					inputScanner.nextLine(); 
				} while (continua);
			}
			else {
				continua=true;
				do {
					try {
						System.out.println("Decidi velocita' di ogni giorno in millisecondi:");
						speed = inputScanner.nextInt();
						if (speed<0) throw new InputMismatchException();
						continua=false;
					} catch (InputMismatchException e) {
						System.out.print("Errore! ");
					}
					inputScanner.nextLine(); 
				} while (continua);
			}
		}
		caratteri.add("random");
		caratteri.add("positivo");
		caratteri.add("negativo");
		caratteri.add("bipolare");
		caratteri.add("malvagio");
		caratteri.add("neutrale");
		caratteri.add("rancoroso");
		caratteri.add("visionario");
		LinkedList<String> listanera=new LinkedList<String>();
		for (String carattere: caratteri) {
			continua=true;
			do {
				try {
					System.out.println("Vuoi aggiungere il carattere "+ carattere + " al mondo? si(1) no(altro):");
					modalita = inputScanner.nextInt();
					continua=false;
				} catch (InputMismatchException e) {
					System.out.print("Errore! ");
				}
				inputScanner.nextLine(); 
			} while (continua);
			if (modalita!=1) listanera.add(carattere);
		}
		for (String carattere: listanera) {
			caratteri.remove(carattere);
		}
		Genera_liste();
		for (int i=0; i<size; i++) {
			ins.add(new Giocatore(caratteri));
			contCaratteri.replace(ins.getLast().carattere.carattere, contCaratteri.get(ins.getLast().carattere.carattere)+1);
		}
		if (player) size++;
		Genera_grafici();
	}




	static void Genera_grafici() {
		chart2 = new PieChartBuilder().title("Caratteri").width(600).height(400).title("Caratteri").theme(ChartTheme.GGPlot2).build();
		Color[] sliceColors = new Color[] { new Color(224, 68, 14), new Color(153, 0, 153), new Color(0, 255, 0), new Color(0, 0, 255), new Color(255, 255, 0), new Color(255, 153, 0), new Color(51, 204, 255), new Color(80, 148, 55) };
		chart2.getStyler().setSeriesColors(sliceColors);
		double[] xData = new double[c];
		double[] yData = new double[c];
		xData[0]=giorno;
		yData[0]=ins.size();
		xyData = new double[][] { xData, yData };
		chart = QuickChart.getChart("Giocatori vivi", "Giorni", "Giocatori", "f(x)", xyData[0], xyData[1]);
		sw = new SwingWrapper<XYChart>(chart);
		sw.displayChart();
		if(player) {
			double[] xumoreData = new double[io.giorno_morte*io.giorno_morte];
			double[] yumoreData = new double[io.giorno_morte*io.giorno_morte];
			xumoreData[0]=contatoresbalziumore;
			yumoreData[0]= Round(io.umore);
			xyumoreData = new double[][] { xumoreData, yumoreData };
			chartumore = QuickChart.getChart("Percentuale umore", "Numero cambiamenti di umore", "Andamento umore", "umore(x)", xyumoreData[0], xyumoreData[1]);
			swumore = new SwingWrapper<XYChart>(chartumore);
			swumore.displayChart();
			contatoresbalziumore++;
		}

		chart2.getStyler().setLegendVisible(true);
		chart2.getStyler().setAnnotationType(AnnotationType.LabelAndPercentage);
		chart2.getStyler().setAnnotationDistance(1.15);
		chart2.getStyler().setPlotContentSize(.7);
		chart2.getStyler().setStartAngleInDegrees(90);
		for (String carattere: caratteri) {
			chart2.addSeries(carattere, contCaratteri.get(carattere));
		}
		sw2 = new SwingWrapper<PieChart>(chart2);
		sw2.displayChart();
	}

	static void aggiornagraficoumore() {
		xyumoreData[0][contatoresbalziumore] = contatoresbalziumore;
		xyumoreData[1][contatoresbalziumore] = Round(io.umore);
		chartumore.updateXYSeries("umore(x)", xyumoreData[0], xyumoreData[1], null);
		swumore.repaintChart();
		contatoresbalziumore++;
	}

	static void AggiornaGrafici() {
		xyData[0][giorno-1] = giorno;
		xyData[1][giorno-1] = ins.size();
		chart.updateXYSeries("f(x)", xyData[0], xyData[1], null);
		sw.repaintChart();
		for (String carattere: caratteri) {
			chart2.updatePieSeries(carattere, contCaratteri.get(carattere));
		}
		sw2.repaintChart();
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
					System.out.println("Il tuo caro amico " + iGiocatore.nome + " " + iGiocatore.cognome + " e' morto! Condoglianze (Il tuo umore scende del 5%)");
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
					System.out.println("Oggi non hai conosciuto nessuno! Il tuo umore scende del 5%...");
					aggiornagraficoumore();
					Thread.sleep(sleep);
				}
				if (slow) {
					System.out.println(iGiocatore.nome + " " + iGiocatore.cognome + " non conosce ancora nessuno e il suo umore scende del 5%");
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
					System.out.println("Hai rotto l'amicizia con " + conoscente.nome + " " + conoscente.cognome + ", qualcosa non andava!");
					Thread.sleep(sleep);
				}
				if (iGiocatore.AggiornaGiocatore() == false) break;
			}
			iGiocatore.giorni_attivi+=1;
			if (MortiViventi.contains(iGiocatore) == false) iGiocatore.AggiornaGiocatore();
		}
	}

	static void Stampa() throws InterruptedException {
		System.out.println("Giorno:"+ giorno + "   Nati oggi:" + NatiOggi + "   Morti oggi:"+ MortiOggi + "   Vivi:"+ ins.size()+" "+"   Morti:" + ContatoreMorti);
		System.out.println(contCaratteri.toString() + "\n");
	}


	static void StampaFinePlayer() throws InterruptedException {
		System.out.println("Il tuo umore e': " + Round(io.umore));
		for (Giocatore conoscente: io.conoscenti) {
			System.out.println("Nome conoscente: " + conoscente.nome + " " + conoscente.cognome + " | Importanza: " + io.interazioni.get(conoscente).importanza + " | Carattere: " + conoscente.carattere.carattere + " | Umore: " + Round(conoscente.umore)); 
			Thread.sleep(sleep);
		}
	}	
}