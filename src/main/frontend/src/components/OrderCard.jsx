import React, { useState } from 'react';
import './OrderCard.css';

export default function OrderCard({ title, price, description, img,note, quantity }) {
  return (
    <article className="order-card">
      <div className="media">
        {img ? (
          <img src={img} alt={title} className="order-img" />
        ) : (
          <div className="img-placeholder" aria-hidden />
        )}
      </div>
      <div className="content">
        <div className="content-header">
          <h3 className="order-title">{title}</h3>
          <div className="order-price">{price}</div>
        </div>
          
        <p className="order-desc">{description}</p>
        <div className='content-last'>
          <div className='order-note'>Note: {note}</div>
          <div className="order-quantity">Quantity: {quantity}</div>
        </div>
        
        
      </div>
    </article>
  );
}
