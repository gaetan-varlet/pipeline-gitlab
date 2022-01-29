# Déploiement automatique par pipeline Gitlab

## Ce qu'on va faire

- lancement automatique des tests et déploiement automatique sur Sonar
- création des livrables de manière automatique
- déploiement manuel du module web
  - déploiement du livrable (war) et des properties correspondant à l'environnement en hord production, quelque soit la branche
  - dépôt du livrable (zip) sur le nexus pour une mise en production, uniquement sur la présence d'un tag
  - appel de majiba-api pour un déploiement en pré-production ou production, uniquement sur la présence d'un tag
- déploiement manuel du module batch
  - déploiement du livrable (zip) et des properties correspondant à l'environnement en hord production, quelque soit la branche
  - dépôt du livrable (zip) sur le nexus pour une mise en production, uniquement sur la présence d'un tag
  - appel de majiba-api pour un déploiement en pré-production ou production, uniquement sur la présence d'un tag

## Mise en place

Création du fichier `.gitlab-ci.yml` à la racine du projet
