# Banking & Investment App - Project Report

## 📋 Project Overview

The **Banking & Investment System** is a comprehensive Java application that demonstrates modern software design patterns to create a flexible, maintainable, and scalable financial platform. The system allows users to manage various types of accounts with additional services, build investment portfolios, and perform currency exchanges.

## 🏗️ Project Structure
src/
├── accounts/ # Core account components and decorators
│ ├── Account.java # Interface
│ ├── BaseDecorator.java # Abstract decorator
│ ├── SavingAccount.java # Concrete component
│ ├── InvestmentAccount.java # Concrete component
│ ├── InsuranceDecorator.java
│ ├── TaxOptimizerDecorator.java
│ └── RewardPointsDecorator.java
├── adapter/ # Currency conversion layer
│ ├── CurrencyAdapter.java
│ └── CurrencyRates.java
├── builder/ # Portfolio construction
│ ├── Portfolio.java
│ └── PortfolioBuilder.java
├── facade/ # Simplified banking interface
│ └── BankingFacade.java
├── factory/ # Account creation
│ └── AccountFactory.java
└── demo/ # Client application
├── ClientApp.java
└── Main.java
## 🎯 Design Patterns Implementation

### 1. **Decorator Pattern** - Account Services

**Purpose**: Dynamically add additional features to accounts without modifying their core structure.

**Implementation**:
- **Component Interface**: `Account`
  - Core operations: `deposit()`, `withdraw()`, `getBalance()`, `getDescription()`, `close()`, `exchange()`
- **Concrete Components**: 
  - `SavingAccount` - Basic savings functionality with balance management
  - `InvestmentAccount` - Investment management with specialized operations
- **Decorators**:
  - `InsuranceDecorator` - Adds insurance protection to accounts
  - `TaxOptimizerDecorator` - Applies tax optimization strategies (+2% on deposits)
  - `RewardPointsDecorator` - Implements loyalty points system (1 point per 10 units deposited)

**BaseDecorator**: Abstract class that implements Account and delegates all operations to wrapped account.

### 2. Facade Pattern - BankingFacade
Purpose: Provide a simplified interface to complex subsystem operations, hiding implementation details from clients.

Implementation:

BankingFacade class serves as single entry point for banking operations

Hides complexity of:

Account creation with proper decorator combinations

Applying multiple business rules and validations

Account closure and cleanup procedures
### 3. Factory Pattern - Account Creation
Purpose: Centralize and standardize account object creation, providing encapsulation of instantiation logic.
### 4. Builder Pattern - Portfolio Construction
Purpose: Construct complex portfolio objects step by step using a fluent interface.
### 5. Adapter Pattern - Currency Conversion
Purpose: Convert between different currency systems seamlessly while maintaining compatibility with existing account interfaces.
### Exchange Rates:

***USD to KZT: 538.0**

**EUR to KZT: 625.0**

**KZT to USD: 1/538.0**

**KZT to EUR: 1/625.0**
