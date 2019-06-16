package libts.scraper

import libts.model.TransSee

import org.jsoup.Jsoup

object StopScraper {

    private const val STOP_SCRAPING_BASE_URL   = TransSee.BASE_URL + "stoplist"
    private const val STOP_SCRAPING_LINK_REGEX = "predict.*"

    fun scrape(route: TransSee.Route): List<TransSee.Stop> {

        val document = Jsoup.connect("$STOP_SCRAPING_BASE_URL?a=${route.agency.code}&r=${route.code}").get()

        return document.select(".routetable a[href~=$STOP_SCRAPING_LINK_REGEX]").map { TransSee.Stop(it.attr("href").split('.')[2]) }
    }

}