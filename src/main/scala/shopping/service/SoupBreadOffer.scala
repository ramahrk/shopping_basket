package shopping.service

import shopping.model.{Basket, Discount}

object SoupBreadOffer extends Offer {
  val description = "Buy 2 Soup get Bread half price"
  def apply(basket: Basket): Option[Discount] = {
    val soups = basket.countOf("Soup")
    val breads = basket.countOf("Bread")
    if (soups >= 2 && breads > 0) {
      basket.items.find(_.name.equalsIgnoreCase("Bread")).map { bread =>
        val eligiblePairs = soups / 2
        val discountedBreads = math.min(eligiblePairs, breads)
        val discountAmount = (bread.priceInPence * discountedBreads) / 2
        Discount(description, discountAmount)
      }
    } else None
  }
}
