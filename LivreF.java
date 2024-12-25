package tp6;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LivreF extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_isbn;
	private JTextField txt_titre;
	private JTextField txt_genre;
	private JComboBox cmb_auteur;
	private String ISBN; 

	public LivreF(Boolean ajouter) {
		if(ajouter)
			setTitle("Ajouter livre");
		else
			setTitle("Modifier livre");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 391, 279);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_isbn = new JLabel("ISBN :");
		lbl_isbn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbl_isbn.setBounds(32, 52, 69, 17);
		contentPane.add(lbl_isbn);
		
		JLabel lbl_titre = new JLabel("Titre :");
		lbl_titre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbl_titre.setBounds(32, 91, 69, 17);
		contentPane.add(lbl_titre);
		
		JLabel lbl_livre_auteurId = new JLabel("Auteur ID :");
		lbl_livre_auteurId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbl_livre_auteurId.setBounds(32, 165, 72, 19);
		contentPane.add(lbl_livre_auteurId);
		
		cmb_auteur = new JComboBox();
		cmb_auteur.setModel(new DefaultComboBoxModel(new String[] {"test 1", "test 2", "test 3"}));
		cmb_auteur.setBounds(123, 167, 154, 17);
		contentPane.add(cmb_auteur);
		
		txt_isbn = new JTextField();
		txt_isbn.setBounds(123, 52, 154, 17);
		contentPane.add(txt_isbn);
		txt_isbn.setColumns(10);
		
		txt_titre = new JTextField();
		txt_titre.setColumns(10);
		txt_titre.setBounds(123, 91, 154, 17);
		contentPane.add(txt_titre);
		
		JButton btn_ajouter_livre = new JButton();
		btn_ajouter_livre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String isbn = txt_isbn.getText();
				String titre = txt_titre.getText();
				String genre = txt_genre.getText();
				if(cmb_auteur.getItemCount() > 0 && isbn.length() > 0 && titre.length() > 0 && genre.length() > 0 ) {
					if(ajouter) {
						try {
							Main.ajouterLivre(isbn, titre, genre, cmb_auteur.getSelectedItem().toString());
						} catch (SQLException e1) {
							System.out.println(e1.getMessage());
						}
					}else {
						try {
							Main.modifierLivre(ISBN, isbn, titre, genre, cmb_auteur.getSelectedItem().toString());
						} catch (SQLException e1) {
							System.out.println(e1.getMessage());
						}
					}
					dispose();
				}
			}
		});
		if(ajouter)
			btn_ajouter_livre.setText("Ajouter livre");
		else
			btn_ajouter_livre.setText("Modifier livre");
		btn_ajouter_livre.setBounds(251, 208, 112, 23);
		contentPane.add(btn_ajouter_livre);
		
		txt_genre = new JTextField();
		txt_genre.setColumns(10);
		txt_genre.setBounds(123, 126, 154, 17);
		contentPane.add(txt_genre);
		
		JLabel lbl_genre = new JLabel("Genre :");
		lbl_genre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbl_genre.setBounds(32, 126, 69, 17);
		contentPane.add(lbl_genre);
	}
	public String getISBN() {
		return txt_isbn.getText();
	}
	public void setISBN(String isbn) {
		txt_isbn.setText(isbn);
		ISBN = isbn;
	}
	public String getTitre() {
		return txt_titre.getText();
	}
	public void setTitre(String txt) {
		txt_titre.setText(txt);
	}
	public String getGenre() {
		return txt_genre.getText();
	}
	public void setGenre(String txt) {
		txt_genre.setText(txt);
	}
    public String getAuteur() {
        return (String) cmb_auteur.getSelectedItem();
    }
    public void setAuteur(String auteur) {
        cmb_auteur.setSelectedItem(auteur);
    }
    public void setAuteurOptions(ResultSet r) throws SQLException {
        cmb_auteur.removeAllItems(); 
        while (r.next()) {
            cmb_auteur.addItem(r.getString("ID"));
        }
    }
}
