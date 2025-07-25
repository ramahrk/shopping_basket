package shopping.service

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import shopping.model.{Basket, Item}

/**
 * Unit tests for OfferEngine, verifying discount rules:
 * - Apples 10% off
 * - Buy 2 Soups get Bread half price
 */
class OfferEngineSpec extends AnyFlatSpec with Matchers {

  val offerEngine = new OfferEngine()

  // Common items
  val soup   = Item("Soup", 65)
  val bread  = Item("Bread", 80)
  val milk   = Item("Milk", 130)
  val apples = Item("Apples", 100)

  "OfferEngine" should "apply 10% apples discount when apples are in the basket" in {
    val basket = Basket(List(apples))
    val discounts = offerEngine.calculateDiscounts(basket)

    discounts should have length 1
    discounts.head.description shouldBe "Apples 10% off"
    discounts.head.amount shouldBe 10
  }

  it should "apply bread discount when 2 soups and 1 bread are in the basket" in {
    val basket = Basket(List(soup, soup, bread))
    val discounts = offerEngine.calculateDiscounts(basket)

    discounts should have length 1
    discounts.head.description shouldBe "Buy 2 Soup get Bread half price"
    discounts.head.amount shouldBe 40
  }

  it should "not apply soup-bread discount with less than 2 soups" in {
    val basket = Basket(List(soup, bread))
    val discounts = offerEngine.calculateDiscounts(basket)

    discounts shouldBe empty
  }

  it should "not apply soup-bread discount if no bread is present" in {
    val basket = Basket(List(soup, soup))
    val discounts = offerEngine.calculateDiscounts(basket)

    discounts shouldBe empty
  }

  it should "not apply apple discount if no apples are present" in {
    val basket = Basket(List(milk, bread))
    val discounts = offerEngine.calculateDiscounts(basket)

    discounts shouldBe empty
  }

  it should "apply both apple and soup-bread discounts when conditions are met" in {
    val basket = Basket(List(soup, soup, bread, apples))
    val discounts = offerEngine.calculateDiscounts(basket)

    discounts should have length 2
    discounts.map(_.description) should contain allOf(
      "Apples 10% off",
      "Buy 2 Soup get Bread half price"
    )
  }

  it should "apply soup-bread discount multiple times if multiple pairs exist" in {
    val basket = Basket(List(soup, soup, soup, soup, bread, bread)) // 4 soups = 2 pairs
    val discounts = offerEngine.calculateDiscounts(basket)

    discounts should have length 1
    discounts.head.description shouldBe "Buy 2 Soup get Bread half price"
    discounts.head.amount shouldBe 80 // 2 breads @ half price = 40 * 2
  }
}
