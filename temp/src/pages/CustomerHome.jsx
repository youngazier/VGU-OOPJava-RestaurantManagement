import { useEffect, useState } from 'react';
import { orderApi } from '../api/orders.js';
import { StatusBadge } from '../components/StatusBadge.jsx';

export const CustomerHome = () => {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const load = async () => {
      setLoading(true);
      setError(null);
      try {
        const data = await orderApi.listMine();
        setOrders(data);
      } catch (err) {
        setError(err.message || 'Unable to load your orders');
      } finally {
        setLoading(false);
      }
    };
    load();
  }, []);

  return (
    <section>
      <header className="page-header">
        <div>
          <p className="eyebrow">Welcome</p>
          <h1>My Orders</h1>
        </div>
      </header>

      <div className="card table-wrapper">
        {loading ? (
          <p>Loading...</p>
        ) : error ? (
          <p className="text-error">{error}</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Order</th>
                <th>Table</th>
                <th>Status</th>
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
                </tr>
              ))}
              {orders.length === 0 && (
                <tr>
                  <td colSpan={3}>You have no orders yet.</td>
                </tr>
              )}
            </tbody>
          </table>
        )}
      </div>
    </section>
  );
};
