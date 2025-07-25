package shopping.model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * Unit tests for the Basket model.
 * 
 * Validates subtotal calculations, item counting, uniqueness,
 * and empty basket behaviors.
 */
class BasketSpec extends AnyFlatSpec with Matchers {

  // Sample items for use in tests
  val soup = Item("Soup", 65)
  val bread = Item("Bread", 80)
  val milk = Item("Milk", 130)

  "Basket" should "calculate subtotal correctly for multiple items" in {
    val basket = Basket(List(soup, bread, milk))
    basket.subtotal shouldBe 275 // 65 + 80 + 130
  }

  it should "count each item type accurately" in {
    val basket = Basket(List(soup, soup, bread))
    basket.countOf("Soup") shouldBe 2
    basket.countOf("Bread") shouldBe 1
    basket.countOf("Milk") shouldBe 0
  }

  it should "identify unique items correctly from duplicates" in {
    val basket = Basket(List(soup, soup, bread))
    basket.uniqueItems should contain allOf (soup, bread)
    basket.uniqueItems should have size 2
  }

  it should "handle an empty basket gracefully" in {
    val basket = Basket.empty
    basket.isEmpty shouldBe true
    basket.subtotal shouldBe 0
    basket.itemCounts shouldBe empty
  }

  it should "provide correct item counts as a map" in {
    val basket = Basket(List(soup, soup, bread, milk))
    val counts = basket.itemCounts

    counts("Soup") shouldBe 2
    counts("Bread") shouldBe 1
    counts("Milk") shouldBe 1
    counts.get("Apples") shouldBe None // verify missing item returns None
  }
}
