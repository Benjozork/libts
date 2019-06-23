package libts.core.utils

import java.net.URLDecoder
import java.net.URLEncoder

import kotlin.text.Charsets.UTF_8

/**
 * A representation of the [String] with URL encoding
 */
val String.urlEncoded: String
    get() = URLEncoder.encode(this, UTF_8)

/**
 * A representation of the [String] without URL encoding
 */
val String.urlDecoded: String
    get() = URLDecoder.decode(this, UTF_8)