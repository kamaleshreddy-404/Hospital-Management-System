/**
 * Smart Hospital Management System JavaScript Helpers
 */

document.addEventListener('DOMContentLoaded', function () {
    // Auto-dismiss alerts after 5 seconds
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(function (alert) {
        setTimeout(function () {
            alert.style.opacity = '0';
            setTimeout(() => alert.remove(), 300);
        }, 5000);
    });
});

// Live Table Search Filter
function filterTable(inputId, tableId) {
    const input = document.getElementById(inputId);
    const filter = input.value.toLowerCase();
    const table = document.getElementById(tableId);
    const tr = table.getElementsByTagName('tr');

    for (let i = 1; i < tr.length; i++) {
        let found = false;
        const td = tr[i].getElementsByTagName('td');
        for (let j = 0; j < td.length; j++) {
            if (td[j]) {
                const textValue = td[j].textContent || td[j].innerText;
                if (textValue.toLowerCase().indexOf(filter) > -1) {
                    found = true;
                    break;
                }
            }
        }
        tr[i].style.display = found ? '' : 'none';
    }
}

// Dynamically Add Prescription Rows in Doctor Form
function addPrescriptionRow() {
    const container = document.getElementById('prescriptionItemsContainer');
    if (!container) return;

    const rowCount = container.children.length;
    const firstRow = container.firstElementChild;
    if (!firstRow) return;

    const newRow = firstRow.cloneNode(true);
    // Clear input values
    const inputs = newRow.querySelectorAll('input, select');
    inputs.forEach(input => input.value = '');

    container.appendChild(newRow);
}

function removePrescriptionRow(btn) {
    const container = document.getElementById('prescriptionItemsContainer');
    if (container && container.children.length > 1) {
        btn.closest('.prescription-row').remove();
    } else {
        alert('Prescription must contain at least one medicine item.');
    }
}

// Printable Invoice & Slip Helper
function printPage() {
    window.print();
}
