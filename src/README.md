# Mini-projet : Système de gestion de bibliothèque (Kotlin)

## Exécution (IntelliJ IDEA)
1. Créer un projet Kotlin (JDK 17) et copier les fichiers du dossier `src/` dans le dossier `src` du projet.
2. Ouvrir `Main.kt` et cliquer sur le bouton ▶ à côté de `fun main()`.
3. Le programme exécute 5 scénarios de test, puis propose de lancer le menu interactif (répondre `o`).

## Structure
| Fichier | Rôle |
|---|---|
| `Media.kt` | Classe abstraite `Media` (validation titre + date ISO) et `MediaEmpruntable` (état emprunté) |
| `Interfaces.kt` | Interfaces `Empruntable` et `Consultable` |
| `TypesDeMedias.kt` | `Livre`,`Magazine`, `Journal`, `EnregistrementAudio`, `DVD` |
| `Bibliotheque.kt` | Catalogue, emprunts, bonus (recherche, disponibilité, statistiques) |
| `Menu.kt` | Menu interactif avec validation des saisies (bonus) |
| `Main.kt` | Scénarios de test |
