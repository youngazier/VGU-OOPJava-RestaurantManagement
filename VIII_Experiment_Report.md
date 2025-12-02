# VIII Experiment

## 1 Environment and Tools

### Environment

**Physical Resources:**
- **Development PC**: 1 workstation
- **CPU**: Any modern processor (dual-core or higher)
- **RAM**: Minimum 4GB RAM (8GB recommended for better performance)
- **Storage**: Minimum 2GB free disk space for development environment
- **Operating System**: Windows 10/11 (as indicated by the development environment)
- **Network**: Local network connection for database access

**Software Environment:**
- **Java Development Kit (JDK)**: Version 21
- **Application Server**: Jakarta EE compatible servlet container (e.g., Apache Tomcat 10.x or later)
- **Database Server**: MySQL Server 8.4.0 or compatible version
- **Web Browser**: Modern browser (Chrome, Firefox, Edge) for frontend testing

### Tools

**Backend Development:**
- **Java**: Programming language (JDK 21)
- **Maven**: Build automation and dependency management tool (version 3.x)
- **Jakarta Servlet API**: Version 6.0.0 - For building RESTful web services
- **MySQL Connector/J**: Version 8.4.0 - JDBC driver for MySQL database connectivity
- **Gson**: Version 2.10.1 - JSON serialization/deserialization library
- **Spring Dotenv**: Version 4.0.0 - Environment variable management

**Frontend Development:**
- **React**: JavaScript library for building user interfaces
- **Vite**: Build tool and development server for frontend
- **Node.js**: JavaScript runtime environment (required for frontend development)

**Database:**
- **MySQL**: Relational database management system (version 8.4.0 or compatible)

**Development Tools:**
- **IDE**: IntelliJ IDEA or similar Java IDE
- **Version Control**: Git (optional but recommended)

**Appendix: Tool Installation Instructions**

**Installing Java JDK 21:**
1. Download JDK 21 from Oracle or OpenJDK website
2. Run the installer and follow the installation wizard
3. Set JAVA_HOME environment variable to the JDK installation directory
4. Add `%JAVA_HOME%\bin` to the system PATH
5. Verify installation by running `java -version` in command prompt

**Installing Maven:**
1. Download Apache Maven from https://maven.apache.org/download.cgi
2. Extract the archive to a directory (e.g., `C:\Program Files\Apache\maven`)
3. Add `MAVEN_HOME` environment variable pointing to Maven directory
4. Add `%MAVEN_HOME%\bin` to the system PATH
5. Verify installation by running `mvn -version` in command prompt

**Installing MySQL:**
1. Download MySQL Community Server from https://dev.mysql.com/downloads/mysql/
2. Run the MySQL Installer
3. Choose "Developer Default" setup type
4. Complete the installation wizard and configure root password
5. Verify installation by connecting to MySQL using MySQL Workbench or command line

**Installing Node.js and npm:**
1. Download Node.js from https://nodejs.org/ (LTS version recommended)
2. Run the installer and follow the installation wizard
3. Verify installation by running `node -v` and `npm -v` in command prompt

**Setting up the Project:**
1. Clone or download the project repository
2. Navigate to project root directory
3. Run `mvn clean install` to build the backend project
4. Navigate to `frontend` directory
5. Run `npm install` to install frontend dependencies
6. Configure database connection in `DBConnection.java` or use environment variables
7. Create the database schema in MySQL
8. Deploy the WAR file to a servlet container or run the frontend with `npm run dev`

---

## 2 Project Functions

The Restaurant Management System is a comprehensive web application designed to manage restaurant operations including table reservations, menu management, order processing, and user administration. The system supports multiple user roles with different access levels and functionalities.

### 2.1 User Management

**User Registration and Authentication:**
- Register new users with different roles (Customer, Waiter, Chef, Manager)
- User login with username and password authentication
- Session-based authentication management
- Role-based access control (RBAC) for different system functions

**User Roles and Permissions:**
- **Customer**: Can view menu items, place orders, and view their own order history
- **Waiter**: Can manage tables, create and update orders, view all orders
- **Chef**: Can view orders, update order status (especially cooking-related statuses)
- **Manager**: Full system access including user management, menu management, table management, and order oversight

**User Information Management:**
- Store user details: username, password (hashed), full name, phone number
- Differentiate between Customer and Employee user types
- View all users (restricted to non-customer roles)

### 2.2 Table Management

**Table Operations:**
- Create new tables with specified capacity
- View all tables and their current status
- Get table details by table ID
- Update table information (capacity, status)
- Update table status independently (Available, Occupied, Not Found)
- Delete tables from the system

**Table Status Tracking:**
- **AVAILABLE**: Table is free and ready for customers
- **OCCUPIED**: Table is currently in use
- **NOT_FOUND**: Table status unknown or unavailable

**Table-Customer Association:**
- Assign customers to tables
- Track which customer is using which table
- Support for walk-in customers (customer ID can be null)

### 2.3 Menu Item Management

**Menu Item Operations:**
- Add new menu items to the system
- View all menu items
- Get specific menu item details by ID
- Update menu item information (name, description, price, cost, category, availability)
- Delete menu items from the system

**Menu Item Properties:**
- Item name and description
- Image URL for displaying item photos
- Cost (internal cost for business calculations)
- Price (selling price to customers)
- Category classification
- Availability status (available/unavailable)

**Image Management:**
- Serve menu item images through dedicated image servlet
- Support for various image formats (JPG, PNG, etc.)
- Secure image file access with path validation
- Image caching for improved performance

### 2.4 Order Management

**Order Creation and Processing:**
- Create new orders associated with tables and customers
- Add menu items to orders with specified quantities
- Update order item quantities
- Remove items from orders
- Update order status throughout the order lifecycle

**Order Status Workflow:**
- **NEW**: Order has been created but not yet processed
- **PENDING**: Order is waiting to be prepared
- **IN_PROGRESS**: Order is currently being prepared
- **COMPLETED**: Order has been finished and served
- **CANCELLED**: Order has been cancelled

**Order Filtering and Viewing:**
- View all orders (for staff roles: Manager, Waiter, Chef)
- Filter orders by status
- View orders by customer ID (for customers to see their own orders)
- Get specific order details by order ID
- View order items with quantities and menu item details

**Order Information:**
- Table association
- Customer association (optional for walk-ins)
- List of order items with quantities
- Order notes/special instructions
- Order status tracking

### 2.5 System Architecture Features

**RESTful API Design:**
- RESTful endpoints for all major operations
- JSON-based data exchange
- HTTP methods (GET, POST, PUT, DELETE) for appropriate operations
- Standard HTTP status codes for error handling

**Database Integration:**
- MySQL database for persistent data storage
- DAO (Data Access Object) pattern for database operations
- Connection pooling and management
- Transaction support for data consistency

**Service Layer Architecture:**
- Service layer abstraction between controllers and data access
- Business logic separation
- Service interface and implementation pattern

**Security Features:**
- Session-based authentication
- Role-based authorization
- CORS (Cross-Origin Resource Sharing) support for frontend integration
- Input validation and error handling

**Frontend Integration:**
- RESTful API endpoints designed for frontend consumption
- JSON responses for easy frontend parsing
- Image serving for menu item display
- Support for modern single-page application (SPA) architecture

### 2.6 Additional Features

**Error Handling:**
- Comprehensive error messages in JSON format
- HTTP status code management (400, 401, 403, 404, 500)
- Input validation for all user inputs
- Database error handling

**Data Validation:**
- Required field validation
- Data type validation
- Business rule validation (e.g., order status transitions)

**Performance Optimization:**
- Image caching headers
- Efficient database query design
- Connection management

---

### Function Summary Table

| Function Category | Key Operations | User Roles |
|-------------------|----------------|------------|
| **User Management** | Register, Login, View Users | All (with role restrictions) |
| **Table Management** | Create, Read, Update, Delete, Update Status | Manager, Waiter |
| **Menu Management** | Create, Read, Update, Delete Menu Items | Manager |
| **Order Management** | Create Orders, Add Items, Update Status, View Orders | Manager, Waiter, Chef, Customer (limited) |
| **Image Serving** | Serve Menu Item Images | All (public) |
| **Authentication** | Login, Session Management | All |
| **Authorization** | Role-based Access Control | System |

---

**Note**: This system follows Object-Oriented Programming principles with clear separation of concerns through the Model-View-Controller (MVC) architecture pattern, implementing DAO and Service layers for maintainability and scalability.

