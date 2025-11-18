import './EditTable_page.css'
import React, { useState } from 'react';

const initialTables = [
  { id: 1, status: 'reserved' },
  { id: 2, status: 'available' },
  { id: 3, status: 'available' },
  { id: 4, status: 'available' },
  { id: 5, status: 'reserved' },
];

export default function EditTable_page() {
  const [tables,setTables] = useState(initialTables);
  const [selected, setSelected] = useState(tables[0]);

  return (
    <div className="edit-table-page">
      <div className="tables-column">
        <div className="tables-grid">
          {tables.map((t) => (
            <button
              key={t.id}
              className={`table-box ${t.status} ${selected && selected.id === t.id ? 'active' : ''}`}
              onClick={() => setSelected(t)}
              aria-pressed={selected && selected.id === t.id}
            >
              <div className="table-number">{t.id}</div>
            </button>
          ))}
        </div>
      </div>

      <aside className="detail-column">
        <div className="detail-card">
          <h3 className="table-title">Table {selected?.id}</h3>
          <div className="status-row">
            <strong>Status:</strong>
            <span className={`status-pill ${selected?.status}`}> {selected?.status === 'available' ? 'Available' : 'Not Available'}</span>
          </div>
          <div className="field"><strong>Name:</strong> <span className="field-value">{selected?.name || ''}</span></div>

          <div className="actions">
            <button className="btn-outline">See Order</button>
            <button className="btn-outline">See Payment</button>
          </div>

          <div className="booking">
            <h4>Booking</h4>
            <div className="booking-row"><strong>Time:</strong> <span>—</span></div>
            <div className="booking-row"><strong>Name:</strong> <span>—</span></div>
            <div className="booking-row"><strong>Number of guest:</strong> <span>—</span></div>
            <div className="booking-row"><strong>Note:</strong> <span>—</span></div>
          </div>
        </div>
      </aside>
    </div>
  )
}