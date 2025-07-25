package shopping.service

import shopping.model.{Basket, Discount}

/**
 * Offer implementation for 10% discount on apples
 */
object ApplesDiscount extends Offer {
  val description = "Apples 10% off"

  /**
   * Applies the apples discount to the given basket, if eligible
   * 
   * @param basket the shopping basket
   * @return an optional Discount if apples are present
   */
  def apply(basket: Basket): Option[Discount] = {
    val count = basket.countOf("Apples")

    // Proceed only if there is at least 1 apple
    if (count > 0) {
      // Find an apple item to retrieve its price
      basket.items.find(_.name.equalsIgnoreCase("Apples")) match {
        case Some(item) =>
          val discount = (item.priceInPence * count * 0.10).round.toInt

          // Ensure discount is non-negative (guard against corrupted data)
          if (discount >= 0)
            Some(Discount(description, discount))
          else
            None // Defensive programming: if somehow the discount is negative

        case None =>
          // No matching item found despite non-zero count (shouldn't happen)
          None
      }
    } else {
      None
    }
  }
}
