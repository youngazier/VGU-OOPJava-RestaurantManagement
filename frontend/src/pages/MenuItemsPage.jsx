import { useEffect, useMemo, useState } from 'react';
import { menuApi } from '../api/menuItems.js';
import { useAuth } from '../context/AuthContext.jsx';

const APP_BASE = 'http://localhost:8080/RestaurantManagement-1.0-SNAPSHOT';
const IMAGE_BASE = `${APP_BASE}/images/`;

const resolveImageUrl = (rawUrl) => {
  if (!rawUrl) return '';
  const url = rawUrl.trim();
  if (!url) return '';
  if (/^https?:\/\//i.test(url)) return url;

  // Handle Windows-style absolute paths (e.g. D:\...images\pho-bo.jpg)
  const hasDriveLetter = /^[a-z]:\\/i.test(url);
  const normalized = hasDriveLetter ? url.split(/[/\\]/).pop() : url;

  // Server-relative path
  if (normalized.startsWith('/')) {
    return `${APP_BASE}${normalized}`;
  }

  // Already includes images/ prefix – ensure we drop duplicates
  const cleaned = normalized.replace(/^images[/\\]/i, '');

  return `${IMAGE_BASE}${cleaned}`;
};

const emptyForm = {
  name: '',
  description: '',
  imgUrl: '',
  cost: 0,
  price: 0,
  category: '',
  isAvailable: true,
};

export const MenuItemsPage = () => {
  const { user } = useAuth();
  const canManage = user?.role !== 'CUSTOMER';
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [form, setForm] = useState(emptyForm);
  const [editingId, setEditingId] = useState(null);
  const [search, setSearch] = useState('');
  const [categoryFilter, setCategoryFilter] = useState('ALL');
  const [statusFilter, setStatusFilter] = useState('ALL');

  const load = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await menuApi.list();
      setItems(data);
    } catch (err) {
      setError(err.message || 'Failed to load menu items');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    load();
  }, []);

  const categories = useMemo(() => {
    const unique = new Set(
      items
        .map((item) => item.category)
        .filter((cat) => typeof cat === 'string' && cat.trim().length > 0),
    );
    return Array.from(unique);
  }, [items]);

  const filteredItems = useMemo(() => {
    const term = search.trim().toLowerCase();
    return items.filter((item) => {
      const matchesSearch =
        !term ||
        item.name.toLowerCase().includes(term) ||
        item.description.toLowerCase().includes(term) ||
        (item.category ?? '').toLowerCase().includes(term);
      const matchesCategory =
        categoryFilter === 'ALL' || (item.category ?? '').toLowerCase() === categoryFilter.toLowerCase();
      const availability = item.isAvailable ?? item.available;
      const matchesStatus =
        statusFilter === 'ALL' ||
        (statusFilter === 'AVAILABLE' && availability) ||
        (statusFilter === 'HIDDEN' && !availability);
      return matchesSearch && matchesCategory && matchesStatus;
    });
  }, [items, search, categoryFilter, statusFilter]);

  const handleChange = (evt) => {
    const { name, value, type, checked } = evt.target;
    setForm((prev) => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value,
    }));
  };

  const handleSubmit = async (evt) => {
    evt.preventDefault();
    try {
      const payload = {
        ...form,
        cost: Number(form.cost),
        price: Number(form.price),
      };
      if (editingId) {
        await menuApi.update(editingId, payload);
      } else {
        await menuApi.create(payload);
      }
      setForm(emptyForm);
      setEditingId(null);
      load();
    } catch (err) {
      alert(err.message || 'Unable to save menu item');
    }
  };

  const handleEdit = (item) => {
    setEditingId(item.id);
    setForm({
      name: item.name,
      description: item.description,
      imgUrl: item.imgUrl,
      cost: item.cost,
      price: item.price,
      category: item.category,
      isAvailable: item.isAvailable ?? item.available ?? true,
    });
  };

  const handleDelete = async (id) => {
    if (!confirm('Delete this menu item?')) return;
    try {
      await menuApi.remove(id);
      load();
    } catch (err) {
      alert(err.message || 'Unable to delete menu item');
    }
  };

  return (
    <section>
      <header className="page-header">
        <div>
          <p className="eyebrow">Kitchen</p>
          <h1>Menu items</h1>
        </div>
        <div className="filters">
          <input
            className="input"
            type="search"
            placeholder="Search menu"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />
          <select className="input" value={categoryFilter} onChange={(e) => setCategoryFilter(e.target.value)}>
            <option value="ALL">All categories</option>
            {categories.map((cat) => (
              <option key={cat} value={cat}>
                {cat}
              </option>
            ))}
          </select>
          <select className="input" value={statusFilter} onChange={(e) => setStatusFilter(e.target.value)}>
            <option value="ALL">All statuses</option>
            <option value="AVAILABLE">Available</option>
            <option value="HIDDEN">Hidden</option>
          </select>
        </div>
      </header>

      <div className={canManage ? 'grid two-columns' : ''}>
        {canManage && (
          <form className="card" onSubmit={handleSubmit}>
          <h3>{editingId ? 'Edit menu item' : 'Add new menu item'}</h3>
          <div className="form-grid">
            <label>
              Name
              <input
                className="input"
                name="name"
                value={form.name}
                onChange={handleChange}
                required
              />
            </label>
            <label>
              Category
              <input
                className="input"
                name="category"
                value={form.category}
                onChange={handleChange}
              />
            </label>
            <label>
              Description
              <textarea
                className="input"
                name="description"
                rows={3}
                value={form.description}
                onChange={handleChange}
              />
            </label>
            <label>
              Image URL
              <input
                className="input"
                name="imgUrl"
                value={form.imgUrl}
                onChange={handleChange}
              />
            </label>
            <label>
              Cost
              <input
                className="input"
                type="number"
                step="0.01"
                min="0"
                name="cost"
                value={form.cost}
                onChange={handleChange}
              />
            </label>
            <label>
              Price
              <input
                className="input"
                type="number"
                step="0.01"
                min="0"
                name="price"
                value={form.price}
                onChange={handleChange}
              />
            </label>
            <label className="checkbox">
              <input
                type="checkbox"
                name="isAvailable"
                checked={form.isAvailable}
                onChange={handleChange}
              />
              Available
            </label>
          </div>
          <div className="actions">
            {editingId && (
              <button
                type="button"
                className="btn btn--ghost"
                onClick={() => {
                  setEditingId(null);
                  setForm(emptyForm);
                }}
              >
                Cancel
              </button>
            )}
            <button className="btn btn--primary" type="submit">
              {editingId ? 'Update item' : 'Create item'}
            </button>
          </div>
          </form>
        )}

        <div className="card table-wrapper">
          {loading ? (
            <p>Loading menu...</p>
          ) : error ? (
            <p className="text-error">{error}</p>
          ) : (
            <table>
              <thead>
                <tr>
                  <th>Photo</th>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Category</th>
                  <th>Price</th>
                  <th>Status</th>
                  {canManage && <th />}
                </tr>
              </thead>
              <tbody>
                {filteredItems.map((item) => (
                  <tr key={item.id}>
                    <td>
                      {item.imgUrl ? (
                        <div className="menu-thumb">
                          <img src={resolveImageUrl(item.imgUrl)} alt={item.name} loading="lazy" />
                        </div>
                      ) : (
                        <div className="menu-thumb menu-thumb--placeholder">
                          <span>{item.name?.[0] ?? '?'}</span>
                        </div>
                      )}
                    </td>
                    <td>{item.id}</td>
                    <td>{item.name}</td>
                    <td>{item.category}</td>
                    <td>${Number(item.price).toFixed(2)}</td>
                    <td>{(item.isAvailable ?? item.available) ? 'Available' : 'Hidden'}</td>
                    {canManage ? (
                      <td className="table-actions">
                        <button className="btn btn--ghost" type="button" onClick={() => handleEdit(item)}>
                          Edit
                        </button>
                        <button
                          className="btn btn--danger"
                          type="button"
                          onClick={() => handleDelete(item.id)}
                        >
                          Delete
                        </button>
                      </td>
                    ) : null}
                  </tr>
                ))}
                {filteredItems.length === 0 && (
                  <tr>
                    <td colSpan={canManage ? 6 : 5}>No menu items found.</td>
                  </tr>
                )}
              </tbody>
            </table>
          )}
        </div>
      </div>
    </section>
  );
};

