package libts.core.model

import com.soywiz.klock.DateTimeTz

import libts.core.scraper.AgencyScraper
import libts.core.scraper.BusScraper
import libts.core.scraper.RouteScraper
import libts.core.scraper.StopScraper

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
    class Route (
        val agency:        Agency,
        val name:          String,
        val code:          String,
        var altAgencyCode: String = "",
        val altAgencyName: String = ""
    ) {

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

        fun buses(route: Route) = BusScraper.scrape(this, route)

        override fun toString(): String {
            return code
        }

    }

    /**
     * Represents a singular bus along a [Route], scheduled to pass at a [Stop]
     *
     * @property stop the [Stop] we are referencing
     * @property time the [DateTimeTz][time] at which the bus is scheduled to arrive
     */
    class ScheduledBus(val stop: Stop, val time: DateTimeTz) {

        override fun toString(): String {
            return time.toString()
        }

    }


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