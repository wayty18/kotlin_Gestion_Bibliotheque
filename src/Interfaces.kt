/**
 * Interactions possibles avec un média.
 */

/** Un média qui peut être emporté puis rendu. */
interface Empruntable {
    /** true si le média est actuellement emprunté. */
    val estEmprunte: Boolean

    /** Emprunte le média. Retourne true si l'emprunt a réussi (false s'il était déjà emprunté). */
    fun emprunter(): Boolean

    /** Retourne le média. Retourne true si le retour a réussi (false s'il n'était pas emprunté). */
    fun retourner(): Boolean
}

/** Un média qui peut être consulté sur place. */
interface Consultable {
    /** Indique si la consultation sur place est possible à cet instant (vrai par défaut). */
    fun peutEtreConsulte(): Boolean = true

    /** Consulte le média sur place. */
    fun consulter()
}
