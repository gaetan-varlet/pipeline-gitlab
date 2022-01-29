package fr.insee;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class FermetureRessourcesBR {

	@Test
	void test1() throws Exception {
		Path path = Paths.get("/home/gaetan/depot-git/pipeline-gitlab/test.csv");
		BufferedReader reader = Files.newBufferedReader(path);
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		reader.close();
	}

	@Test
	void test2() throws Exception {
		Path path = Paths.get("/home/gaetan/depot-git/pipeline-gitlab/test.csv");
		BufferedReader reader = null;
		try {
			reader = Files.newBufferedReader(path);
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception e) {
			// ...
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// ignoré
				}
			}
		}
	}

	@Test
	void test3() throws Exception {
		Path path = Paths.get("/home/gaetan/depot-git/pipeline-gitlab/test.csv");
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception e) {
			// ...
		}
	}

}
