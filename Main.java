package tp6;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	private static PageAccueil page;
	private static AuteurF autF;
	private static LivreF livreF;
	private static Connection con;
	
	public static void main(String[] args) throws SQLException {
		con = conn();
		if(con == null)
			return;
		page = new PageAccueil();
		page.ajouterAuteursTable(selectAuteurs());
		page.ajouterLivresTable(selectLivres());
		page.setVisible(true);
	}
    public static Connection conn() {
        String url = "jdbc:mysql://localhost:3306/java_test";
        String username = "root";
        String password = ""; 

        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }
    public static void ajouterAuteurFrame(boolean modifier, String id, String nom, String nationalite) {
    	autF = new AuteurF(!modifier);
    	if(modifier) {
    		autF.setNom(nom);
    		autF.setNat(nationalite);
    		autF.setId(id);
    	}
		autF.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent e) {
	            page.setEnabled(true);
	        }
	    });
		autF.setVisible(true);
		page.setEnabled(false);
    }
    public static void ajouterLivreFrame(boolean modifier, String isbn, String titre, String genre, String id)  {
        livreF = new LivreF(!modifier);
        try {
			livreF.setAuteurOptions(selectAuteurs());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
        if(modifier) {
        	livreF.setISBN(isbn);
        	livreF.setTitre(titre);
        	livreF.setGenre(genre);
        	livreF.setAuteur(id);
        }
        livreF.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                page.setEnabled(true); 
            }
        });
        livreF.setVisible(true);
        page.setEnabled(false);
    }
    
    public static void ajouterLivre(String isbn, String titre, String genre, String id) throws SQLException {
        Statement stm = con.createStatement();
        String query = "INSERT INTO livres (ISBN, Titre, Genre, AuteurID) VALUES (\"" + isbn + "\", \"" + titre + "\", \"" + genre + "\", \"" + id + "\");";
        stm.executeUpdate(query);
        page.ajouterLivreLigne(isbn, titre, genre, id);
        page.setEnabled(true);
    }
    public static void modifierLivre(String ISBN, String isbn, String titre, String genre, String id) throws SQLException {
        Statement stm = con.createStatement();
        String query = "UPDATE livres SET ISBN = \"" + isbn + "\", Titre = \"" + titre + "\", Genre = \"" + 
        				genre + "\", AuteurID = \"" + id + "\" WHERE ISBN = \"" + ISBN + "\";";
        System.out.println();
        stm.executeUpdate(query);
        page.modifierLivreLigne(isbn, titre, genre, id);
    	page.setEnabled(true);
    }
    public static void supLivre(String isbn) throws SQLException {
    	Statement stm = con.createStatement();
    	stm.executeUpdate("DELETE FROM livres WHERE ISBN = \"" + isbn + "\";");
    }
    
    public static void ajouterAuteur(String nom, String nationalite) throws SQLException {
        Statement stm = con.createStatement();
        String query = "INSERT INTO auteurs (Nom, Nationalite) VALUES (\"" + nom + "\", \"" + nationalite + "\");";
        stm.executeUpdate(query);
        
        query = "SELECT ID FROM auteurs WHERE Nom = \"" + nom + "\" AND Nationalite = \"" + nationalite + "\""; 
        ResultSet rs = stm.executeQuery(query);
    	if (rs.next()) {
    		String id = rs.getString("ID"); 
    		page.ajouterAuteurLigne(id, nom, nationalite);
    	}
    	page.setEnabled(true);
    }
    public static void modifierAuteur(String id, String nom, String nationalite) throws SQLException {
        Statement stm = con.createStatement();
        String query = "UPDATE auteurs SET Nom = \"" + nom + "\", Nationalite = \"" + nationalite + "\" WHERE ID = \"" + id + "\";";
        stm.executeUpdate(query);
        page.modifierAuteurLigne(id, nom, nationalite);
    	page.setEnabled(true);
    }
    public static void supAuteur(String id) throws SQLException {
    	Statement stm = con.createStatement();
    	stm.executeUpdate("DELETE FROM auteurs WHERE ID = " + id + ";");
    }
    
    public static ResultSet selectAuteurs() throws SQLException {
        Statement stm = con.createStatement();
        String query = "SELECT * FROM auteurs;";
        return stm.executeQuery(query);
    }
    public static ResultSet selectLivres() throws SQLException {
        Statement stm = con.createStatement();
        String query = "SELECT * FROM livres;";
        return stm.executeQuery(query);
    }
}
