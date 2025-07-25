package shopping.service

import shopping.model.{Basket, Discount}

/**
 * OfferEngine is responsible for applying all registered special offers
 * to a given shopping basket.
 *
 * Offers must implement the `Offer` trait and be listed in the `offers` list.
 */
class OfferEngine {

  // List of all active offers in the system
  private val offers: List[Offer] = List(
    ApplesDiscount,
    SoupBreadOffer
  )

  /**
   * Applies all applicable offers to the given shopping basket.
   *
   * @param basket the shopping basket to which offers should be applied
   * @return a list of all valid discounts that apply
   */
  def calculateDiscounts(basket: Basket): List[Discount] = {
    // Each offer returns Option[Discount]; flatMap removes Nones
    offers.flatMap(_.apply(basket))
  }
}
