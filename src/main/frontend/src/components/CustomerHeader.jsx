import React from 'react';
import jollibee from '../assest/jollibee.png';
import NavBar from './NavBar';


export default function CustomerHeader({ active = 'menu', onNavigate }) {
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
}  

    return (
    <header className="app-header">
      <div className="brand">
        <img src={jollibee} alt="Jollibee" className="logo" />
      </div>
      <NavBar active={active} onNavigate={onNavigate} />
      
    </header>
  );
}
