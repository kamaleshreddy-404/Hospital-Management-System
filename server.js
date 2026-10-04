const http = require('http');
const fs = require('fs');
const path = require('path');
const url = require('url');

const PORT = 3000;
const PUBLIC_DIR = path.join(__dirname, 'src', 'main', 'webapp');

// In-memory mock database state for live interactive web server demo
// Strict Doctor Order: 1. Dr. Neha, 2. Dr. Shobana, 3. Dr. Sruthi, 4. Dr. Jagadeesh, 5. Dr. Jaswanth, 6. Dr. Harsha, 7. Dr. Haasith, 8. Dr. Kamalesh
const state = {
    users: [
        { id: 1, name: 'System Administrator', email: 'admin@hospital.com', roleId: 1, roleName: 'Administrator' },
        { id: 2, name: 'Dr. Neha', email: 'doctor@hospital.com', roleId: 2, roleName: 'Doctor' },
        { id: 3, name: 'Dr. Shobana', email: 'shobana@hospital.com', roleId: 2, roleName: 'Doctor' },
        { id: 4, name: 'Dr. Sruthi', email: 'sruthi@hospital.com', roleId: 2, roleName: 'Doctor' },
        { id: 5, name: 'Dr. Jagadeesh', email: 'jagadeesh@hospital.com', roleId: 2, roleName: 'Doctor' },
        { id: 6, name: 'Dr. Jaswanth', email: 'jaswanth@hospital.com', roleId: 2, roleName: 'Doctor' },
        { id: 7, name: 'Dr. Harsha', email: 'harsha@hospital.com', roleId: 2, roleName: 'Doctor' },
        { id: 8, name: 'Dr. Haasith', email: 'haasith@hospital.com', roleId: 2, roleName: 'Doctor' },
        { id: 9, name: 'Dr. Kamalesh', email: 'kamalesh@hospital.com', roleId: 2, roleName: 'Doctor' },
        { id: 10, name: 'Sarah Jenkins', email: 'reception@hospital.com', roleId: 3, roleName: 'Receptionist' },
        { id: 11, name: 'Michael Vance', email: 'pharma@hospital.com', roleId: 4, roleName: 'Pharmacist' },
        { id: 12, name: 'John Doe', email: 'patient@hospital.com', roleId: 5, roleName: 'Patient' }
    ],
    doctors: [
        { id: 1, name: 'Dr. Neha', qualification: 'MS, M.Ch (Cardiothoracic Surgery)', specialization: 'Heart Surgeon', exp: 16, fee: 900, status: 'Active (Senior Doctor & Dept Head)', dept: 'Cardiology', icon: 'fa-heart-pulse', opd: 'Mon-Fri 10:00 AM' },
        { id: 2, name: 'Dr. Shobana', qualification: 'MS (Orthopedics), Joint Replacement Fellow', specialization: 'Orthopedic Surgeon', exp: 10, fee: 750, status: 'Active', dept: 'Orthopedics', icon: 'fa-bone', opd: 'Mon-Sat 11:00 AM' },
        { id: 3, name: 'Dr. Sruthi', qualification: 'MD (Dermatology, Venereology & Leprosy)', specialization: 'Dermatologist & Cosmetologist', exp: 5, fee: 600, status: 'Active', dept: 'Dermatology', icon: 'fa-spa', opd: 'Tue-Sun 02:00 PM' },
        { id: 4, name: 'Dr. Jagadeesh', qualification: 'MD (Gen Med), DM (Gastroenterology)', specialization: 'Gastroenterologist', exp: 7, fee: 650, status: 'Active', dept: 'Gastroenterology', icon: 'fa-stomach', opd: 'Mon-Fri 12:00 PM' },
        { id: 5, name: 'Dr. Jaswanth', qualification: 'MD (Pediatrics), DM (Pediatric Cardiology)', specialization: 'Pediatric Cardiologist', exp: 8, fee: 700, status: 'Active', dept: 'Pediatric Cardiology', icon: 'fa-child-rearing', opd: 'Mon-Sat 04:00 PM' },
        { id: 6, name: 'Dr. Harsha', qualification: 'MD (Gen Med), DM (Pulmonology)', specialization: 'Pulmonologist & Critical Care', exp: 9, fee: 750, status: 'Active', dept: 'Pulmonology', icon: 'fa-lungs', opd: 'Mon-Sat 04:30 PM' },
        { id: 7, name: 'Dr. Haasith', qualification: 'MD (Gen Med), DM (Endocrinology)', specialization: 'Endocrinologist & Diabetologist', exp: 11, fee: 780, status: 'Active', dept: 'Endocrinology', icon: 'fa-dna', opd: 'Mon-Fri 05:00 PM' },
        { id: 8, name: 'Dr. Kamalesh', qualification: 'MD (Neurology), M.Ch (Neurosurgery)', specialization: 'Neurosurgeon', exp: 12, fee: 800, status: 'Active', dept: 'Neurology', icon: 'fa-brain', opd: 'Mon-Fri 05:30 PM' }
    ],
    patients: [
        { id: 1, name: 'John Doe', gender: 'Male', age: 35, blood: 'O+', phone: '9876543216', emergency: '9876500001', address: '123 MG Road, Bangalore' },
        { id: 2, name: 'Anita Roy', gender: 'Female', age: 29, blood: 'A+', phone: '9876543217', emergency: '9876500002', address: '45 Park Street, Kolkata' }
    ],
    departments: [
        { id: 1, name: 'Cardiology', desc: 'Heart health, cardiac surgery, valve repair, and vascular treatment.', head: 'Dr. Neha (Heart Surgeon)', icon: 'fa-heart-pulse' },
        { id: 2, name: 'Orthopedics', desc: 'Robotic joint replacement, spine surgery, and musculoskeletal care.', head: 'Dr. Shobana (Orthopedic Surgeon)', icon: 'fa-bone' },
        { id: 3, name: 'Dermatology', desc: 'Advanced skin care, laser surgery, aesthetic procedures, and cosmetic dermatology.', head: 'Dr. Sruthi (Dermatologist & Cosmetologist)', icon: 'fa-spa' },
        { id: 4, name: 'Gastroenterology', desc: 'Digestive health, liver care, endoscopy, colonoscopy, and hepatology.', head: 'Dr. Jagadeesh (Gastroenterologist)', icon: 'fa-stomach' },
        { id: 5, name: 'Pediatric Cardiology', desc: 'Pediatric heart care, congenital defect correction, and neonatal cardiology.', head: 'Dr. Jaswanth (Pediatric Cardiologist)', icon: 'fa-child-rearing' },
        { id: 6, name: 'Pulmonology', desc: 'Advanced respiratory care, asthma management, COPD, and critical care pulmonology.', head: 'Dr. Harsha (Pulmonologist & Critical Care)', icon: 'fa-lungs' },
        { id: 7, name: 'Endocrinology', desc: 'Diabetes management, thyroid disorders, and metabolic hormone care.', head: 'Dr. Haasith (Endocrinologist & Diabetologist)', icon: 'fa-dna' },
        { id: 8, name: 'Neurology', desc: 'Disorders of the brain, spinal cord fusion, stroke care, and neurosurgery.', head: 'Dr. Kamalesh (Neurosurgeon)', icon: 'fa-brain' }
    ],
    packages: [
        { id: 1, name: 'Full Body Executive Checkup', tests: '65 Essential Tests: CBC, Lipid Profile, Liver Function, Kidney Function, HbA1c, ECG', price: 1499 },
        { id: 2, name: 'Cardiac Care Comprehensive Panel', tests: 'Advanced Heart Health: Treadmill Test (TMT), 2D Echo, Lipid Panel, Troponin, ECG', price: 2199 },
        { id: 3, name: 'Neuro-Spine Diagnostic Panel', tests: 'Brain & Nervous System: Vitamin B12, Calcium, Electrolytes, Nerve Conduction Study', price: 3499 }
    ],
    medicines: [
        { id: 1, name: 'Paracetamol 650mg', category: 'Analgesic', price: 25, stock: 150, expiry: '2027-12-31' },
        { id: 2, name: 'Amoxicillin 500mg', category: 'Antibiotic', price: 85, stock: 80, expiry: '2027-08-30' },
        { id: 3, name: 'Atorvastatin 10mg', category: 'Cardiovascular', price: 120, stock: 200, expiry: '2028-01-15' },
        { id: 4, name: 'Pantoprazole 40mg', category: 'Antacid', price: 45, stock: 30, expiry: '2026-11-20' }
    ],
    appointments: [
        { id: 1, patientName: 'John Doe', doctorName: 'Dr. Neha', spec: 'Heart Surgeon', date: '2026-10-03', time: '10:30 AM', symptoms: 'Mild chest tightness after exercise.', status: 'Completed' },
        { id: 2, patientName: 'Anita Roy', doctorName: 'Dr. Shobana', spec: 'Orthopedic Surgeon', date: '2026-10-03', time: '11:15 AM', symptoms: 'Lower back pain when bending down.', status: 'Confirmed' },
        { id: 3, patientName: 'Robert Smith', doctorName: 'Dr. Kamalesh', spec: 'Neurosurgeon', date: '2026-10-03', time: '02:00 PM', symptoms: 'Frequent migraines.', status: 'Pending' },
        { id: 4, patientName: 'Emily Davis', doctorName: 'Dr. Jaswanth', spec: 'Pediatric Cardiologist', date: '2026-10-03', time: '04:30 PM', symptoms: 'Child heart rate checkup.', status: 'Pending' }
    ],
    bills: [
        { id: 1, patientName: 'John Doe', consult: 900, med: 145, test: 500, total: 1545, status: 'Paid', method: 'Credit Card', date: '2026-10-03' }
    ]
};

const mimeTypes = {
    '.html': 'text/html',
    '.css': 'text/css',
    '.js': 'text/javascript',
    '.png': 'image/png',
    '.jpg': 'image/jpeg',
    '.svg': 'image/svg+xml',
    '.txt': 'text/plain'
};

const SVG_LOGO = `<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 320 70" width="220" height="48" style="display: block;">
  <defs>
    <linearGradient id="logoShield" x1="0%" y1="0%" x2="100%" y2="100%">
      <stop offset="0%" stop-color="#0F172A"/>
      <stop offset="50%" stop-color="#0284C7"/>
      <stop offset="100%" stop-color="#0D9488"/>
    </linearGradient>
    <linearGradient id="logoPulse" x1="0%" y1="0%" x2="100%" y2="0%">
      <stop offset="0%" stop-color="#38BDF8"/>
      <stop offset="100%" stop-color="#34D399"/>
    </linearGradient>
    <filter id="logoGlow" x="-20%" y="-20%" width="140%" height="140%">
      <feDropShadow dx="0" dy="4" stdDeviation="6" flood-color="#0284C7" flood-opacity="0.3"/>
    </filter>
  </defs>
  <g filter="url(#logoGlow)">
    <rect x="6" y="6" width="56" height="56" rx="16" fill="url(#logoShield)" />
    <rect x="29" y="18" width="10" height="32" rx="3" fill="#FFFFFF"/>
    <rect x="18" y="29" width="32" height="10" rx="3" fill="#FFFFFF"/>
    <path d="M 12,34 L 23,34 L 27,24 L 32,44 L 37,28 L 41,36 L 46,34 L 56,34" fill="none" stroke="url(#logoPulse)" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/>
  </g>
  <text x="76" y="38" font-family="'Plus Jakarta Sans', sans-serif" font-size="22" font-weight="800" fill="#0F172A" letter-spacing="-0.5">
    SMART<tspan fill="#0284C7">HOSPITAL</tspan>
  </text>
  <text x="77" y="54" font-family="'Plus Jakarta Sans', sans-serif" font-size="10" font-weight="700" fill="#0D9488" letter-spacing="1.5">
    NEXT-GEN MEDICAL CENTER
  </text>
</svg>`;

function renderLayout(title, content, activeTab = 'home', activeRole = 'public') {
    return `<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
    <link rel="icon" type="image/svg+xml" href="data:image/svg+xml,<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 100 100'><rect width='100' height='100' rx='25' fill='%230284C7'/><path d='M50 20v60M20 50h60' stroke='%23ffffff' stroke-width='16' stroke-linecap='round'/></svg>">
    <link rel="stylesheet" href="/assets/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <!-- Top Emergency Contact Notification Bar -->
    <div style="background: #0F172A; color: #94A3B8; font-size: 13px; padding: 8px 48px; display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #1E293B; flex-wrap: wrap; gap: 10px;">
        <div style="display: flex; gap: 20px; align-items: center;">
            <span style="color: #EF4444; font-weight: 700;"><i class="fa-solid fa-truck-medical"></i> 24/7 Emergency Helpline: <strong>1800-123-9999 / 102</strong></span>
            <span><i class="fa-solid fa-droplet" style="color: #EF4444;"></i> Blood Bank Status: <strong style="color: #10B981;">O+, A+, B+, AB+ Available</strong></span>
        </div>
        <div style="display: flex; gap: 16px; align-items: center; font-weight: 600;">
            <span><i class="fa-solid fa-bed-pulse" style="color: #38BDF8;"></i> ICU Beds: <strong style="color: #38BDF8;">28/35 Available</strong></span>
            <span><i class="fa-solid fa-clock"></i> OPD Hours: 8:00 AM - 8:00 PM</span>
        </div>
    </div>

    ${activeRole === 'public' ? `
    <header class="public-navbar">
        <div class="nav-brand-group">
            <a href="/home" style="display: flex; align-items: center; text-decoration: none;">
                ${SVG_LOGO}
            </a>
        </div>
        <nav style="display: flex; align-items: center; gap: 10px;">
            <a href="/home" class="${activeTab === 'home' ? 'active' : ''}">Home</a>
            <a href="/about" class="${activeTab === 'about' ? 'active' : ''}">About Us</a>
            <a href="/services" class="${activeTab === 'services' ? 'active' : ''}">Services</a>
            <a href="/doctors" class="${activeTab === 'doctors' ? 'active' : ''}">Doctors</a>
            <a href="/departments" class="${activeTab === 'departments' ? 'active' : ''}">Departments</a>
            <a href="/packages" class="${activeTab === 'packages' ? 'active' : ''}">Health Packages</a>
            <a href="/contact" class="${activeTab === 'contact' ? 'active' : ''}">Contact</a>
            <button id="themeToggleBtn" onclick="toggleDarkMode()" class="btn btn-outline" style="padding: 6px 14px; font-size: 13px; display: inline-flex; align-items: center; gap: 6px; cursor: pointer; border-radius: 20px;">
                <i class="fa-solid fa-moon" id="themeIcon" style="color: #0284C7;"></i> <span id="themeText">Dark</span>
            </button>
            <a href="/login" class="btn btn-primary"><i class="fa-solid fa-right-to-bracket"></i> Portal Login</a>
        </nav>
    </header>
    ` : `
    <header class="top-navbar">
        <div class="nav-brand-group">
            <a href="/home" style="display: flex; align-items: center; text-decoration: none;">
                ${SVG_LOGO}
            </a>
        </div>
        <div class="user-profile-badge">
            <button id="themeToggleBtn" onclick="toggleDarkMode()" class="btn btn-outline btn-sm" style="margin-right: 12px; padding: 4px 12px; font-size: 12px; display: inline-flex; align-items: center; gap: 4px; border-radius: 16px;">
                <i class="fa-solid fa-moon" id="themeIcon" style="color: #0284C7;"></i> <span id="themeText">Dark</span>
            </button>
            <div class="avatar-circle">${activeRole.charAt(0).toUpperCase()}</div>
            <div>
                <div style="font-weight: 700; font-size: 14px;">Demo User (${activeRole})</div>
                <div style="font-size: 11px; color: var(--brand-blue); font-weight: 700;">${activeRole.toUpperCase()} PORTAL</div>
            </div>
            <a href="/login" class="btn btn-outline btn-sm" style="margin-left: 12px;"><i class="fa-solid fa-right-from-bracket"></i> Logout</a>
        </div>
    </header>
    `}

    ${content}

    <footer style="background: var(--white); border-top: 1px solid var(--gray-200); padding: 32px 40px; text-align: center; color: var(--gray-600); font-size: 14px; margin-top: 60px;">
        <div style="max-width: 1240px; margin: 0 auto; display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 16px;">
            <div>
                <strong>Smart Hospital Management System</strong> &copy; 2026 | Major Academic Project Edition
            </div>
            <div style="display: flex; gap: 20px; font-weight: 600;">
                <a href="/home">Home</a>
                <a href="/doctors">Doctors Directory</a>
                <a href="/departments">Departments</a>
                <a href="/packages">Health Packages</a>
                <a href="/contact">Emergency Contact</a>
            </div>
        </div>
    </footer>
    <script src="/assets/js/main.js"></script>
</body>
</html>`;
}

const server = http.createServer((req, res) => {
    const parsedUrl = url.parse(req.url, true);
    let pathname = parsedUrl.pathname;

    if (pathname === '/') pathname = '/index.html';

    // Serve next.txt from root if requested
    if (pathname === '/next.txt') {
        const filePath = path.join(__dirname, 'next.txt');
        if (fs.existsSync(filePath)) {
            res.writeHead(200, { 'Content-Type': 'text/plain' });
            return res.end(fs.readFileSync(filePath));
        }
    }

    // Serve static assets
    let filePath = path.join(PUBLIC_DIR, pathname);
    if (fs.existsSync(filePath) && fs.statSync(filePath).isFile()) {
        const ext = path.extname(filePath);
        res.writeHead(200, { 'Content-Type': mimeTypes[ext] || 'text/plain' });
        return res.end(fs.readFileSync(filePath));
    }

    // Handle POST Patient Registration Form Submission
    if (req.method === 'POST' && pathname === '/register') {
        let body = '';
        req.on('data', chunk => { body += chunk.toString(); });
        req.on('end', () => {
            res.writeHead(302, { 'Location': '/login?msg=Registration successful! Please login.' });
            return res.end();
        });
        return;
    }

    // Handle POST Login Form Submission
    if (req.method === 'POST' && pathname === '/login') {
        let body = '';
        req.on('data', chunk => { body += chunk.toString(); });
        req.on('end', () => {
            const params = new URLSearchParams(body);
            const email = (params.get('email') || '').toLowerCase();
            const role = params.get('role') || '';

            let redirectUrl = '/doctor/dashboard'; // Default
            if (email.includes('admin') || role === 'admin') redirectUrl = '/admin/dashboard';
            else if (email.includes('doctor') || role === 'doctor') redirectUrl = '/doctor/dashboard';
            else if (email.includes('reception') || role === 'receptionist') redirectUrl = '/receptionist/dashboard';
            else if (email.includes('pharma') || role === 'pharmacist') redirectUrl = '/pharmacist/dashboard';
            else if (email.includes('patient') || role === 'patient') redirectUrl = '/patient/dashboard';

            res.writeHead(302, { 'Location': redirectUrl });
            return res.end();
        });
        return;
    }


    // 1. Home Page
    if (pathname === '/home' || pathname === '/index.html') {
        const html = renderLayout('Smart Hospital Management System - Home', `
        <section class="hero-banner" style="text-align: left; padding: 60px 48px 80px;">
            <div style="max-width: 1240px; margin: 0 auto; display: grid; grid-template-columns: 1.2fr 0.8fr; gap: 40px; align-items: center;">
                <div>
                    <div class="hero-badge">
                        <i class="fa-solid fa-shield-halved"></i> Multi-Specialty Clinical Excellence & Digital Health Records
                    </div>
                    <h1 style="font-size: 42px; font-weight: 800; line-height: 1.2; margin-bottom: 18px;">Advanced Medical Care & Specialist Doctor Consultation</h1>
                    <p style="font-size: 17px; opacity: 0.92; margin-bottom: 28px; line-height: 1.7;">Empowering cardiac surgery, joint replacements, laser dermatology, gastroenterology, pediatric cardiology, and neurosurgery with 24/7 emergency care and digital health portals.</p>
                    <div style="display: flex; gap: 16px; flex-wrap: wrap;">
                        <a href="/register" class="btn btn-primary" style="background: #FFFFFF; color: #0F172A; font-size: 15px; padding: 14px 28px;">
                            <i class="fa-solid fa-user-plus" style="color: #0284C7;"></i> Register Patient Profile
                        </a>
                        <a href="/login" class="btn btn-outline" style="border-color: rgba(255,255,255,0.4); color: #FFFFFF; font-size: 15px; padding: 14px 28px;">
                            <i class="fa-solid fa-stethoscope"></i> Staff & Doctor Portal
                        </a>
                    </div>
                </div>

                <div style="background: #FFFFFF; color: #0F172A; border-radius: var(--radius-xl); padding: 32px; box-shadow: 0 20px 40px rgba(0,0,0,0.25);">
                    <h3 style="font-size: 20px; font-weight: 800; color: var(--gray-900); margin-bottom: 6px; display: flex; align-items: center; gap: 10px;">
                        <i class="fa-solid fa-calendar-plus" style="color: var(--brand-blue);"></i> Instant OPD Booking
                    </h3>
                    <p style="font-size: 13px; color: var(--gray-500); margin-bottom: 20px;">Book consultation with specialist doctors in 30 seconds</p>
                    
                    <form onsubmit="alert('✅ OPD Token Confirmed! Token #OPD-' + Math.floor(1000 + Math.random() * 9000) + '. Please report to reception 15 minutes before your time slot.'); return false;">
                        <div class="form-group">
                            <label>Select Specialist Doctor *</label>
                            <select class="form-control" required style="font-weight: 600;">
                                ${state.doctors.map(d => `<option value="${d.id}">${d.id}. ${d.name} (${d.specialization} - ${d.exp}y Exp, ₹${d.fee})</option>`).join('')}
                            </select>
                        </div>
                        <div class="form-row">
                            <div class="form-group"><label>Patient Name *</label><input type="text" class="form-control" placeholder="John Doe" required></div>
                            <div class="form-group"><label>Phone *</label><input type="tel" class="form-control" placeholder="Mobile" required></div>
                        </div>
                        <button type="submit" class="btn btn-primary" style="width: 100%; justify-content: center; padding: 12px; font-size: 15px;">
                            <i class="fa-solid fa-check-circle"></i> Confirm Booking Token
                        </button>
                    </form>
                </div>
            </div>
        </section>

        <div class="page-body">
            <div class="dashboard-grid">
                <div class="stat-card"><div><div class="stat-label">Specialist Doctors</div><div class="stat-number">8 Faculty</div></div><div class="stat-icon"><i class="fa-solid fa-user-doctor"></i></div></div>
                <div class="stat-card"><div><div class="stat-label">Clinical Departments</div><div class="stat-number">8 Centers</div></div><div class="stat-icon" style="background: rgba(13, 148, 136, 0.1); color: var(--brand-teal);"><i class="fa-solid fa-building-user"></i></div></div>
                <div class="stat-card"><div><div class="stat-label">ICU & Emergency</div><div class="stat-number" style="font-size: 22px; color: var(--brand-emerald);">24/7 Active</div></div><div class="stat-icon" style="background: rgba(16, 185, 129, 0.1); color: var(--brand-emerald);"><i class="fa-solid fa-heart-circle-bolt"></i></div></div>
                <div class="stat-card"><div><div class="stat-label">Patients Cured</div><div class="stat-number">15,000+</div></div><div class="stat-icon" style="background: rgba(245, 158, 11, 0.1); color: var(--warning);"><i class="fa-solid fa-hospital-user"></i></div></div>
            </div>

            <div class="card-panel">
                <div class="card-header">
                    <div>
                        <h2 class="card-title"><i class="fa-solid fa-user-md" style="color: var(--brand-blue);"></i> Specialist Doctors Directory (Ordered 1-8)</h2>
                        <p style="font-size: 13px; color: var(--gray-500); margin-top: 2px;">Official medical faculty ordered 1 to 8 with qualifications and experience</p>
                    </div>
                    <a href="/doctors" class="btn btn-outline btn-sm">Full Directory</a>
                </div>

                <div class="doctor-grid">
                    ${state.doctors.map((d, index) => `
                        <div class="doctor-card">
                            <div class="doctor-order-badge">${index + 1}</div>
                            <div class="doctor-card-header">
                                <div class="doctor-avatar"><i class="fa-solid ${d.icon}"></i></div>
                                <div>
                                    <div class="doctor-name">${d.name}</div>
                                    <div class="doctor-spec">${d.specialization}</div>
                                    ${d.exp === 16 ? '<span class="badge badge-senior" style="margin-top: 4px;">SENIOR HEART SURGEON & HEAD</span>' : '<span class="badge badge-confirmed" style="margin-top: 4px;">Specialist</span>'}
                                </div>
                            </div>
                            <div class="doctor-meta">
                                <div><label>Qualification</label><span>${d.qualification}</span></div>
                                <div><label>Experience</label><span>${d.exp} Years</span></div>
                                <div><label>OPD Schedule</label><span>${d.opd}</span></div>
                                <div><label>Consult Fee</label><span style="color: var(--brand-emerald);">₹${d.fee}</span></div>
                            </div>
                            <a href="/login" class="btn btn-primary btn-sm" style="width: 100%; justify-content: center;"><i class="fa-solid fa-calendar-check"></i> Book Consultation</a>
                        </div>
                    `).join('')}
                </div>
            </div>

            <div class="card-panel">
                <div class="card-header">
                    <div>
                        <h2 class="card-title"><i class="fa-solid fa-vial-circle-check" style="color: var(--brand-teal);"></i> Preventive Health Diagnostic Packages</h2>
                        <p style="font-size: 13px; color: var(--gray-500); margin-top: 2px;">Comprehensive laboratory test screening packages</p>
                    </div>
                </div>
                <div class="grid-3">
                    ${state.packages.map(p => `
                        <div class="service-card" style="text-align: left;">
                            <h3 style="font-size: 18px; font-weight: 800; margin-bottom: 8px;">${p.name}</h3>
                            <p style="font-size: 13px; color: var(--gray-600); margin-bottom: 16px;">${p.tests}</p>
                            <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 16px;">
                                <span style="font-size: 22px; font-weight: 800; color: var(--brand-emerald);">₹${p.price}</span>
                                <a href="/login" class="btn btn-outline btn-sm">Book Package</a>
                            </div>
                        </div>
                    `).join('')}
                </div>
            </div>
        </div>
        `, 'home');
        res.writeHead(200, { 'Content-Type': 'text/html' });
        return res.end(html);
    }

    // 2. Health Packages Route
    if (pathname === '/packages') {
        const html = renderLayout('Health Packages - Smart HMS', `
        <div class="page-body">
            <h2 style="font-size: 28px; font-weight: 800; margin-bottom: 24px; color: var(--gray-900);">Preventive Health Checkup Packages</h2>
            <div class="grid-3">
                ${state.packages.map(p => `
                    <div class="card-panel">
                        <h3 style="color: var(--brand-blue); font-size: 20px; margin-bottom: 12px;">${p.name}</h3>
                        <p style="color: var(--gray-600); font-size: 14px; margin-bottom: 16px;">${p.tests}</p>
                        <div style="font-size: 24px; font-weight: 800; color: var(--brand-emerald); margin-bottom: 16px;">₹${p.price}</div>
                        <a href="/login" class="btn btn-primary" style="width: 100%; justify-content: center;">Book Package</a>
                    </div>
                `).join('')}
            </div>
        </div>
        `, 'packages');
        res.writeHead(200, { 'Content-Type': 'text/html' });
        return res.end(html);
    }

    // 3. Patient Registration Page
    if (pathname === '/register') {
        const html = renderLayout('Patient Registration - Smart HMS', `
        <div style="min-height: 80vh; display: flex; align-items: center; justify-content: center; padding: 40px 20px;">
            <div style="width: 100%; max-width: 640px; background: var(--white); border: 1px solid var(--gray-200); border-radius: var(--radius-xl); padding: 40px; box-shadow: var(--shadow-lg);">
                <div style="text-align: center; margin-bottom: 32px;">
                    <div style="display: flex; justify-content: center; margin-bottom: 16px;">${SVG_LOGO}</div>
                    <h2 style="font-size: 24px; font-weight: 800; color: var(--gray-900);"><i class="fa-solid fa-user-plus" style="color: var(--brand-blue);"></i> Patient Registration</h2>
                    <p style="font-size: 14px; color: var(--gray-500); margin-top: 4px;">Create your personal digital medical record profile</p>
                </div>

                <form action="/register" method="post">
                    <div class="form-row">
                        <div class="form-group"><label>Full Name *</label><input type="text" name="name" class="form-control" required placeholder="John Doe"></div>
                        <div class="form-group"><label>Email Address *</label><input type="email" name="email" class="form-control" required placeholder="john@example.com"></div>
                    </div>
                    <div class="form-row">
                        <div class="form-group"><label>Password *</label><input type="password" name="password" class="form-control" required placeholder="••••••••"></div>
                        <div class="form-group"><label>Phone Number *</label><input type="tel" name="phone" class="form-control" required placeholder="10-digit mobile"></div>
                    </div>
                    <button type="submit" class="btn btn-primary" style="width: 100%; justify-content: center; padding: 14px; font-size: 16px; margin-top: 12px;">
                        <i class="fa-solid fa-check"></i> Register Account & Create EHR
                    </button>
                </form>
            </div>
        </div>
        `, 'register');
        res.writeHead(200, { 'Content-Type': 'text/html' });
        return res.end(html);
    }

    // 4. About Page
    if (pathname === '/about') {
        const html = renderLayout('About Hospital - Smart HMS', `
        <div class="page-body" style="max-width: 1000px;">
            <div class="card-panel">
                <h2 style="font-size: 28px; font-weight: 800; color: var(--gray-900); margin-bottom: 20px;"><i class="fa-solid fa-hospital" style="color: var(--brand-blue);"></i> About Smart Hospital Center</h2>
                <p style="margin-bottom: 20px; line-height: 1.8; font-size: 16px; color: var(--gray-700);">
                    Smart Hospital is a premier multi-specialty healthcare institution led by senior <strong>Heart Surgeon Dr. Neha</strong> (16 Years Exp), alongside <strong>Dr. Shobana</strong> (Orthopedic Surgeon, 10y), <strong>Dr. Sruthi</strong> (Dermatologist & Cosmetologist, 5y), <strong>Dr. Jagadeesh</strong> (Gastroenterologist, 7y), <strong>Dr. Jaswanth</strong> (Pediatric Cardiologist, 8y), <strong>Dr. Harsha</strong> (Pulmonologist & Critical Care, 9y), <strong>Dr. Haasith</strong> (Endocrinologist & Diabetologist, 11y), and <strong>Dr. Kamalesh</strong> (Neurosurgeon, 12y).
                </p>
                <p style="margin-bottom: 24px; line-height: 1.8; font-size: 16px; color: var(--gray-700);">
                    This full-stack system was engineered as an Academic Major Project demonstrating Java Web development standards, MVC architecture, DAO design patterns, secure database integration via JDBC/H2, and role-based portal security.
                </p>
            </div>
        </div>
        `, 'about');
        res.writeHead(200, { 'Content-Type': 'text/html' });
        return res.end(html);
    }

    // 5. Services Page
    if (pathname === '/services') {
        const html = renderLayout('Services - Smart HMS', `
        <div class="page-body">
            <h2 style="font-size: 28px; font-weight: 800; margin-bottom: 24px; color: var(--gray-900);">Clinical Centers & Advanced Healthcare Services</h2>
            <div class="grid-3">
                <div class="service-card">
                    <div class="service-card-icon"><i class="fa-solid fa-heart-pulse"></i></div>
                    <h3 style="font-size: 20px; font-weight: 800; margin-bottom: 8px;">Cardiac & Vascular Surgery</h3>
                    <p style="color: var(--gray-600); font-size: 14px;">Open heart surgery and vascular interventions led by Senior Heart Surgeon Dr. Neha (16y Exp).</p>
                </div>
                <div class="service-card">
                    <div class="service-card-icon" style="background: rgba(13, 148, 136, 0.1); color: var(--brand-teal);"><i class="fa-solid fa-bone"></i></div>
                    <h3 style="font-size: 20px; font-weight: 800; margin-bottom: 8px;">Orthopedic Joint Surgery</h3>
                    <p style="color: var(--gray-600); font-size: 14px;">Robotic joint replacement and spine care led by Dr. Shobana (10y Exp).</p>
                </div>
                <div class="service-card">
                    <div class="service-card-icon" style="background: rgba(16, 185, 129, 0.1); color: var(--brand-emerald);"><i class="fa-solid fa-child-rearing"></i></div>
                    <h3 style="font-size: 20px; font-weight: 800; margin-bottom: 8px;">Pediatric Cardiology</h3>
                    <p style="color: var(--gray-600); font-size: 14px;">Congenital heart defect treatments for infants led by Dr. Jaswanth (8y Exp).</p>
                </div>
                <div class="service-card">
                    <div class="service-card-icon" style="background: rgba(2, 132, 199, 0.1); color: var(--brand-blue);"><i class="fa-solid fa-lungs"></i></div>
                    <h3 style="font-size: 20px; font-weight: 800; margin-bottom: 8px;">Pulmonology & Critical Care</h3>
                    <p style="color: var(--gray-600); font-size: 14px;">Asthma, COPD, pulmonary rehab, and critical care led by Dr. Harsha (9y Exp).</p>
                </div>
                <div class="service-card">
                    <div class="service-card-icon" style="background: rgba(124, 58, 237, 0.1); color: #7C3AED;"><i class="fa-solid fa-dna"></i></div>
                    <h3 style="font-size: 20px; font-weight: 800; margin-bottom: 8px;">Endocrinology & Diabetology</h3>
                    <p style="color: var(--gray-600); font-size: 14px;">Diabetes control, thyroid disorders, and metabolic care led by Dr. Haasith (11y Exp).</p>
                </div>
                <div class="service-card">
                    <div class="service-card-icon" style="background: rgba(139, 92, 246, 0.1); color: #8B5CF6;"><i class="fa-solid fa-brain"></i></div>
                    <h3 style="font-size: 20px; font-weight: 800; margin-bottom: 8px;">Neurology & Neurosurgery</h3>
                    <p style="color: var(--gray-600); font-size: 14px;">Brain tumor surgery, spine surgery, and stroke care led by Dr. Kamalesh (12y Exp).</p>
                </div>
            </div>
        </div>
        `, 'services');
        res.writeHead(200, { 'Content-Type': 'text/html' });
        return res.end(html);
    }

    // 6. Doctors Directory Page (Strict Ordered List: 1-8)
    if (pathname === '/doctors') {
        const html = renderLayout('Doctors Directory - Smart HMS', `
        <div class="page-body">
            <div class="card-panel">
                <div class="card-header">
                    <div>
                        <h2 class="card-title"><i class="fa-solid fa-user-doctor" style="color: var(--brand-blue);"></i> Specialist Doctors Directory (Ordered 1-8)</h2>
                        <p style="font-size: 13px; color: var(--gray-500); margin-top: 2px;">Faculty order strictly maintained 1 through 8</p>
                    </div>
                </div>

                <div class="doctor-grid">
                    ${state.doctors.map((d, index) => `
                        <div class="doctor-card">
                            <div class="doctor-order-badge">${index + 1}</div>
                            <div class="doctor-card-header">
                                <div class="doctor-avatar"><i class="fa-solid ${d.icon}"></i></div>
                                <div>
                                    <div class="doctor-name">${d.name}</div>
                                    <div class="doctor-spec">${d.specialization}</div>
                                    ${d.exp === 16 ? '<span class="badge badge-senior" style="margin-top: 4px;">SENIOR HEART SURGEON</span>' : '<span class="badge badge-confirmed" style="margin-top: 4px;">Specialist</span>'}
                                </div>
                            </div>
                            <div class="doctor-meta">
                                <div><label>Qualification</label><span>${d.qualification}</span></div>
                                <div><label>Experience</label><span>${d.exp} Years</span></div>
                                <div><label>OPD Schedule</label><span>${d.opd}</span></div>
                                <div><label>Consult Fee</label><span style="color: var(--brand-emerald);">₹${d.fee}</span></div>
                            </div>
                            <a href="/login" class="btn btn-primary btn-sm" style="width: 100%; justify-content: center;"><i class="fa-solid fa-calendar-check"></i> Book Consultation</a>
                        </div>
                    `).join('')}
                </div>
            </div>
        </div>
        `, 'doctors');
        res.writeHead(200, { 'Content-Type': 'text/html' });
        return res.end(html);
    }

    // 7. Departments Page
    if (pathname === '/departments') {
        const html = renderLayout('Departments - Smart HMS', `
        <div class="page-body">
            <h2 style="font-size: 28px; font-weight: 800; margin-bottom: 24px; color: var(--gray-900);">Clinical Departments</h2>
            <div class="grid-3">
                ${state.departments.map(dept => `
                    <div class="card-panel" style="margin-bottom: 0;">
                        <div style="font-size: 32px; color: var(--brand-blue); margin-bottom: 12px;"><i class="fa-solid ${dept.icon}"></i></div>
                        <h3 style="color: var(--gray-900); font-size: 22px; font-weight: 800; margin-bottom: 8px;">${dept.name}</h3>
                        <p style="color: var(--gray-600); font-size: 14px; margin-bottom: 16px;">${dept.desc}</p>
                        <div style="font-size: 13px; font-weight: 700; color: var(--gray-900); background: var(--gray-100); padding: 8px 12px; border-radius: var(--radius-sm);">Head of Dept: <span style="color: var(--brand-blue);">${dept.head}</span></div>
                    </div>
                `).join('')}
            </div>
        </div>
        `, 'departments');
        res.writeHead(200, { 'Content-Type': 'text/html' });
        return res.end(html);
    }

    // 8. Contact Page
    if (pathname === '/contact') {
        const html = renderLayout('Contact Us - Smart HMS', `
        <div class="page-body" style="max-width: 960px;">
            <div class="card-panel">
                <h2 style="font-size: 28px; font-weight: 800; color: var(--gray-900); margin-bottom: 24px;"><i class="fa-solid fa-headset" style="color: var(--brand-blue);"></i> Emergency & Contact Center</h2>
                <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 32px;">
                    <div>
                        <h4 style="margin-bottom: 8px; font-size: 16px;">Address:</h4>
                        <p style="color: var(--gray-600); font-size: 14px; margin-bottom: 20px;">123 Healthcare Boulevard, Tech City, Karnataka 560001</p>
                        <h4 style="margin-bottom: 8px; font-size: 16px;">24/7 Emergency Helpline:</h4>
                        <p style="color: var(--danger); font-weight: 800; font-size: 20px; margin-bottom: 20px;"><i class="fa-solid fa-phone-volume"></i> 1800-123-9999 / 102</p>
                        <h4 style="margin-bottom: 8px; font-size: 16px;">Email Support:</h4>
                        <p style="color: var(--gray-600); font-size: 14px;">contact@smarthospital.org</p>
                    </div>
                    <div>
                        <form onsubmit="alert('Thank you for contacting Smart Hospital.'); return false;">
                            <div class="form-group"><label>Your Name</label><input type="text" class="form-control" required placeholder="Full name"></div>
                            <div class="form-group"><label>Email Address</label><input type="email" class="form-control" required placeholder="Email"></div>
                            <div class="form-group"><label>Message</label><textarea class="form-control" rows="3" required placeholder="Write message..."></textarea></div>
                            <button type="submit" class="btn btn-primary" style="width: 100%;">Send Message</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        `, 'contact');
        res.writeHead(200, { 'Content-Type': 'text/html' });
        return res.end(html);
    }

    // Portal Login Route
    if (pathname === '/login') {
        const msg = parsedUrl.query.msg ? `<div class="alert alert-success" style="margin-bottom: 16px;"><i class="fa-solid fa-circle-check"></i> ${parsedUrl.query.msg}</div>` : '';
        const error = parsedUrl.query.error ? `<div class="alert alert-danger" style="margin-bottom: 16px;"><i class="fa-solid fa-triangle-exclamation"></i> ${parsedUrl.query.error}</div>` : '';

        const html = renderLayout('Portal Login - Smart HMS', `
        <div style="min-height: 80vh; display: flex; align-items: center; justify-content: center; padding: 40px 20px;">
            <div style="width: 100%; max-width: 480px; background: var(--white); border: 1px solid var(--gray-200); border-radius: var(--radius-xl); padding: 36px; box-shadow: var(--shadow-lg);">
                <div style="text-align: center; margin-bottom: 24px;">
                    <div style="display: flex; justify-content: center; margin-bottom: 12px;">${SVG_LOGO}</div>
                    <h2 style="font-size: 22px; font-weight: 800; color: var(--gray-900);">Digital Hospital Portal Sign In</h2>
                    <p style="font-size: 13px; color: var(--gray-500); margin-top: 4px;">Sign in to access your secure role-based medical dashboard</p>
                </div>

                ${msg}
                ${error}

                <form action="/login" method="post">
                    <div class="form-group">
                        <label for="loginEmail"><i class="fa-solid fa-envelope" style="color: var(--brand-blue);"></i> Email Address *</label>
                        <input type="email" id="loginEmail" name="email" class="form-control" required placeholder="name@hospital.com" value="doctor@hospital.com">
                    </div>

                    <div class="form-group">
                        <label for="loginPassword"><i class="fa-solid fa-key" style="color: var(--brand-blue);"></i> Password *</label>
                        <input type="password" id="loginPassword" name="password" class="form-control" required placeholder="••••••••" value="doctor123">
                    </div>

                    <div class="form-group">
                        <label for="loginRole"><i class="fa-solid fa-user-shield" style="color: var(--brand-blue);"></i> User Role Portal *</label>
                        <select id="loginRole" name="role" class="form-control" style="font-weight: 600;">
                            <option value="admin">Administrator Portal</option>
                            <option value="doctor" selected>Doctor Portal (Dr. Neha - Senior Heart Surgeon)</option>
                            <option value="receptionist">Receptionist Desk Portal</option>
                            <option value="pharmacist">Pharmacist Inventory Portal</option>
                            <option value="patient">Patient Medical EHR Portal</option>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-primary" style="width: 100%; justify-content: center; padding: 12px; font-size: 15px; margin-top: 8px;">
                        <i class="fa-solid fa-right-to-bracket"></i> Sign In to Portal Dashboard
                    </button>
                </form>

                <div style="margin-top: 24px; padding-top: 18px; border-top: 1px solid var(--gray-200); font-size: 13px;">
                    <p style="font-weight: 700; color: var(--gray-700); margin-bottom: 10px; text-align: center;">⚡ Quick Demo Login Fill Shortcuts:</p>
                    <div style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 8px;">
                        <button type="button" onclick="fillServerLogin('admin@hospital.com', 'admin123', 'admin')" class="btn btn-outline btn-sm" style="font-size: 12px;"><i class="fa-solid fa-user-gear"></i> Admin</button>
                        <button type="button" onclick="fillServerLogin('doctor@hospital.com', 'doctor123', 'doctor')" class="btn btn-outline btn-sm" style="font-size: 12px;"><i class="fa-solid fa-stethoscope"></i> Doctor</button>
                        <button type="button" onclick="fillServerLogin('reception@hospital.com', 'reception123', 'receptionist')" class="btn btn-outline btn-sm" style="font-size: 12px;"><i class="fa-solid fa-desktop"></i> Reception</button>
                        <button type="button" onclick="fillServerLogin('pharma@hospital.com', 'pharma123', 'pharmacist')" class="btn btn-outline btn-sm" style="font-size: 12px;"><i class="fa-solid fa-pills"></i> Pharma</button>
                        <button type="button" onclick="fillServerLogin('patient@hospital.com', 'patient123', 'patient')" class="btn btn-outline btn-sm" style="font-size: 12px;"><i class="fa-solid fa-user-injured"></i> Patient</button>
                        <a href="/register" class="btn btn-secondary btn-sm" style="font-size: 12px; justify-content: center;"><i class="fa-solid fa-user-plus"></i> Register</a>
                    </div>
                </div>

                <div style="text-align: center; margin-top: 20px;">
                    <a href="/home" style="font-size: 13px; color: var(--gray-600); font-weight: 600;">
                        <i class="fa-solid fa-arrow-left"></i> Back to Hospital Homepage
                    </a>
                </div>
            </div>
        </div>

        <script>
        function fillServerLogin(email, pwd, role) {
            document.getElementById('loginEmail').value = email;
            document.getElementById('loginPassword').value = pwd;
            if (role) {
                document.getElementById('loginRole').value = role;
            }
        }
        </script>
        `, 'login');
        res.writeHead(200, { 'Content-Type': 'text/html' });
        return res.end(html);
    }

    // Portal Dashboards
    if (pathname.startsWith('/admin')) {
        const html = renderLayout('Admin Dashboard - Smart HMS', `
        <div class="page-body">
            <h2 style="font-size: 24px; font-weight: 800; margin-bottom: 24px; color: var(--gray-900);">Administrator Executive Portal</h2>
            <div class="dashboard-grid">
                <div class="stat-card"><div><div class="stat-label">Total Patients</div><div class="stat-number">${state.patients.length}</div></div><div class="stat-icon"><i class="fa-solid fa-procedures"></i></div></div>
                <div class="stat-card"><div><div class="stat-label">Active Doctors</div><div class="stat-number">${state.doctors.length}</div></div><div class="stat-icon" style="background: rgba(16, 185, 129, 0.1); color: var(--brand-emerald);"><i class="fa-solid fa-user-doctor"></i></div></div>
                <div class="stat-card"><div><div class="stat-label">Appointments</div><div class="stat-number">${state.appointments.length}</div></div><div class="stat-icon" style="background: rgba(245, 158, 11, 0.1); color: var(--warning);"><i class="fa-solid fa-calendar-day"></i></div></div>
                <div class="stat-card"><div><div class="stat-label">Total Revenue</div><div class="stat-number" style="color: var(--brand-emerald);">₹1,545</div></div><div class="stat-icon" style="background: rgba(2, 132, 199, 0.1); color: var(--brand-blue);"><i class="fa-solid fa-indian-rupee-sign"></i></div></div>
            </div>
            <div class="card-panel">
                <h3 class="card-title"><i class="fa-solid fa-user-doctor" style="color: var(--brand-blue);"></i> Registered Doctor Roster (Ordered 1-8)</h3>
                <div class="table-responsive">
                    <table class="custom-table">
                        <thead><tr><th>#</th><th>ID</th><th>Doctor Name</th><th>Specialization</th><th>Experience</th><th>Fee</th></tr></thead>
                        <tbody>
                            ${state.doctors.map((d, i) => `<tr><td><strong>${i + 1}</strong></td><td>#DOC-${d.id}</td><td style="font-weight:700;">${d.name} ${d.exp === 16 ? '(Senior Heart Surgeon)' : ''}</td><td>${d.specialization}</td><td>${d.exp} Yrs</td><td style="color:var(--brand-emerald); font-weight:800;">₹${d.fee}</td></tr>`).join('')}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        `, 'admin', 'admin');
        res.writeHead(200, { 'Content-Type': 'text/html' });
        return res.end(html);
    }

    if (pathname.startsWith('/doctor')) {
        const html = renderLayout('Doctor Portal - Today Schedule', `
        <div class="page-body">
            <h2 style="font-size: 24px; font-weight: 800; margin-bottom: 24px; color: var(--gray-900);">Dr. Neha (Senior Heart Surgeon) - Consultation Schedule</h2>
            <div class="card-panel">
                <div class="table-responsive">
                    <table class="custom-table">
                        <thead><tr><th>ID</th><th>Patient Name</th><th>Time</th><th>Symptoms</th><th>Status</th></tr></thead>
                        <tbody>
                            ${state.appointments.map(a => `<tr><td>#APT-${a.id}</td><td style="font-weight:700;">${a.patientName}</td><td>${a.time}</td><td>${a.symptoms}</td><td><span class="badge badge-confirmed">${a.status}</span></td></tr>`).join('')}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        `, 'doctor', 'doctor');
        res.writeHead(200, { 'Content-Type': 'text/html' });
        return res.end(html);
    }

    if (pathname.startsWith('/receptionist')) {
        const html = renderLayout('Receptionist Front Desk', `
        <div class="page-body">
            <h2 style="font-size: 24px; font-weight: 800; margin-bottom: 24px; color: var(--gray-900);">Front Desk Patient Reception</h2>
            <div class="card-panel">
                <h3 class="card-title">Walk-in Patients Intake</h3>
                <div class="table-responsive">
                    <table class="custom-table">
                        <thead><tr><th>ID</th><th>Patient Name</th><th>Gender / Age</th><th>Phone</th><th>Blood Group</th></tr></thead>
                        <tbody>
                            ${state.patients.map(p => `<tr><td>#PAT-${p.id}</td><td style="font-weight:700;">${p.name}</td><td>${p.gender}, ${p.age} Yrs</td><td>${p.phone}</td><td><span class="badge badge-pending">${p.blood}</span></td></tr>`).join('')}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        `, 'receptionist', 'receptionist');
        res.writeHead(200, { 'Content-Type': 'text/html' });
        return res.end(html);
    }

    if (pathname.startsWith('/pharmacist')) {
        const html = renderLayout('Pharmacist Inventory & Stock', `
        <div class="page-body">
            <h2 style="font-size: 24px; font-weight: 800; margin-bottom: 24px; color: var(--gray-900);">Pharmacy Stock Inventory Control</h2>
            <div class="card-panel">
                <div class="table-responsive">
                    <table class="custom-table">
                        <thead><tr><th>ID</th><th>Medicine Name</th><th>Category</th><th>Price</th><th>Current Stock</th></tr></thead>
                        <tbody>
                            ${state.medicines.map(m => `<tr><td>#MED-${m.id}</td><td style="font-weight:700;">${m.name}</td><td>${m.category}</td><td>₹${m.price}</td><td><span class="badge ${m.stock <= 50 ? 'badge-cancelled' : 'badge-completed'}">${m.stock} Units</span></td></tr>`).join('')}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        `, 'pharmacist', 'pharmacist');
        res.writeHead(200, { 'Content-Type': 'text/html' });
        return res.end(html);
    }

    if (pathname.startsWith('/patient')) {
        const html = renderLayout('Patient Portal Dashboard', `
        <div class="page-body">
            <h2 style="font-size: 24px; font-weight: 800; margin-bottom: 24px; color: var(--gray-900);">John Doe - Patient Health Portal</h2>
            <div class="card-panel">
                <h3 class="card-title">Appointment History</h3>
                <div class="table-responsive">
                    <table class="custom-table">
                        <thead><tr><th>Appt ID</th><th>Doctor</th><th>Date & Time</th><th>Symptoms</th><th>Status</th></tr></thead>
                        <tbody>
                            ${state.appointments.filter(a => a.patientName === 'John Doe').map(a => `<tr><td>#APT-${a.id}</td><td style="font-weight:700;">${a.doctorName}</td><td>${a.date} ${a.time}</td><td>${a.symptoms}</td><td><span class="badge badge-completed">${a.status}</span></td></tr>`).join('')}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        `, 'patient', 'patient');
        res.writeHead(200, { 'Content-Type': 'text/html' });
        return res.end(html);
    }

    // Fallback 404
    res.writeHead(404, { 'Content-Type': 'text/html' });
    res.end(renderLayout('Page Not Found', `<div class="page-body" style="text-align:center;"><h2>404 - Page Not Found</h2><p><a href="/home" class="btn btn-primary">Return to Home</a></p></div>`));
});

server.listen(PORT, () => {
    console.log(`Smart Hospital Management Server is running live on http://localhost:${PORT}`);
});
