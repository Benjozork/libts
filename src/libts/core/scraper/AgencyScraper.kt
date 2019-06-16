package libts.core.scraper

import libts.core.model.TransSee

import org.jsoup.Jsoup

object AgencyScraper {

    private const val AGENCY_SCRAPING_URL = TransSee.BASE_URL

    fun scrape(): List<TransSee.Agency> {
        val document = Jsoup.connect(AGENCY_SCRAPING_URL).get()

        val agencies = document.select(".Agencies ul li a")

        return agencies.map {

            val agencyName = it.text()
            val agencyCode = it.attr("href").split("=")[1]

            TransSee.Agency(agencyName, agencyCode)
        }
    }

}