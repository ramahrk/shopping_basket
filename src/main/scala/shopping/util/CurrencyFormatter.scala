package shopping.util

object CurrencyFormatter {
  def format(pence: Int): String = {
    require(pence >= 0, "Amount cannot be negative")
    if (pence >= 100) {
      val pounds = pence / 100
      val remainingPence = pence % 100
      if (remainingPence == 0)
        f"GBP $pounds%d.00"
      else
        f"GBP $pounds%d.$remainingPence%02d"
    } else {
      s"${pence}p"
    }
  }
}
