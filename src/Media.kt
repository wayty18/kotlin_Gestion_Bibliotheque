import java.time.LocalDate
import java.time.format.DateTimeParseException

/**
 * Classe de base de tous les médias.
 *
 * @param titre titre du média (non vide)
 * @param dateDeParution date au format ISO 8601 : YYYY-MM-DD.
 * Si la date exacte est inconnue, utiliser une date conventionnelle valide.
 */
abstract class Media(val titre: String, val dateDeParution: String) {

    init {
        require(titre.isNotBlank()) { "Le titre ne peut pas être vide." }
        try {
            LocalDate.parse(dateDeParution) // ISO_LOCAL_DATE = YYYY-MM-DD, vérifie aussi que la date existe
        } catch (e: DateTimeParseException) {
            throw IllegalArgumentException(
                "Date de parution invalide '$dateDeParution' (format attendu : YYYY-MM-DD).", e
            )
        }
    }

    /** Nom du type de média, utilisé pour l'affichage et les statistiques. */
    abstract val typeMedia: String

    /** Courte description utilisée dans les messages, ex. "Le DVD 'Inception'". */
    abstract val designation: String

    /** Vrai pour les médias féminins (sert à accorder les participes : empruntée, consultée...). */
    protected open val feminin: Boolean get() = false

    /** Accorde un participe passé : accorder("emprunté") -> "empruntée" si le média est féminin. */
    fun accorder(participe: String): String = if (feminin) participe + "e" else participe

    /** Affiche (sans retour à la ligne initial) les détails spécifiques du média. */
    abstract fun afficher()
}

/**
 * Média qui peut être emprunté : gère l'état « emprunté / disponible ».
 * Évite de dupliquer cette logique dans Livre, Audio, DVD.
 */
abstract class MediaEmpruntable(titre: String, dateDeParution: String) :
    Media(titre, dateDeParution), Empruntable {

    override var estEmprunte: Boolean = false
        protected set

    override fun emprunter(): Boolean {
        if (estEmprunte) return false
        estEmprunte = true
        return true
    }

    override fun retourner(): Boolean {
        if (!estEmprunte) return false
        estEmprunte = false
        return true
    }
}