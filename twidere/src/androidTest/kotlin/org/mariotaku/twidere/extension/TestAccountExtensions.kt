package org.mariotaku.twidere.extension
import android.accounts.Account
import android.accounts.AccountManager
import org.mariotaku.twidere.TwidereConstants.ACCOUNT_USER_DATA_TEST
import org.mariotaku.twidere.extension.model.AccountDataQueue

fun Account.isTest(am: AccountManager): Boolean{
    return AccountDataQueue.getUserData(am, this, ACCOUNT_USER_DATA_TEST)?.toBoolean() ?: true
}
fun Account.setTest(am: AccountManager, test: Boolean){
    am.setUserData(this.ACCOUNT_USER_DATA_TEST, test.toString())
}