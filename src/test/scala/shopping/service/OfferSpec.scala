
package shopping.service

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import shopping.model.{Basket, Item}

class OfferSpec extends AnyFlatSpec with Matchers {

  val soup = Item("Soup", 65)
  val bread = Item("Bread", 80)
  val milk = Item("Milk", 130)
  val apples = Item("Apples", 100)

  "ApplesDiscount" should "apply 10% discount when apples are present" in {
    val basket = Basket(List(apples, apples))
    val discount = ApplesDiscount(basket)

    discount shouldBe defined
    discount.get.description shouldBe "Apples 10% off"
    discount.get.amount shouldBe 20
  }

  it should "not apply discount when apples are not present" in {
    val basket = Basket(List(soup, bread))
    ApplesDiscount(basket) shouldBe empty
  }

  "SoupBreadOffer" should "apply discount when 2 soups and 1 bread are present" in {
    val basket = Basket(List(soup, soup, bread))
    val discount = SoupBreadOffer(basket)

    discount shouldBe defined
    discount.get.description shouldBe "Buy 2 Soup get Bread half price"
    discount.get.amount shouldBe 40
  }

  it should "apply correct discount for multiple soups and breads" in {
    val basket = Basket(List(soup, soup, soup, soup, bread, bread))
    val discount = SoupBreadOffer(basket)

    discount shouldBe defined
    discount.get.amount shouldBe 80
  }

  it should "not apply discount when not enough soup" in {
    val basket = Basket(List(soup, bread))
    SoupBreadOffer(basket) shouldBe empty
  }

  it should "not apply discount when no bread" in {
    val basket = Basket(List(soup, soup))
    SoupBreadOffer(basket) shouldBe empty
  }
}
