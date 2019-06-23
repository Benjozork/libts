package libts.core.scraper

import libts.core.model.Route
import libts.core.model.Stop
import libts.core.model.TransSee

import libts.core.scraper.backend.loadDocument

import libts.core.utils.urlEncoded

object StopScraper {

    private const val STOP_SCRAPING_BASE_URL   = TransSee.BASE_URL + "stoplist"
    private const val STOP_SCRAPING_LINK_REGEX = "predict.*"

    fun scrape(route: Route): List<Stop> {

        val finalAgencyCode = if (route.altAgencyCode == "") route.agency.code.urlEncoded else route.altAgencyCode.urlEncoded

        val document = loadDocument("$STOP_SCRAPING_BASE_URL?a=$finalAgencyCode&r=${route.code.urlEncoded}")

        return document.select(".routetable a[href~=$STOP_SCRAPING_LINK_REGEX]")
            .map { Stop(it.attr("href").split('.')[2]) }
    }

}