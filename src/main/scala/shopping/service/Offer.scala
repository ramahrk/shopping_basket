package shopping.service

import shopping.model.{Basket, Discount}

trait Offer {
  def description: String
  def apply(basket: Basket): Option[Discount]
}
