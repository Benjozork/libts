package libts.core.scraper

import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeTz
import com.soywiz.klock.seconds

import libts.core.model.TransSee

import org.jsoup.Jsoup

object BusScraper  {

    private const val BUS_SCRAPING_BASE_URL = TransSee.BASE_URL + "predict"

    fun scrape(stop: TransSee.Stop, route: TransSee.Route): List<TransSee.ScheduledBus> {

        val predictionCode = "${route.agency.code}.${route.code}.${stop.code}"
        val document = Jsoup.connect("$BUS_SCRAPING_BASE_URL?s=$predictionCode").get()

        return document.run {
            select("p[id^=${route.code}_${stop.code}_]")
            .mapIndexed { i, _ ->
                val offset = DateTimeTz.nowLocal().offset
                TransSee.ScheduledBus(stop, (DateTime.now() + select("span[id=SECS$i]").attr("seconds").toInt().seconds).toOffset(offset))
            }
        }
    }

}