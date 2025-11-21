import { apiFetch } from './client.js';

export const authApi = {
  register: (payload) =>
    apiFetch('/users/register', {
      method: 'POST',
      body: payload,
    }),
  login: (payload) =>
    apiFetch('/users/login', {
      method: 'POST',
      body: payload,
    }),
  list: () => apiFetch('/users'),
};

