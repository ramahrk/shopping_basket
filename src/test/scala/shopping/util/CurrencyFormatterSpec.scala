package shopping.util

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * Unit tests for CurrencyFormatter.
 * Verifies correct formatting of pence and pound values.
 */
class CurrencyFormatterSpec extends AnyFlatSpec with Matchers {

  // ------------------------------------------
  // Small Values in Pence (< 100)
  // ------------------------------------------
  "CurrencyFormatter" should "format small pence amounts correctly" in {
    CurrencyFormatter.format(65) shouldBe "65p"
    CurrencyFormatter.format(10) shouldBe "10p"
    CurrencyFormatter.format(5)  shouldBe "5p"
  }

  // ------------------------------------------
  // Whole Pound Formatting (Multiples of 100)
  // ------------------------------------------
  it should "format round pound values correctly" in {
    CurrencyFormatter.format(100) shouldBe "GBP 1.00"
    CurrencyFormatter.format(200) shouldBe "GBP 2.00"
    CurrencyFormatter.format(1000) shouldBe "GBP 10.00"
  }

  // ------------------------------------------
  // Mixed Pound and Pence Values
  // ------------------------------------------
  it should "format mixed pounds and pence correctly" in {
    CurrencyFormatter.format(130) shouldBe "GBP 1.30"
    CurrencyFormatter.format(310) shouldBe "GBP 3.10"
    CurrencyFormatter.format(1005) shouldBe "GBP 10.05"
    CurrencyFormatter.format(1050) shouldBe "GBP 10.50"
  }

  // ------------------------------------------
  // Edge Case: Zero
  // ------------------------------------------
  it should "format zero correctly" in {
    CurrencyFormatter.format(0) shouldBe "0p"
  }

  // ------------------------------------------
  // Validation: Negative Values
  // ------------------------------------------
  it should "throw exception for negative values" in {
    an[IllegalArgumentException] should be thrownBy {
      CurrencyFormatter.format(-1)
    }
  }
}
