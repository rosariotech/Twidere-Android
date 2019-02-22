package org.mariotaku.twidere.util
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DataStoreUtilsTest{
    @Test
    fun testCleanDatabaseByItemLimit(){
        val context = InstrumentationRegistry.getTargetContext()
        DataStoreUtils.cleanDatabaseByItemLimit(context)
    }
    @Test
    fun testGetAccountKeys(){
        val context = InstrumentationRegistry.getTargetContext()
        DataStoreUtils.getAccountKeys(context)
    }
}