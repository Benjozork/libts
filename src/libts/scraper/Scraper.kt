package libts.scraper

open class ScrapingContext(val url: String)

interface Scraper<Scraped> {

    fun scrape(ctx: ScrapingContext): List<Scraped>

}