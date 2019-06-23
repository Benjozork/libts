package libts.core.scraper.route

import libts.core.model.Agency
import libts.core.model.TransSee
import libts.core.model.Route

import libts.core.scraper.backend.loadDocument

import libts.core.utils.urlDecoded
import libts.core.utils.urlEncoded

import org.jsoup.nodes.Document

@Suppress("SpellCheckingInspection")
object RouteScraper {

    private const val ROUTE_SCRAPING_BASE_URL   = TransSee.BASE_URL + "routelist"
    private const val ROUTE_SCRAPING_LINK_REGEX = "stoplist.*"

    fun scrape(agency: Agency): List<Route> {

        val document: Document = try {
            loadDocument("$ROUTE_SCRAPING_BASE_URL?a=${agency.code.urlEncoded}")
        } catch (e: Exception) {
            println("libts -> ERROR: ${e.javaClass.simpleName} while scraping routes for \"${agency.name}\"_${agency.code} !")
            return emptyList()
        }

        return document.select("p:has(a[href~=$ROUTE_SCRAPING_LINK_REGEX])")
            .map { routeElem ->

                val routeAgencyCode = routeElem
                    .select("a[href~=$ROUTE_SCRAPING_LINK_REGEX]")
                    .attr("href")
                    .substringAfter('=')
                    .substringBefore('&')
                    .urlDecoded

                Route (
                    agency = agency,
                    code   = routeElem.id(),
                    name   = routeElem.id()
                ).apply {
                    altAgencyCode = if (routeAgencyCode != agency.code) routeAgencyCode else ""
                    altAgencyName = if (routeAgencyCode != agency.code) routeAgencyCode else ""
                }
            }
    }

}