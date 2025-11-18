import React from 'react';
import jollibee from '../assest/jollibee.png';
import NavBar from './NavBar';

export default function AdminHeader({ active = 'editmenu', onNavigate }) {
  return (
    <header className="app-header admin-header">
      <div className="brand">
        <img src={jollibee} alt="Jollibee" className="logo" />
      </div>
      {/* Admin may want a different active set — reuse NavBar for consistency */}
      
    </header>
  );
}
