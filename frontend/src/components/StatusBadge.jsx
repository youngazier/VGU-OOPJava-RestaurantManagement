const COLORS = {
  NEW: 'status--info',
  PENDING: 'status--warning',
  IN_PROGRESS: 'status--warning',
  COMPLETED: 'status--success',
  CANCELLED: 'status--danger',
  AVAILABLE: 'status--success',
  OCCUPIED: 'status--warning',
  NOT_FOUND: 'status--muted',
};

export const StatusBadge = ({ value }) => {
  if (!value) return null;
  const className = `status-badge ${COLORS[value] ?? 'status--muted'}`;
  return <span className={className}>{value.replaceAll('_', ' ')}</span>;
};

