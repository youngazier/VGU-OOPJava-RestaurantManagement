import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext.jsx';
import { ROLES } from '../constants.js';

export const RegisterPage = () => {
  const { register, loading, error } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState({
    username: '',
    password: '',
    fullName: '',
    phone: '',
    role: 'CUSTOMER',
  });

  const handleChange = (evt) => {
    const { name, value } = evt.target;
    setForm((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (evt) => {
    evt.preventDefault();
    try {
      const res = await register(form);
      if (res?.success) navigate('/login');
    } catch {
      // handled by context
    }
  };

  return (
    <div className="auth-page">
      <form className="card auth-card" onSubmit={handleSubmit}>
        <h1>Register</h1>
        <label>
          Full name
          <input className="input" name="fullName" value={form.fullName} onChange={handleChange} required />
        </label>
        <label>
          Phone
          <input className="input" name="phone" value={form.phone} onChange={handleChange} />
        </label>
        <label>
          Username
          <input className="input" name="username" value={form.username} onChange={handleChange} required />
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
        <label>
          Role
          <select className="input" name="role" value={form.role} onChange={handleChange}>
            {ROLES.map((role) => (
              <option key={role} value={role}>
                {role}
              </option>
            ))}
          </select>
        </label>
        {error && <p className="text-error">{error}</p>}
        <button className="btn btn--primary" type="submit" disabled={loading}>
          {loading ? 'Submitting...' : 'Create account'}
        </button>
        <p>
          Already have an account? <Link to="/login">Login</Link>
        </p>
      </form>
    </div>
  );
};

