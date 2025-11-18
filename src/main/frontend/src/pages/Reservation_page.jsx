import React from 'react';
import Header from '../components/Header';
import '../pages/Reservation_page.css';

export default function Reservation_page() {
  return (
    <div className="page-reservation">
      <h1 className="heading">Reservation A Table</h1>

      <div className="reservation-grid">

        <div className="field card">
          <label className="field-label">Adult</label>
          <div className="field-control">
            <input className="number-input" type="number" min={1} max={20} defaultValue={2} />
          </div>
        </div>

        <div className="field card">
          <label className="field-label">Children</label>
          <div className="field-control">
            <input className="number-input" type="number" min={0} max={10} defaultValue={0} />
          </div>
        </div>

        <div className="field card fullwidth">
          <label className="field-label">Date</label>
          <div className="field-control">
            <input className="input" type="date" defaultValue="2026-09-12" />
          </div>
        </div>

        <div className="field note-card fullwidth">
          <div className="note-field-label">Note</div>
          <div className="field-control">
            <textarea className="textarea" rows={4} placeholder="Optional"></textarea>
          </div>
        </div>
      </div>
    </div>
  );
}
