import { Navigate, Route, Routes } from 'react-router-dom';
import './App.css';
import { Layout } from './components/Layout.jsx';
import { ProtectedRoute } from './components/ProtectedRoute.jsx';
import { Dashboard } from './pages/Dashboard.jsx';
import { MenuItemsPage } from './pages/MenuItemsPage.jsx';
import { OrdersPage } from './pages/OrdersPage.jsx';
import { TablesPage } from './pages/TablesPage.jsx';
import { UsersPage } from './pages/UsersPage.jsx';
import { LoginPage } from './pages/LoginPage.jsx';
import { RegisterPage } from './pages/RegisterPage.jsx';
import { CustomerHome } from './pages/CustomerHome.jsx';

function App() {
  return (
    <Routes>
      <Route element={<Layout />}>
        <Route
          index
          element={
            <ProtectedRoute roles={['WAITER', 'CHEF', 'MANAGER']}>
              <Dashboard />
            </ProtectedRoute>
          }
        />
        <Route
          path="menu"
          element={
            <ProtectedRoute roles={['WAITER', 'CHEF', 'MANAGER', 'CUSTOMER']}>
              <MenuItemsPage />
            </ProtectedRoute>
          }
        />
        <Route
          path="orders"
          element={
            <ProtectedRoute roles={['WAITER', 'CHEF', 'MANAGER']}>
              <OrdersPage />
            </ProtectedRoute>
          }
        />
        <Route
          path="tables"
          element={
            <ProtectedRoute roles={['WAITER', 'CHEF', 'MANAGER']}>
              <TablesPage />
            </ProtectedRoute>
          }
        />
        <Route
          path="users"
          element={
            <ProtectedRoute roles={['WAITER', 'CHEF', 'MANAGER']}>
              <UsersPage />
            </ProtectedRoute>
          }
        />
        <Route
          path="customer"
          element={
            <ProtectedRoute roles={['CUSTOMER']}>
              <CustomerHome />
            </ProtectedRoute>
          }
        />
      </Route>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}

export default App;
