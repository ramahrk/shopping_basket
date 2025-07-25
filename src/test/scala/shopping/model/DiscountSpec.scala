package shopping.model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * Unit tests for the Discount model.
 * 
 * Ensures discount creation rules, validation constraints, and behavior in collections.
 */
class DiscountSpec extends AnyFlatSpec with Matchers {

  "Discount" should "be created with valid description and amount" in {
    val discount = Discount("Apples 10% off", 10)
    discount.description shouldBe "Apples 10% off"
    discount.amount shouldBe 10
  }

  it should "not allow negative amounts" in {
    an[IllegalArgumentException] should be thrownBy {
      Discount("Invalid discount", -5)
    }
  }

  it should "not allow empty descriptions" in {
    an[IllegalArgumentException] should be thrownBy {
      Discount("", 10)
    }
  }

  it should "allow zero discount amount" in {
    val discount = Discount("No discount applied", 0)
    discount.amount shouldBe 0
  }

  it should "support equality comparison" in {
    val discount1 = Discount("Apples 10% off", 10)
    val discount2 = Discount("Apples 10% off", 10)
    val discount3 = Discount("Bread half price", 40)

    discount1 shouldEqual discount2
    discount1 should not equal discount3
  }

  it should "work correctly in collections" in {
    val discounts = List(
      Discount("Apples 10% off", 10),
      Discount("Bread half price", 40),
      Discount("Apples 10% off", 10)
    )

    val uniqueDiscounts = discounts.toSet
    uniqueDiscounts should have size 2

    val totalDiscount = discounts.map(_.amount).sum
    totalDiscount shouldBe 60
  }

  it should "handle large discount amounts" in {
    val discount = Discount("Huge discount", 10000)
    discount.amount shouldBe 10000
  }
}
