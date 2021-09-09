import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.ButtonGroup;
import java.awt.Color;

public class Interfaccia extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField testoNome;
	private JTextField testoCognome;
	private JLabel lblInserisciNumeroGiocatori;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	public static void main(String[] args) {
		//main
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interfaccia frame = new Interfaccia();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Interfaccia() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 100, 1000, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//interfaccia 2 giocatore e guarda
		JLabel labelNome = new JLabel("Inserisci nome:");
		labelNome.setFont(new Font("Tahoma", Font.PLAIN, 28));
		labelNome.setBounds(133, 62, 203, 37);
		contentPane.add(labelNome);
		testoNome = new JTextField();
		testoNome.setBounds(133, 112, 236, 39);
		contentPane.add(testoNome);
		testoNome.setColumns(10);
		
		JLabel labelCognome = new JLabel("Inserisci cognome:");
		labelCognome.setFont(new Font("Tahoma", Font.PLAIN, 28));
		labelCognome.setBounds(130, 200, 234, 34);
		contentPane.add(labelCognome);
		testoCognome = new JTextField();
		testoCognome.setBounds(130, 250, 236, 39);
		contentPane.add(testoCognome);
		testoCognome.setColumns(10);
		
		lblInserisciNumeroGiocatori = new JLabel("Inserisci numero giocatori:");
		lblInserisciNumeroGiocatori.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblInserisciNumeroGiocatori.setBounds(128, 323, 330, 34);
		contentPane.add(lblInserisciNumeroGiocatori);
		JSpinner numGiocatori = new JSpinner();
		numGiocatori.setBounds(133, 370, 115, 40);
		contentPane.add(numGiocatori);
		
		JLabel lblGiorni = new JLabel("Inserisci numero di giorni:");
		lblGiorni.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblGiorni.setBounds(129, 202, 324, 34);
		contentPane.add(lblGiorni);
		JSpinner numGiorni = new JSpinner();
		numGiorni.setBounds(130, 250, 117, 40);
		contentPane.add(numGiorni);
		
		JLabel lblSelionaICaratteri = new JLabel("Seleziona i caratteri:");
		lblSelionaICaratteri.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblSelionaICaratteri.setBounds(631, 62, 252, 34);
		contentPane.add(lblSelionaICaratteri);
		
		JLabel lblErrore = new JLabel("Errore nel completamento del modulo");
		lblErrore.setForeground(Color.RED);
		lblErrore.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblErrore.setBounds(412, 500, 169, 13);
		contentPane.add(lblErrore);
		
		JCheckBox random = new JCheckBox("random");
		random.setFont(new Font("Tahoma", Font.PLAIN, 26));
		random.setSelected(true);
		random.setBounds(631, 102, 133, 41);
		contentPane.add(random);
		
		JCheckBox positivo = new JCheckBox("positivo");
		positivo.setFont(new Font("Tahoma", Font.PLAIN, 26));
		positivo.setSelected(true);
		positivo.setBounds(631, 152, 133, 41);
		contentPane.add(positivo);
		
		JCheckBox negativo = new JCheckBox("negativo");
		negativo.setFont(new Font("Tahoma", Font.PLAIN, 26));
		negativo.setSelected(true);
		negativo.setBounds(631, 202, 143, 41);
		contentPane.add(negativo);
		
		JCheckBox bipolare = new JCheckBox("bipolare");
		bipolare.setFont(new Font("Tahoma", Font.PLAIN, 26));
		bipolare.setSelected(true);
		bipolare.setBounds(631, 252, 135, 41);
		contentPane.add(bipolare);
		
		JCheckBox malvagio = new JCheckBox("malvagio");
		malvagio.setFont(new Font("Tahoma", Font.PLAIN, 26));
		malvagio.setSelected(true);
		malvagio.setBounds(631, 302, 147, 41);
		contentPane.add(malvagio);
		
		JCheckBox neutrale = new JCheckBox("neutrale");
		neutrale.setFont(new Font("Tahoma", Font.PLAIN, 26));
		neutrale.setSelected(true);
		neutrale.setBounds(631, 352, 141, 41);
		contentPane.add(neutrale);
		
		JCheckBox rancoroso = new JCheckBox("rancoroso");
		rancoroso.setFont(new Font("Tahoma", Font.PLAIN, 26));
		rancoroso.setSelected(true);
		rancoroso.setBounds(631, 402, 157, 41);
		contentPane.add(rancoroso);
		
		JCheckBox visionario = new JCheckBox("visionario");
		visionario.setFont(new Font("Tahoma", Font.PLAIN, 26));
		visionario.setSelected(true);
		visionario.setBounds(631, 452, 141, 41);
		contentPane.add(visionario);
		
		JLabel lblVelocita = new JLabel("Velocita':");
		lblVelocita.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblVelocita.setBounds(133, 63, 112, 34);
		contentPane.add(lblVelocita);
		
		JSpinner Delay = new JSpinner();
		Delay.setBounds(133, 111, 115, 40);
		contentPane.add(Delay);
		Delay.setValue(2000);
		
		JCheckBox Slow = new JCheckBox("Slow");
		Slow.setFont(new Font("Tahoma", Font.PLAIN, 26));
		Slow.setBounds(284,61,85,41);
		contentPane.add(Slow);
		
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if ((!testoNome.getText().equals("") && !testoCognome.getText().equals("") && (Integer)numGiocatori.getValue()>1) && (positivo.isSelected() || negativo.isSelected() || random.isSelected() || malvagio.isSelected() || bipolare.isSelected() || rancoroso.isSelected() || neutrale.isSelected())) {
					//gioca
					if (positivo.isSelected()) Center.caratteri.add("positivo");
					if (negativo.isSelected()) Center.caratteri.add("negativo");
					if (malvagio.isSelected()) Center.caratteri.add("malvagio");
					if (bipolare.isSelected()) Center.caratteri.add("bipolare");
					if (neutrale.isSelected()) Center.caratteri.add("neutrale");
					if (random.isSelected()) Center.caratteri.add("random");
					if (rancoroso.isSelected()) Center.caratteri.add("rancoroso");
					if (visionario.isSelected()) Center.caratteri.add("visionario");
					Center.player=true;
					Center.size = (Integer)numGiocatori.getValue();
					Center.Crea_io();
					Center.sleep=2000;
					Center.io.nome = testoNome.getText();
					Center.io.cognome = testoCognome.getText();
					Center.c = Center.io.giorno_morte;
					try {Center.Start();}
					catch (IOException e) {e.printStackTrace();}
					dispose();
					try {
						Center.main(null);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else if (((Integer)numGiocatori.getValue()>1 && (Integer)numGiorni.getValue()>1) && (positivo.isSelected() || negativo.isSelected() || random.isSelected() || malvagio.isSelected() || bipolare.isSelected() || rancoroso.isSelected() || neutrale.isSelected()) && (Integer)Delay.getValue()>=0) {
					//guarda
					if (positivo.isSelected()) Center.caratteri.add("positivo");
					if (negativo.isSelected()) Center.caratteri.add("negativo");
					if (malvagio.isSelected()) Center.caratteri.add("malvagio");
					if (bipolare.isSelected()) Center.caratteri.add("bipolare");
					if (neutrale.isSelected()) Center.caratteri.add("neutrale");
					if (random.isSelected()) Center.caratteri.add("random");
					if (rancoroso.isSelected()) Center.caratteri.add("rancoroso");
					if (visionario.isSelected()) Center.caratteri.add("visionario");
					if (Slow.isSelected()) { Center.slow=true; Center.sleep=(Integer)Delay.getValue(); }
					else Center.speed=(Integer)Delay.getValue();
					Center.size = (Integer)numGiocatori.getValue();
					Center.c = (Integer)numGiorni.getValue();
					Center.gtot=Center.c;
					try {Center.Start();}
					catch (IOException e) {e.printStackTrace();}
					dispose();
					try {
						Center.main(null);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else lblErrore.setVisible(true);
			}
		});
		btnOk.setBounds(411, 455, 171, 41);
		contentPane.add(btnOk);

		lblVelocita.setVisible(false);
		positivo.setVisible(false);
		malvagio.setVisible(false);
		btnOk.setVisible(false);
		rancoroso.setVisible(false);
		neutrale.setVisible(false);
		random.setVisible(false);
		negativo.setVisible(false);
		visionario.setVisible(false);
		lblSelionaICaratteri.setVisible(false);
		numGiocatori.setVisible(false);
		lblInserisciNumeroGiocatori.setVisible(false);
		labelCognome.setVisible(false);
		testoNome.setVisible(false);
		labelNome.setVisible(false);
		testoCognome.setVisible(false);
		lblErrore.setVisible(false);
		bipolare.setVisible(false);
		numGiorni.setVisible(false);
		lblGiorni.setVisible(false);
		Slow.setVisible(false);
		Delay.setVisible(false);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//interfaccia iniziale scelta
		JRadioButton rdbtnGioca = new JRadioButton("Gioca");
		JRadioButton rdbtnGuarda = new JRadioButton("Guarda");
		buttonGroup.add(rdbtnGuarda);
		rdbtnGuarda.setFont(new Font("Tahoma", Font.PLAIN, 30));
		rdbtnGuarda.setBounds(535, 245, 141, 45);
		contentPane.add(rdbtnGuarda);
		
		buttonGroup.add(rdbtnGioca);
		rdbtnGioca.setFont(new Font("Tahoma", Font.PLAIN, 30));
		rdbtnGioca.setBounds(328, 245, 141, 45);
		contentPane.add(rdbtnGioca);
		
		JLabel lblNewLabel = new JLabel("Social Game System");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblNewLabel.setBounds(318, 156, 358, 49);
		contentPane.add(lblNewLabel);
		
		JButton btnInizio = new JButton("Inizio");
		btnInizio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rdbtnGioca.isSelected()){
					positivo.setVisible(true);
					malvagio.setVisible(true);
					bipolare.setVisible(true);
					btnOk.setVisible(true);
					rancoroso.setVisible(true);
					neutrale.setVisible(true);
					random.setVisible(true);
					negativo.setVisible(true);
					visionario.setVisible(true);
					lblSelionaICaratteri.setVisible(true);
					numGiocatori.setVisible(true);
					lblInserisciNumeroGiocatori.setVisible(true);
					labelCognome.setVisible(true);
					testoNome.setVisible(true);
					labelNome.setVisible(true);
					testoCognome.setVisible(true);

					rdbtnGioca.setVisible(false);
					lblNewLabel.setVisible(false);
					btnInizio.setVisible(false);
					rdbtnGuarda.setVisible(false);
				}
				 if (rdbtnGuarda.isSelected()) {
					 	Delay.setVisible(true);
					 	Slow.setVisible(true);
						lblVelocita.setVisible(true);
						positivo.setVisible(true);
						malvagio.setVisible(true);
						bipolare.setVisible(true);
						visionario.setVisible(true);
						btnOk.setVisible(true);
						rancoroso.setVisible(true);
						neutrale.setVisible(true);
						random.setVisible(true);
						negativo.setVisible(true);
						lblSelionaICaratteri.setVisible(true);
						numGiocatori.setVisible(true);
						lblInserisciNumeroGiocatori.setVisible(true);
						numGiorni.setVisible(true);
						rdbtnGuarda.setVisible(true);
						lblGiorni.setVisible(true);
						rdbtnGioca.setVisible(false);
						lblNewLabel.setVisible(false);
						btnInizio.setVisible(false);
						rdbtnGioca.setVisible(false);
						rdbtnGuarda.setVisible(false);					
				}
			}
		});
		btnInizio.setBounds(411, 346, 171, 41);
		contentPane.add(btnInizio);
	}
}
