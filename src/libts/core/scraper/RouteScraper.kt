package libts.core.scraper

import libts.core.model.TransSee

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

@Suppress("SpellCheckingInspection")
object RouteScraper {

    private const val ROUTE_SCRAPING_BASE_URL   = TransSee.BASE_URL + "routelist"
    private const val ROUTE_SCRAPING_LINK_REGEX = "stoplist.*"

    fun scrape(agency: TransSee.Agency): List<TransSee.Route> {

        val document: Document = try {
            Jsoup.connect("$ROUTE_SCRAPING_BASE_URL?a=${agency.code}").get()
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

                TransSee.Route (
                    agency = agency,
                    code   = routeElem.id(),
                    name   = routeElem.id(),
                    altAgencyCode = if (routeAgencyCode != agency.code) routeAgencyCode else "",
                    altAgencyName = if (routeAgencyCode != agency.code) routeAgencyCode else ""
                )
            }
    }

}