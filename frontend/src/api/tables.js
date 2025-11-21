import { apiFetch } from './client.js';

export const tableApi = {
  list: () => apiFetch('/tables'),
  get: (id) => apiFetch(`/tables/${id}`),
  create: (payload) =>
    apiFetch('/tables', {
      method: 'POST',
      body: payload,
    }),
  update: (id, payload) =>
    apiFetch(`/tables/${id}`, {
      method: 'PUT',
      body: payload,
    }),
  updateStatus: (id, status) =>
    apiFetch(`/tables/${id}/status`, {
      method: 'PUT',
      body: { status },
    }),
  remove: (id) =>
    apiFetch(`/tables/${id}`, {
      method: 'DELETE',
    }),
};

