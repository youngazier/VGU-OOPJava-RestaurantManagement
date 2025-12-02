# Restaurant Management System

A Java-based restaurant management system with a React + Vite frontend.

## Features

- User authentication and roles (Customer, Waiter, Chef, Manager)
- Manage menu items, tables, and orders
- RESTful API backend (Java Servlet)
- Modern React frontend

## Getting Started

### Backend Setup

1. **Configure Database Connection**

   Edit the `.env` file in `src/main/resources`:

   ```
   DB_URL=jdbc:mysql://localhost:3306/restaurantdb
   DB_USER=root
   DB_PASSWORD=123456
   ```

2. **Build and Run**

   ```
   mvn clean package
   ```

   Deploy the generated WAR file in your servlet container (e.g., Tomcat).

### Frontend Setup

1. Go to the `frontend` directory:

   ```
   cd frontend
   ```

2. Install dependencies:

   ```
   npm install
   ```

3. Start the development server:

   ```
   npm run dev
   ```

   The frontend runs on [http://localhost:5173](http://localhost:5173).

## Project Structure

- `src/main/java` - Java backend source code
- `frontend/` - React frontend source code
- `src/main/resources/.env` - Database configuration

## License

MIT