package shopping.model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BasketSpec extends AnyFlatSpec with Matchers {
  
  val soup = Item("Soup", 65)
  val bread = Item("Bread", 80)
  val milk = Item("Milk", 130)
  
  "Basket" should "calculate subtotal correctly" in {
    val basket = Basket(List(soup, bread, milk))
    basket.subtotal shouldBe 275
  }
  
  it should "count items correctly" in {
    val basket = Basket(List(soup, soup, bread))
    basket.countOf("Soup") shouldBe 2
    basket.countOf("Bread") shouldBe 1
    basket.countOf("Milk") shouldBe 0
  }
  
  it should "identify unique items correctly" in {
    val basket = Basket(List(soup, soup, bread))
    basket.uniqueItems should contain allOf(soup, bread)
    basket.uniqueItems should have size 2
  }
  
  it should "handle empty basket correctly" in {
    val basket = Basket.empty
    basket.isEmpty shouldBe true
    basket.subtotal shouldBe 0
    basket.itemCounts shouldBe empty
  }
  
  it should "provide correct item counts map" in {
    val basket = Basket(List(soup, soup, bread, milk))
    val counts = basket.itemCounts
    
    counts("Soup") shouldBe 2
    counts("Bread") shouldBe 1
    counts("Milk") shouldBe 1
  }
}
