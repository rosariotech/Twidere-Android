package org.mariotaku.twidere.activity

import android.support.test.rule.ActivityTestRule
import org.mariotaku.twidere.util.TestAccountUtils

class ComposeActivityTestRule(initialTouchMode: Boolean = false, launchActivity: Boolean = true):
    ActivityTestRule<ComposeActivity>(ComposeActivity::class.java, initialTouchMode, launchActivity){
        override fun beforeActivityLaunched(){
            TestAccountUtils.insertTestAccounts()
        }
        override fun afterActivityFinished(){
            TestAccountUtils.removeTestAccounts()
        }
    }