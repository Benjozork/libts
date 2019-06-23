package libts.core.scraper.backend

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

fun loadDocument(url: String = ""): Document {
    return Jsoup.connect(url).get()
}