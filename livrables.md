# Création des livrables avec Maven

## Rappel sur les phases Maven

- `clean` => nettoyage du projet
- `compile` => compilation du code source du projet
- `test` => exécution des tests unitaires
- `package` => package le code compilé dans un format distribuable comme un JAR, dans le/les dossier(s) *target* du projet
- `verify` => exécution des tests d'intégration
- `install` => installe le livrable dans le dépôt local, pour l'utiliser comme dépendance dans d'autres projets locaux
- `deploy` => copie le paquet final dans le dépôt distant pour le partager avec d'autres développeurs et projets


## Ce qu'on va faire

**DISCLAIMER** : properties des différents environnements dans le code source, la sortie du code source se fera dans une présentation future

Projet **project** avec 3 modules, en version 2.0.0
- **project-core** en version 1.0.0
- **project-web** en version 1.1.0
- **project-batch** en version 1.2.0

Livrables pour le module WEB :
- création d'un war : **ROOT.war** pour déploiement en hors-prod à la racine du tomcat
- création d'un zip **project-1.1.0.zip** ou **project-web-1.1.0.zip**, contenant **project.war** et **project.properties**

Livrables pour le module BATCH :
- création d'un jar **project-batch-1.2.0.jar**
- création d'un zip **project-batch-1.2.0.zip** contenant un dossier lib avec le JAR du batch et toutes les bibliothèques nécessaires au fonctionnement

## Comment le faire

Utilisation de **Maven Assembly Plugin**
- plugin qui permet combiner les résultats d'un projet en une seule archive distribuable qui contient également les dépendances, les modules, la documentation du site et d'autres fichiers.
- permet de construire des assemblages de distribution en utilisant l'un des descripteurs préfabriqués. Alternativement, votre projet peut fournir son propre descripteur et assumer un niveau de contrôle beaucoup plus élevé sur la façon dont les dépendances, les modules, les ensembles de fichiers et les fichiers individuels sont empaquetés dans l'assemblage
- utilisation du **goal single**, seul goal devant utilisé aujourd'hui, qui permet d'assembler une application à partir d'un descripteur. Cet objectif peut être lié au cycle de vie (`mvn package`) ou appelé directement depuis la ligne de commande (`mvn package assembly:single`)


### Configuration module batch

POM du module batch :
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>fr.insee</groupId>
    <artifactId>parent-project</artifactId>
    <version>2.0.0</version>
  </parent>

  <groupId>fr.insee</groupId>
  <artifactId>project-batch</artifactId>
  <version>1.2.0</version>
  <packaging>jar</packaging>

  <dependencies>
    <dependency>
      <groupId>fr.insee</groupId>
      <artifactId>project-core</artifactId>
      <version>1.0.0</version>
    </dependency>
  </dependencies>

  <build>
    <!-- permet de lire les propriétés maven dans les properties -->
    <resources>
      <resource>
        <directory>src/main/resources</directory>
        <filtering>true</filtering>
      </resource>
    </resources>
    <plugins>
      <plugin>
        <artifactId>maven-assembly-plugin</artifactId>
        <version>3.3.0</version>
        <configuration>
          <descriptors>
            <descriptor>src/main/resources/assembly-batch.xml</descriptor>
          </descriptors>
          <!-- changer le nom du zip, par défaut même nom que le jar -->
          <finalName>project-batch</finalName>
        </configuration>
        <executions>
          <execution>
            <id>make-assembly</id>
            <!-- permet de lier l'assembly dans le cycle de vie de construction du livrable -->
            <phase>package</phase>
            <goals>
              <goal>single</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>

</project>
```

Descripteur du module batch :
```xml
<assembly xmlns="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.0 http://maven.apache.org/xsd/assembly-1.1.0.xsd">
	<id>${project.version}</id>
	<formats>
		<format>zip</format>
	</formats>
	<!-- permet de ne pas créer de dossier contenant les éléments dans le zip -->
	<includeBaseDirectory>false</includeBaseDirectory>

	<dependencySets>
		<dependencySet>
			<outputDirectory>lib</outputDirectory>
		</dependencySet>
	</dependencySets>
</assembly>
```

### Configuration module web

POM du module web :
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>fr.insee</groupId>
    <artifactId>parent-project</artifactId>
    <version>2.0.0</version>
  </parent>

  <groupId>fr.insee</groupId>
  <artifactId>project-web</artifactId>
  <version>1.1.0</version>
  <packaging>war</packaging>

  <dependencies>
    <dependency>
      <groupId>fr.insee</groupId>
      <artifactId>project-core</artifactId>
      <version>1.0.0</version>
    </dependency>
  </dependencies>

  <build>
    <!-- permet de lire les propriétés maven dans les properties -->
    <resources>
      <resource>
        <directory>src/main/resources</directory>
        <filtering>true</filtering>
      </resource>
    </resources>
    <finalName>ROOT</finalName>    <!-- définit le nom du WAR, par défaut ${artifactId}-${version} -->
    <plugins>
      <plugin>
        <artifactId>maven-assembly-plugin</artifactId>
        <version>3.3.0</version>
        <configuration>
          <descriptors>
            <descriptor>src/main/resources/assembly-web.xml</descriptor>
          </descriptors>
          <!-- définit le nom du ZIP, par défaut même nom que le WAR -->
          <finalName>project</finalName>
        </configuration>
        <executions>
          <execution>
            <id>make-assembly</id>
            <phase>package</phase>
            <goals>
              <goal>single</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>

</project>
```

Descripteur du module web :
```xml
<assembly xmlns="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.2"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.2 http://maven.apache.org/xsd/assembly-1.1.2.xsd">
	<id>${project.version}</id>
	<formats>
		<format>zip</format>
	</formats>
	<includeBaseDirectory>false</includeBaseDirectory>

	<files>
		<file>
			<source>target/ROOT.war</source>
			<outputDirectory />
			<destName>project.war</destName>
		</file>
		<file>
			<source>src/main/resources/project.properties.PROD</source>
			<outputDirectory />
			<destName>project.properties</destName>
		</file>
	</files>
</assembly>
```
