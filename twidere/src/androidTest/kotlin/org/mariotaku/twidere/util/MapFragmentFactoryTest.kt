package org.mariotaku.twidere.util
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MapFragmentFactoryTest{
    @Test
    fun testGetInstance(){
        val context = InstrumentationRegistry.getTargetContext()
        MapFragmentFactory.instance.createMapFragment(context = context)
    }
}