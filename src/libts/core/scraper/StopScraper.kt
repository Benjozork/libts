package libts.core.scraper

import libts.core.model.TransSee

import org.jsoup.Jsoup

object StopScraper {

    private const val STOP_SCRAPING_BASE_URL   = TransSee.BASE_URL + "stoplist"
    private const val STOP_SCRAPING_LINK_REGEX = "predict.*"

    fun scrape(route: TransSee.Route): List<TransSee.Stop> {

        val finalAgencyCode = if (route.altAgencyCode == "") route.agency.code else route.altAgencyCode

        val document = Jsoup.connect("$STOP_SCRAPING_BASE_URL?a=$finalAgencyCode&r=${route.code}").get()

        return document.select(".routetable a[href~=$STOP_SCRAPING_LINK_REGEX]")
            .map { TransSee.Stop(it.attr("href").split('.')[2]) }
    }

}