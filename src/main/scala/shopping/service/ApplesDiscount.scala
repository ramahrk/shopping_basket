package shopping.service

import shopping.model.{Basket, Discount}

object ApplesDiscount extends Offer {
  val description = "Apples 10% off"
  def apply(basket: Basket): Option[Discount] = {
    val count = basket.countOf("Apples")
    if (count > 0) {
      basket.items.find(_.name.equalsIgnoreCase("Apples")).map { item =>
        val discount = (item.priceInPence * count * 0.10).round.toInt
        Discount(description, discount)
      }
    } else None
  }
}
