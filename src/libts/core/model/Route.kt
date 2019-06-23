package libts.core.model

import libts.core.scraper.StopScraper

/**
 * Represents a route part of an [Agency]
 *
 * **Warning**: a route may have an agency code/name different from the code of the agency it's part of.
 *
 * This can happen if it is part of a "subsystem" of the agency. i.e. the TTC subway.
 *
 * This is due to the way TransSee handles those cases. When such alternative agency code/name exists for the route,
 *
 * it will be indicated in [altAgencyCode] and [altAgencyName] respectively.
 *
 * @property agency the [Agency] this route is part of
 * @property name   the name of the route, with possibly multiple words
 * @property code   a consecutive alphanumeric code that represents the route
 *
 * @property stops a lazily-loaded list of stops this route makes
 *
 * @property altAgencyCode the alternative agency code, if it differs
 * @property altAgencyName the alternative agency name, if it differs
 */
data class Route (
    val agency: Agency,
    val name:   String,
    val code:   String
) {

    var altAgencyName = ""
    var altAgencyCode = ""

    val stops by lazy { StopScraper.scrape(this) }

    /**
     * Finds a [Stop] along this [Route] using it's [alphanumeric code][Stop.code]
     */
    fun stop(code: String) = stops.find { it.code == code }
        ?: error("libts -> ERROR: no stop with code \"$code\" for route \"${this.code}\" in agency \"${this.agency.code}\"")

    override fun toString(): String {
        return "Route(name='$name', code='$code')"
    }

    /**
     * Represents a direction of a [Route]
     *
     * @property route
     * @property name
     * @property code
     */
    data class Direction (
        val route: Route,
        val name:  String,
        val code:  String
    ) {

        override fun toString(): String {
            return "Direction(name='$name', code='$code')"
        }

    }

}