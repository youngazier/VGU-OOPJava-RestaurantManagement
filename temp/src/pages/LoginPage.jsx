import { useState } from 'react';
import { useLocation, useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext.jsx';

export const LoginPage = () => {
  const { login, loading, error } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const [form, setForm] = useState({ username: '', password: '' });

  const handleChange = (evt) => {
    const { name, value } = evt.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (evt) => {
    evt.preventDefault();
    try {
      const userData = await login(form);
      const fallback = userData.role === 'CUSTOMER' ? '/customer' : '/';
      const redirectTo = location.state?.from?.pathname ?? fallback;
      navigate(redirectTo, { replace: true });
    } catch (err) {
      // handled by context
    }
  };

  return (
    <div className="auth-page">
      <form className="card auth-card" onSubmit={handleSubmit}>
        <h1>Login</h1>
        <label>
          Username
          <input
            className="input"
            name="username"
            value={form.username}
            onChange={handleChange}
            required
          />
        </label>
        <label>
          Password
          <input
            className="input"
            type="password"
            name="password"
            value={form.password}
            onChange={handleChange}
            required
          />
        </label>
        {error && <p className="text-error">{error}</p>}
        <button className="btn btn--primary" type="submit" disabled={loading}>
          {loading ? 'Signing in...' : 'Login'}
        </button>
        <p>
          Need an account? <Link to="/register">Register</Link>
        </p>
      </form>
    </div>
  );
};

