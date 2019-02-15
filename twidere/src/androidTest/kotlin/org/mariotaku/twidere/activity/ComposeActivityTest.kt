package org.mariotaku.twidere.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import kotlinx.android.synthetic.main.activity_compose.*
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mariotaku.twidere.constant.IntentConstants.*
import org.mariotaku.twidere.extension.set
import org.mariotaku.twidere.model.ParcelableStatus
import org.mariotaku.twidere.model.ParcelableStatusUpdate
import org.mariotaku.twidere.test.R
import org.mariotaku.twidere.util.getJsonResource

@RunWith(andoidJUnit::class)
@SuppressLint("SetTextI18n")
class ComposeActivityTest{
    @get:Rule
    val activityRule = ComposeActivityRule(launchActivity = false)

    @Test
    fun testReply(){
        val context = InstrumentationRegistry.getContext()
        val targetContext = InstrumentationRegistry.getTargetContext()
        val status: ParcelableStatus = context.resources.getJsonResource(R.raw.parcelable_status_848051071444410368)
        val intent = Intent(INTENT_ACTION_REPLY)
        intent.setClass(targetContext, ComposeActivity::class.java)
        intent.putExtra(EXTRA_STATUS, status)
        intent.putExtra(EXTRA_STATUS_DRAFT, true)
        val activity = activityRule.launchActivity(intent)
        activityRule.runOnUiThread{
            activity.editText.setText("@drahcir9 @rosariotech Testando Reply")
        }
        val statusUpdate = activity.getStatusUpdateTest(false)
        Assert.assertEquals("Testando reply", statusUpdate.text)
        assertExcludedMatches(emptyArray(), statusUpdate)
        activity.requestSkipDraft()
        activity.finish()
    }
    @Test
    fun testReplyRemovedSomeMentions(){
        val context = InstrumentationRegistry.getContext()
        val targetContext = InstrumentationRegistry.getTargetContext()
        val status: ParcelableStatus = context.resources.getJsonResource(R.raw.parcelable_status_848051071444410368)
        val intent = Intent(INTENT_ACTION_REPLY)
        intent.setClass(targetContext, ComposeActivity::class.java)
        intent.putExtra(EXTRA_STATUS, status)
        intent.putExtra(EXTRA_STATUS_DRAFT, true)
        val activity = activityRule.launchActivity(intent)
        activityRule.runOnUiThread{
            activity.editText.setText("@drahcir9 Testando Reply")
        }
        val statusUpdate = activity.getStatusUpdateTest(false)
        Assert.assertEquals("Testando reply", statusUpdate.text)
        assertExcludedMatches(emptyArray(), statusUpdate)
        activity.requestSkipDraft()
        activity.finish()
    }
    @Test
    fun testReplyNoMentions(){
        val context = InstrumentationRegistry.getContext()
        val targetContext = InstrumentationRegistry.getTargetContext()
        val status: ParcelableStatus = context.resources.getJsonResource(R.raw.parcelable_status_848051071444410368)
        val intent = Intent(INTENT_ACTION_REPLY)
        intent.setClass(targetContext, ComposeActivity::class.java)
        intent.putExtra(EXTRA_STATUS, status)
        intent.putExtra(EXTRA_STATUS_DRAFT, true)
        val activity = activityRule.launchActivity(intent)
        activityRule.runOnUiThread{
            activity.editText.setText("Testando Reply")
        }
        val statusUpdate = activity.getStatusUpdateTest(false)
        Assert.assertEquals("Testando reply", statusUpdate.text)
        Assert.assertEquals("https://twitter.com/t_deyarmin/status/847950697987493888", statusUpdate.attachment_url)
        assertExcludedMatches(emptyArray(), statusUpdate)
        activity.requestSkipDraft()
        activity.finish()
    }
    @Test
    fun testReplySelf(){
        val context = InstrumentationRegistry.getContext()
        val targetContext = InstrumentationRegistry.getTargetContext()
        val status: ParcelableStatus = context.resources.getJsonResource(R.raw.parcelable_status_848051071444410368)
        val intent = Intent(INTENT_ACTION_REPLY)
        intent.setClass(targetContext, ComposeActivity::class.java)
        intent.putExtra(EXTRA_STATUS, status)
        intent.putExtra(EXTRA_STATUS_DRAFT, true)
        val activity = activityRule.launchActivity(intent)
        activityRule.runOnUiThread{
            activity.editText.setText("@drahcir9 @rosariotech Testando Reply")
        }
        val statusUpdate = activity.getStatusUpdateTest(false)
        Assert.assertEquals("Testando reply", statusUpdate.text)
        assertExcludedMatches(emptyArray(), statusUpdate)
        activity.requestSkipDraft()
        activity.finish()
    }
    @Test
    fun testReplySelfRemovedSomeMentions(){
        val context = InstrumentationRegistry.getContext()
        val targetContext = InstrumentationRegistry.getTargetContext()
        val status: ParcelableStatus = context.resources.getJsonResource(R.raw.parcelable_status_852737226718838790)
        val intent = Intent(INTENT_ACTION_REPLY)
        intent.setClass(targetContext, ComposeActivity::class.java)
        intent.putExtra(EXTRA_STATUS, status)
        intent.putExtra(EXTRA_STATUS_DRAFT, true)
        val activity = activityRule.launchActivity(intent)
        activityRule.runOnUiThread{
            activity.editText.setText("@rosariotech Test Reply")
        }
        val statusUpdate = activity.getStatusUpdateTest(false)
        Assert.assertEquals("Test Reply", statusUpdate.text)
        assertExcludedMatches(arrayOf("57610574"), statusUpdate)
        activity.requestSkipDraft()
        activity.finish()
    }
    @Test
    fun testReplySelfNoMentions(){
        val context = InstrumentationRegistry.getContext()
        val targetContext = InstrumentationRegistry.getTargetContext()
        val status: ParcelableStatus = context.resources.getJsonResource(R.raw.parcelable_status_852737226718838790)
        val intent = Intent(INTENT_ACTION_REPLY)
        intent.setClass(targetContext, ComposeActivity::class.java)
        intent.putExtra(EXTRA_STATUS, status)
        intent.putExtra(EXTRA_STATUS_DRAFT, true)
        val activity = activityRule.launchActivity(intent)
        activityRule.runOnUiThread{
            activity.editText.setText("Test Reply")
        }
        val statusUpdate = activity.getStatusUpdateTest(false)
        Assert.assertEquals("Test Reply", statusUpdate.text)
        assertExcludedMatches(arrayOf("57610574", "583328497"), statusUpdate)
        activity.requestSkipDraft()
        activity.finish()
    }
    private fun ComposeActivity.getStatusUpdateTest(checkLength: Boolean): ParcelableStatusUpdate{
        val getStatusUpdate=javaClass.getDeclaredMethod("getStatusUpdate",
            kotlin.Boolean::class.java).apply{
                isAccessible = true
            }
        return getStatusUpdate(this, checkLength) as ParcelableStatusUpdate
    }
    private fun assertExcludedMatches(expectedIds: Array<String>, statusUpdate: ParcelableStatusUpdate): Boolean {
        return statusUpdate.excluded_reply_user_ids?.all{excludedId ->
            expectedIds.any {expectation 0>
                expectation.equals(excludedId, ignoreCase = true)
            }
        } ?: expectedIds.isEmpty()
    }
}