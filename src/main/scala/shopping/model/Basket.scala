package shopping.model

/**
 * Represents a shopping basket containing items
 * @param items the list of items in the basket
 */
case class Basket(items: List[Item]) {
  
  /**
   * Calculate the subtotal of all items in the basket
   * @return subtotal in pence
   */
  def subtotal: Int = items.map(_.priceInPence).sum
  
  /**
   * Count occurrences of each item type
   * @return map of item name to count
   */
  def itemCounts: Map[String, Int] = {
    items.groupBy(_.name).view.mapValues(_.length).toMap
  }
  
  /**
   * Get all unique items in the basket
   * @return set of unique items
   */
  def uniqueItems: Set[Item] = items.toSet
  
  /**
   * Check if basket is empty
   * @return true if basket contains no items
   */
  def isEmpty: Boolean = items.isEmpty
  
  /**
   * Get count of specific item
   * @param itemName name of the item to count
   * @return number of items with the given name
   */
  def countOf(itemName: String): Int = {
    items.count(_.name.equalsIgnoreCase(itemName))
  }
}

object Basket {
  /**
   * Create an empty basket
   */
  def empty: Basket = Basket(List.empty)
}
