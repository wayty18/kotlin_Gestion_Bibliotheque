/** Livre : empruntable ET consultable sur place (sauf s'il est emprunté). */
class Livre(
    titre: String,
    val auteur: String,
    val editeur: String,
    dateDeParution: String
) : MediaEmpruntable(titre, dateDeParution), Consultable {

    init {
        require(auteur.isNotBlank()) { "L'auteur ne peut pas être vide." }
        require(editeur.isNotBlank()) { "L'éditeur ne peut pas être vide." }
    }

    override val typeMedia = "Livre"
    override val designation: String get() = "Le livre '$titre' de '$auteur'"

    override fun peutEtreConsulte() = !estEmprunte

    override fun consulter() = println("$designation est ${accorder("consulté")} sur place.")

    override fun afficher() = println(
        "$typeMedia: Titre = '$titre', Auteur = '$auteur', Éditeur = '$editeur', Date de parution = '$dateDeParution'"
    )
}

/** Magazine : consultation sur place uniquement. */
class Magazine(
    titre: String,
    dateDeParution: String,
    val numero: String
) : Media(titre, dateDeParution), Consultable {

    init {
        require(numero.isNotBlank()) { "Le numéro ne peut pas être vide." }
    }

    override val typeMedia = "Magazine"
    override val designation: String get() = "Le magazine '$titre', numéro '$numero' ($dateDeParution)"

    override fun consulter() = println("$designation est ${accorder("consulté")} sur place.")

    override fun afficher() = println(
        "$typeMedia: Titre = '$titre', Numéro = '$numero', Date de parution = '$dateDeParution'"
    )
}

/** Journal : consultation sur place uniquement. */
class Journal(titre: String, dateDeParution: String) : Media(titre, dateDeParution), Consultable {

    override val typeMedia = "Journal"
    override val designation: String get() = "Le journal '$titre' du '$dateDeParution'"

    override fun consulter() = println("$designation est ${accorder("consulté")} sur place.")

    override fun afficher() = println("$typeMedia: Titre = '$titre', Date de parution = '$dateDeParution'")
}

/** Enregistrement audio : empruntable uniquement. [duree] en secondes. */
class EnregistrementAudio(
    titre: String,
    val duree: Int,
    val genre: String,
    dateDeParution: String
) : MediaEmpruntable(titre, dateDeParution) {

    init {
        require(duree > 0) { "La durée doit être strictement positive (en secondes)." }
        require(genre.isNotBlank()) { "Le genre ne peut pas être vide." }
    }

    override val typeMedia = "Enregistrement Audio"
    override val designation: String get() = "L'enregistrement audio '$titre'"

    override fun afficher() = println("$typeMedia: Titre = '$titre', Durée = '$duree' secondes, Genre = '$genre'")
}

/** DVD : empruntable uniquement. [duree] en secondes. */
class DVD(
    titre: String,
    val duree: Int,
    val genre: String,
    dateDeParution: String
) : MediaEmpruntable(titre, dateDeParution) {

    init {
        require(duree > 0) { "La durée doit être strictement positive (en secondes)." }
        require(genre.isNotBlank()) { "Le genre ne peut pas être vide." }
    }

    override val typeMedia = "DVD"
    override val designation: String get() = "Le DVD '$titre'"

    override fun afficher() = println("$typeMedia: Titre = '$titre', Durée = '$duree' secondes, Genre = '$genre'")
}
