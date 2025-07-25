
# Shopping Basket Pricing System

A Scala-based command-line application that calculates the total price of a shopping basket with special offers.

## Features

- Calculate subtotal for basket items
- Apply special offers:
  - 10% discount on Apples
  - Buy 2 tins of soup, get bread for half price
- Command-line interface
- Comprehensive unit tests
- Clean, maintainable code structure
- 🔧 Easily extendable offer system — add new discount rules by implementing the Offer trait and registering them in OfferEngine.scala

## Prerequisites

**Tested With:**
- SBT 1.8.2
- Eclipse Adoptium Java 17.0.16

- Java 8 or higher
- SBT (Scala Build Tool) 1.5.0 or higher

## Installation

### Step 1: Install Java

Ensure you have Java 8 or higher installed:

```bash
java -version
```

If Java is not installed:

- **Windows**: Download Temurin OpenJDK 17 from https://adoptium.net/en-GB/temurin/releases/
  - Install the MSI Package.
  - Set `JAVA_HOME = C:\Program Files\Eclipse Adoptium\jdk-17.x.x`
  - Add `%JAVA_HOME%\bin` to your system `PATH`
- **Ubuntu/Debian**: 
  ```bash
  sudo apt-get install openjdk-17-jre-headless -y
  export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
  export PATH=$JAVA_HOME/bin:$PATH
  source ~/.bashrc
  ```

### Step 2: Install SBT

**Windows**:
1. Download from https://www.scala-sbt.org/download.html
2. Download the Windows MSI installer and complete the installation.
3. Locate the SBT Installation Directory
4. Edit the system environment variables and add SBT to your system PATH

**Ubuntu/Debian**:
```bash
echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | sudo tee /etc/apt/sources.list.d/sbt.list
curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x99E82A75642AC823" | sudo apt-key add -
sudo apt update
sudo apt install sbt -y
```

### Step 3: Clone and Setup Project

```bash
git clone <repository-url>
cd shopping-basket-new
```

### Step 4: Verify Installation

```bash
java --version
sbt version
sbt compile
```

---

## Running the Application

```bash
sbt "run item1 item2 item3"
```

### Available Items

- Soup - 65p per tin
- Bread - 80p per loaf
- Milk - GBP 1.30 per bottle
- Apples - GBP 1.00 per bag

---

### Example Commands and Output

#### Example 1: Items with Apple discount
```bash
sbt "run Apples Milk Bread"
```
```
Subtotal: GBP 3.10
Apples 10% off: 10p
Total price: GBP 3.00
```

#### Example 2: Soup and bread offer
```bash
sbt "run Soup Soup Bread"
```
```
Subtotal: GBP 2.10
Buy 2 Soup get Bread half price: 40p
Total price: GBP 1.70
```

#### Example 3: No offers available
```bash
sbt "run Milk"
```
```
Subtotal: GBP 1.30
(No offers available)
Total price: GBP 1.30
```

#### Example 4: Multiple offers
```bash
sbt "run Soup Soup Bread Apples"
```
```
Subtotal: GBP 3.10
Apples 10% off: 10p
Buy 2 Soup get Bread half price: 40p
Total price: GBP 2.60
```

#### Example 5: Invalid items (with warning)
```bash
sbt "run Apples Bananas Milk"
```
```
Warning: Unknown items ignored: Bananas
Subtotal: GBP 2.30
Apples 10% off: 10p
Total price: GBP 2.20
```

---

## Running Tests

```bash
sbt test                     # Run all tests
sbt "testOnly * -- -oD"      # Detailed output
sbt "testOnly *BasketSpec"  # Individual suite
sbt "testOnly *DiscountSpec"
sbt "testOnly *ItemSpec"
sbt "testOnly *OfferEngineSpec"
sbt "testOnly *OfferSpec"
sbt "testOnly *PriceCalculatorSpec"
sbt "testOnly *CurrencyFormatterSpec"
```

## Project Structure

```
shopping-basket/
├── README.md
├── build.sbt
├── project/
│   └── build.properties
├── src/
│   ├── main/scala/shopping/
│   │   ├── Main.scala
│   │   ├── model/
│   │   ├── service/
│   │   └── util/
│   └── test/scala/shopping/
│       ├── model/
│       ├── service/
│       └── util/
```

## Special Offers

- Apples 10% off (`ApplesDiscount.scala`)
- Buy 2 Soups get Bread half price (`SoupBreadOffer.scala`)

Offers apply only when conditions are met, and multiple offers can apply together.

## Development Tips

### Add a New Item

Update `availableItems` map in `Main.scala`.

### Add a New Offer

Create a new `Offer` object extending the `Offer` trait and add it to `OfferEngine.scala`.

### Continuous Dev Mode

```bash
sbt ~compile
sbt ~test
```

---

## Encoding Note (Windows Terminal)

On **Windows terminals** (e.g., PowerShell, Git Bash), the pound symbol `£` may render incorrectly as garbled characters (like `┬ú`) due to code page or font limitations.

### Resolution / Fix

This issue has been fixed by updating the currency output:

- `CurrencyFormatter.scala` now uses `"GBP"` instead of `£`  
  Example: `Subtotal: GBP 3.10` instead of `Subtotal: £3.10`

### Supported Environments

You can safely run the application from:

- **Linux/macOS terminals**
- **WSL (Windows Subsystem for Linux)**
- **Windows Command Prompt (cmd.exe)**
- **PowerShell and Git Bash** — now also display correct output

> If reverting to the `£` symbol, use WSL or change Windows code page with `chcp 65001`.

---

## Code Quality & Error Handling

This project includes robust error handling and well-documented source code:

- **Input Validation**: Warnings for unknown items, ignored in calculations
- **Safe Runtime**: Uses `Option`, pattern matching, and guards against invalid operations
- **Clear Exceptions**: Graceful error messages for invalid inputs and states
- **Encapsulation**: Separated concerns across services, models, and utilities
- **Scaladoc Comments**: Documented every core class and function
- **Formatted Output**: Output formatting via centralized `CurrencyFormatter`

---

## Quick Start

```bash
java -version
sbt version
git clone <repo>
cd shopping-basket-new
sbt compile
sbt "run Apples Milk Bread"
sbt test
```

## Conclusion

This application fulfills all assignment requirements for:

- Functional discount logic
- Clean modular architecture
- Command-line interface
- Unit tests for correctness
- Easily extensible offer system – New offers can be implemented by:
  - Creating a new object extending the `Offer` trait
  - Implementing custom discount logic in the `apply` method
  - Registering the new offer in `OfferEngine.scala`
