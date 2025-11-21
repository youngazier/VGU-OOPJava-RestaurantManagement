import { apiFetch } from './client.js';

export const menuApi = {
  list: () => apiFetch('/menu-items'),
  get: (id) => apiFetch(`/menu-items/${id}`),
  create: (item) =>
    apiFetch('/menu-items', {
      method: 'POST',
      body: item,
    }),
  update: (id, item) =>
    apiFetch(`/menu-items/${id}`, {
      method: 'PUT',
      body: item,
    }),
  remove: (id) =>
    apiFetch(`/menu-items/${id}`, {
      method: 'DELETE',
    }),
};

