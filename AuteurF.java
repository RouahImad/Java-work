package tp6;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class AuteurF extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_nom;
	private JTextField txt_nationalite;
	private String id;

	public AuteurF(Boolean ajouter) {
		if(ajouter)
			setTitle("Ajouter auteur");
		else 
			setTitle("Modifier auteur");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 426, 265);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_auteur_nom = new JLabel("Nom auteur :");
		lbl_auteur_nom.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbl_auteur_nom.setBounds(24, 41, 83, 14);
		contentPane.add(lbl_auteur_nom);
		
		JLabel lbl_auteur_nat = new JLabel("Nationalite :");
		lbl_auteur_nat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbl_auteur_nat.setBounds(24, 87, 83, 14);
		contentPane.add(lbl_auteur_nat);
		
		JButton btn_ajouter_auteur = new JButton();
		btn_ajouter_auteur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nom = txt_nom.getText();
				String nat = txt_nationalite.getText();
				if(nom.length() > 0 && nat.length() >0) {
					if(ajouter) {
						try {
							Main.ajouterAuteur(nom, nat);
						} catch (SQLException e1) {
							System.out.println(e1.getMessage());
						}
					} else {
						try {
							Main.modifierAuteur(id, nom, nat);
						} catch (SQLException e1) {
							System.out.println(e1.getMessage());
						}
					}
					dispose();	
				}
			}
		});
		if(ajouter)
			btn_ajouter_auteur.setText("Ajouter auteur");
		else
			btn_ajouter_auteur.setText("Modifier auteur");
		btn_ajouter_auteur.setBounds(259, 175, 129, 29);
		contentPane.add(btn_ajouter_auteur);
		
		txt_nom = new JTextField();
		txt_nom.setBounds(138, 39, 173, 23);
		contentPane.add(txt_nom);
		txt_nom.setColumns(10);
		
		txt_nationalite = new JTextField();
		txt_nationalite.setColumns(10);
		txt_nationalite.setBounds(138, 86, 173, 23);
		contentPane.add(txt_nationalite);
	}
	public String getNom() {
		return txt_nom.getText();
	}
	public void setNom(String txt) {
		txt_nom.setText(txt);
	}
	public String getNat() {
		return txt_nationalite.getText();
	}
	public void setNat(String txt) {
		txt_nationalite.setText(txt);
	}
	public void setId(String id) {
		this.id = id;
	}
}
