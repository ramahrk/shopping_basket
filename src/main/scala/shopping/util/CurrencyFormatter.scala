package shopping.util

/**
 * Utility for formatting currency values
 */
object CurrencyFormatter {
  
  /**
   * Format pence amount as pounds and pence string
   * @param pence amount in pence
   * @return formatted string (e.g., "£1.30", "65p")
   */
  def format(pence: Int): String = {
    require(pence >= 0, "Amount cannot be negative")
    
    if (pence >= 100) {
      val pounds = pence / 100
      val remainingPence = pence % 100
      if (remainingPence == 0) {
        f"£$pounds%d.00"
      } else {
        f"£$pounds%d.$remainingPence%02d"
      }
    } else {
      s"${pence}p"
    }
  }
}
