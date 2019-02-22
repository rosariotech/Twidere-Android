package org.mariotaku.twidere.util.filter

import android.content.Context
import android.net.ConnectivityManager
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.mariotaku.twidere.model.filter.UrlFiltersSubscriptionProviderArguments

@RunWith(AndroidJUnit4::class)
class UrlFiltersSubscriptionProviderTest{
    @Test
    fun tesFetchXml(){
        val context = InstrumentationRegistry.getTargetContext()
        val cm=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (!(cm.activeNetworkInfo?.isConnected ?:false)) return
        val url = "https://raw.githubusercontent.com/mariotaku/wtb/master/twidere/bots.xml"
        val arguments=UrlFiltersSubscriptionProviderArguments().apply{
            this.url=url
        }
        val provider = UrlFiltersSubscriptionProvider(context, arguments)
        provider.fetchFilters()
        provider.sources
    }
}