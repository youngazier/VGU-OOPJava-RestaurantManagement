import './Login_page.css'
import React, { useState } from 'react';
import { login, register } from '../services/authService';

export default function Login_page({ onLoginSuccess }) {
  const [isRegister, setIsRegister] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  // Login form state
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  // Register form state
  const [regUsername, setRegUsername] = useState('');
  const [regPassword, setRegPassword] = useState('');
  const [regFullName, setRegFullName] = useState('');
  const [regPhone, setRegPhone] = useState('');
  const [regRole, setRegRole] = useState('CUSTOMER');

  const handleLogin = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    setLoading(true);

    try {
      const result = await login(username, password);
      
      if (result.success) {
        setSuccess('Login successful!');
        // Gọi callback với thông tin user
        if (onLoginSuccess) {
          onLoginSuccess({
            id: result.id,
            username: result.username,
            fullName: result.fullName,
            role: result.role,
          });
        }
      } else {
        setError(result.error || 'Login failed');
      }
    } catch (err) {
      setError('An error occurred. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    setLoading(true);

    try {
      const result = await register({
        username: regUsername,
        password: regPassword,
        fullName: regFullName,
        phone: regPhone,
        role: regRole,
      });

      if (result.success) {
        setSuccess('Registration successful! You can now login.');
        // Clear form
        setRegUsername('');
        setRegPassword('');
        setRegFullName('');
        setRegPhone('');
        setRegRole('CUSTOMER');
        // Switch to login after 2 seconds
        setTimeout(() => {
          setIsRegister(false);
          setSuccess('');
        }, 2000);
      } else {
        setError(result.message || result.error || 'Registration failed');
      }
    } catch (err) {
      setError('An error occurred. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-page">
      <main className="login-main">
        <div className="login-card">
          {/* Toggle buttons */}
          <div className="auth-tabs">
            <button
              type="button"
              className={`tab-btn ${!isRegister ? 'active' : ''}`}
              onClick={() => {
                setIsRegister(false);
                setError('');
                setSuccess('');
              }}
            >
              Login
            </button>
            <button
              type="button"
              className={`tab-btn ${isRegister ? 'active' : ''}`}
              onClick={() => {
                setIsRegister(true);
                setError('');
                setSuccess('');
              }}
            >
              Register
            </button>
          </div>

          {/* Error/Success messages */}
          {error && <div className="message error">{error}</div>}
          {success && <div className="message success">{success}</div>}

          {/* Login Form */}
          {!isRegister && (
            <form onSubmit={handleLogin}>
              <h2 className="login-title">Login</h2>

              <label className="field">
                <span className="label">Username</span>
                <input
                  type="text"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                  placeholder="Enter your username"
                  required
                  disabled={loading}
                />
              </label>

              <label className="field">
                <span className="label">Password</span>
                <input
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  placeholder="Enter your password"
                  required
                  disabled={loading}
                />
              </label>

              <div className="actions">
                <button type="submit" className="btn login-btn" disabled={loading}>
                  {loading ? 'Logging in...' : 'Login'}
                </button>
              </div>
            </form>
          )}

          {/* Register Form */}
          {isRegister && (
            <form onSubmit={handleRegister}>
              <h2 className="login-title">Register</h2>

              <label className="field">
                <span className="label">Username</span>
                <input
                  type="text"
                  value={regUsername}
                  onChange={(e) => setRegUsername(e.target.value)}
                  placeholder="Choose a username"
                  required
                  disabled={loading}
                />
              </label>

              <label className="field">
                <span className="label">Password</span>
                <input
                  type="password"
                  value={regPassword}
                  onChange={(e) => setRegPassword(e.target.value)}
                  placeholder="Choose a password"
                  required
                  disabled={loading}
                />
              </label>

              <label className="field">
                <span className="label">Full Name</span>
                <input
                  type="text"
                  value={regFullName}
                  onChange={(e) => setRegFullName(e.target.value)}
                  placeholder="Enter your full name"
                  required
                  disabled={loading}
                />
              </label>

              <label className="field">
                <span className="label">Phone</span>
                <input
                  type="tel"
                  value={regPhone}
                  onChange={(e) => setRegPhone(e.target.value)}
                  placeholder="Enter your phone number"
                  required
                  disabled={loading}
                />
              </label>

              <label className="field">
                <span className="label">Role</span>
                <select
                  value={regRole}
                  onChange={(e) => setRegRole(e.target.value)}
                  disabled={loading}
                  className="field"
                >
                  <option value="CUSTOMER">Customer</option>
                  <option value="WAITER">Waiter</option>
                  <option value="CHEF">Chef</option>
                  <option value="MANAGER">Manager</option>
                </select>
              </label>

              <div className="actions">
                <button type="submit" className="btn login-btn" disabled={loading}>
                  {loading ? 'Registering...' : 'Register'}
                </button>
              </div>
            </form>
          )}
        </div>
      </main>
    </div>
  );
}