import { useEffect, useState } from 'react';
import { tableApi } from '../api/tables.js';
import { TABLE_STATUSES } from '../constants.js';
import { StatusBadge } from '../components/StatusBadge.jsx';

const emptyForm = {
    capacity: 2,
    status: 'AVAILABLE',
};

export const TablesPage = () => {
    const [tables, setTables] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [form, setForm] = useState(emptyForm);
    const [editingId, setEditingId] = useState(null);

    const load = async () => {
        setLoading(true);
        setError(null);
        try {
            const data = await tableApi.list();
            setTables(data);
        } catch (err) {
            setError(err.message || 'Failed to load tables');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        load();
    }, []);

    const handleChange = (evt) => {
        const { name, value } = evt.target;
        setForm((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleSubmit = async (evt) => {
        evt.preventDefault();
        const payload = {
            capacity: Number(form.capacity),
            status: form.status,
        };
        try {
            if (editingId) {
                await tableApi.update(editingId, payload);
            } else {
                await tableApi.create(payload);
            }
            setForm(emptyForm);
            setEditingId(null);
            load();
        } catch (err) {
            alert(err.message || 'Unable to save table');
        }
    };

    const handleEdit = (table) => {
        setEditingId(table.id);
        setForm({
            capacity: table.capacity,
            status: table.status,
        });
    };

    const handleDelete = async (id) => {
        if (!confirm('Delete this table?')) return;
        try {
            await tableApi.remove(id);
            load();
        } catch (err) {
            alert(err.message || 'Unable to delete table');
        }
    };

    const handleStatusUpdate = async (table, status) => {
        try {
            await tableApi.updateStatus(table.id, status);
            load();
        } catch (err) {
            alert(err.message || 'Unable to update status');
        }
    };

    return (
        <section>
            <header className="page-header">
                <div>
                    <p className="eyebrow">Floor</p>
                    <h1>Dining tables</h1>
                </div>
            </header>

            <div className="grid two-columns">
                <form className="card" onSubmit={handleSubmit}>
                    <h3>{editingId ? 'Edit table' : 'Create table'}</h3>
                    <label>
                        Capacity
                        <input
                            className="input"
                            type="number"
                            min="1"
                            name="capacity"
                            value={form.capacity}
                            onChange={handleChange}
                        />
                    </label>
                    <label>
                        Status
                        <select className="input" name="status" value={form.status} onChange={handleChange}>
                            {TABLE_STATUSES.map((status) => (
                                <option key={status} value={status}>
                                    {status}
                                </option>
                            ))}
                        </select>
                    </label>
                    <div className="actions">
                        {editingId && (
                            <button
                                className="btn btn--ghost"
                                type="button"
                                onClick={() => {
                                    setEditingId(null);
                                    setForm(emptyForm);
                                }}
                            >
                                Cancel
                            </button>
                        )}
                        <button className="btn btn--primary" type="submit">
                            {editingId ? 'Update table' : 'Add table'}
                        </button>
                    </div>
                </form>

                <div className="card table-wrapper">
                    {loading ? (
                        <p>Loading tables...</p>
                    ) : error ? (
                        <p className="text-error">{error}</p>
                    ) : (
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Capacity</th>
                                    <th>Status</th>
                                    <th />
                                </tr>
                            </thead>
                            <tbody>
                                {tables.map((table) => (
                                    <tr key={table.id}>
                                        <td>{table.id}</td>
                                        <td>{table.capacity}</td>
                                        <td>
                                            <select
                                                className="input"
                                                value={table.status}
                                                onChange={(e) => handleStatusUpdate(table, e.target.value)}
                                            >
                                                {TABLE_STATUSES.map((status) => (
                                                    <option key={status} value={status}>
                                                        {status}
                                                    </option>
                                                ))}
                                            </select>
                                        </td>
                                        <td className="table-actions">
                                            <button className="btn btn--ghost" type="button" onClick={() => handleEdit(table)}>
                                                Edit
                                            </button>
                                            <button
                                                className="btn btn--danger"
                                                type="button"
                                                onClick={() => handleDelete(table.id)}
                                            >
                                                Delete
                                            </button>
                                        </td>
                                    </tr>
                                ))}
                                {tables.length === 0 && (
                                    <tr>
                                        <td colSpan={4}>No tables found.</td>
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

