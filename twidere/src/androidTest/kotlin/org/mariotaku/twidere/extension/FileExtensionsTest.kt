package org.mariotaku.twidere.extension
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class FileExtensionsTest{
    @Test
    fun testTempFileInputStream(){
        val context = InstrumentationRegistry.getTargetContext()
        val random = Random()
        val testData=ByteArray(1024)
        random.nextBytes(testData)
        val compareData = context.cacheDir.tempInputStream { os ->
            os.write(testData)
        }.use {it.readBytes(1024)}
        Assert.assertArrayEquals(testData, compareData)
    }

}