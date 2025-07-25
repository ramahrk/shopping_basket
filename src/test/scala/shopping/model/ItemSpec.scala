package shopping.model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ItemSpec extends AnyFlatSpec with Matchers {
  
  "Item" should "be created with valid name and price" in {
    val item = Item("Soup", 65)
    item.name shouldBe "Soup"
    item.priceInPence shouldBe 65
  }
  
  it should "not allow negative prices" in {
    an[IllegalArgumentException] should be thrownBy {
      Item("Soup", -1)
    }
  }
  
  it should "not allow empty names" in {
    an[IllegalArgumentException] should be thrownBy {
      Item("", 65)
    }
  }
  
  it should "allow zero price" in {
    val item = Item("Free Sample", 0)
    item.priceInPence shouldBe 0
  }
  
  it should "support equality comparison" in {
    val item1 = Item("Soup", 65)
    val item2 = Item("Soup", 65)
    val item3 = Item("Bread", 80)
    
    item1 shouldBe item2
    item1 should not be item3
  }
  
  it should "work correctly in collections" in {
    val items = List(
      Item("Soup", 65),
      Item("Bread", 80),
      Item("Soup", 65)
    )
    
    val uniqueItems = items.toSet
    uniqueItems should have size 2
  }
}
