# Restaurant Management System - Class Diagram and Documentation

## Class Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                         <<enumeration>>                          │
│                          Role                                    │
├─────────────────────────────────────────────────────────────────┤
│ CUSTOMER                                                         │
│ WAITER                                                           │
│ CHEF                                                             │
│ MANAGER                                                          │
├─────────────────────────────────────────────────────────────────┤
│ + fromString(String): Role                                       │
└─────────────────────────────────────────────────────────────────┘
         ▲
         │ uses
         │
┌────────┴────────────────────────────────────────────────────────┐
│                         <<abstract>>                            │
│                            User                                  │
├─────────────────────────────────────────────────────────────────┤
│ - id: int                                                        │
│ - username: String                                               │
│ - password: String                                               │
│ - fullName: String                                               │
│ - role: Role                                                     │
│ - phone: String                                                  │
├─────────────────────────────────────────────────────────────────┤
│ + User()                                                         │
│ + User(int, String, String, String, Role, String)               │
│ + User(String, String, String, Role, String)                     │
│ + getId(): int                                                   │
│ + setId(int): void                                               │
│ + getUsername(): String                                          │
│ + setUsername(String): void                                      │
│ + getPassword(): String                                          │
│ + setPassword(String): void                                     │
│ + getFullName(): String                                          │
│ + setFullName(String): void                                      │
│ + getRole(): Role                                                │
│ + setRole(Role): void                                            │
│ + getPhone(): String                                             │
│ + setPhone(String): void                                         │
└─────────────────────────────────────────────────────────────────┘
         ▲
         │ extends
         │
    ┌────┴────┐
    │         │
┌───┴──────┐  │  ┌──────────────┐
│ Employee │  │  │   Customer    │
├──────────┤  │  ├──────────────┤
│          │  │  │              │
├──────────┤  │  ├──────────────┤
│ + Employee()│  │ + Customer() │
│ + Employee(│  │ + Customer(  │
│   String,  │  │   String,    │
│   String,  │  │   String,    │
│   String,  │  │   String)    │
│   Role,    │  │ + Customer(  │
│   String)  │  │   int,       │
│ + Employee(│  │   String,    │
│   int,     │  │   String,    │
│   String,  │  │   String,    │
│   String,  │  │   String)    │
│   String,  │  │              │
│   Role,    │  │              │
│   String)  │  │              │
└───────────┘  │  └──────────────┘
               │         ▲
               │         │
               │         │ references (customerId)
               │         │
┌──────────────┴─────────┴──────────────────────────────────────┐
│                          Table                                 │
├────────────────────────────────────────────────────────────────┤
│ - id: int                                                       │
│ - customerId: Integer                                           │
│ - capacity: int                                                 │
│ - status: TableStatus                                           │
├────────────────────────────────────────────────────────────────┤
│ + Table()                                                       │
│ + Table(int, Integer, int, TableStatus)                        │
│ + Table(int, int, TableStatus)                                   │
│ + Table(int, TableStatus)                                       │
│ + getId(): int                                                  │
│ + setId(int): void                                              │
│ + getCustomerId(): Integer                                      │
│ + setCustomerId(Integer): void                                  │
│ + getCapacity(): int                                            │
│ + setCapacity(int): void                                        │
│ + getStatus(): TableStatus                                      │
│ + setStatus(TableStatus): void                                  │
└────────────────────────────────────────────────────────────────┘
         ▲                              │
         │                              │ uses
         │ references (tableId)         │
         │                              │
┌────────┴──────────────────────────────┴─────────────────────────┐
│                          Order                                  │
├─────────────────────────────────────────────────────────────────┤
│ - id: int                                                        │
│ - tableId: int                                                   │
│ - customerId: Integer                                            │
│ - items: List<OrderItem>                                         │
│ - status: OrderStatus                                            │
│ - note: String                                                   │
├─────────────────────────────────────────────────────────────────┤
│ + Order()                                                        │
│ + Order(int, int, Integer, List<OrderItem>, OrderStatus, String) │
│ + Order(int, Integer, List<OrderItem>, OrderStatus, String)     │
│ + getId(): int                                                   │
│ + setId(int): void                                               │
│ + getTableId(): int                                              │
│ + setTableId(int): void                                          │
│ + getCustomerId(): Integer                                       │
│ + setCustomerId(Integer): void                                   │
│ + getItems(): List<OrderItem>                                    │
│ + setItems(List<OrderItem>): void                                │
│ + getStatus(): OrderStatus                                       │
│ + setStatus(OrderStatus): void                                   │
│ + getNote(): String                                              │
│ + setNote(String): void                                          │
└─────────────────────────────────────────────────────────────────┘
         │                              │
         │                              │ uses
         │ 1                            │
         │                              │
         │ ◄───────────────────────────┘
         │ contains (composition)
         │ *
         │
         ▼
┌─────────────────────────────────────────────────────────────────┐
│                        OrderItem                                 │
├─────────────────────────────────────────────────────────────────┤
│ - id: int                                                        │
│ - orderId: int                                                   │
│ - menuItemId: int                                                │
│ - price: double                                                  │
│ - quantity: int                                                  │
│ - note: String                                                   │
├─────────────────────────────────────────────────────────────────┤
│ + OrderItem()                                                    │
│ + OrderItem(int, int, int, double, int, String)                 │
│ + OrderItem(int, int, double, int, String)                      │
│ + getId(): int                                                   │
│ + setId(int): void                                               │
│ + getOrderId(): int                                              │
│ + setOrderId(int): void                                          │
│ + getMenuItemId(): int                                           │
│ + setMenuItemId(int): void                                       │
│ + getPrice(): double                                             │
│ + setPrice(double): void                                         │
│ + getQuantity(): int                                             │
│ + setQuantity(int): void                                         │
│ + getNote(): String                                              │
│ + setNote(String): void                                          │
└─────────────────────────────────────────────────────────────────┘
         │
         │ references (menuItemId)
         │
         ▼
┌─────────────────────────────────────────────────────────────────┐
│                        MenuItem                                  │
├─────────────────────────────────────────────────────────────────┤
│ - id: int                                                        │
│ - name: String                                                   │
│ - description: String                                            │
│ - imgUrl: String                                                 │
│ - cost: double                                                   │
│ - price: double                                                  │
│ - category: String                                               │
│ - isAvailable: boolean                                           │
├─────────────────────────────────────────────────────────────────┤
│ + MenuItem()                                                     │
│ + MenuItem(int, String, String, String, double, double,         │
│   String, boolean)                                               │
│ + MenuItem(String, String, String, double, double,             │
│   String, boolean)                                               │
│ + getId(): int                                                   │
│ + setId(int): void                                               │
│ + getName(): String                                              │
│ + setName(String): void                                          │
│ + getDescription(): String                                       │
│ + setDescription(String): void                                   │
│ + getImgUrl(): String                                            │
│ + setImgUrl(String): void                                        │
│ + getCost(): double                                              │
│ + setCost(double): void                                          │
│ + getPrice(): double                                             │
│ + setPrice(double): void                                         │
│ + getCategory(): String                                          │
│ + setCategory(String): void                                      │
│ + isAvailable(): boolean                                         │
│ + setAvailable(boolean): void                                   │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                         <<enumeration>>                          │
│                       OrderStatus                                │
├─────────────────────────────────────────────────────────────────┤
│ NEW                                                              │
│ PENDING                                                           │
│ IN_PROGRESS                                                      │
│ COMPLETED                                                        │
│ CANCELLED                                                        │
└─────────────────────────────────────────────────────────────────┘
         ▲
         │ uses
         │
         │ (Order class)

┌─────────────────────────────────────────────────────────────────┐
│                         <<enumeration>>                          │
│                       TableStatus                                │
├─────────────────────────────────────────────────────────────────┤
│ AVAILABLE                                                        │
│ OCCUPIED                                                         │
│ NOT_FOUND                                                        │
└─────────────────────────────────────────────────────────────────┘
         ▲
         │ uses
         │
         │ (Table class)
```

### Relationship Legend:
- **extends** (solid line with triangle): Inheritance relationship
- **references** (dashed arrow): Association via ID (e.g., Order → Customer via customerId)
- **contains** (solid line with diamond): Composition (Order contains OrderItem)
- **uses** (dashed arrow): Dependency on enum types

## Relationships Summary

### Visual Relationship Map:

```
User (abstract)
  ▲
  │ extends
  ├─── Employee
  └─── Customer
        ▲
        │ references (customerId)
        │
        ├─── Order ────► Customer (via customerId)
        │     │
        │     ├───► Table (via tableId)
        │     │
        │     ├───► OrderStatus (uses enum)
        │     │
        │     └───► OrderItem (composition, 1 to *)
        │             │
        │             └───► MenuItem (via menuItemId)
        │
        └─── Table ────► Customer (via customerId)
              │
              └───► TableStatus (uses enum)

User ────► Role (uses enum)
```

### Detailed Relationships:

1. **Inheritance (extends):**
   - `Employee` extends `User`
   - `Customer` extends `User`

2. **Composition (contains):**
   - `Order` contains a list of `OrderItem` (1 to many) - OrderItem cannot exist without Order

3. **Association (references via ID):**
   - `Order` → `Customer` (via `customerId: Integer`)
   - `Order` → `Table` (via `tableId: int`)
   - `OrderItem` → `MenuItem` (via `menuItemId: int`)
   - `Table` → `Customer` (via `customerId: Integer`)

4. **Dependency (uses enum):**
   - `User` uses `Role` enum (via `role: Role`)
   - `Order` uses `OrderStatus` enum (via `status: OrderStatus`)
   - `Table` uses `TableStatus` enum (via `status: TableStatus`)

---

## Detailed Relationship Specifications

### 1. INHERITANCE (Generalization) - "is-a" relationship

| Relationship | From Class | To Class | Type | Multiplicity | Direction | Description |
|--------------|------------|----------|------|--------------|-----------|-------------|
| **R1** | Employee | User | Inheritance | 1:1 | Unidirectional | Employee **is-a** User. Employee inherits all attributes and methods from User. |
| **R2** | Customer | User | Inheritance | 1:1 | Unidirectional | Customer **is-a** User. Customer inherits all attributes and methods from User. |

**UML Notation:** Solid line with hollow triangle arrow pointing to parent class

**Code Evidence:**
- `public class Employee extends User`
- `public class Customer extends User`

---

### 2. COMPOSITION - "part-of" relationship (strong ownership, lifecycle dependent)

| Relationship | From Class | To Class | Type | Multiplicity | Direction | Description |
|--------------|------------|----------|------|--------------|-----------|-------------|
| **R3** | Order | OrderItem | Composition | 1:* (one-to-many) | Unidirectional | Order **contains** OrderItem. OrderItem cannot exist independently of Order. When Order is deleted, OrderItems are also deleted. |

**UML Notation:** Solid line with filled diamond on the side of the whole (Order)

**Code Evidence:**
- `Order` has: `private List<OrderItem> items;`
- `OrderItem` has: `private int orderId;` (foreign key reference)

**Characteristics:**
- **Ownership:** Order owns OrderItem
- **Lifecycle:** OrderItem lifecycle is dependent on Order
- **Cardinality:** One Order can have zero or more OrderItems (1..*)

---

### 3. ASSOCIATION - "has-a" relationship (reference, no ownership)

| Relationship | From Class | To Class | Type | Multiplicity | Direction | Description |
|--------------|------------|----------|------|--------------|-----------|-------------|
| **R4** | Order | Customer | Association | 1:0..1 (one-to-zero-or-one) | Unidirectional | Order **references** Customer via customerId. Order can optionally have a Customer. |
| **R5** | Order | Table | Association | 1:1 (one-to-one) | Unidirectional | Order **references** Table via tableId. Each Order is associated with one Table. |
| **R6** | OrderItem | MenuItem | Association | *:1 (many-to-one) | Unidirectional | OrderItem **references** MenuItem via menuItemId. Multiple OrderItems can reference the same MenuItem. |
| **R7** | Table | Customer | Association | 1:0..1 (one-to-zero-or-one) | Unidirectional | Table **references** Customer via customerId. Table can optionally be assigned to a Customer. |

**UML Notation:** Dashed arrow or solid line with arrow pointing to referenced class

**Code Evidence:**
- `Order` has: `private Integer customerId;` (nullable, optional)
- `Order` has: `private int tableId;` (required)
- `OrderItem` has: `private int menuItemId;`
- `Table` has: `private Integer customerId;` (nullable, optional)

**Characteristics:**
- **Ownership:** No ownership - classes reference each other via IDs
- **Lifecycle:** Independent - referenced objects can exist without the referencer
- **Cardinality:** Various (1:1, 1:0..1, *:1)

---

### 4. DEPENDENCY - "uses" relationship (typically for enums, interfaces)

| Relationship | From Class | To Class | Type | Multiplicity | Direction | Description |
|--------------|------------|----------|------|--------------|-----------|-------------|
| **R8** | User | Role | Dependency | 1:1 | Unidirectional | User **uses** Role enum to define user roles. User depends on Role for type safety. |
| **R9** | Order | OrderStatus | Dependency | 1:1 | Unidirectional | Order **uses** OrderStatus enum to track order state. Order depends on OrderStatus for status management. |
| **R10** | Table | TableStatus | Dependency | 1:1 | Unidirectional | Table **uses** TableStatus enum to track table availability. Table depends on TableStatus for state management. |

**UML Notation:** Dashed arrow pointing to the dependency

**Code Evidence:**
- `User` has: `private Role role;`
- `Order` has: `private OrderStatus status;`
- `Table` has: `private TableStatus status;`

**Characteristics:**
- **Ownership:** No ownership - enum is a type dependency
- **Lifecycle:** Independent - enum exists independently
- **Purpose:** Type safety and state management

---

## Relationship Summary Table

| Relationship ID | From | To | Type | Multiplicity | Navigation |
|-----------------|------|-----|------|---------------|------------|
| R1 | Employee | User | Inheritance | 1:1 | Employee → User |
| R2 | Customer | User | Inheritance | 1:1 | Customer → User |
| R3 | Order | OrderItem | Composition | 1:* | Order → OrderItem |
| R4 | Order | Customer | Association | 1:0..1 | Order → Customer (via ID) |
| R5 | Order | Table | Association | 1:1 | Order → Table (via ID) |
| R6 | OrderItem | MenuItem | Association | *:1 | OrderItem → MenuItem (via ID) |
| R7 | Table | Customer | Association | 1:0..1 | Table → Customer (via ID) |
| R8 | User | Role | Dependency | 1:1 | User → Role (enum) |
| R9 | Order | OrderStatus | Dependency | 1:1 | Order → OrderStatus (enum) |
| R10 | Table | TableStatus | Dependency | 1:1 | Table → TableStatus (enum) |

---

## Relationship Type Definitions

### Inheritance (Generalization)
- **Definition:** A relationship where a child class inherits attributes and methods from a parent class
- **Characteristics:** "is-a" relationship, code reuse, polymorphism support
- **Example:** Employee is-a User, Customer is-a User

### Composition
- **Definition:** A strong "part-of" relationship where the part cannot exist without the whole
- **Characteristics:** Ownership, lifecycle dependency, deletion cascade
- **Example:** OrderItem is part of Order and cannot exist independently

### Association
- **Definition:** A relationship between classes where one class references another
- **Characteristics:** No ownership, independent lifecycle, reference via IDs
- **Example:** Order references Customer via customerId, but Customer can exist without Order

### Dependency
- **Definition:** A relationship where one class uses another class (typically enums, interfaces)
- **Characteristics:** Type dependency, no ownership, loose coupling
- **Example:** User depends on Role enum for type safety

---

## Table 1: Class Details

| No | Class | Instance Variables | Methods | Description |
|---|---|---|---|---|
| 1 | User | `private int id`<br>`private String username`<br>`private String password`<br>`private String fullName`<br>`private Role role`<br>`private String phone` | `User()`<br>`User(int, String, String, String, Role, String)`<br>`User(String, String, String, Role, String)`<br>`getId(): int`<br>`setId(int): void`<br>`getUsername(): String`<br>`setUsername(String): void`<br>`getPassword(): String`<br>`setPassword(String): void`<br>`getFullName(): String`<br>`setFullName(String): void`<br>`getRole(): Role`<br>`setRole(Role): void`<br>`getPhone(): String`<br>`setPhone(String): void` | This is an abstract class used for managing user information and behaviors. It serves as the base class for Employee and Customer classes. |
| 2 | Employee | (Inherits all from User) | `Employee()`<br>`Employee(String, String, String, Role, String)`<br>`Employee(int, String, String, String, Role, String)` | This class extends User and represents restaurant employees. It inherits all user properties and behaviors. |
| 3 | Customer | (Inherits all from User) | `Customer()`<br>`Customer(String, String, String, String)`<br>`Customer(int, String, String, String, String)` | This class extends User and represents restaurant customers. It automatically sets the role to CUSTOMER. |
| 4 | Order | `private int id`<br>`private int tableId`<br>`private Integer customerId`<br>`private List<OrderItem> items`<br>`private OrderStatus status`<br>`private String note` | `Order()`<br>`Order(int, int, Integer, List<OrderItem>, OrderStatus, String)`<br>`Order(int, Integer, List<OrderItem>, OrderStatus, String)`<br>`getId(): int`<br>`setId(int): void`<br>`getTableId(): int`<br>`setTableId(int): void`<br>`getCustomerId(): Integer`<br>`setCustomerId(Integer): void`<br>`getItems(): List<OrderItem>`<br>`setItems(List<OrderItem>): void`<br>`getStatus(): OrderStatus`<br>`setStatus(OrderStatus): void`<br>`getNote(): String`<br>`setNote(String): void` | This class is used for managing order information. It contains a list of order items and tracks the order status. |
| 5 | OrderItem | `private int id`<br>`private int orderId`<br>`private int menuItemId`<br>`private double price`<br>`private int quantity`<br>`private String note` | `OrderItem()`<br>`OrderItem(int, int, int, double, int, String)`<br>`OrderItem(int, int, double, int, String)`<br>`getId(): int`<br>`setId(int): void`<br>`getOrderId(): int`<br>`setOrderId(int): void`<br>`getMenuItemId(): int`<br>`setMenuItemId(int): void`<br>`getPrice(): double`<br>`setPrice(double): void`<br>`getQuantity(): int`<br>`setQuantity(int): void`<br>`getNote(): String`<br>`setNote(String): void` | This class represents a single item within an order. It links a menu item to an order with quantity and pricing information. |
| 6 | MenuItem | `private int id`<br>`private String name`<br>`private String description`<br>`private String imgUrl`<br>`private double cost`<br>`private double price`<br>`private String category`<br>`private boolean isAvailable` | `MenuItem()`<br>`MenuItem(int, String, String, String, double, double, String, boolean)`<br>`MenuItem(String, String, String, double, double, String, boolean)`<br>`getId(): int`<br>`setId(int): void`<br>`getName(): String`<br>`setName(String): void`<br>`getDescription(): String`<br>`setDescription(String): void`<br>`getImgUrl(): String`<br>`setImgUrl(String): void`<br>`getCost(): double`<br>`setCost(double): void`<br>`getPrice(): double`<br>`setPrice(double): void`<br>`getCategory(): String`<br>`setCategory(String): void`<br>`isAvailable(): boolean`<br>`setAvailable(boolean): void` | This class is used for managing menu item information including name, description, pricing, and availability status. |
| 7 | Table | `private int id`<br>`private Integer customerId`<br>`private int capacity`<br>`private TableStatus status` | `Table()`<br>`Table(int, Integer, int, TableStatus)`<br>`Table(int, int, TableStatus)`<br>`Table(int, TableStatus)`<br>`getId(): int`<br>`setId(int): void`<br>`getCustomerId(): Integer`<br>`setCustomerId(Integer): void`<br>`getCapacity(): int`<br>`setCapacity(int): void`<br>`getStatus(): TableStatus`<br>`setStatus(TableStatus): void` | This class is used for managing table information including capacity and current status (available, occupied, etc.). |
| 8 | Role | (Enum values: CUSTOMER, WAITER, CHEF, MANAGER) | `fromString(String): Role` | This enum defines the different roles that users can have in the restaurant system. |
| 9 | OrderStatus | (Enum values: NEW, PENDING, IN_PROGRESS, COMPLETED, CANCELLED) | (No methods) | This enum defines the possible states of an order in the system. |
| 10 | TableStatus | (Enum values: AVAILABLE, OCCUPIED, NOT_FOUND) | (No methods) | This enum defines the possible states of a table in the restaurant. |

---

## Table 2: Abstract Classes

| No | Abstract Class | Abstract Methods | Concrete Methods | Description |
|---|---|---|---|---|
| 1 | User | None | `User()`<br>`User(int, String, String, String, Role, String)`<br>`User(String, String, String, Role, String)`<br>`getId(): int`<br>`setId(int): void`<br>`getUsername(): String`<br>`setUsername(String): void`<br>`getPassword(): String`<br>`setPassword(String): void`<br>`getFullName(): String`<br>`setFullName(String): void`<br>`getRole(): Role`<br>`setRole(Role): void`<br>`getPhone(): String`<br>`setPhone(String): void` | This class is an abstract class that is used by subclasses Employee and Customer. It provides common user information and behaviors that are shared across all user types in the restaurant management system. |

---

## Notes

- **User** is the only abstract class in the model package
- All instance variables are private, following encapsulation principles
- All classes follow JavaBean conventions with getters and setters
- The system uses enums (Role, OrderStatus, TableStatus) for type safety
- Relationships are primarily maintained through ID references rather than direct object references

