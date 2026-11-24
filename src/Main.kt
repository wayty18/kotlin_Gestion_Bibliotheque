/** Point d'entrée : exécute plusieurs scénarios de test puis propose le menu interactif. */
fun main() {
    titre("1. Scénario de l'énoncé")
    scenarioEnonce()

    titre("2. Livre : ajout, emprunt, retour, consultation")

    titre("3. Cas particuliers (actions invalides)")

    titre("4. Gestion des exceptions (données invalides)")
    scenarioExceptions()

    titre("5. Bonus : recherche, disponibilité, statistiques")
    scenarioBonus()

    print("\nLancer le menu interactif ? (o/n) : ")
    if (readlnOrNull()?.trim()?.lowercase() == "o") {
        lancerMenu(creerBibliothequeDemo())
    }
}

private fun titre(texte: String) = println("\n===== $texte =====")

private fun scenarioEnonce() {
    val bibliotheque = Bibliotheque()
    val magazine = Magazine("National Geographic", "2026-10-28", "10")
    val journal = Journal("Le Monde", "2026-10-28")
    val enregistrementAudio = EnregistrementAudio("Beethoven - Symphonie No.9", 960, "Classical", "1967-01-01")
    val dvd = DVD("Inception", 8880, "Action", "2010-01-01") /*2h 28m*/
    val livre = Livre("1984", "George Orwell", "Gallimard", "1972-01-01")

    bibliotheque.emprunter(dvd)
    bibliotheque.emprunter(livre)
    bibliotheque.consulter(magazine)
    bibliotheque.consulter(journal)
    bibliotheque.emprunter(enregistrementAudio)

    bibliotheque.afficherEmprunts()

    bibliotheque.retourner(dvd)
    bibliotheque.retourner(livre)
    bibliotheque.afficherEmprunts()
}


private fun scenarioExceptions() {
    tenter("Titre vide") { Journal("  ", "2026-10-28") }
    tenter("Date au mauvais format") { Livre("1984", "George Orwell", "Gallimard", "28/10/1972") }
    tenter("Date inexistante") { Journal("Le Monde", "2026-02-30") }
    tenter("Durée négative") { DVD("Inception", -5, "Action", "2010-01-01") }
}

/** Exécute [action] et affiche l'erreur de validation attendue. */
private fun tenter(description: String, action: () -> Unit) {
    try {
        action()
        println("$description -> aucune erreur (inattendu)")
    } catch (e: IllegalArgumentException) {
        println("$description -> erreur attendue : ${e.message}")
    }
}

private fun scenarioBonus() {
    val bibliotheque = creerBibliothequeDemo()
    bibliotheque.afficherRecherche("introuvable")
    bibliotheque.emprunter(bibliotheque.rechercher("Inception").first())
    bibliotheque.afficherDisponibles()
    bibliotheque.afficherStatistiques()
}

/** Bibliothèque pré-remplie pour les démonstrations et le menu. */
private fun creerBibliothequeDemo(): Bibliotheque {
    val bibliotheque = Bibliotheque()
    listOf(
        Livre("1984", "George Orwell", "Gallimard", "1972-01-01"),
        Magazine("National Geographic", "2026-10-28", "10"),
        Journal("Le Monde", "2026-10-28"),
        EnregistrementAudio("Beethoven - Symphonie No.9", 960, "Classical", "1967-01-01"),
        DVD("Inception", 8880, "Action", "2010-01-01")
    ).forEach { media ->
        bibliotheque.ajouterMedia(media, silencieux = true)
    }
    return bibliotheque
}
