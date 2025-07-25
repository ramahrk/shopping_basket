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

- **Windows**: [Download from Oracle](https://www.oracle.com/java/technologies/downloads/)
- **macOS**: `brew install openjdk@11`
- **Ubuntu/Debian**: `sudo apt-get install openjdk-11-jdk`

### Step 2: Install SBT

**macOS (using Homebrew)**:
```bash
brew install sbt
```

**Ubuntu/Debian**:
```bash
echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | sudo tee /etc/apt/sources.list.d/sbt.list
curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | sudo apt-key add
sudo apt-get update
sudo apt-get install sbt
```

**Windows**:

1. Download from [sbt download page](https://www.scala-sbt.org/download.html)
2. Run the installer
3. Add SBT to your system PATH

**Manual (for all OS)**:
```bash
curl -L https://github.com/sbt/sbt/releases/download/v1.8.2/sbt-1.8.2.tgz | tar -xz
export PATH=$PATH:./sbt/bin
```

### Step 3: Clone and Setup Project

```bash
git clone <repository-url>
cd shopping-basket
```

### Step 4: Verify Installation

```bash
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
- Milk - £1.30 per bottle
- Apples - £1.00 per bag

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

## Known Issue (Windows Terminal Encoding)

If GBP symbol (`£`) appears as junk like `┬ú`, either:

1. **Use WSL or Linux/Mac terminals**, or
2. **Override format with "GBP" instead of "£" in CurrencyFormatter.scala**

---

## Quick Start

```bash
java -version         # Ensure Java 8+
brew install sbt      # or apt/scoop
git clone <repo>
cd shopping-basket
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

🎉 Ready for submission.

## Java and SBT Installation Guide (Windows & Linux)

### Linux (Ubuntu/Debian)

**Step 1: Install Java (OpenJDK 17)**
```bash
sudo apt update
sudo apt install openjdk-17-jre-headless -y
```

**Verify Java Installation**
```bash
java -version
```

**Step 2: Install SBT**

*Option A: Manual install (Recommended)*
```bash
echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | sudo tee /etc/apt/sources.list.d/sbt.list
curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x99E82A75642AC823" | sudo apt-key add -
sudo apt update
sudo apt install sbt -y
```

**Verify SBT Installation**
```bash
sbt sbtVersion
```

*Option B: Install via SDKMAN (for version control)*
```bash
sudo apt update && sudo apt install zip unzip -y
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install sbt 1.8.2
```

---

### Windows

**Step 1: Install Java (OpenJDK 17)**

1. Download Temurin OpenJDK 17 MSI installer from:  
   https://adoptium.net/en-GB/temurin/releases/
2. Install it, then set environment variables:  
   `JAVA_HOME = C:\Program Files\Eclipse Adoptium\jdk-17.x.x`  
   Add `%JAVA_HOME%\bin` to your `PATH`

**Verify Java Installation**
```cmd
java -version
```

**Step 2: Install SBT**

1. Download the SBT installer from:  
   https://www.scala-sbt.org/download.html
2. Run the installer. It sets up everything.

**Verify SBT Installation**
```cmd
sbt sbtVersion
```
