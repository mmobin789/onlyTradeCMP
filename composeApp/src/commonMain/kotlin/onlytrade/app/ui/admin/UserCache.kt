package onlytrade.app.ui.admin

import onlytrade.app.viewmodel.login.repository.data.db.User

object UserCache {
    private val cache = hashMapOf<Long, User>()

    fun add(user: User) {
        cache[user.id] = user
    }

    fun get(id: Long) = cache[id]

}