package shopping.service

import shopping.model.{Basket, Discount}

/**
 * Main service for calculating the total price of a basket including all applicable discounts.
 */
class PriceCalculator {

  // Instance of the OfferEngine that applies all discounts
  private val offerEngine = new OfferEngine()

  /**
   * Calculates subtotal, discounts, and final total for the given shopping basket.
   *
   * @param basket the shopping basket
   * @return a PriceResult containing subtotal, applied discounts, and final total
   */
  def calculatePrice(basket: Basket): PriceCalculator.PriceResult = {
    val subtotal = basket.subtotal
    val discounts = offerEngine.calculateDiscounts(basket)
    val totalDiscount = discounts.map(_.amount).sum
    val total = subtotal - totalDiscount

    // Construct and return result with validation
    PriceCalculator.PriceResult(subtotal, discounts, total)
  }
}

object PriceCalculator {

  /**
   * Represents the result of a price calculation.
   *
   * @param subtotal the original price before discounts (in pence)
   * @param discounts the list of applied discounts
   * @param total the final price after discounts (in pence)
   */
  case class PriceResult(subtotal: Int, discounts: List[Discount], total: Int) {
    require(subtotal >= 0, "Subtotal cannot be negative")
    require(total >= 0, "Total cannot be negative")
    require(total <= subtotal, "Total cannot be greater than subtotal")
  }
}
