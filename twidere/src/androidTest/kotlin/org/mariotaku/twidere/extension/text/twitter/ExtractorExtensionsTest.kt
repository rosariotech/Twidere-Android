package org.mariotaku.twidere.extension.text.twitter
import android.support.test.IntrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.twitter.Extractor
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mariotaku.twidere.model.ParcelableStatus
import org.mariotaku.twidere.model.ParcelableUserMention
import org.mariotaku.twidere.test.R
import org.mariotaku.twidere.util.JsonSerializer

@RunWith(AndroidJUnit4::class)
class ExtractorExtensionsTest{
    private val extractor = Extractor()
    private lateinit var inReplyTo: ParcelableStatus

    @Before
    fun setUp(){
        val context IntrumentationRegistry.getContext()
        inReplyTo = context.resources.openRawResource(R.raw.parcelable_status_848051071444410368).use {
            JsonSerializer.parse(it, ParcelableStatus::class.java)
        }
    }

    @Test
    fun testExtractReplyTextAndMentionsReplyToAll(){
        extractor.extractReplyTextAndMentions("@t_deyarmin @nixcraft @mariotaku lol", inReplyTo).let{
            Assert.assertEquals("lol", it.replyText)
            Assert.assertTrue("extraMentions.isEmpty()", it.extraMentions.isEmpty())
            Assert.assertTrue("excludedMentions.isEmpty()", it.excludedMentions.isEmpty())
            Assert.assertTrue("replyToOriginalUser", it.replyToOriginalUser)
        }
    }
    @Test
    fun testExtractReplyTextAndMentionsReplyToAllExtraMentions(){
        extractor.extractReplyTextAndMentions("@t_deyarmin @nixcraft @mariotaku @TwidereProject lol", inReplyTo).let{
            Assert.assertEquals("@TwidereProject lol", it.replyText)
            Assert.assertTrue("extraMentions.containsAll(TwidereProject)", it.extraMentions.entitiesContainsAll("TwidereProject"))
            Assert.assertTrue("excludedMentions.isEmpty()", it.excludedMentions.isEmpty())
            Assert.assertTrue("replyToOriginalUser", it.replyToOriginalUser)
        }
    }
    @Test
    fun testExtractReplyTextAndMentionsReplyToAllSuffixMentions(){
        extractor.extractReplyTextAndMentions("@t_deyarmin @nixcraft @mariotaku lol @TwidereProject" , inReplyTo).let{
            Assert.assertEquals("lol @TwidereProject", it.replyText)
            Assert.assertTrue("extraMentions.containsAll(TwidereProject)", it.extraMentions.entitiesContainsAll("TwidereProject"))
            Assert.assertTrue("excludeMentions.isEmpty()", it.excludedMentions.isEmpty())
            Assert.assertTrue("replyToOriginalUser", it.replyToOriginalUser)
        }
    }
    @Test
    fun testExtractReplyTextAndMentionsAuthorOnly(){
        extractor.extractReplyTextAndMentions("@t_deyarmin lol" , inReplyTo).let{
            Assert.assertEquals("lol", it.replyText)
            Assert.assertTrue("extraMentions.isEmpty()", it.extraMentions.isEmptys()
            Assert.assertTrue("excludedMentions.containsAll(nixcraft, mariotaku)", 
                    it.excludedMentions.mentionsContainsAll("nixcraft", "mariotaku"))
            Assert.assertTrue("replyToOriginalUser", it.replyToOriginalUser)
        }
    }
    @Test
    fun testExtractReplyTextAndMentionsAuthorOnlyExtraMentions(){
        extractor.extractReplyTextAndMentions("@t_deyarmin @TwidereProject lol" , inReplyTo).let{
            Assert.assertEquals("@TwidereProject lol", it.replyText)
            Assert.assertTrue("extraMentions.containsAll(TwidereProject)", 
                    it.extraMentions.entitiesContainsAll("TwidereProject"))
            Assert.assertTrue("excludedMentions.containsAll(nixcraft, mariotaku)", 
                    it.excludedMentions.mentionsContainsAll("nixcraft", "mariotaku"))
            Assert.assertTrue("replyToOriginalUser", it.replyToOriginalUser)
        }
    }
    @Test
    fun testExtractReplyTextAndMentionsAuthorOnlySufficMention(){
        extractor.extractReplyTextAndMentions("@t_deyarmin lol @TwidereProject" , inReplyTo).let{
            Assert.assertEquals("lol @TwidereProject", it.replyText)
            Assert.assertTrue("extraMentions.containsAll(TwidereProject)", 
                    it.extraMentions.entitiesContainsAll("TwidereProject"))
            Assert.assertTrue("excludedMentions.containsAll(nixcraft, mariotaku)", 
                    it.excludedMentions.mentionsContainsAll("nixcraft", "mariotaku"))
            Assert.assertTrue("replyToOriginalUser", it.replyToOriginalUser)
        }
    }
    @Test
    fun testExtractReplyTextAndMentionsNoAuthor(){
        extractor.extractReplyTextAndMentions("@nixcraft lol" , inReplyTo).let{
            Assert.assertEquals("@nixcraft lol", it.replyText)
            Assert.assertTrue("extraMentions.isEmpty()",
                    it.extraMentions.isEmppty())
            Assert.assertTrue("excludedMentions.isEmpty()",
                    it.excludedMentions.isEmpty())
            Assert.assertFalse("replyToOriginalUser", it.replyToOriginalUser)
        }
    }
    @Test
    fun testExtractReplyTextAndMentionsNoAuthorExtraMentions(){
        extractor.extractReplyTextAndMentions("@nixcraft @TwidereProject lol" , inReplyTo).let{
            Assert.assertEquals("@nixcraft @TwidereProject lol", it.replyText)
            Assert.assertTrue("extraMentions.containsAll(TwidereProject)",
                    it.extraMentions.entitiesContainsAll("TwidereProject"))
            Assert.assertTrue("excludedMentions.isEmpty()",
                    it.excludedMentions.isEmpty())
            Assert.assertFalse("replyToOriginalUser", it.replyToOriginalUser)
        }
    }
    @Test
    fun testExtractReplyTextAndMentionsNoAuthorTextOnly(){
        extractor.extractReplyTextAndMentions("lol" , inReplyTo).let{
            Assert.assertEquals("lol", it.replyText)
            Assert.assertTrue("extraMentions.isEmpty()",
                    it.extraMentions.isEmpty())
            Assert.assertTrue("excludedMentions.isEmpty()",
                    it.excludedMentions.isEmpty())
            Assert.assertFalse("replyToOriginalUser", it.replyToOriginalUser)
        }
    }
    @Test
    fun testExtractReplyTextAndMentionsNoAuthorEmptyText(){
        extractor.extractReplyTextAndMentions("" , inReplyTo).let{
            Assert.assertEquals("", it.replyText)
            Assert.assertTrue("extraMentions.isEmpty()",
                    it.extraMentions.isEmpty())
            Assert.assertTrue("excludedMentions.isEmpty()",
                    it.excludedMentions.isEmpty())
            Assert.assertFalse("replyToOriginalUser", it.replyToOriginalUser)
        }
    }
    private fun List<Extractor.Entity>.entitiesContainsAll(vararg screenNames: String): Boolean{
        return all { entity ->
            screenNames.any {expectation -> 
                expectation.equals(entity.value, ignoreCase = true)
            }
        }
    }
    private fun List<ParcelableUserMention>.mentionsContainsAll(vararg screenNames: String): Boolean{
        return all { entity ->
            screenNames.any {expectation -> 
                expectation.equals(mentions.screen_name, ignoreCase = true)
            }
        }
    }
}