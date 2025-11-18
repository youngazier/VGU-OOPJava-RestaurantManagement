import API_BASE_URL from '../config/api';

/**
 * Auth Service - Xử lý đăng ký và đăng nhập
 */

/**
 * Đăng ký user mới
 * @param {Object} userData - { username, password, fullName, phone, role }
 * @returns {Promise<Object>} Response từ server
 */
export const register = async (userData) => {
  try {
    const response = await fetch(`${API_BASE_URL}/api/users/register`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include', // Quan trọng: gửi cookie để duy trì session
      body: JSON.stringify(userData),
    });

    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Register error:', error);
    return {
      success: false,
      error: 'Network error. Please check if backend is running.',
    };
  }
};

/**
 * Đăng nhập
 * @param {string} username
 * @param {string} password
 * @returns {Promise<Object>} Response từ server chứa user info nếu thành công
 */
export const login = async (username, password) => {
  try {
    const response = await fetch(`${API_BASE_URL}/api/users/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include', // Quan trọng: nhận cookie session từ server
      body: JSON.stringify({ username, password }),
    });

    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Login error:', error);
    return {
      success: false,
      error: 'Network error. Please check if backend is running.',
    };
  }
};

