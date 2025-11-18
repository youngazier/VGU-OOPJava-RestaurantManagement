import './EditEmployee_page.css';
import React from 'react';

const sampleEmployees = [
  { id: 1, name: 'James', role: 'Chef', contact: '+98 934 532 383', salary: '150$' },
  { id: 2, name: 'Mary Jane', role: 'Waiter', contact: '+98 934 532 383', salary: '50$' },
  { id: 3, name: 'Linda', role: 'Waiter', contact: '+98 934 532 383', salary: '50$' },
  { id: 4, name: 'Mina', role: 'Chef', contact: '+98 934 532 383', salary: '120$' },
  { id: 5, name: 'Kelly', role: 'Waiter', contact: '+98 934 532 383', salary: '50$' },
];

export default function EditEmployee_page() {
  return (
    <div className="edit-employee-page">
      <div className="table-wrap">
        <table className="employee-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>NAME</th>
              <th>ROLE</th>
              <th>CONTACT</th>
              <th>SALARY</th>
              <th>ACTION</th>
            </tr>
          </thead>
          <tbody>
            {sampleEmployees.map((emp) => (
              <tr key={emp.id}>
                <td>{emp.id}</td>
                <td>{emp.name}</td>
                <td>{emp.role}</td>
                <td>{emp.contact}</td>
                <td>{emp.salary}</td>
                <td className="actions">
                  <button title="Edit" className="action edit" aria-label={`Edit ${emp.name}`}>
                    Edit
                  </button>
                  <button title="Delete" className="action delete" aria-label={`Delete ${emp.name}`}>
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}