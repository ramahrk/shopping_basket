package shopping

import shopping.model.{Basket, Item}
import shopping.service.PriceCalculator
import shopping.util.CurrencyFormatter

object Main extends App {
  
  // Available items with their prices (in pence)
  private val availableItems = Map(
    "Soup" -> Item("Soup", 65),
    "Bread" -> Item("Bread", 80),
    "Milk" -> Item("Milk", 130),
    "Apples" -> Item("Apples", 100)
  )
  
  // Main application logic
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
        println(s"Error: ${e.getMessage}")
      case e: Exception =>
        println(s"An unexpected error occurred: ${e.getMessage}")
    }
  }
  
  private def parseItems(itemNames: Array[String]): List[Item] = {
    val normalizedAvailableItems = availableItems.map { case (k, v) => k.toLowerCase -> v }
    val (validItems, invalidItems) = itemNames.toList
      .map(_.trim.capitalize)
      .map(name => (name, availableItems.get(name)))
      .partition(_._2.isDefined)
    
    if (invalidItems.nonEmpty) {
      val invalidNames = invalidItems.map(_._1).mkString(", ")
      println(s"Warning: Unknown items ignored: $invalidNames")
    }
    
    validItems.map(_._2.get)
  }
  
  private def printResult(result: shopping.service.PriceCalculator.PriceResult): Unit = {
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
