import React from 'react';
import jollibee from '../assest/jollibee.png';

export default function NavBar({role, active = 'menu', onNavigate }) {
  const navigate = (e, page) => { //hàm navigate nhan hai tham số
    if (e && e.preventDefault) e.preventDefault(); //chan cuộn lên đầu trang
    if (typeof onNavigate === 'function') {
      onNavigate(page); //setState page = gtri page tuong ung voi event
      //update the URL hash so the path reflects current page
      if (typeof window !== 'undefined' && window.location) {
        window.location.hash = page;
      }
    } else if (typeof window !== 'undefined' && window.location) {
      window.location.hash = page;
    }
  };

  return (
    <header className="app-header admin-header">
      <div className="brand">
              <img src={jollibee} alt="Jollibee" className="logo" />
      </div>
      
      {role === 'customer' && (
        <nav className="main-nav" aria-label="Main navigation">
          <div className="nav-item">
              <a href="#menu" onClick={(e) => navigate(e, 'menu')} className={`nav-link ${active === 'menu' ? 'active' : ''}`}>MENU</a>
          </div>
          <div className="nav-item">
              <a href="#reservation" onClick={(e) => navigate(e, 'reservation')} className={`nav-link ${active === 'reservation' ? 'active' : ''}`}>Reservation</a>
          </div>
          <div className="nav-item">
              <a href="#my_order" onClick={(e) => navigate(e, 'my_order')} className={`nav-link ${active === 'my_order' ? 'active' : ''}`}>My order</a>
          </div>
        </nav>
      )}

      {role === 'admin' && (
        <nav className="main-nav" aria-label="Main navigation">
          <div className="nav-item">
              <a href="#menu" onClick={(e) => navigate(e, 'menu')} className={`nav-link ${active === 'menu' ? 'active' : ''}`}>MENU</a>
          </div>
          <div className="nav-item">
              <a href="#employee" onClick={(e) => navigate(e, 'employee')} className={`nav-link ${active === 'employee' ? 'active' : ''}`}>Employee</a>
          </div>
          <div className="nav-item">
              <a href="#table" onClick={(e) => navigate(e, 'table')} className={`nav-link ${active === 'table' ? 'active' : ''}`}>Table</a>
          </div>
          <div className="nav-item">
              <a href="#order" onClick={(e) => navigate(e, 'order')} className={`nav-link ${active === 'order' ? 'active' : ''}`}>Order</a>
          </div>
        </nav>
      )}
      
    </header>
    
  );
}
