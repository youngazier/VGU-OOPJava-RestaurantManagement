import React, { useState } from 'react';
import './EditOrder_page.css';

const sampleOrders = [
	{ id: 1, food: '2 Fried Chicken', table: 1, tableChecked: false, served: false },
	{ id: 2, food: 'Combo for 3 people', table: 3, tableChecked: false, served: false },
	{ id: 3, food: '6 Fried Chicken', table: 2, tableChecked: false, served: false },
	{ id: 4, food: 'Combo for 1 people', table: 1, tableChecked: false, served: false },
	{ id: 5, food: 'Combo for 3 people', table: 1, tableChecked: false, served: false },
];

export default function EditOrder_page() {
	const [orders, setOrders] = useState(sampleOrders);

	const toggleField = (index, field) => {
		setOrders((prev) => {
			const copy = [...prev];
			copy[index] = { ...copy[index], [field]: !copy[index][field] };
			return copy;
		});
	};

	return (
		<div className="edit-order-page">
			<div className="table-wrap">
				<table className="orders-table">
					<thead>
						<tr>
							<th>ItemID</th>
							<th>Food Name</th>
							<th>Table</th>
							<th>Table</th>
							<th>Served</th>
						</tr>
					</thead>
					<tbody>
						{orders.map((o, i) => (
							<tr key={o.id}>
								<td className="col-id">{o.id}</td>
								<td className="col-food">{o.food}</td>
								<td className="col-table-number">{o.table}</td>
								<td className="col-checkbox">
									<input
										type="checkbox"
										checked={o.tableChecked}
										onChange={() => toggleField(i, 'tableChecked')}
										aria-label={`Table checked for item ${o.id}`}
									/>
								</td>
								<td className="col-checkbox">
									<input
										type="checkbox"
										checked={o.served}
										onChange={() => toggleField(i, 'served')}
										aria-label={`Served for item ${o.id}`}
									/>
								</td>
							</tr>
						))}
					</tbody>
				</table>
			</div>
		</div>
	);
}

