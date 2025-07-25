package shopping.service

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import shopping.model.{Basket, Item}

/**
 * Unit tests for PriceCalculator service.
 * Verifies subtotal, discounts, and total calculation
 * across various basket combinations.
 */
class PriceCalculatorSpec extends AnyFlatSpec with Matchers {
  
  val calculator = new PriceCalculator()

  // Test items
  val soup   = Item("Soup", 65)
  val bread  = Item("Bread", 80)
  val milk   = Item("Milk", 130)
  val apples = Item("Apples", 100)

  // ----------------------------
  // Single Discount Scenarios
  // ----------------------------

  "PriceCalculator" should "calculate correct price for basket with apples discount" in {
    val basket = Basket(List(apples, milk, bread)) // 100 + 130 + 80 = 310
    val result = calculator.calculatePrice(basket)

    result.subtotal shouldBe 310
    result.discounts should have length 1
    result.discounts.head.description shouldBe "Apples 10% off"
    result.discounts.head.amount shouldBe 10
    result.total shouldBe 300
  }

  it should "calculate correct price for basket with soup and bread discount" in {
    val basket = Basket(List(soup, soup, bread)) // 65 + 65 + 80 = 210
    val result = calculator.calculatePrice(basket)

    result.subtotal shouldBe 210
    result.discounts should have length 1
    result.discounts.head.description shouldBe "Buy 2 Soup get Bread half price"
    result.discounts.head.amount shouldBe 40
    result.total shouldBe 170
  }

  it should "calculate correct price for basket with no applicable discounts" in {
    val basket = Basket(List(milk))
    val result = calculator.calculatePrice(basket)

    result.subtotal shouldBe 130
    result.discounts shouldBe empty
    result.total shouldBe 130
  }

  // ----------------------------
  // No Items Scenario
  // ----------------------------

  it should "calculate correct price for empty basket" in {
    val basket = Basket.empty
    val result = calculator.calculatePrice(basket)

    result.subtotal shouldBe 0
    result.discounts shouldBe empty
    result.total shouldBe 0
  }

  // ----------------------------
  // Multiple Offers
  // ----------------------------

  it should "apply both discounts when applicable" in {
    val basket = Basket(List(soup, soup, bread, apples)) // 65+65+80+100 = 310
    val result = calculator.calculatePrice(basket)

    result.subtotal shouldBe 310
    result.discounts should have length 2
    result.total shouldBe 260 // -10 (apples) -40 (bread)
  }

  // ----------------------------
  // Quantity Scaling
  // ----------------------------

  it should "handle multiple apples correctly" in {
    val basket = Basket(List(apples, apples)) // 200
    val result = calculator.calculatePrice(basket)

    result.subtotal shouldBe 200
    result.discounts should have length 1
    result.discounts.head.amount shouldBe 20
    result.total shouldBe 180
  }

  it should "handle soup discount with multiple breads" in {
    val basket = Basket(List(soup, soup, soup, soup, bread, bread)) // 4x65 + 2x80 = 420
    val result = calculator.calculatePrice(basket)

    result.subtotal shouldBe 420
    result.discounts should have length 1
    result.discounts.head.amount shouldBe 80
    result.total shouldBe 340
  }
}
