import { useCallback, useEffect, useMemo, useState } from 'react';
import { orderApi } from '../api/orders.js';
import { menuApi } from '../api/menuItems.js';
import { ORDER_STATUSES } from '../constants.js';
import { StatusBadge } from '../components/StatusBadge.jsx';

const emptyOrderForm = {
    tableId: '',
    customerId: '',
    note: '',
    status: 'NEW',
    items: [],
};

export const OrdersPage = () => {
    const [orders, setOrders] = useState([]);
    const [menuItems, setMenuItems] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [statusFilter, setStatusFilter] = useState('ALL');
    const [form, setForm] = useState(emptyOrderForm);
    const [itemDraft, setItemDraft] = useState({
        menuItemId: '',
        quantity: 1,
        price: '',
        note: '',
    });
    const [itemForms, setItemForms] = useState({});

    const getExistingItemForm = (orderId) =>
        itemForms[orderId] ?? {
            menuItemId: '',
            quantity: 1,
            price: '',
            note: '',
        };

    const load = useCallback(
        async (filter = statusFilter) => {
            setLoading(true);
            setError(null);
            try {
                const statusValue = filter && filter !== 'ALL' ? filter : undefined;
                const [orderData, menuData] = await Promise.all([orderApi.list(statusValue), menuApi.list()]);
                setOrders(orderData);
                setMenuItems(menuData);
            } catch (err) {
                setError(err.message || 'Failed to load orders');
            } finally {
                setLoading(false);
            }
        },
        [statusFilter],
    );

    useEffect(() => {
        load(statusFilter);
    }, [load, statusFilter]);

    const handleFormChange = (evt) => {
        const { name, value } = evt.target;
        setForm((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleDraftChange = (evt) => {
        const { name, value } = evt.target;
        setItemDraft((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const addDraftItem = () => {
        if (!itemDraft.menuItemId) return;
        const menuItem = menuItems.find((m) => String(m.id) === String(itemDraft.menuItemId));
        const price = itemDraft.price || menuItem?.price || 0;
        setForm((prev) => ({
            ...prev,
            items: [
                ...prev.items,
                {
                    menuItemId: Number(itemDraft.menuItemId),
                    quantity: Number(itemDraft.quantity),
                    price: Number(price),
                    note: itemDraft.note,
                },
            ],
        }));
        setItemDraft({ menuItemId: '', quantity: 1, price: '', note: '' });
    };

    const removeDraftItem = (index) => {
        setForm((prev) => ({
            ...prev,
            items: prev.items.filter((_, i) => i !== index),
        }));
    };

    const handleExistingItemChange = (orderId, field, value) => {
        setItemForms((prev) => ({
            ...prev,
            [orderId]: {
                ...getExistingItemForm(orderId),
                [field]: value,
            },
        }));
    };

    const addItemToExistingOrder = async (orderId) => {
        const values = getExistingItemForm(orderId);
        if (!values.menuItemId) {
            alert('Select a menu item first');
            return;
        }
        const menuItem = menuItems.find((m) => String(m.id) === String(values.menuItemId));
        const price = values.price || menuItem?.price || 0;
        try {
            await orderApi.addItem(orderId, {
                orderId,
                menuItemId: Number(values.menuItemId),
                quantity: Number(values.quantity),
                price: Number(price),
                note: values.note,
            });
            setItemForms((prev) => {
                const next = { ...prev };
                delete next[orderId];
                return next;
            });
            load();
        } catch (err) {
            alert(err.message || 'Unable to add item');
        }
    };

    const updateExistingQuantity = async (orderItemId, quantity) => {
        if (quantity < 1) {
            if (confirm('Quantity is zero. Remove item instead?')) {
                removeOrderItem(orderItemId);
            }
            return;
        }
        try {
            await orderApi.updateItemQuantity(orderItemId, quantity);
            load();
        } catch (err) {
            alert(err.message || 'Unable to update quantity');
        }
    };

    const removeOrderItem = async (orderItemId) => {
        if (!confirm('Remove this item from the order?')) return;
        try {
            await orderApi.removeItem(orderItemId);
            load();
        } catch (err) {
            alert(err.message || 'Unable to remove item');
        }
    };

    const submitOrder = async (evt) => {
        evt.preventDefault();
        try {
            const payload = {
                ...form,
                tableId: Number(form.tableId),
                customerId: form.customerId ? Number(form.customerId) : null,
                items: form.items,
                status: form.status,
            };
            await orderApi.create(payload);
            setForm(emptyOrderForm);
            load();
        } catch (err) {
            alert(err.message || 'Unable to create order');
        }
    };

    const changeStatus = async (orderId, status) => {
        try {
            await orderApi.updateStatus(orderId, status);
            load();
        } catch (err) {
            alert(err.message || 'Unable to update status');
        }
    };

    const totalByOrder = useMemo(
        () =>
            orders.reduce((acc, order) => {
                const total = (order.items ?? []).reduce(
                    (sum, item) => sum + Number(item.price) * Number(item.quantity),
                    0,
                );
                acc[order.id] = total;
                return acc;
            }, {}),
        [orders],
    );

    return (
        <section>
            <header className="page-header">
                <div>
                    <p className="eyebrow">Service</p>
                    <h1>Orders</h1>
                </div>
                <label className="filter">
                    <span className="eyebrow">Status filter</span>
                    <select
                        className="input"
                        value={statusFilter}
                        onChange={(evt) => setStatusFilter(evt.target.value)}
                    >
                        {['ALL', ...ORDER_STATUSES].map((status) => (
                            <option key={status} value={status}>
                                {status.replaceAll('_', ' ')}
                            </option>
                        ))}
                    </select>
                </label>
            </header>

            <div className="grid two-columns">
                <form className="card" onSubmit={submitOrder}>
                    <h3>Create order</h3>
                    <div className="form-grid">
                        <label>
                            Table ID
                            <input
                                className="input"
                                name="tableId"
                                value={form.tableId}
                                onChange={handleFormChange}
                                required
                            />
                        </label>
                        <label>
                            Customer ID (optional)
                            <input
                                className="input"
                                name="customerId"
                                value={form.customerId}
                                onChange={handleFormChange}
                            />
                        </label>
                        <label>
                            Note
                            <textarea
                                className="input"
                                name="note"
                                rows={2}
                                value={form.note}
                                onChange={handleFormChange}
                            />
                        </label>
                        <label>
                            Status
                            <select className="input" name="status" value={form.status} onChange={handleFormChange}>
                                {ORDER_STATUSES.map((status) => (
                                    <option key={status} value={status}>
                                        {status.replaceAll('_', ' ')}
                                    </option>
                                ))}
                            </select>
                        </label>
                    </div>

                    <div className="card card--sub">
                        <h4>Items</h4>
                        <div className="form-grid three-columns">
                            <label>
                                Menu item
                                <select
                                    className="input"
                                    name="menuItemId"
                                    value={itemDraft.menuItemId}
                                    onChange={handleDraftChange}
                                >
                                    <option value="">Select item</option>
                                    {menuItems.map((item) => (
                                        <option key={item.id} value={item.id}>
                                            {item.name}
                                        </option>
                                    ))}
                                </select>
                            </label>
                            <label>
                                Quantity
                                <input
                                    className="input"
                                    type="number"
                                    name="quantity"
                                    min="1"
                                    value={itemDraft.quantity}
                                    onChange={handleDraftChange}
                                />
                            </label>
                            <label>
                                Price (override)
                                <input
                                    className="input"
                                    type="number"
                                    step="0.01"
                                    name="price"
                                    value={itemDraft.price}
                                    onChange={handleDraftChange}
                                />
                            </label>
                            <label className="full-width">
                                Note
                                <input
                                    className="input"
                                    name="note"
                                    value={itemDraft.note}
                                    onChange={handleDraftChange}
                                />
                            </label>
                        </div>
                        <button type="button" className="btn btn--ghost" onClick={addDraftItem}>
                            Add item
                        </button>

                        <ul className="list list--compact">
                            {form.items.map((item, index) => (
                                <li key={`${item.menuItemId}-${index}`}>
                                    Item #{item.menuItemId} · qty {item.quantity} @ ${item.price}
                                    <button type="button" className="link" onClick={() => removeDraftItem(index)}>
                                        remove
                                    </button>
                                </li>
                            ))}
                            {form.items.length === 0 && <p>No items yet.</p>}
                        </ul>
                    </div>

                    <button className="btn btn--primary" type="submit">
                        Submit order
                    </button>
                </form>

                <div className="card table-wrapper">
                    {loading ? (
                        <p>Loading orders...</p>
                    ) : error ? (
                        <p className="text-error">{error}</p>
                    ) : (
                        <>
                            <table>
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Table</th>
                                        <th>Status</th>
                                        <th>Total</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {orders.map((order) => (
                                        <tr key={order.id}>
                                            <td>{order.id}</td>
                                            <td>{order.tableId}</td>
                                            <td>
                                                <StatusBadge value={order.status} />
                                            </td>
                                            <td>${Number(totalByOrder[order.id] ?? 0).toFixed(2)}</td>
                                            <td>
                                                <select
                                                    className="input"
                                                    value={order.status}
                                                    onChange={(e) => changeStatus(order.id, e.target.value)}
                                                >
                                                    {ORDER_STATUSES.map((status) => (
                                                        <option key={status} value={status}>
                                                            {status.replaceAll('_', ' ')}
                                                        </option>
                                                    ))}
                                                </select>
                                            </td>
                                        </tr>
                                    ))}
                                    {orders.length === 0 && (
                                        <tr>
                                            <td colSpan={5}>No orders yet.</td>
                                        </tr>
                                    )}
                                </tbody>
                            </table>

                            {orders.length > 0 && (
                                <div className="stack">
                                    {orders.map((order) => {
                                        const formState = getExistingItemForm(order.id);
                                        return (
                                            <div className="card card--sub" key={`order-${order.id}`}>
                                                <div className="order-detail__header">
                                                    <div>
                                                        <strong>Order #{order.id}</strong>
                                                        <p>Table {order.tableId}</p>
                                                    </div>
                                                    <p>Total ${Number(totalByOrder[order.id] ?? 0).toFixed(2)}</p>
                                                </div>
                                                <table className="order-items-table">
                                                    <thead>
                                                        <tr>
                                                            <th>Item</th>
                                                            <th>Price</th>
                                                            <th>Quantity</th>
                                                            <th>Note</th>
                                                            <th />
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        {(order.items ?? []).map((item) => (
                                                            <tr key={item.id}>
                                                                <td>{item.menuItemId}</td>
                                                                <td>${Number(item.price).toFixed(2)}</td>
                                                                <td>
                                                                    <div className="qty-control">
                                                                        <button
                                                                            type="button"
                                                                            className="btn btn--ghost"
                                                                            onClick={() =>
                                                                                updateExistingQuantity(item.id, Number(item.quantity) - 1)
                                                                            }
                                                                        >
                                                                            −
                                                                        </button>
                                                                        <span>{item.quantity}</span>
                                                                        <button
                                                                            type="button"
                                                                            className="btn btn--ghost"
                                                                            onClick={() =>
                                                                                updateExistingQuantity(item.id, Number(item.quantity) + 1)
                                                                            }
                                                                        >
                                                                            +
                                                                        </button>
                                                                    </div>
                                                                </td>
                                                                <td>{item.note}</td>
                                                                <td>
                                                                    <button
                                                                        type="button"
                                                                        className="btn btn--danger"
                                                                        onClick={() => removeOrderItem(item.id)}
                                                                    >
                                                                        Remove
                                                                    </button>
                                                                </td>
                                                            </tr>
                                                        ))}
                                                        {(order.items ?? []).length === 0 && (
                                                            <tr>
                                                                <td colSpan={5}>No items yet.</td>
                                                            </tr>
                                                        )}
                                                    </tbody>
                                                </table>

                                                <div className="form-grid three-columns">
                                                    <label>
                                                        Menu item
                                                        <select
                                                            className="input"
                                                            value={formState.menuItemId}
                                                            onChange={(e) =>
                                                                handleExistingItemChange(order.id, 'menuItemId', e.target.value)
                                                            }
                                                        >
                                                            <option value="">Select</option>
                                                            {menuItems.map((item) => (
                                                                <option key={item.id} value={item.id}>
                                                                    {item.name}
                                                                </option>
                                                            ))}
                                                        </select>
                                                    </label>
                                                    <label>
                                                        Quantity
                                                        <input
                                                            className="input"
                                                            type="number"
                                                            min="1"
                                                            value={formState.quantity}
                                                            onChange={(e) =>
                                                                handleExistingItemChange(order.id, 'quantity', e.target.value)
                                                            }
                                                        />
                                                    </label>
                                                    <label>
                                                        Price
                                                        <input
                                                            className="input"
                                                            type="number"
                                                            step="0.01"
                                                            value={formState.price}
                                                            onChange={(e) =>
                                                                handleExistingItemChange(order.id, 'price', e.target.value)
                                                            }
                                                        />
                                                    </label>
                                                    <label className="full-width">
                                                        Note
                                                        <input
                                                            className="input"
                                                            value={formState.note}
                                                            onChange={(e) =>
                                                                handleExistingItemChange(order.id, 'note', e.target.value)
                                                            }
                                                        />
                                                    </label>
                                                </div>
                                                <button
                                                    type="button"
                                                    className="btn btn--ghost"
                                                    onClick={() => addItemToExistingOrder(order.id)}
                                                >
                                                    Add item to order #{order.id}
                                                </button>
                                            </div>
                                        );
                                    })}
                                </div>
                            )}
                        </>
                    )}
                </div>
            </div>
        </section>
    );
};

