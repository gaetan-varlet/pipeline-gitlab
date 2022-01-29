package fr.insee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;

public class FermetureRessourcesJDBC {

	DataSource dataSource;

	@Test
	void test1() throws Exception {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement("SELECT * FROM table");
				ResultSet rs = ps.executeQuery();) {
			while (rs.next()) {
				// ...
			}
		} catch (Exception e) {
			// ...
		}
	}

	@Test
	void test2() throws Exception {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement("SELECT * FROM table WHERE code = ?");) {
			ps.setString(1, "AZE");
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					// ...
				}
			}
		} catch (Exception e) {
			// ...
		}
	}

	@Test
	void test3() throws Exception {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement("SELECT * FROM table");) {
			int nbLignes = ps.executeUpdate();
			System.out.println(nbLignes);
			// ...
		} catch (Exception e) {
			// ...
		}
	}

}
