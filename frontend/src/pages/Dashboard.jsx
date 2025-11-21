import { useEffect, useState } from 'react';
import { menuApi } from '../api/menuItems.js';
import { orderApi } from '../api/orders.js';
import { tableApi } from '../api/tables.js';
import { StatusBadge } from '../components/StatusBadge.jsx';

const initialState = {
  menu: [],
  orders: [],
  tables: [],
};

export const Dashboard = () => {
  const [data, setData] = useState(initialState);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const load = async () => {
      setLoading(true);
      setError(null);
      try {
        const [menu, orders, tables] = await Promise.all([
          menuApi.list(),
          orderApi.list(),
          tableApi.list(),
        ]);
        setData({ menu, orders, tables });
      } catch (err) {
        setError(err.message || 'Failed to load dashboard');
      } finally {
        setLoading(false);
      }
    };
    load();
  }, []);

  if (loading) {
    return <p>Loading dashboard...</p>;
  }

  if (error) {
    return <p className="text-error">{error}</p>;
  }

  const openOrders = data.orders.filter(
    (o) => o.status !== 'COMPLETED' && o.status !== 'CANCELLED',
  );
  const availableTables = data.tables.filter((t) => t.status === 'AVAILABLE');

  return (
    <section>
      <header className="page-header">
        <div>
          <p className="eyebrow">Overview</p>
          <h1>Restaurant Dashboard</h1>
        </div>
      </header>

      <div className="grid grid--stats">
        <article className="card">
          <p className="eyebrow">Menu items</p>
          <h2>{data.menu.length}</h2>
          <p>{data.menu.filter((item) => item.available || item.isAvailable).length} available</p>
        </article>

        <article className="card">
          <p className="eyebrow">Active orders</p>
          <h2>{openOrders.length}</h2>
          <p>Out of {data.orders.length} total</p>
        </article>

        <article className="card">
          <p className="eyebrow">Available tables</p>
          <h2>{availableTables.length}</h2>
          <p>{data.tables.length} total tables</p>
        </article>
      </div>

      <div className="grid two-columns">
        <section className="card">
          <h3>Latest orders</h3>
          <ul className="list">
            {data.orders.slice(0, 5).map((order) => (
              <li key={order.id}>
                <div>
                  <strong>Order #{order.id}</strong>
                  <p>Table {order.tableId}</p>
                </div>
                <StatusBadge value={order.status} />
              </li>
            ))}
            {data.orders.length === 0 && <p>No orders yet.</p>}
          </ul>
        </section>

        <section className="card">
          <h3>Tables</h3>
          <ul className="list">
            {data.tables.slice(0, 5).map((table) => (
              <li key={table.id}>
                <div>
                  <strong>Table #{table.id}</strong>
                  <p>Capacity: {table.capacity}</p>
                </div>
                <StatusBadge value={table.status} />
              </li>
            ))}
            {data.tables.length === 0 && <p>No tables configured.</p>}
          </ul>
        </section>
      </div>
    </section>
  );
};

