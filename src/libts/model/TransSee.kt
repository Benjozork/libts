package libts.model

import libts.scraper.AgencyScraper
import libts.scraper.RouteScraper
import libts.scraper.StopScraper

/**
 * The base namespace object holding references to all available and supported transsee information
 */
object TransSee {

    const val BASE_URL = "https://transsee.ca/"

    /**
     * Represents an agency tracked by transsee
     *
     * @property name   the name of the agency, with multiple words
     * @property code   a consecutive alphanumeric code that represents the agency.
     * @property routes a lazily-loaded list of this agency's routes
     */
    class Agency(val name: String, val code: String) {

        val routes by lazy { RouteScraper.scrape(this) }

        /**
         * Finds a [Route] within this [Agency] using it's [alphanumeric code][Route.code]
         */
        fun route(code: String) = routes.find { it.code == code }
            ?: error("libts -> ERROR: no route with code \"$code\" for agency \"${this.code}\"")

    }

    /**
     * Represents a route part of an [Agency]
     *
     * @property agency the [Agency] this route is part of
     * @property name   the name of the route, with possibly multiple words
     * @property code   a consecutive alphanumeric code that represents the route
     * @property stops  a lazily-loaded list of stops this route makes
     */
    class Route(val agency: Agency, val name: String, val code: String) {

        val stops by lazy { StopScraper.scrape(this) }

        /**
         * Finds a [Stop] along this [Route] using it's [alphanumeric code][Stop.code]
         */
        fun stop(code: String) = stops.find { it.code == code }
            ?: error("libts -> ERROR: no stop with code \"$code\" for route \"${this.code}\" in agency \"${this.agency.code}\"")

        override fun toString(): String {
            return name
        }

    }

    /**
     * Represents a stop along a [Route]
     *
     * WARNING: operations using stops are currently very limited
     *
     * @property code a consecutive alphanumeric code that represents the stop
     */
    class Stop(val code: String) {

        override fun toString(): String {
            return code
        }

    }

    /**
     * Represents a singular bus along a [Route]
     *
     * @property route the [Route] this bus is currently operating on
     */
    class Bus(val route: Route)


    /**
     * A lazily loaded list of all [Agencies][Agency] tracked by transsee
     */
    val agencies by lazy { AgencyScraper.scrape() }

    /**
     * Finds an [Agency] using it's [alphanumeric code][Agency.code]
     */
    fun agency(code: String) =
        agencies.find { it.code == code } ?: error("libts -> ERROR: no agency with code \"$code\"")

}