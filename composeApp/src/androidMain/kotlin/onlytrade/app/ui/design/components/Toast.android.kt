package onlytrade.app.ui.design.components

import android.widget.Toast
import onlytrade.app.OnlyTradeApp.Companion.appContext


class AndroidToast : CMPToast {
    override fun showToast(msg: String) = Toast.makeText(appContext, msg, Toast.LENGTH_SHORT).show()

}

actual fun getToast(): CMPToast = AndroidToast()
