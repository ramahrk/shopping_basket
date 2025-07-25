package shopping.model

/**
 * Represents a discount applied to a basket
 * @param description human-readable description of the discount
 * @param amount discount amount in pence (positive value)
 */
case class Discount(description: String, amount: Int) {
  require(amount >= 0, "Discount amount cannot be negative")
  require(description.nonEmpty, "Discount description cannot be empty")
}
