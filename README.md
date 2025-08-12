# 💰 Personal Finance Tracker

A comprehensive personal finance management application built with Java, demonstrating core programming concepts through real-world problem-solving.

## 🎯 Project Overview

This Personal Finance Tracker is designed as a learning project that combines practical financial management with Java programming fundamentals. Currently in **Phase 1**, it provides a solid foundation for transaction management with plans to evolve into a feature-rich financial management system.

## ✨ Current Features (Phase 1)

- ✅ **Transaction Management**: Record income and expenses with comprehensive validation
- ✅ **File Persistence**: Automatic data saving and loading with backup system
- ✅ **Balance Tracking**: Real-time balance calculations and display
- ✅ **Error Handling**: Custom exceptions for financial constraints and invalid inputs
- ✅ **Console Interface**: User-friendly menu-driven interaction
- ✅ **Data Integrity**: Automatic ID management and transaction history
- ✅ **Input Validation**: Robust handling of invalid user inputs

## 🚀 Live Demo

```
=== PERSONAL FINANCE TRACKER ===
Enter your account name: mk
Loaded 4 transactions.
Welcome, mk!

=== MAIN MENU ===
1. Add Income
2. Add Expense
3. View Transactions
4. View Account Summary
5. Exit
Enter your choice: 3

=== TRANSACTION HISTORY ===
ID: 1001 | INCOME | 1000.00 | Salary | 10-08-2025
ID: 1002 | EXPENSE | 300.00 | Rent | 11-08-2025
ID: 1003 | EXPENSE | 500.00 | Tuition Fee | 11-08-2025
ID: 1004 | INCOME | 200.00 | pocket money | 12-08-2025

Current Balance: 400.00
```

## 🛠️ Technologies Used

- **Java 8+** - Core programming language
- **File I/O** - Data persistence using BufferedReader/Writer
- **Collections Framework** - ArrayList for transaction management
- **Exception Handling** - Custom exceptions for business logic validation
- **Object-Oriented Design** - Clean class structure with encapsulation

## 📋 Java Concepts Demonstrated

### Core Programming Concepts
- **Variables and Data Types**: Proper use of primitives and objects
- **Control Flow**: if-else statements, while loops for menu navigation
- **Input Validation**: Robust error handling for user inputs
- **String Processing**: Data formatting and parsing

### Object-Oriented Programming
- **Classes & Objects**: Transaction and Account classes with proper encapsulation
- **Encapsulation**: Private fields with controlled access through getters/setters
- **Data Validation**: Business logic enforcement through method validation
- **Constructor Overloading**: Multiple constructors for different use cases

### Advanced Java Features
- **Custom Exceptions**: InsufficientFundsException, InvalidTransactionException, DataFileException
- **File I/O Operations**: Persistent data storage with automatic backup
- **Collections**: ArrayList for dynamic transaction storage
- **Static Methods**: Utility methods and ID management
- **Date Handling**: LocalDate for transaction timestamps

## 🚀 Getting Started

### Prerequisites
- Java 8 or higher
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code) or command line
- Basic understanding of Java syntax

### Installation
1. Clone the repository:
```bash
git clone https://github.com/yourusername/personal-finance-tracker.git
cd personal-finance-tracker
```

2. Navigate to the source directory:
```bash
cd src
```

3. Compile the Java files:
```bash
javac com/financetracker/main/FinanceTrackerApp.java
```

4. Run the application:
```bash
java com.financetracker.main.FinanceTrackerApp
```

### First Time Setup
1. Enter your account name when prompted
2. Start adding income and expense transactions
3. Use the menu to view transaction history and account summary
4. Your data will be automatically saved and loaded on restart

## 📖 Usage Examples

### Adding Income
```
=== MAIN MENU ===
Enter your choice: 1

--- ADD INCOME ---
Enter income amount: 1000
Enter description: Salary
Income added: 1000.00. New balance: 1000.00
Income added successfully.
```

### Adding Expense with Validation
```
=== MAIN MENU ===
Enter your choice: 2

--- ADD EXPENSE ---
Enter expense amount: abc
Please enter a valid amount.
Enter expense amount: 250
Enter description: Groceries
Expense added: 250.00. New balance: 750.00
Expense added successfully.
```

### Viewing Account Summary
```
=== ACCOUNT SUMMARY ===
Account: mk
Total Income: 1500.00
Total Expenses: 1050.00
Net Balance: 450.00
```

## 📁 Project Structure

```
personal-finance-tracker/
├── src/
│   └── com/
│       └── financetracker/
│           ├── exceptions/
│           │   ├── DataFileException.java
│           │   ├── InsufficientFundsException.java
│           │   └── InvalidTransactionException.java
│           ├── main/
│           │   ├── AccountTesterSimple.java
│           │   ├── FileManagerTester.java
│           │   ├── FinanceTrackerApp.java
│           │   └── TransactionTester.java
│           ├── managers/
│           │   └── FileManager.java
│           └── models/
│               ├── Account.java
│               └── Transaction.java
├── bin/                           # Compiled classes
├── transactions.txt               # Main data file
├── transactions_backup.txt        # Automatic backup
├── .gitignore
└── README.md
```

## 🎓 Learning Outcomes Achieved

By completing Phase 1, the following Java concepts have been mastered:

### **Object-Oriented Programming**
- ✅ **Class Design**: Well-structured Transaction and Account classes
- ✅ **Encapsulation**: Private fields with public accessor methods
- ✅ **Constructor Design**: Multiple constructors for different scenarios
- ✅ **Method Design**: Clear separation of concerns across methods

### **Exception Handling**
- ✅ **Custom Exceptions**: Domain-specific exception classes
- ✅ **Exception Propagation**: Proper throwing and catching of exceptions
- ✅ **Error Recovery**: Graceful handling of invalid inputs and file errors
- ✅ **Resource Management**: Proper file handling with try-with-resources

### **File Operations**
- ✅ **Data Persistence**: Saving and loading transaction data
- ✅ **File Format Design**: CSV-like format for easy parsing
- ✅ **Backup Strategy**: Automatic backup before data modifications
- ✅ **Error Handling**: Robust file operation error management

### **Data Structures**
- ✅ **ArrayList Usage**: Dynamic transaction storage and management
- ✅ **Data Manipulation**: Filtering, sorting, and calculating totals
- ✅ **Memory Management**: Efficient data structure usage

### **Input Validation & User Interface**
- ✅ **Input Sanitization**: Robust validation of user inputs
- ✅ **Menu-Driven Interface**: Professional console application design
- ✅ **User Experience**: Clear prompts, confirmations, and error messages
- ✅ **Flow Control**: Proper program flow and menu navigation

## ⚡ Performance & Features

### **Current Capabilities**
- **Transaction Capacity**: Efficiently handles hundreds of transactions
- **Data Persistence**: Automatic save/load with backup protection
- **Startup Time**: Instant loading for typical personal finance usage
- **Memory Usage**: Lightweight with minimal resource consumption
- **Validation**: Comprehensive input validation preventing data corruption

### **Data Security**
- **Local Storage**: All data stored locally on user's machine
- **Automatic Backup**: Backup created before each data modification
- **No Network Access**: Complete offline operation for privacy
- **Human-Readable Format**: Transparent data storage in text format

## 🛣️ Development Roadmap

### ✅ **Phase 1 - COMPLETED**
- [x] Basic transaction recording (Income/Expense)
- [x] File persistence with backup system
- [x] Account balance management
- [x] Custom exception handling
- [x] Console user interface
- [x] Input validation and error handling
- [x] Transaction history display
- [x] Account summary reporting

### 📋 **Planned Future Phases**

#### **Phase 2 - Enhanced Features** (Upcoming)
- [ ] Transaction categories (Food, Transportation, Bills, etc.)
- [ ] Monthly budget tracking with alerts
- [ ] Enhanced reporting (Monthly/Category reports)
- [ ] Transaction inheritance structure (Income/Expense subclasses)

#### **Phase 3 - Advanced Features** (Future)
- [ ] Search and filter functionality
- [ ] Recurring transaction support
- [ ] Multi-threading for background operations
- [ ] Advanced financial analytics

#### **Phase 4 - Professional Features** (Future)
- [ ] Data export (CSV, PDF reports)
- [ ] Enhanced UI with better formatting
- [ ] Performance optimization
- [ ] Comprehensive testing suite

## 🧪 Testing

The project includes comprehensive test classes:

- **TransactionTester.java**: Tests transaction creation, validation, and file format conversion
- **FileManagerTester.java**: Tests data persistence, backup, and recovery
- **AccountTesterSimple.java**: Tests account operations and balance calculations
- **FinanceTrackerApp.java**: Full integration testing through user interface

### Running Tests
```bash
# Compile and run individual test classes
javac com/financetracker/main/TransactionTester.java
java com.financetracker.main.TransactionTester
```


### **Phase 1 Enhancements**
- Better input validation messages
- Enhanced console formatting
- Additional transaction validation rules
- Performance optimizations for large datasets
- Unit test improvements

## 🐛 Known Issues

- Console input buffer occasionally needs clearing after invalid input
- Date format currently locked to dd-MM-yyyy format
- Large transaction files (>1000 transactions) may impact startup performance
- Error messages could be more user-friendly in some edge cases

## 📚 Learning Resources Used

### **Java Documentation**
- [Oracle Java SE Documentation](https://docs.oracle.com/javase/)
- [Java File I/O Tutorial](https://docs.oracle.com/javase/tutorial/essential/io/)
- [Exception Handling Best Practices](https://docs.oracle.com/javase/tutorial/essential/exceptions/)



**Current Status: Phase 1 Complete ✅**

*A solid foundation for personal finance management, demonstrating core Java programming concepts through practical application development.*

**Next Milestone: Phase 2 - Enhanced Features with Categories and Budgets**
