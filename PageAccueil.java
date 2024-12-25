package tp6;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

public class PageAccueil extends JFrame  {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable auteursTable;
	private JTable livresTable;
	private JButton btn_ajouter_auteur, btn_modifier_auteur, btn_supp_auteur;
	private JButton btn_ajouter_livre, btn_modifier_livre, btn_supp_livre;
	private JButton btn_imprimer_livre, btn_imprimer_auteur;
	private JLabel lblNewLabel;
	
	public PageAccueil() {
		setTitle("Page d'accueil");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(914, 578);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		auteursTable = new JTable();
		auteursTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		auteursTable.setModel(new DefaultTableModel(
				new Object[][] {
				},
			new String[] {
				"Id", "Nom", "Nationalite"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Object.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		auteursTable.getColumnModel().getColumn(0).setResizable(false);
		auteursTable.getColumnModel().getColumn(1).setResizable(false);
		auteursTable.getColumnModel().getColumn(2).setResizable(false);
		auteursTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                	int ligne = auteursTable.getSelectedRow();
                    if (ligne != -1) {
                        enableBtns(true, false);
                    } else {
                    	enableBtns(false, false);
                    }

                }
            }
        });

		livresTable = new JTable();
		livresTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ISBN", "Titre", "Genre", "ID"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		livresTable.getColumnModel().getColumn(0).setResizable(false);
		livresTable.getColumnModel().getColumn(1).setResizable(false);
		livresTable.getColumnModel().getColumn(2).setResizable(false);
		livresTable.getColumnModel().getColumn(3).setResizable(false);
        livresTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                	int ligne = livresTable.getSelectedRow();
                    if (ligne != -1) {
                        enableBtns(true, true);
                    }else {
                    	enableBtns(false, true);
                    }

                }
            }
        });
        
        ActionListener action = new AjouterListener();

		btn_ajouter_auteur = new JButton("Ajouter");
		btn_ajouter_auteur.setBounds(10, 11, 89, 23);
		btn_ajouter_auteur.addActionListener(action);

		btn_modifier_auteur = new JButton("Modifier");
		btn_modifier_auteur.setEnabled(false);
		btn_modifier_auteur.setBounds(124, 45, 87, 23);
		btn_modifier_auteur.addActionListener(action);

		btn_supp_auteur = new JButton("Supprimer");
		btn_supp_auteur.setEnabled(false);
		btn_supp_auteur.setBounds(109, 11, 96, 23);
		btn_supp_auteur.addActionListener(action);

		btn_ajouter_livre = new JButton("Ajouter");
		btn_ajouter_livre.setBounds(805, 11, 89, 23);
		btn_ajouter_livre.addActionListener(action);

		btn_modifier_livre = new JButton("Modifier");
		btn_modifier_livre.setEnabled(false);
		btn_modifier_livre.setBounds(693, 45, 87, 23);
		btn_modifier_livre.addActionListener(action);

		btn_supp_livre = new JButton("Supprimer");
		btn_supp_livre.setEnabled(false);
		btn_supp_livre.setBounds(699, 11, 96, 23);
		btn_supp_livre.addActionListener(action);

		JPanel topPanel = new JPanel();
		topPanel.setBounds(0, 0, 904, 24);
		topPanel.setLayout(null);
		
		lblNewLabel = new JLabel("Gestion des livres et des auteurs");
		lblNewLabel.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		lblNewLabel.setBounds(311, 0, 187, 25);
		topPanel.add(lblNewLabel);
		contentPane.add(topPanel);

		JPanel centerPanel = new JPanel();
		centerPanel.setBounds(0, 24, 904, 445);
		centerPanel.setLayout(null);

		JScrollPane scroll_tab1 = new JScrollPane(auteursTable);
		scroll_tab1.setBounds(0, 0, 396, 444);
		centerPanel.add(scroll_tab1);

		JScrollPane scroll_tab2 = new JScrollPane(livresTable);
		scroll_tab2.setBounds(394, 0, 510, 444);
		centerPanel.add(scroll_tab2);

		contentPane.add(centerPanel);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setBounds(0, 467, 904, 74);
		bottomPanel.setLayout(null);

		bottomPanel.add(btn_ajouter_auteur);
		bottomPanel.add(btn_modifier_auteur);
		bottomPanel.add(btn_supp_auteur);
		
		bottomPanel.add(btn_ajouter_livre);
		bottomPanel.add(btn_modifier_livre);
		bottomPanel.add(btn_supp_livre);
		
		contentPane.add(bottomPanel);
		
		btn_imprimer_auteur = new JButton("Imprimer");
		btn_imprimer_auteur.setBounds(10, 45, 104, 23);
		btn_imprimer_auteur.addActionListener(action);
		bottomPanel.add(btn_imprimer_auteur);
		
		btn_imprimer_livre = new JButton("Imprimer");
		btn_imprimer_livre.setBounds(790, 45, 104, 23);
		btn_imprimer_livre.addActionListener(action);
		bottomPanel.add(btn_imprimer_livre);
	}
	private class AjouterListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btn_ajouter_auteur) {
				Main.ajouterAuteurFrame(false, null, null, null);
			} else if(e.getSource() == btn_modifier_auteur) {
				int ligne = auteursTable.getSelectedRow();
				Object id = auteursTable.getValueAt(ligne, 0);
				Object nom = auteursTable.getValueAt(ligne, 1);
				Object nationalite = auteursTable.getValueAt(ligne, 2);
				Main.ajouterAuteurFrame(true, id.toString(), nom.toString(), nationalite.toString());
			} else if(e.getSource() == btn_supp_auteur) {
				int ligne = auteursTable.getSelectedRow();
				Object id = auteursTable.getValueAt(ligne, 0);
				try {
				    Main.supAuteur(id.toString());
				    DefaultTableModel model = (DefaultTableModel) auteursTable.getModel();
				    model.removeRow(ligne);

				    for (int i = livresTable.getRowCount() - 1; i >= 0; i--) {
				        if (livresTable.getValueAt(i, 3).toString().equals(id.toString())) {
				            DefaultTableModel mdl = (DefaultTableModel) livresTable.getModel();
				            mdl.removeRow(i);
				        }
				    }
				    if(model.getRowCount() > 0)
						btn_imprimer_livre.setEnabled(true);
				} catch (SQLException e1) {
				    System.out.println(e1.getMessage());
				}
		
			} else if(e.getSource() == btn_imprimer_auteur) {
				exporterTable(auteursTable, "auteurs.pdf");
			}
			
			else if(e.getSource() == btn_ajouter_livre) {
				Main.ajouterLivreFrame(false, null, null, null, null);
			} else if(e.getSource() == btn_modifier_livre) {
				int ligne = livresTable.getSelectedRow();
				Object isbn = livresTable.getValueAt(ligne, 0);
				Object titre = livresTable.getValueAt(ligne, 1);
				Object genre = livresTable.getValueAt(ligne, 2);
				Object auteurId = livresTable.getValueAt(ligne, 3);
				Main.ajouterLivreFrame(true, isbn.toString(), titre.toString(), genre.toString(), auteurId.toString());
			} else if(e.getSource() == btn_supp_livre) {
				int ligne = livresTable.getSelectedRow();
				Object isbn = livresTable.getValueAt(ligne, 0);
				try {
					Main.supLivre(isbn.toString());
					DefaultTableModel model = (DefaultTableModel) livresTable.getModel();
					model.removeRow(ligne);
					if(model.getRowCount() > 0)
						btn_imprimer_auteur.setEnabled(true);
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				}
			}else if(e.getSource() == btn_imprimer_livre) {
				exporterTable(livresTable, "livres.pdf");
			}
		}
	}
	private void enableBtns(Boolean val, Boolean livreEnable) {
		if(livreEnable) {
			btn_supp_livre.setEnabled(val);
			btn_modifier_livre.setEnabled(val);
		}else {
			btn_supp_auteur.setEnabled(val);
			btn_modifier_auteur.setEnabled(val);
		}
	}
	public void ajouterAuteursTable(ResultSet resultSet) {
	    DefaultTableModel model = (DefaultTableModel) auteursTable.getModel();
	    model.setRowCount(0);
	    int c = 0;
	    try {
	        while (resultSet.next()) {
	            String id = resultSet.getString("ID");
	            String nom = resultSet.getString("Nom");
	            String nationalite = resultSet.getString("Nationalite");
	            model.addRow(new Object[]{id, nom, nationalite});
	            c++;
	        }
	        btn_imprimer_auteur.setEnabled(c > 0);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public void ajouterLivresTable(ResultSet resultSet) {
	    DefaultTableModel model = (DefaultTableModel) livresTable.getModel();
	    model.setRowCount(0); 
	    int c = 0;
	    try {
	        while (resultSet.next()) {
	            String isbn = resultSet.getString("ISBN");
	            String titre = resultSet.getString("Titre");
	            String genre = resultSet.getString("Genre");
	            String auteurID = resultSet.getString("auteurID");
	            model.addRow(new Object[]{isbn, titre, genre, auteurID});
	            c++;
	        }
	        btn_imprimer_livre.setEnabled(c > 0);
	        	
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	public void ajouterLivreLigne(String isbn, String title, String genre, String auteurId) {
		DefaultTableModel model = (DefaultTableModel) livresTable.getModel();
		model.addRow(new Object[]{isbn, title, genre, auteurId});
		if(model.getRowCount() > 0)
			btn_imprimer_livre.setEnabled(true);
	}
	public void ajouterAuteurLigne(String id, String nom, String nat) {
		DefaultTableModel model = (DefaultTableModel) auteursTable.getModel();
		model.addRow(new Object[]{id, nom, nat});
		if(model.getRowCount() > 0)
			btn_imprimer_auteur.setEnabled(true);
	}
	public void modifierLivreLigne(String isbn, String title, String genre, String auteurId) {
		DefaultTableModel model = (DefaultTableModel) livresTable.getModel();
		int ligne = livresTable.getSelectedRow();
		if(ligne >= 0) {
			model.setValueAt(isbn, ligne, 0);
			model.setValueAt(title, ligne, 1);
			model.setValueAt(genre, ligne, 2);
			model.setValueAt(auteurId, ligne, 3);
		}
	}
	public void modifierAuteurLigne(String id, String nom, String nat) {
		DefaultTableModel model = (DefaultTableModel) auteursTable.getModel();
		int ligne = auteursTable.getSelectedRow();
		if(ligne >= 0) {
			model.setValueAt(id, ligne, 0);
			model.setValueAt(nom, ligne, 1);
			model.setValueAt(nat, ligne, 2);
		}
	}

	public void exporterTable(JTable table, String cheminFichier) {
	    try {
	        Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(cheminFichier));
	        document.open();

	        PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
	        pdfTable.setWidthPercentage(100);
	        pdfTable.setHorizontalAlignment(Element.ALIGN_CENTER);

	        for (int col = 0; col < table.getColumnCount(); col++) {
	            PdfPCell header = new PdfPCell(new Paragraph(table.getColumnName(col)));
	            header.setHorizontalAlignment(Element.ALIGN_CENTER);
	            pdfTable.addCell(header);
	        }

	        for (int ligne = 0; ligne < table.getRowCount(); ligne++) {
	            for (int col = 0; col < table.getColumnCount(); col++) {
	                Object cellValue = table.getValueAt(ligne, col);
	                String cellText = cellValue == null ? "" : cellValue.toString();
	                pdfTable.addCell(new PdfPCell(new Paragraph(cellText)));
	            }
	        }

	        document.add(pdfTable);

	        document.close();
	        System.out.println("PDF cree avec succes : " + cheminFichier);
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    }
	}
}