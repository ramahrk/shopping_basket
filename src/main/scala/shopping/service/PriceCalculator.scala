package shopping.service

import shopping.model.{Basket, Discount}

/**
 * Main service for calculating basket prices including discounts
 */
class PriceCalculator {
  
  private val offerEngine = new OfferEngine()
  
  /**
   * Calculate the final price of a basket including all applicable discounts
   * @param basket the shopping basket
   * @return price calculation result
   */
  def calculatePrice(basket: Basket): PriceCalculator.PriceResult = {
    val subtotal = basket.subtotal
    val discounts = offerEngine.calculateDiscounts(basket)
    val totalDiscount = discounts.map(_.amount).sum
    val total = subtotal - totalDiscount
    
    PriceCalculator.PriceResult(subtotal, discounts, total)
  }
}

object PriceCalculator {
  /**
   * Result of price calculation
   * @param subtotal subtotal before discounts (in pence)
   * @param discounts list of applied discounts
   * @param total final total after discounts (in pence)
   */
  case class PriceResult(subtotal: Int, discounts: List[Discount], total: Int) {
    require(subtotal >= 0, "Subtotal cannot be negative")
    require(total >= 0, "Total cannot be negative")
    require(total <= subtotal, "Total cannot be greater than subtotal")
  }
}
