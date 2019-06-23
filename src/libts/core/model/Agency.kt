package libts.core.model

import libts.core.scraper.route.RouteScraper

/**
 * Represents an agency tracked by transsee
 *
 * @property name   the name of the agency, with multiple words
 * @property code   a consecutive alphanumeric code that represents the agency.
 * @property routes a lazily-loaded list of this agency's routes
 */
data class Agency(val name: String, val code: String) {

    val routes by lazy { RouteScraper.scrape(this) }

    /**
     * Finds a [Route] within this [Agency] using it's [alphanumeric code][Route.code]
     */
    fun route(code: String) = routes.find { it.code == code }
        ?: error("libts -> ERROR: no route with code \"$code\" for agency \"${this.code}\"")

}