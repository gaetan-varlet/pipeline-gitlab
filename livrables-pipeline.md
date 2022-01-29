# Création des livrables et déploiement automatique par pipeline

## Rappel sur les phases Maven

- `clean` => nettoyage du projet
- `compile` => compilation du code source du projet
- `test` => exécution des tests unitaires
- `package` => package le code compilé dans un format distribuable comme un JAR, dans le/les dossier(s) *target* du projet
- `verify` => exécution des tests d'intégration
- `install` => installe le livrable dans le dépôt local, pour l'utiliser comme dépendance dans d'autres projets locaux
- `deploy` => copie le paquet final dans le dépôt distant pour le partager avec d'autres développeurs et projets

## Création des livrables avec Maven

### Ce qu'on va faire

Projet **project** avec 3 modules
- **project-core** en version 1.0.0
- **project-web** en version 1.1.0
- **project-batch** en version 1.2.0

Livrables pour le module WEB :
- création d'un war : **ROOT.war** pour déploiement en hors-prod à la racine du tomcat
- création d'un zip **project-1.1.0.zip** ou **project-web-1.1.0.zip**, contenant **project.war** et **project.properties**

Livrables pour le module BATCH :
- création d'un jar **project-batch-1.2.0.jar**
- création d'un zip **project-batch-1.2.0.zip** contenant un dossier lib avec le JAR du batch et toutes les bibliothèques nécessaires au fonctionnement

### Comment le faire

Utilisation de **Maven Assembly Plugin**
- plugin qui permet combiner les résultats d'un projet en une seule archive distribuable qui contient également les dépendances, les modules, la documentation du site et d'autres fichiers.
- permet de construire des assemblages de distribution en utilisant l'un des descripteurs préfabriqués. Alternativement, votre projet peut fournir son propre descripteur et assumer un niveau de contrôle beaucoup plus élevé sur la façon dont les dépendances, les modules, les ensembles de fichiers et les fichiers individuels sont empaquetés dans l'assemblage


```xml
<plugins>
	<plugin>
	<artifactId>maven-assembly-plugin</artifactId>
	<executions>
		<execution>
		<id>make-assembly</id>
		<phase>package</phase>
		<goals>
			<goal>single</goal>
		</goals>
		<configuration>
			<descriptors>
			<descriptor>src/main/resources/assembly-batch.xml</descriptor>
			</descriptors>
			<finalName>project-batch-${project.version}</finalName>              <!-- changer le nom du zip, par défaut même nom que le jar -->
		</configuration>
		</execution>
	</executions>
	</plugin>
</plugins>
```


## Livraison par pipeline Gitlab
