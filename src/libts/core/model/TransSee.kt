package libts.core.model

import libts.core.scraper.AgencyScraper

const val URL_PROTOCOL = "https://"

/**
 * The base namespace object holding references to all available and supported transsee information
 */
object TransSee {

    const val BASE_URL = "${URL_PROTOCOL}transsee.ca/"

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