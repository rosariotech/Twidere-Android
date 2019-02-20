package org.mariotaku.twidere.model
import org.junit.Assert
import org.junit.Test
import java.util.*

class CronExpressionTest{
    @Test
    fun testMatches(){
        val cal0h0m = Calendar.getInstance().apply{
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
        }
        val cal15dom1h0m=Calendar.getInstance(TimeZone.getTimeZone("JST")).apply{
            set(Calendar.HOUR_OF_DAY, 1)
            set(Calendar.MINUTE,0)
            set(Calendar.DAY_OF_MONTH, 15)
        }
        Assert.assertTrue(CronExpression.valueOf("0 0 * * *").matches(cal0h0m))
        Assert.assertFalse(CronExpression.valueOf("0 0 * * *").matches(cal15dom1h0m))
        Assert.assertTrue(CronExpression.valueOf("0 1 15 * *").matches(cal15dom1h0m))
    }
    class FieldTest{
        @Test
        fun testParseBasic(){
            Assert.assertSame(CronExpression.AnyField.INSTANCE, CronExpression.FieldType.MINUTE.parseField("*"))
            Assert.assertArrayEquals(arrayOf(CronExpression.Range.single(0), CronExpression.Range.single(1)),
                (CronExpression.FieldType.DAY_OF_WEEK.parseField("SUN,MON") as CronExpression.BasicField).ranges)
            Assert.assertArrayEquals(arrayOf(CronExpression.Range.single(0), CronExpression.Range(1,5)),
                (CronExpression.FieldType.DAY_OF_WEEK.parseField("SUN,MON-FRI") as CronExpression.BasicField).ranges)
        }
    }
    class RangeTest{
        @Test
        fun testParse(){
            Assert.assertEquals(CronExpression.Range(0,6), CronExpression.Range.parse("0-6", CronExpression.Range(0,10), null) )
            Assert.assertEquals(CronExpression.Range.single(0), CronExpression.Range.parse("SUN", CronExpression.Range(0,7), CronExpression.FieldType.DAY_OF_WEEK.textRepresentations))
            Assert.assertEquals(CronExpression.Range(0,3), CronExpression.Range.parse("SUN-WED", CronExpression.Range(0,7), CronExpression.FieldType.DAY_OF_WEEK.textRepresentations))
        }
    }
}