package shopping.util

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CurrencyFormatterSpec extends AnyFlatSpec with Matchers {
  
  "CurrencyFormatter" should "format pence amounts correctly" in {
    CurrencyFormatter.format(65) shouldBe "65p"
    CurrencyFormatter.format(10) shouldBe "10p"
    CurrencyFormatter.format(5) shouldBe "5p"
  }
  
  it should "format pound amounts correctly" in {
    CurrencyFormatter.format(100) shouldBe "£1.00"
    CurrencyFormatter.format(130) shouldBe "£1.30"
    CurrencyFormatter.format(200) shouldBe "£2.00"
    CurrencyFormatter.format(310) shouldBe "£3.10"
  }
  
  it should "format zero correctly" in {
    CurrencyFormatter.format(0) shouldBe "0p"
  }
  
  it should "handle large amounts correctly" in {
    CurrencyFormatter.format(1000) shouldBe "£10.00"
    CurrencyFormatter.format(1005) shouldBe "£10.05"
    CurrencyFormatter.format(1050) shouldBe "£10.50"
  }
  
  it should "throw exception for negative amounts" in {
    an[IllegalArgumentException] should be thrownBy {
      CurrencyFormatter.format(-1)
    }
  }
}
