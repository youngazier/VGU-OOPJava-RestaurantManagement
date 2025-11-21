const API_PREFIX = 'http://localhost:8080/RestaurantManagement-1.0-SNAPSHOT/api';

const defaultHeaders = {
  'Content-Type': 'application/json',
};

export async function apiFetch(path, options = {}) {
  const {
    method = 'GET',
    headers = {},
    body,
    signal,
    credentials = 'include',
  } = options;

  const url = path.startsWith('http')
    ? path
    : `${API_PREFIX}${path.startsWith('/') ? path : `/${path}`}`;

  const config = {
    method,
    headers: { ...defaultHeaders, ...headers },
    credentials,
    signal,
  };

  if (body !== undefined && body !== null) {
    config.body = typeof body === 'string' ? body : JSON.stringify(body);
  }

  const resp = await fetch(url, config);
  const text = await resp.text();
  let data = null;

  if (text) {
    try {
      data = JSON.parse(text);
    } catch {
      data = text;
    }
  }

  if (!resp.ok) {
    const error = new Error(data?.error || 'Request failed');
    error.status = resp.status;
    error.data = data;
    throw error;
  }

  return data;
}

