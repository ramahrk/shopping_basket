package shopping.util

/**
 * Utility for formatting monetary amounts (in pence) into readable GBP format.
 *
 * All internal amounts are expected to be in pence (integer values).
 * Outputs will be in one of the following formats:
 *   - "GBP 3.10" for values >= 100p (pounds and pence)
 *   - "95p" for values < 100p
 *
 * Example:
 *   - format(230) => "GBP 2.30"
 *   - format(99)  => "99p"
 */
object CurrencyFormatter {

  /**
   * Format a monetary value in pence into a GBP string
   *
   * @param pence amount in pence (must be non-negative)
   * @return formatted currency string
   * @throws IllegalArgumentException if amount is negative
   */
  def format(pence: Int): String = {
    require(pence >= 0, s"Amount cannot be negative: $pence")

    if (pence >= 100) {
      val pounds = pence / 100
      val remainingPence = pence % 100

      // Format with two decimal places when needed
      if (remainingPence == 0)
        f"GBP $pounds%d.00"
      else
        f"GBP $pounds%d.$remainingPence%02d"
    } else {
      s"${pence}p"
    }
  }
}
