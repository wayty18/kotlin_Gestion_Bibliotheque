/**
 * Gère le catalogue de médias et le suivi des emprunts.
 * Les méthodes affichent un message et retournent true si l'action a réussi.
 */
class Bibliotheque {
    private val medias = mutableListOf<Media>()
    private val emprunts = mutableListOf<Media>()

    /** Copie en lecture seule du catalogue. */
    fun catalogue(): List<Media> = medias.toList()

    fun ajouterMedia(media: Media, silencieux: Boolean = false): Boolean {
        if (media in medias) {
            if (!silencieux) println("${media.designation} est déjà ${media.accorder("présent")} dans la bibliothèque.")
            return false
        }
        medias.add(media)
        if (!silencieux) println("${media.designation} a été ${media.accorder("ajouté")} à la bibliothèque.")
        return true
    }

    fun emprunter(media: Media): Boolean {
        if (media !is Empruntable) {
            println("${media.designation} ne peut pas être ${media.accorder("emprunté")} (consultation sur place uniquement).")
            return false
        }
        enregistrerSiAbsent(media)
        if (!media.emprunter()) {
            println("${media.designation} est déjà ${media.accorder("emprunté")}.")
            return false
        }
        emprunts.add(media)
        println("${media.designation} a été ${media.accorder("emprunté")}.")
        return true
    }

    fun retourner(media: Media): Boolean {
        if (media !is Empruntable) {
            println("${media.designation} ne peut pas être ${media.accorder("retourné")} (il n'est pas empruntable).")
            return false
        }
        if (media !in emprunts || !media.retourner()) {
            println("${media.designation} n'est pas actuellement ${media.accorder("emprunté")}.")
            return false
        }
        emprunts.remove(media)
        println("${media.designation} a été ${media.accorder("retourné")}.")
        return true
    }

    fun consulter(media: Media): Boolean {
        if (media !is Consultable) {
            println("${media.designation} ne peut pas être ${media.accorder("consulté")} sur place.")
            return false
        }
        if (!media.peutEtreConsulte()) {
            println("${media.designation} est actuellement ${media.accorder("emprunté")} : consultation sur place impossible.")
            return false
        }
        enregistrerSiAbsent(media)
        media.consulter()
        return true
    }

    fun afficherEmprunts() {
        if (emprunts.isEmpty()) {
            println("Aucun emprunt en cours.")
            return
        }
        println("Emprunts en cours:")
        emprunts.forEach {
            print(" - ")
            it.afficher()
        }
    }

    // ------------------------- Bonus -------------------------

    /** Recherche par titre (insensible à la casse, correspondance partielle). */
    fun rechercher(titre: String): List<Media> {
        val critere = titre.trim()
        if (critere.isEmpty()) return emptyList()
        return medias.filter { it.titre.contains(critere, ignoreCase = true) }
    }

    fun afficherRecherche(titre: String) {
        val resultats = rechercher(titre)
        if (resultats.isEmpty()) {
            println("Aucun média trouvé pour '$titre'.")
            return
        }
        println("Résultats pour '$titre' :")
        resultats.forEach {
            print(" - ")
            it.afficher()
        }
    }

    /** Médias empruntables et non empruntés. */
    fun mediasDisponibles(): List<Media> = medias.filter { it is Empruntable && !it.estEmprunte }

    fun afficherDisponibles() {
        val disponibles = mediasDisponibles()
        if (disponibles.isEmpty()) {
            println("Aucun média empruntable n'est disponible.")
            return
        }
        println("Médias disponibles à l'emprunt :")
        disponibles.forEach {
            print(" - ")
            it.afficher()
        }
    }

    fun afficherStatistiques() {
        println("Statistiques (${medias.size} média(s) au total) :")
        medias.groupingBy { it.typeMedia }.eachCount().forEach { (type, nombre) ->
            println(" - $type : $nombre")
        }
    }

    fun afficherCatalogue() {
        if (medias.isEmpty()) {
            println("La bibliothèque est vide.")
            return
        }
        println("Catalogue :")
        medias.forEach {
            print(" - ")
            it.afficher()
        }
    }

    /** Un média manipulé sans avoir été ajouté (cas de l'énoncé) est enregistré silencieusement. */
    private fun enregistrerSiAbsent(media: Media) {
        if (media !in medias) medias.add(media)
    }
}
