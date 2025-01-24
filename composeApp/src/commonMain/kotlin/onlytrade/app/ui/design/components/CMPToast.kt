package onlytrade.app.ui.design.components

interface CMPToast {
    fun showToast(msg: String)
}

expect fun getToast(): CMPToast