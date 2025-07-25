package shopping

import shopping.model.{Basket, Item}
import shopping.service.PriceCalculator
import shopping.util.CurrencyFormatter

/**
 * Entry point for the Shopping Basket Pricing Application.
 * 
 * Accepts item names as command-line arguments, builds a shopping basket,
 * calculates the total price including applicable discounts, and prints the result.
 *
 * Usage:
 *   sbt "run Apples Milk Bread"
 */
object Main extends App {

  // Available items with their prices (in pence)
  private val availableItems = Map(
    "Soup" -> Item("Soup", 65),
    "Bread" -> Item("Bread", 80),
    "Milk" -> Item("Milk", 130),
    "Apples" -> Item("Apples", 100)
  )

  // Entry point logic
  if (args.isEmpty) {
    println("Usage: PriceBasket item1 item2 item3 ...")
    println("Available items: Soup, Bread, Milk, Apples")
  } else {
    try {
      val items = parseItems(args)
      val basket = Basket(items)
      val calculator = new PriceCalculator()
      val result = calculator.calculatePrice(basket)

      printResult(result)

    } catch {
      case e: IllegalArgumentException =>
        System.err.println(s"[Input Error] ${e.getMessage}")
      case e: Exception =>
        System.err.println(s"[Unexpected Error] ${e.getMessage}")
    }
  }

  /**
   * Parses input item names into valid Item objects.
   * Invalid items are warned but ignored.
   *
   * @param itemNames list of user-supplied item names
   * @return list of valid items found in the available catalog
   */
  private def parseItems(itemNames: Array[String]): List[Item] = {
    val (validItems, invalidItems) = itemNames.toList
      .map(_.trim.capitalize)
      .map(name => (name, availableItems.get(name)))
      .partition(_._2.isDefined)

    // Display warning for unknown items
    if (invalidItems.nonEmpty) {
      val invalidNames = invalidItems.map(_._1).mkString(", ")
      println(s"Warning: Unknown items ignored: $invalidNames")
    }

    validItems.map(_._2.get)
  }

  /**
   * Prints the subtotal, applied discounts (if any), and final total.
   *
   * @param result the computed PriceResult for the basket
   */
  private def printResult(result: PriceCalculator.PriceResult): Unit = {
    println(s"Subtotal: ${CurrencyFormatter.format(result.subtotal)}")

    if (result.discounts.nonEmpty) {
      result.discounts.foreach { discount =>
        println(s"${discount.description}: ${CurrencyFormatter.format(discount.amount)}")
      }
    } else {
      println("(No offers available)")
    }

    println(s"Total price: ${CurrencyFormatter.format(result.total)}")
  }
}
