package shopping.service

import shopping.model.{Basket, Discount}

/**
 * Engine for calculating special offers and discounts
 */
class OfferEngine {

   private val offers: List[Offer] = List(
    ApplesDiscount,
    SoupBreadOffer
  )
  
  /**
   * Calculate all applicable discounts for a basket
   * @param basket the shopping basket
   * @return list of applicable discounts
   */
  def calculateDiscounts(basket: Basket): List[Discount] = {
    offers.flatMap(_.apply(basket))
  }
  
  /**
   * Calculate 10% discount on apples
   * @param basket the shopping basket
   * @return optional apple discount
   */
  private def calculateApplesDiscount(basket: Basket): Option[Discount] = {
    val appleCount = basket.countOf("Apples")
    if (appleCount > 0) {
      // Find apple price from basket items
      basket.items.find(_.name.equalsIgnoreCase("Apples")).map { apple =>
        val totalApplePrice = apple.priceInPence * appleCount
        val discountAmount = (totalApplePrice * 0.1).round.toInt
        Discount("Apples 10% off", discountAmount)
      }
    } else {
      None
    }
  }
  
  /**
   * Calculate soup and bread offer: Buy 2 tins of soup and get a loaf of bread for half price
   * @param basket the shopping basket
   * @return optional soup/bread discount
   */
  private def calculateSoupBreadDiscount(basket: Basket): Option[Discount] = {
    val soupCount = basket.countOf("Soup")
    val breadCount = basket.countOf("Bread")
    
    if (soupCount >= 2 && breadCount > 0) {
      // Find bread price from basket items
      basket.items.find(_.name.equalsIgnoreCase("Bread")).map { bread =>
        val eligibleSoupPairs = soupCount / 2
        val discountedBreads = math.min(eligibleSoupPairs, breadCount)
        val discountAmount = (bread.priceInPence * discountedBreads) / 2
        Discount("Buy 2 Soup get Bread half price", discountAmount)
      }
    } else {
      None
    }
  }
}
