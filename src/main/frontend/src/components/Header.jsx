import React from 'react';
import './Header.css';
import AdminHeader from './AdminHeader';
import CustomerHeader from './CustomerHeader';
import NavBar from './NavBar';

export default function Header({ role, active, onNavigate }) { // role-aware header
  // role: 'admin' | 'customer' (default 'customer')
  return (
  <NavBar role={role} active={active} onNavigate={onNavigate} />
  )
}

export { AdminHeader, CustomerHeader };
