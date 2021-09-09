import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.Label;
import java.awt.Color;

public class Interfaccia2 extends JFrame {
	private static final long serialVersionUID = 1L;
	public JPanel contentPane;
	public Label lblginiziali;
	public Label lblgtotali;
	public Label oggilbl;
	public Label lblMorti;
	public Label lblVivi;
	public Label InputDati;
	public Label lblUmore;
	public Label Giorno;
	public Label morti;
	public Label vivi;
	public Label GiorniTotali;
	public Label GiocatoriIniziali;
	public TextArea textArea;
	public Label umore;

	public Interfaccia2() throws InterruptedException {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 600, 999, 323);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblginiziali = new Label("Giocatori Iniziali:");
		lblginiziali.setBounds(12, 10, 150, 25);
		lblginiziali.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblginiziali);

		lblgtotali = new Label("Giorni Totali:");
		lblgtotali.setBounds(14, 74, 116, 25);
		lblgtotali.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblgtotali);

		oggilbl = new Label("Giorno Corrente:");
		oggilbl.setBounds(14, 144, 148, 25);
		oggilbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(oggilbl);

		lblMorti = new Label("Morti:");
		lblMorti.setBounds(186, 239, 52, 25);
		lblMorti.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblMorti);

		lblVivi = new Label("Vivi:");
		lblVivi.setBounds(536, 239, 39, 25);
		lblVivi.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblVivi);
		
		InputDati = new Label("");
		InputDati.setBounds(186, 208, 807, 25);
		InputDati.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(InputDati);

		lblUmore = new Label("Umore:");
		lblUmore.setBounds(12, 74, 66, 25);
		lblUmore.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblUmore);

		Giorno = new Label(Integer.toString(Center.giorno));
		Giorno.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Giorno.setBounds(22, 172, 157, 25);
		contentPane.add(Giorno);

		morti = new Label(Integer.toString(Center.ContatoreMorti));
		morti.setFont(new Font("Tahoma", Font.PLAIN, 20));
		morti.setBounds(246, 239, 221, 25);
		contentPane.add(morti);

		vivi = new Label(Integer.toString(Center.size));
		vivi.setFont(new Font("Tahoma", Font.PLAIN, 20));
		vivi.setBounds(581, 239, 116, 25);
		contentPane.add(vivi);

		GiorniTotali = new Label(Integer.toString(Center.gtot));
		GiorniTotali.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GiorniTotali.setBounds(24, 99, 116, 25);
		contentPane.add(GiorniTotali);

		GiocatoriIniziali = new Label(Integer.toString(Center.size));
		GiocatoriIniziali.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GiocatoriIniziali.setBounds(22, 33, 116, 25);
		contentPane.add(GiocatoriIniziali);
		
		umore = new Label("50.0");
		umore.setFont(new Font("Tahoma", Font.PLAIN, 20));
		umore.setBounds(24, 99, 66, 25);
		contentPane.add(umore);
		
		textArea = new TextArea();
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textArea.setBounds(186, 0, 807, 202);
		contentPane.add(textArea);
		
		InputDati.setVisible(false);
		lblUmore.setVisible(false);
		lblgtotali.setVisible(false);
		GiorniTotali.setVisible(false);
		umore.setVisible(false);

		if (Center.player) {
			InputDati.setVisible(true);
			lblUmore.setVisible(true);
			umore.setVisible(true);
		}
		else {
			lblgtotali.setVisible(true);
			GiorniTotali.setVisible(true);
		}
	}
}
