/** Menu interactif en console (bonus), avec validation des saisies. */
fun lancerMenu(bibliotheque: Bibliotheque) {
    while (true) {
        println(
            """
            
            ===== MENU BIBLIOTHÈQUE =====
            1. Afficher le catalogue
            2. Emprunter un média
            3. Retourner un média
            4. Consulter un média sur place
            5. Afficher les emprunts en cours
            6. Rechercher un média par titre
            7. Afficher les médias disponibles
            8. Afficher les statistiques
            0. Quitter
            """.trimIndent()
        )
        when (lireEntier("Votre choix : ", 0, 8)) {
            null, 0 -> {
                println("Au revoir !")
                return
            }
            1 -> bibliotheque.afficherCatalogue()
            2 -> choisirMedia(bibliotheque)?.let { bibliotheque.emprunter(it) }
            3 -> choisirMedia(bibliotheque)?.let { bibliotheque.retourner(it) }
            4 -> choisirMedia(bibliotheque)?.let { bibliotheque.consulter(it) }
            5 -> bibliotheque.afficherEmprunts()
            6 -> {
                print("Titre (ou partie du titre) : ")
                bibliotheque.afficherRecherche(readlnOrNull() ?: "")
            }
            7 -> bibliotheque.afficherDisponibles()
            8 -> bibliotheque.afficherStatistiques()
        }
    }
}

/** Demande un entier entre min et max (inclus). Retourne null si l'entrée est fermée (EOF). */
private fun lireEntier(invite: String, min: Int, max: Int): Int? {
    while (true) {
        print(invite)
        val ligne = readlnOrNull() ?: return null
        val nombre = ligne.trim().toIntOrNull()
        if (nombre != null && nombre in min..max) return nombre
        println("Saisie invalide : entrez un nombre entre $min et $max.")
    }
}

/** Affiche le catalogue numéroté et retourne le média choisi (null si annulation). */
private fun choisirMedia(bibliotheque: Bibliotheque): Media? {
    val catalogue = bibliotheque.catalogue()
    if (catalogue.isEmpty()) {
        println("La bibliothèque est vide.")
        return null
    }
    catalogue.forEachIndexed { index, media ->
        print("${index + 1}. ")
        media.afficher()
    }
    val choix = lireEntier("Numéro du média (0 pour annuler) : ", 0, catalogue.size)
    return if (choix == null || choix == 0) null else catalogue[choix - 1]
}
