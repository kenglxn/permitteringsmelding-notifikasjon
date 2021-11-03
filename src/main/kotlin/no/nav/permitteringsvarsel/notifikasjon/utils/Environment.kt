package no.nav.permitteringsvarsel.notifikasjon.utils

object Environment {
    private val miljøvariabler: MutableMap<String, String> = HashMap()

    fun get(s: String): String? {
        return miljøvariabler[s] ?: System.getenv(s)
    }

    /**
     * For testkode: Bør brukes kun før startLokalApp(...) for å unngå forvirring om hvilken konfig som gjelder
     */
    fun set(s: String, value: String) {
        miljøvariabler[s] = value
    }
}