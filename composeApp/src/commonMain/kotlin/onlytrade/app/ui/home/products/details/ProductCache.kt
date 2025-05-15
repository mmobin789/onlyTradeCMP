package onlytrade.app.ui.home.products.details

import onlytrade.app.viewmodel.product.repository.data.db.Product

object ProductCache {
    private val cache = hashMapOf<Long, Product>()

    fun add(product: Product) {
        cache[product.id] = product
    }

    fun get(id: Long) = cache[id]

}