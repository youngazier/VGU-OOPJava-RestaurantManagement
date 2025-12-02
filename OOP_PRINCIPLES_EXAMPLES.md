# OOP Principles Examples in Restaurant Management System

This document provides clear examples of the four core Object-Oriented Programming principles found in this codebase.

---

## 1. ENCAPSULATION

**Definition**: Encapsulation is the bundling of data (fields) and methods (getters/setters) that operate on that data within a single unit (class), while hiding internal implementation details from outside access.

### Example 1: `Table` Class
**Location**: `src/main/java/com/vgu/restaurant/model/Table.java`

```java
public class Table {
    // Private fields - data is hidden from direct access
    private int id;
    private Integer customerId;
    private int capacity;
    private TableStatus status;
    
    // Public getters and setters - controlled access to private data
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    
    public int getCapacity() {return capacity;}
    public void setCapacity(int capacity) {this.capacity = capacity;}
    
    // ... more getters/setters
}
```

**Why it's Encapsulation**:
- Fields (`id`, `capacity`, `status`) are `private`, preventing direct external modification
- Access is controlled through public `getter` and `setter` methods
- Internal representation can change without affecting code that uses the class

### Example 2: `MenuItem` Class
**Location**: `src/main/java/com/vgu/restaurant/model/MenuItem.java`

```java
public class MenuItem {
    private int id;
    private String name;
    private double cost;      // Internal cost (hidden from customers)
    private double price;      // Selling price
    private boolean isAvailable;
    
    // Getters/setters provide controlled access
    public double getCost() {return cost;}  // Only internal use
    public double getPrice() {return price;} // Public price
}
```

**Why it's Encapsulation**:
- Business logic: `cost` (internal) vs `price` (public) are separated
- Data integrity: Cannot directly set invalid values without validation
- Future changes: Can add validation logic in setters without breaking existing code

---

## 2. ABSTRACTION

**Definition**: Abstraction hides complex implementation details and shows only essential features through interfaces or abstract classes.

### Example 1: Service Layer Abstraction
**Location**: 
- Interface: `src/main/java/com/vgu/restaurant/service/TableService.java`
- Implementation: `src/main/java/com/vgu/restaurant/service/TableServiceImpl.java`

```java
// INTERFACE - Defines WHAT operations are available (abstraction)
public interface TableService {
    boolean create(Table table);
    Optional<Table> getById(int id);
    List<Table> getAll();
    boolean update(Table table);
    boolean delete(int tableId);
}

// IMPLEMENTATION - Defines HOW operations work (hidden details)
public class TableServiceImpl implements TableService {
    private final TableDAO tableDAO = TableDAOImpl.getInstance();
    
    @Override
    public boolean create(Table table) {
        return tableDAO.add(table);  // Implementation details hidden
    }
    
    @Override
    public boolean delete(int tableId) {
        // Business logic: only delete if table is available
        return tableDAO.getById(tableId)
                .filter(t -> t.getStatus() == TableStatus.AVAILABLE)
                .map(tableDAO::delete)
                .orElse(false);
    }
}
```

**Why it's Abstraction**:
- Controllers use `TableService` interface, not the implementation
- Implementation details (database operations, business rules) are hidden
- Can swap implementations without changing controller code

### Example 2: Generic DAO Interface
**Location**: `src/main/java/com/vgu/restaurant/dao/DAOInterface.java`

```java
// Generic interface - abstracts common CRUD operations
public interface DAOInterface<T> {
    boolean add(T t);
    boolean update(T t);
    boolean delete(T t);
    Optional<T> getById(int id);
    List<T> getAll();
}
```

**Why it's Abstraction**:
- Defines a contract for data access operations
- Hides database-specific implementation details
- Works with any entity type (`Table`, `Order`, `MenuItem`, etc.)

### Example 3: Abstract User Class
**Location**: `src/main/java/com/vgu/restaurant/model/User.java`

```java
public abstract class User {
    private int id;
    private String username;
    private String password;
    // ... common fields
    
    // Common getters/setters for all user types
    public String getUsername() {return username;}
    // ...
}
```

**Why it's Abstraction**:
- `abstract` keyword prevents direct instantiation
- Defines common structure for all user types
- Forces subclasses to provide specific implementations

---

## 3. INHERITANCE

**Definition**: Inheritance allows a class (child/subclass) to inherit properties and methods from another class (parent/superclass), promoting code reuse.

### Example 1: User Hierarchy
**Location**: 
- Parent: `src/main/java/com/vgu/restaurant/model/User.java`
- Children: `src/main/java/com/vgu/restaurant/model/Employee.java` and `Customer.java`

```java
// PARENT CLASS (Abstract)
public abstract class User {
    private int id;
    private String username;
    private String password;
    private String fullName;
    private Role role;
    private String phone;
    
    // Constructors and getters/setters
    public User(int id, String username, String password, 
                String fullName, Role role, String phone) {
        this.id = id;
        this.username = username;
        // ... initialize all fields
    }
}

// CHILD CLASS 1
public class Employee extends User {
    public Employee(int id, String username, String password, 
                    String fullName, Role role, String phone) {
        super(id, username, password, fullName, role, phone);  // Calls parent constructor
    }
}

// CHILD CLASS 2
public class Customer extends User {
    public Customer(int id, String username, String password, 
                    String fullName, String phone) {
        // Automatically sets role to CUSTOMER
        super(id, username, password, fullName, Role.CUSTOMER, phone);
    }
}
```

**Why it's Inheritance**:
- `Employee` and `Customer` inherit all fields and methods from `User`
- Code reuse: Don't need to duplicate `id`, `username`, `password`, etc.
- `extends` keyword establishes parent-child relationship
- `super()` calls parent constructor

### Example 2: Interface Inheritance
**Location**: `src/main/java/com/vgu/restaurant/dao/TableDAO.java`

```java
// TableDAO extends the generic DAOInterface
public interface TableDAO extends DAOInterface<Table> {
    // Inherits: add, update, delete, getById, getAll
    // Adds table-specific methods
    boolean updateStatus(int tableId, TableStatus newStatus);
    List<Table> getByStatus(TableStatus status);
}
```

**Why it's Inheritance**:
- `TableDAO` inherits all methods from `DAOInterface<Table>`
- Adds table-specific operations
- `extends` keyword for interface inheritance

---

## 4. POLYMORPHISM

**Definition**: Polymorphism allows objects of different types to be treated as instances of the same type, enabling one interface to represent different underlying forms.

### Example 1: Interface Polymorphism (Service Layer)
**Location**: `src/main/java/com/vgu/restaurant/controller/TableController.java`

```java
public class TableController extends HttpServlet {
    // Polymorphism: variable is of interface type, but holds implementation
    private final TableService tableService = new TableServiceImpl();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        // Uses interface methods - doesn't know about implementation
        boolean created = tableService.create(table);
        // Could swap TableServiceImpl with another implementation
    }
}
```

**Why it's Polymorphism**:
- `tableService` is declared as `TableService` (interface type)
- But holds `TableServiceImpl` (concrete implementation)
- Can be replaced with any class implementing `TableService`
- Same interface, different implementations possible

### Example 2: Runtime Polymorphism (User Hierarchy)
**Example Code** (demonstrates concept):

```java
// Polymorphic behavior with User hierarchy
User user1 = new Employee(1, "john", "pass", "John Doe", Role.WAITER, "123");
User user2 = new Customer(2, "jane", "pass", "Jane Smith", "456");

// Both are User type, but different implementations
List<User> users = Arrays.asList(user1, user2);

// Polymorphism: same method call, different behavior based on actual type
for (User user : users) {
    System.out.println(user.getUsername());  // Works for both Employee and Customer
    // Runtime determines which class's method to call
}
```

**Why it's Polymorphism**:
- `Employee` and `Customer` can be treated as `User`
- Same interface (`User`), different underlying types
- Method calls resolved at runtime (dynamic binding)

### Example 3: Generic Interface Polymorphism
**Location**: DAO implementations

```java
// Generic interface allows polymorphism with different entity types
DAOInterface<Table> tableDAO = new TableDAOImpl();
DAOInterface<Order> orderDAO = new OrderDAOImpl();
DAOInterface<MenuItem> menuDAO = new MenuItemDAOImpl();

// Same interface, different implementations
tableDAO.add(new Table(...));
orderDAO.add(new Order(...));
menuDAO.add(new MenuItem(...));
```

**Why it's Polymorphism**:
- Same interface (`DAOInterface<T>`) used for different entity types
- Different implementations (`TableDAOImpl`, `OrderDAOImpl`, etc.)
- Code can work with any DAO implementation through the interface

### Example 4: Method Overriding (Polymorphism in Action)
**Location**: `src/main/java/com/vgu/restaurant/service/TableServiceImpl.java`

```java
public class TableServiceImpl implements TableService {
    @Override
    public boolean delete(int tableId) {
        // Overrides interface method with specific implementation
        // Adds business logic: only delete if available
        return tableDAO.getById(tableId)
                .filter(t -> t.getStatus() == TableStatus.AVAILABLE)
                .map(tableDAO::delete)
                .orElse(false);
    }
}
```

**Why it's Polymorphism**:
- `TableServiceImpl.delete()` provides specific implementation
- Other implementations could have different logic
- Caller uses `TableService.delete()` - doesn't know which implementation

---

## Summary Table

| Principle | Key Example | Location |
|-----------|-------------|----------|
| **Encapsulation** | `Table` class with private fields and public getters/setters | `model/Table.java` |
| **Abstraction** | `TableService` interface hiding implementation details | `service/TableService.java` |
| **Inheritance** | `Employee` and `Customer` extending `User` | `model/User.java`, `Employee.java`, `Customer.java` |
| **Polymorphism** | `TableService` interface used polymorphically in controller | `controller/TableController.java` |

---

## Benefits in This Codebase

1. **Encapsulation**: Protects data integrity, allows validation, enables future changes
2. **Abstraction**: Simplifies controller code, enables testing with mocks, allows implementation swapping
3. **Inheritance**: Reduces code duplication, promotes consistency across user types
4. **Polymorphism**: Enables flexible, extensible code that works with multiple implementations


