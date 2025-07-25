package shopping.service

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import shopping.model.{Basket, Item}

class OfferEngineSpec extends AnyFlatSpec with Matchers {
  
  val offerEngine = new OfferEngine()
  
  val soup = Item("Soup", 65)
  val bread = Item("Bread", 80)
  val milk = Item("Milk", 130)
  val apples = Item("Apples", 100)
  
  "OfferEngine" should "calculate apples discount correctly" in {
    val basket = Basket(List(apples))
    val discounts = offerEngine.calculateDiscounts(basket)
    
    discounts should have length 1
    discounts.head.description shouldBe "Apples 10% off"
    discounts.head.amount shouldBe 10
  }
  
  it should "calculate soup and bread discount correctly" in {
    val basket = Basket(List(soup, soup, bread))
    val discounts = offerEngine.calculateDiscounts(basket)
    
    discounts should have length 1
    discounts.head.description shouldBe "Buy 2 Soup get Bread half price"
    discounts.head.amount shouldBe 40
  }
  
  it should "not apply soup discount without enough soup" in {
    val basket = Basket(List(soup, bread))
    val discounts = offerEngine.calculateDiscounts(basket)
    
    discounts shouldBe empty
  }
  
  it should "not apply soup discount without bread" in {
    val basket = Basket(List(soup, soup))
    val discounts = offerEngine.calculateDiscounts(basket)
    
    discounts shouldBe empty
  }
  
  it should "not apply apple discount without apples" in {
    val basket = Basket(List(milk, bread))
    val discounts = offerEngine.calculateDiscounts(basket)
    
    discounts shouldBe empty
  }
  
  it should "apply both discounts when both are applicable" in {
    val basket = Basket(List(soup, soup, bread, apples))
    val discounts = offerEngine.calculateDiscounts(basket)
    
    discounts should have length 2
    discounts.map(_.description) should contain allOf("Apples 10% off", "Buy 2 Soup get Bread half price")
  }
}
