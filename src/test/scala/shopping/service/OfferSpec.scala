package shopping.service

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import shopping.model.{Basket, Item}

/**
 * Unit tests for individual Offer implementations:
 * - ApplesDiscount
 * - SoupBreadOffer
 */
class OfferSpec extends AnyFlatSpec with Matchers {

  // Common test items
  val soup   = Item("Soup", 65)
  val bread  = Item("Bread", 80)
  val milk   = Item("Milk", 130)
  val apples = Item("Apples", 100)

  // ------------------------
  // ApplesDiscount Tests
  // ------------------------

  "ApplesDiscount" should "apply 10% discount when apples are present" in {
    val basket = Basket(List(apples, apples)) // 2 apples = £2.00
    val discount = ApplesDiscount(basket)

    discount shouldBe defined
    discount.get.description shouldBe "Apples 10% off"
    discount.get.amount shouldBe 20 // 10% of 200p
  }

  it should "not apply discount when apples are not present" in {
    val basket = Basket(List(soup, bread))
    ApplesDiscount(basket) shouldBe empty
  }

  // ------------------------
  // SoupBreadOffer Tests
  // ------------------------

  "SoupBreadOffer" should "apply discount when 2 soups and 1 bread are present" in {
    val basket = Basket(List(soup, soup, bread))
    val discount = SoupBreadOffer(basket)

    discount shouldBe defined
    discount.get.description shouldBe "Buy 2 Soup get Bread half price"
    discount.get.amount shouldBe 40 // 50% of 80p
  }

  it should "apply correct discount for multiple soups and breads" in {
    val basket = Basket(List(soup, soup, soup, soup, bread, bread))
    val discount = SoupBreadOffer(basket)

    discount shouldBe defined
    discount.get.amount shouldBe 80 // Two breads at half price = 40 * 2
  }

  it should "not apply discount when not enough soup" in {
    val basket = Basket(List(soup, bread)) // Only 1 soup
    SoupBreadOffer(basket) shouldBe empty
  }

  it should "not apply discount when no bread" in {
    val basket = Basket(List(soup, soup)) // No bread present
    SoupBreadOffer(basket) shouldBe empty
  }
}
