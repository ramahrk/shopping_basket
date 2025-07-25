package shopping.model

/**
 * Represents an item that can be purchased
 * @param name the name of the item
 * @param priceInPence the price in pence (to avoid floating point precision issues)
 */
case class Item(name: String, priceInPence: Int) {
  require(priceInPence >= 0, "Price cannot be negative")
  require(name.nonEmpty, "Item name cannot be empty")
  val normalizedName: String = name.trim.capitalize
}
