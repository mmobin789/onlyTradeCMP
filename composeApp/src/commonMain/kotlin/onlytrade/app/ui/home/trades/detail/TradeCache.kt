package onlytrade.app.ui.home.trades.detail

import onlytrade.app.viewmodel.product.offer.repository.data.db.Offer

object TradeCache {
    private val cache = hashMapOf<Long, Offer>()

    fun add(offer: Offer) {
        cache[offer.id] = offer
    }

    fun get(id: Long) = cache[id]

}