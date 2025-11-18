import React, { useEffect, useState } from 'react';
import './EditModal.css';

export default function EditModal({ isOpen, onClose, item = {}, onSave }) {
  const [form, setForm] = useState({
    title: '',
    price: '',
    category: '',
    imageFile: null,
  });

  useEffect(() => {
    if (isOpen) {
      setForm({
        title: item.title || '',
        price: item.price || '',
        category: item.category || '',
        imageFile: null,
      });
    }
  }, [isOpen, item]);

  if (!isOpen) return null;

  const handleChange = (e) => {
    const { name, value, files } = e.target;
    if (name === 'imageFile') {
      setForm((s) => ({ ...s, imageFile: files && files[0] }));
    } else {
      setForm((s) => ({ ...s, [name]: value }));
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const out = {
      ...item,
      title: form.title,
      price: form.price,
      category: form.category,
      imageFile: form.imageFile,
    };
    if (typeof onSave === 'function') onSave(out);
    onClose();
  };

  return (
    <div className="modal-overlay" role="dialog" aria-modal="true">
      <div className="modal">
        <header className="modal-header">
          <h3>Edit item</h3>
        </header>
        <form className="modal-body" onSubmit={handleSubmit}>
          <label>
            Item name
            <input name="title" value={form.title} onChange={handleChange} />
          </label>

          <label>
            Price
            <input name="price" value={form.price} onChange={handleChange} />
          </label>

          <label>
            Category
            <input name="category" value={form.category} onChange={handleChange} />
          </label>

          <label className="file-row">
            Image
            <input name="imageFile" type="file" accept="image/*" onChange={handleChange} />
          </label>

          <div className="modal-actions">
            <button type="submit" className="btn btn-primary">Edit</button>
            <button type="button" className="btn btn-outline" onClick={onClose}>Cancel</button>
          </div>
        </form>
      </div>
    </div>
  );
}
