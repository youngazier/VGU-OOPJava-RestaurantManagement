import { useEffect, useState } from 'react';
import { authApi } from '../api/users.js';
import { ROLES } from '../constants.js';

export const UsersPage = () => {
  const [users, setUsers] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const load = async () => {
      setLoading(true);
      setError(null);
      try {
        const data = await authApi.list();
        setUsers(data);
      } catch (err) {
        setError(
          err.data?.error ||
            err.message ||
            'Failed to fetch users (login as non-customer role).',
        );
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
          <p className="eyebrow">Team</p>
          <h1>Users</h1>
        </div>
      </header>

      <div className="card table-wrapper">
        {loading ? (
          <p>Loading users...</p>
        ) : error ? (
          <p className="text-error">{error}</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Name</th>
                <th>Role</th>
                <th>Phone</th>
              </tr>
            </thead>
            <tbody>
              {users.map((user) => (
                <tr key={user.id}>
                  <td>{user.id}</td>
                  <td>{user.username}</td>
                  <td>{user.fullName}</td>
                  <td>{user.role}</td>
                  <td>{user.phone}</td>
                </tr>
              ))}
              {users.length === 0 && (
                <tr>
                  <td colSpan={5}>No users available.</td>
                </tr>
              )}
            </tbody>
          </table>
        )}
      </div>

      <section className="card">
        <h3>Roles supported</h3>
        <ul className="list list--compact">
          {ROLES.map((role) => (
            <li key={role}>{role}</li>
          ))}
        </ul>
      </section>
    </section>
  );
};

