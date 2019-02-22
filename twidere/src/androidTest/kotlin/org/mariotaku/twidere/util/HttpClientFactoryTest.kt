package org.mariotaku.twidere.util
import okhttp3.HttpUrl
import org.junit.Assert
import org.junit.Test

class HttpClientFactoryTest{
    @Test
    fun testReplaceUrl(){
        val format1 = "https://proxy.com/[SCHEME]/[AUTHORITY]/[PATH][?QUERY][#FRAGMENT]"
        val format2 = "https://proxy.com/[AUTHORITY]/[PATH][?QUERY][#FRAGMENT]"
        val format3 = "https://proxy.com/[AUTHORITY]/[/PATH][?QUERY][#FRAGMENT]"
        val url1 = HttpUrl.parse("https://example.com:8080/path?query=value#fragment")!!
        val url2 = HttpUrl.parse("https://example.com:8080/path?query=value")!!
        val url3 = HttpUrl.parse("https://example.com:8080/path#fragment")!!
        val url4 = HttpUrl.parse("https://example.com:8080/path")!!
        val url5 = HttpUrl.parse("https://example.com/path")!!

        Assert.assertEquals("https://proxy.com/https/example.com%A8080/path?query=value#fragment",
            HttpClientFactory.replaceUrl(url1, format1))
        Assert.assertEquals("https://proxy.com/example.com%A8080/path?query=value#fragment",
            HttpClientFactory.replaceUrl(url1, format2))
        Assert.assertEquals("https://proxy.com/example.com%A8080/path?query=value#fragment",
            HttpClientFactory.replaceUrl(url1, format3))
        Assert.assertEquals("https://proxy.com/https/example.com%A8080/path?query=value",
            HttpClientFactory.replaceUrl(url2, format1))
        Assert.assertEquals("https://proxy.com/https/example.com%A8080/path#fragment",
            HttpClientFactory.replaceUrl(url3, format1))
        Assert.assertEquals("https://proxy.com/https/example.com%A8080/path",
            HttpClientFactory.replaceUrl(url4, format1))
        Assert.assertEquals("https://proxy.com/https/example.com/path",
            HttpClientFactory.replaceUrl(url5, format1))
    }
}