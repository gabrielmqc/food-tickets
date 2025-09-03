import { Link, Outlet, RouterProvider, createBrowserRouter } from 'react-router-dom'
import EmployeesPage from './pages/Employees'
import TicketsPage from './pages/Tickets'
import './App.css'
import { ToastContainer } from 'react-toastify'

function RootLayout() {
  return (
    <div>
      <nav style={{ display: 'flex', gap: 12, padding: 16, borderBottom: '1px solid #eee' }}>
        <Link to="/employees">Funcionários</Link>
        <Link to="/tickets">Tickets</Link>
      </nav>
      <Outlet />
      <ToastContainer
        position="top-right"
        autoClose={3000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
      />
    </div>
  )
}

const router = createBrowserRouter([
  {
    path: '/',
    element: <RootLayout />,
    children: [
      { index: true, element: <EmployeesPage /> },
      { path: 'employees', element: <EmployeesPage /> },
      { path: 'tickets', element: <TicketsPage /> },
    ],
  },
])

export default function App() {
  return <RouterProvider router={router} />
}
