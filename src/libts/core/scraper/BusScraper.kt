package libts.core.scraper

import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeTz
import com.soywiz.klock.seconds

import libts.core.model.Route
import libts.core.model.ScheduledBus
import libts.core.model.Stop
import libts.core.model.TransSee

import libts.core.scraper.backend.loadDocument

import libts.core.utils.urlEncoded

object BusScraper  {

    private const val BUS_SCRAPING_BASE_URL = TransSee.BASE_URL + "predict"

    fun scrape(stop: Stop, route: Route): List<ScheduledBus> {

        val predictionCode = "${route.agency.code.urlEncoded}.${route.code.urlEncoded}.${stop.code.urlEncoded}"
        val document = loadDocument("$BUS_SCRAPING_BASE_URL?s=$predictionCode")

        return document.run {
            select("p[id^=${route.code}_${stop.code}_]")
            .mapIndexed { i, _ ->
                val offset = DateTimeTz.nowLocal().offset
                ScheduledBus(stop, (DateTime.now() + select("span[id=SECS$i]").attr("seconds").toInt().seconds).toOffset(offset))
            }
        }
    }

}