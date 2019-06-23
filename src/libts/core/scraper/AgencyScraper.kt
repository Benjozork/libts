package libts.core.scraper

import libts.core.model.Agency
import libts.core.model.TransSee

import libts.core.scraper.backend.loadDocument

import libts.core.utils.urlDecoded


object AgencyScraper {

    private const val AGENCY_SCRAPING_URL = TransSee.BASE_URL

    fun scrape(): List<Agency> {
        val document = loadDocument(AGENCY_SCRAPING_URL)

        val agencies = document.select(".Agencies ul li a")

        return agencies.map {

            val agencyName = it.text()
            val agencyCode = it.attr("href").split("=")[1].urlDecoded

            Agency(agencyName, agencyCode)
        }
    }

}