import { NavLink, Outlet } from 'react-router-dom';
import { useAuth } from '../context/AuthContext.jsx';

const staffNavLinks = [
  { to: '/', label: 'Dashboard', end: true },
  { to: '/menu', label: 'Menu' },
  { to: '/orders', label: 'Orders' },
  { to: '/tables', label: 'Tables' },
  { to: '/users', label: 'Users' },
];

const customerNavLinks = [
  { to: '/customer', label: 'My reservations', end: true },
  { to: '/menu', label: 'Menu' },
];

export const Layout = () => {
  const { user, logout } = useAuth();
  const isCustomer = user?.role === 'CUSTOMER';
  const navLinks = isCustomer ? customerNavLinks : staffNavLinks;

  return (
    <div className="app-layout">
      <aside className="sidebar">
        <div className="brand">{isCustomer ? 'Restaurant Portal' : 'Restaurant Admin'}</div>

        {user ? (
          <>
            <p className="sidebar__greeting">
              Signed in as <strong>{user.fullName}</strong>
            </p>
            <nav>
              {navLinks.map((link) => (
                <NavLink
                  key={link.to}
                  to={link.to}
                  end={link.end}
                  className={({ isActive }) =>
                    `nav-link${isActive ? ' nav-link--active' : ''}`
                  }
                >
                  {link.label}
                </NavLink>
              ))}
            </nav>
            <button type="button" className="btn btn--secondary" onClick={logout}>
              Logout
            </button>
          </>
        ) : (
          <div className="sidebar__unauth">
            <p>Please login to manage the restaurant.</p>
            <NavLink to="/login" className="btn btn--primary">
              Go to login
            </NavLink>
          </div>
        )}
      </aside>
      <main className="content">
        <Outlet />
      </main>
    </div>
  );
};

