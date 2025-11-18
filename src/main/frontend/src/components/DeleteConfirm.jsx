import React from 'react';
import './DeleteConfirm.css';

export default function DeleteConfirm({ isOpen, onClose, onConfirm, item }) {
  if (!isOpen) return null;

  return (
    <div className="delete-overlay" role="dialog" aria-modal="true">
      <div className="delete-modal">
        <div className="delete-body">
          <h4>Are you sure you want to delete this item?</h4>
          {item && item.title && <div className="delete-item">"{item.title}"</div>}
        </div>
        <div className="delete-actions">
          <button className="btn btn-outline" onClick={onClose}>Cancel</button>
          <button
            className="btn btn-danger"
            onClick={() => {
              if (typeof onConfirm === 'function') onConfirm(item);
            }}
          >
            Delete
          </button>
        </div>
      </div>
    </div>
  );
}
