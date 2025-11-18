import React from 'react';
import '../pages/myorder_page.css';
import OrderCard from '../components/OrderCard';
import jollibee from '../assest/jollibee.png'
const sampleOrder = [
  { title: 'Phở ', price: '₫60,000', description: 'Phở bò thơm ngon với nước dùng đậm đà.',quantity: 3,note:"khong hanh" },
  { title: 'Cơm Tấm', price: '₫55,000', description: 'Cơm tấm sườn bì chả truyền thống.' ,quantity: 1},
  { title: 'Bún Chả', price: '₫50,000', description: 'Bún chả Hà Nội với chả nướng và nước chấm.',quantity: 1 },
  { title: 'Phở ', price: '₫60,000', description: 'Phở bò thơm ngon với nước dùng đậm đà.',quantity: 4 },
  { title: 'Cơm Tấm', price: '₫55,000', description: 'Cơm tấm sườn bì chả truyền thống.' ,quantity: 1},
  
];

export default function Myorder_page() {

  return (
    <div className="mainpage">
          
          <main className="page-menu">
            <section className="order" id="order">
              <div className="order-grid">
                {sampleOrder.map((m, i) => (
                  <OrderCard key={i} title={m.title} price={m.price} description={m.description} img={jollibee} note={m.note} quantity={m.quantity} /> // sua img
                ))}
              </div>
              <button className="pay_button">Pay now</button>

            </section>
          </main>
        </div>
    
  );
}
