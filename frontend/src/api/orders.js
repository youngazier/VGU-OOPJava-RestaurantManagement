import { apiFetch } from './client.js';

export const orderApi = {
    list: (status) =>
        apiFetch(status ? `/orders?status=${encodeURIComponent(status)}` : '/orders'),
    listMine: () => apiFetch('/orders?mine=true'),
    get: (id) => apiFetch(`/orders/${id}`),
    create: (order) =>
        apiFetch('/orders', {
            method: 'POST',
            body: order,
        }),
    addItem: (orderId, item) =>
        apiFetch(`/orders/${orderId}/items`, {
            method: 'POST',
            body: { ...item, orderId },
        }),
    updateItemQuantity: (orderItemId, quantity) =>
        apiFetch(`/orders/${orderItemId}/quantity`, {
            method: 'PUT',
            body: { quantity },
        }),
    updateStatus: (orderId, status) =>
        apiFetch(`/orders/${orderId}/status`, {
            method: 'PUT',
            body: { status },
        }),
    removeItem: (orderItemId) =>
        apiFetch(`/orders/items/${orderItemId}`, {
            method: 'DELETE',
        }),
};

