import { useEffect, useMemo, useState } from 'react'
import { api, downloadBlob } from '../api/client'
import type { TicketDTO, EmployeeDTO, Situation } from '../types'

const defaultTicket: Omit<TicketDTO, 'id' | 'alterationDate'> = {
  employeeId: '' as unknown as string,
  quantity: 1,
  situation: 'A',
}

export default function TicketsPage() {
  const [items, setItems] = useState<TicketDTO[]>([])
  const [employees, setEmployees] = useState<EmployeeDTO[]>([])
  const [loading, setLoading] = useState<boolean>(true)
  const [error, setError] = useState<string | null>(null)
  const [form, setForm] = useState<typeof defaultTicket>({ ...defaultTicket })

  useEffect(() => {
    let mounted = true
    setLoading(true)
    Promise.all([api.listTickets(), api.listEmployees()])
      .then(([tickets, emps]) => {
        if (!mounted) return
        setItems(tickets)
        setEmployees(emps)
        if (emps.length > 0 && !form.employeeId) {
          setForm((f) => ({ ...f, employeeId: emps[0].id as string }))
        }
      })
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false))
    return () => {
      mounted = false
    }
  }, [])

  async function onSubmit(e: React.FormEvent) {
    e.preventDefault()
    setError(null)
    try {
      const created = await api.createTicket({ ...form, situation: 'A' })
      setItems((prev) => [created, ...prev])
      setForm((f) => ({ ...f, quantity: 1 }))
    } catch (e: any) {
      setError(e.message)
    }
  }

  const hasItems = useMemo(() => items.length > 0, [items])

  return (
    <div className="container grid">
      <div className="panel grid">
        <h2 style={{ margin: 0 }}>Tickets</h2>
        <form onSubmit={onSubmit} className="grid" style={{ gridTemplateColumns: 'repeat(4, minmax(0, 1fr))' }}>
          <select
            value={form.employeeId}
            onChange={(e) => setForm((f) => ({ ...f, employeeId: e.target.value }))}
          >
            {employees.map((emp) => (
              <option key={emp.id ?? emp.name} value={emp.id ?? ''}>
                {emp.name}
              </option>
            ))}
          </select>
          <input
            type="number"
            min={1}
            placeholder="Quantity"
            value={form.quantity}
            onChange={(e) => setForm((f) => ({ ...f, quantity: Number(e.target.value) }))}
            required
          />
          <button type="submit" disabled={!form.employeeId}>Create (Ativo)</button>
        </form>

        <ReportsToolbar employees={employees} />
      </div>

      <div className="panel">
        {loading && <p>Loading...</p>}
        {error && <p style={{ color: 'salmon' }}>{error}</p>}

        {hasItems ? (
          <table>
            <thead>
              <tr>
                <th>Employee</th>
                <th>Quantity</th>
                <th>Situation</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {items.map((t) => (
                <TicketRow key={t.id ?? Math.random()} t={t} employees={employees} onChange={(updated) => setItems((prev) => prev.map((x) => (x.id === updated.id ? updated : x)))} />
              ))}
            </tbody>
          </table>
        ) : (
          !loading && <p>No tickets.</p>
        )}
      </div>
    </div>
  )
}

function TicketRow({ t, employees, onChange }: { t: TicketDTO; employees: EmployeeDTO[]; onChange: (x: TicketDTO) => void }) {
  const [editing, setEditing] = useState<boolean>(false)
  const [form, setForm] = useState<Partial<TicketDTO>>({ quantity: t.quantity, situation: t.situation })

  async function save() {
    const updated = await api.updateTicket(t.id!, form)
    onChange(updated)
    setEditing(false)
  }

  return (
    <tr>
      <td>{employees.find((e) => e.id === t.employeeId)?.name ?? t.employeeId}</td>
      <td>
        {editing ? (
          <input type="number" min={1} value={form.quantity ?? 1} onChange={(e) => setForm((f) => ({ ...f, quantity: Number(e.target.value) }))} />
        ) : (
          t.quantity
        )}
      </td>
      <td>
        {editing ? (
          <select value={form.situation ?? 'A'} onChange={(e) => setForm((f) => ({ ...f, situation: e.target.value as Situation }))}>
            <option value="A">Ativo</option>
            <option value="I">Inativo</option>
          </select>
        ) : (
          t.situation === 'A' ? 'Ativo' : 'Inativo'
        )}
      </td>
      <td style={{ display: 'flex', gap: 8 }}>
        {!editing ? (
          <>
            <button className="btn-outline" onClick={() => setEditing(true)}>Edit</button>
            <button onClick={() => api.activateTicket(t.id!).then(() => onChange({ ...t, situation: 'A' }))}>Activate</button>
            <button className="btn-outline" onClick={() => api.deactivateTicket(t.id!).then(() => onChange({ ...t, situation: 'I' }))}>Deactivate</button>
          </>
        ) : (
          <>
            <button className="btn-success" onClick={save}>Save</button>
            <button className="btn-danger" onClick={() => setEditing(false)}>Cancel</button>
          </>
        )}
      </td>
    </tr>
  )
}

function ReportsToolbar({ employees }: { employees: EmployeeDTO[] }) {
  const [period, setPeriod] = useState<{ start: string; end: string }>({ start: '', end: '' })
  const [employeeId, setEmployeeId] = useState<string>('')

  useEffect(() => {
    if (employees.length > 0) setEmployeeId(employees[0].id as string)
  }, [employees])

  function formatDate(d: string) {
    if (!d) return ''
    const [y, m, day] = d.split('-')
    const dd = day.padStart(2, '0')
    const mm = m.padStart(2, '0')
    return `${dd}-${mm}-${y}`
  }

  return (
    <div className="toolbar" style={{ marginTop: 8 }}>
      <div className="group">
        <label>Start</label>
        <input type="date" value={period.start} onChange={(e) => setPeriod((p) => ({ ...p, start: e.target.value }))} />
      </div>
      <div className="group">
        <label>End</label>
        <input type="date" value={period.end} onChange={(e) => setPeriod((p) => ({ ...p, end: e.target.value }))} />
      </div>
      <div className="group">
        <label>Employee</label>
        <select value={employeeId} onChange={(e) => setEmployeeId(e.target.value)}>
          {employees.map((e) => (
            <option key={e.id ?? e.name} value={e.id ?? ''}>{e.name}</option>
          ))}
        </select>
      </div>
      <button className="btn-outline" onClick={async () => {
        if (!period.start || !period.end) return
        const blob = await api.ticketsPdfByPeriod(formatDate(period.start), formatDate(period.end))
        downloadBlob(blob, 'relatorio-tickets.pdf')
      }}>PDF por período</button>
      <button className="btn-outline" onClick={async () => {
        if (!employeeId) return
        const blob = await api.ticketsPdfByEmployee(employeeId)
        downloadBlob(blob, 'relatorio-tickets.pdf')
      }}>PDF por funcionário</button>
      <button className="btn-outline" onClick={async () => {
        if (!employeeId || !period.start || !period.end) return
        const blob = await api.ticketsPdfByEmployeeAndPeriod(employeeId, formatDate(period.start), formatDate(period.end))
        downloadBlob(blob, 'relatorio-tickets.pdf')
      }}>PDF funcionário + período</button>
    </div>
  )
}


