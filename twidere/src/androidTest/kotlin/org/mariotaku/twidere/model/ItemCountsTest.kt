package org.mariotaku.twidere.model
import org.junit.Assert
import org.junit.Test

class ItemCountsTest{
    @Test
    fun getItemCountIndex(){
        val counts = ItemCounts(3)
        counts[0] = 2
        counts[1] = 3
        counts[2] = 3

        Assert.assertEquals(0, counts.getItemCountIndex(1))
        Assert.assertEquals(1, counts.getItemCountIndex(2))
        Assert.assertEquals(1, counts.getItemCountIndex(4))
        Assert.assertEquals(2, counts.getItemCountIndex(7))
        Assert.assertEquals(-1, counts.getItemCountIndex(10))
        Assert.assertEquals(-1, counts.getItemCountIndex(-1))
    }
}