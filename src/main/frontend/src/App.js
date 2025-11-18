import React, { useState } from 'react';
import './App.css';
import Header from './components/Header';
import Login_page from './pages/Login_file';
import Menu_page from './pages/Menu_page';
import Reservation_page from './pages/Reservation_page';
import Myorder_page from './pages/myorder_page';
import EditEmployee_page from './pages/Admin/EditEmployee_page'
import EditTable_page from './pages/Admin/EditTable_page'

function App() {
  // User state: null khi chưa login, object chứa user info khi đã login
  const [user, setUser] = useState(null);
  const [page, setPage] = useState('login');

  // Map backend role (CUSTOMER, WAITER, etc.) to frontend role ('customer' or 'admin')
  const getFrontendRole = (backendRole) => {
    if (!backendRole) return 'customer';
    // CUSTOMER -> 'customer', các role khác -> 'admin'
    return backendRole === 'CUSTOMER' ? 'customer' : 'admin';
  };

  // Handle login success
  const handleLoginSuccess = (userData) => {
    setUser(userData);
    // Điều hướng dựa trên role
    const frontendRole = getFrontendRole(userData.role);
    if (frontendRole === 'customer') {
      setPage('menu');
    } else {
      setPage('menu'); // hoặc 'employee' nếu muốn admin vào trang employee ngay
    }
  };

  // Handle logout (có thể thêm button logout sau)
  const handleLogout = () => {
    setUser(null);
    setPage('login');
    // TODO: Gọi API logout nếu có
  };

  // Lấy frontend role từ user
  const role = user ? getFrontendRole(user.role) : 'customer';

  // Render page component
  let PageComponent = null;
  if (page === 'login') {
    PageComponent = <Login_page onLoginSuccess={handleLoginSuccess} />;
  } else if (!user) {
    // Nếu chưa login nhưng không ở trang login, redirect về login
    PageComponent = <Login_page onLoginSuccess={handleLoginSuccess} />;
  } else if (page === 'menu') {
    PageComponent = <Menu_page role={role} user={user} />;
  } else if (page === 'reservation') {
    PageComponent = <Reservation_page user={user} />;
  } else if (page === 'my_order') {
    PageComponent = <Myorder_page user={user} />;
  } else if (page === 'employee') {
    PageComponent = <EditEmployee_page user={user} />;
  } else if (page === 'table') {
    PageComponent = <EditTable_page user={user} />;
  }

  return (
    <div className="App">
      {/* Chỉ hiện Header khi đã login */}
      {user && <Header role={role} active={page} onNavigate={setPage} />}
      {PageComponent}
    </div>
  );
}

export default App;
