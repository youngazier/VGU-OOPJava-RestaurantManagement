import React from 'react';
import '../pages/Menu_page.css';
import MenuCard from '../components/MenuCard';
import jollibee from '../assest/jollibee.png';

const sampleMenu = [
  { title: 'Phở ', price: '₫60,000', description: 'Phở bò thơm ngon với nước dùng đậm đà.' },
  { title: 'Cơm Tấm', price: '₫55,000', description: 'Cơm tấm sườn bì chả truyền thống.' },
  { title: 'Bún Chả', price: '₫50,000', description: 'Bún chả Hà Nội với chả nướng và nước chấm.' },
  { title: 'Phở ', price: '₫60,000', description: 'Phở bò thơm ngon với nước dùng đậm đà.' },
  { title: 'Cơm Tấm', price: '₫55,000', description: 'Cơm tấm sườn bì chả truyền thống.' },
  
];

export default function Menu_page({role}) {
  return (
    <div className="mainpage">
      
      <main className="page-menu">

        <section className="menu" id="menu">
          <div className="menu-grid">
            {sampleMenu.map((m, i) => (
              <MenuCard role={role} key={i} title={m.title} price={m.price} description={m.description} img={jollibee} /> // sua img
            ))}
          </div>
        </section>
      </main>
    </div>
  );
}
