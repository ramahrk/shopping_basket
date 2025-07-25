package shopping.service

import shopping.model.{Basket, Discount}

/**
 * Trait representing a special offer that can be applied to a shopping basket.
 * 
 * Each concrete implementation must:
 * - Define a `description` of the offer
 * - Implement `apply` to determine if a Discount should be returned based on basket contents
 */
trait Offer {

  /**
   * A brief human-readable description of the offer (e.g., "10% off Apples").
   */
  def description: String

  /**
   * Applies the offer to a given basket.
   * 
   * @param basket the shopping basket containing items
   * @return Some(Discount) if the offer is applicable, or None if not
   */
  def apply(basket: Basket): Option[Discount]
}
