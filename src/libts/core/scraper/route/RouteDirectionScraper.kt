package libts.core.scraper.route

import libts.core.model.Route
import libts.core.model.TransSee

import libts.core.scraper.backend.loadDocument

import libts.core.utils.urlEncoded

object RouteDirectionScraper {

    private const val ROUTE_DIRECTION_SCRAPING_BASE_URL = TransSee.BASE_URL + "stoplist"

    fun scrape(route: Route): List<Route.Direction> {

        val finalAgencyCode = if (route.altAgencyCode == "") route.agency.code.urlEncoded else route.altAgencyCode.urlEncoded

        val document = loadDocument("$ROUTE_DIRECTION_SCRAPING_BASE_URL?a=$finalAgencyCode&r=${route.code.urlEncoded}")

        return document.select("table.DirTable").map { Route.Direction(route, it.id(), it.id()) }

    }

}