package libts.core.model

import libts.core.scraper.BusScraper

/**
 * Represents a stop along a [Route]
 *
 * WARNING: operations using stops are currently very limited
 *
 * @property code a consecutive alphanumeric code that represents the stop
 */
data class Stop(val code: String) {

    fun buses(route: Route) = BusScraper.scrape(this, route)

}