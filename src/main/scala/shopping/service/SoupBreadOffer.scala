package shopping.service

import shopping.model.{Basket, Discount}

/**
 * Special offer:
 * Buy 2 tins of soup and get 1 loaf of bread for half price.
 *
 * - For every 2 soups, 1 bread qualifies for 50% discount.
 * - Multiple soup pairs can discount multiple breads.
 * - Example: 4 soups + 2 breads => 2 breads discounted.
 */
object SoupBreadOffer extends Offer {

  override val description: String = "Buy 2 Soup get Bread half price"

  /**
   * Apply the discount if the offer conditions are met.
   *
   * @param basket the shopping basket
   * @return Some(Discount) if applicable, otherwise None
   */
  override def apply(basket: Basket): Option[Discount] = {
    // Defensive null checks
    if (basket == null || basket.items == null) {
      return None
    }

    val soupCount = basket.countOf("Soup")
    val breadCount = basket.countOf("Bread")

    // Proceed only if minimum quantities are met
    if (soupCount >= 2 && breadCount > 0) {
      // Find bread item to get price
      basket.items.find(_.name.equalsIgnoreCase("Bread")) match {
        case Some(bread) =>
          // Defensive check on bread price
          if (bread.priceInPence <= 0) return None

          val eligibleSoupPairs = soupCount / 2
          val discountedBreads = math.min(eligibleSoupPairs, breadCount)
          val discountAmount = (bread.priceInPence * discountedBreads) / 2

          if (discountAmount > 0)
            Some(Discount(description, discountAmount))
          else
            None // Discount of 0 not worth applying
        case None =>
          None // No Bread item in basket to apply discount
      }
    } else {
      None // Not enough soups or no breads
    }
  }
}
