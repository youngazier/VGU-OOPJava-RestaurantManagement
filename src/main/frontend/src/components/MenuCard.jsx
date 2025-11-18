import React, { useState } from 'react';
import './MenuCard.css';
import EditModal from './EditModal';
import DeleteConfirm from './DeleteConfirm';

export default function MenuCard({ role,title, price, description, img }) {
  const [isEditOpen, setIsEditOpen] = useState(false);
  const [isDeleteOpen, setIsDeleteOpen] = useState(false);
  const [itemForEdit] = useState({ title, price, description });
  return (
    <article className="menu-card">
      <div className="media">
        {img ? (
          <img src={img} alt={title} className="menu-img" />
        ) : (
          <div className="img-placeholder" aria-hidden />
        )}
      </div>
      <div className="content">
        <h3 className="menu-title">{title}</h3>
        <p className="menu-desc">{description}</p>
        <div className="menu-price">{price}</div>
        {role === 'customer' && (
          <button className="cart_button">Add to cart</button>
        )}
        {role === 'admin' && (
          <div>
            <button className="edit_button" onClick={() => setIsEditOpen(true)}>Edit</button>
            <button className="delete_button" onClick={() => setIsDeleteOpen(true)}>Delete</button>
          </div>
        )}
        
        <EditModal
          isOpen={isEditOpen}
          onClose={() => setIsEditOpen(false)}
          item={itemForEdit}
          onSave={(updated) => {
            // For now just log — you can wire this to update your data/store
            // Replace with an API call or parent callback to persist changes
            // Example: props.onEdit && props.onEdit(updated)
            // eslint-disable-next-line no-console
            console.log('Edited item:', updated);
          }}
        />
        <DeleteConfirm
          isOpen={isDeleteOpen}
          onClose={() => setIsDeleteOpen(false)}
          item={itemForEdit}
          onConfirm={(it) => {
            // call parent handler if provided; otherwise log
            // You can pass an `onDelete` prop to MenuCard to perform actual deletion
            // eslint-disable-next-line no-console
            console.log('Delete confirmed for', it);
            setIsDeleteOpen(false);
          }}
        />
        
      </div>
    </article>
  );
}
