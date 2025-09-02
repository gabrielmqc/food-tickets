import { useEffect, useMemo, useState } from 'react'
import { api } from '../api/client'
import type { EmployeeDTO, Situation } from '../types'

const defaultEmployee: Omit<EmployeeDTO, 'id' | 'alterationDate' | 'ticketsIds'> = {
  name: '',
  cpf: '',
  situation: 'A',
}

export default function EmployeesPage() {
  const [items, setItems] = useState<EmployeeDTO[]>([])
  const [loading, setLoading] = useState<boolean>(true)
  const [error, setError] = useState<string | null>(null)
  const [form, setForm] = useState<typeof defaultEmployee>({ ...defaultEmployee })

  useEffect(() => {
    let mounted = true
    setLoading(true)
    api
      .listEmployees()
      .then((data) => {
        if (mounted) setItems(data)
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
      const created = await api.createEmployee({ ...form, situation: 'A' })
      setItems((prev) => [created, ...prev])
      setForm({ ...defaultEmployee })
    } catch (e: any) {
      setError(e.message)
    }
  }

  const hasItems = useMemo(() => items.length > 0, [items])

  return (
    <div className="container grid">
      <div className="panel">
        <h2 style={{ marginTop: 0 }}>Employees</h2>
        <form onSubmit={onSubmit} className="grid" style={{ gridTemplateColumns: 'repeat(3, minmax(0, 1fr))' }}>
          <input
            placeholder="Name"
            value={form.name}
            onChange={(e) => setForm((f) => ({ ...f, name: e.target.value }))}
            required
          />
          <input
            placeholder="CPF"
            value={form.cpf}
            onChange={(e) => setForm((f) => ({ ...f, cpf: e.target.value }))}
            required
          />
          <button type="submit">Create (Ativo)</button>
        </form>
      </div>

      <div className="panel">
        {loading && <p>Loading...</p>}
        {error && <p style={{ color: 'salmon' }}>{error}</p>}

        {hasItems ? (
          <table>
            <thead>
              <tr>
                <th>Name</th>
                <th>CPF</th>
                <th>Situation</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {items.map((emp) => (
                <EmployeeRow key={emp.id ?? Math.random()} emp={emp} onChange={(updated) => setItems((prev) => prev.map((e) => (e.id === updated.id ? updated : e)))} />
              ))}
            </tbody>
          </table>
        ) : (
          !loading && <p>No employees.</p>
        )}
      </div>
    </div>
  )
}

function EmployeeRow({ emp, onChange }: { emp: EmployeeDTO; onChange: (e: EmployeeDTO) => void }) {
  const [editing, setEditing] = useState<boolean>(false)
  const [form, setForm] = useState<Partial<EmployeeDTO>>({ name: emp.name, cpf: emp.cpf, situation: emp.situation })

  async function save() {
    const updated = await api.updateEmployee(emp.id!, form)
    onChange(updated)
    setEditing(false)
  }

  return (
    <tr>
      <td>
        {editing ? (
          <input value={form.name ?? ''} onChange={(e) => setForm((f) => ({ ...f, name: e.target.value }))} />
        ) : (
          emp.name
        )}
      </td>
      <td>
        {editing ? (
          <input value={form.cpf ?? ''} onChange={(e) => setForm((f) => ({ ...f, cpf: e.target.value }))} />
        ) : (
          emp.cpf
        )}
      </td>
      <td>
        {editing ? (
          <select value={form.situation ?? 'A'} onChange={(e) => setForm((f) => ({ ...f, situation: e.target.value as Situation }))}>
            <option value="A">Ativo</option>
            <option value="I">Inativo</option>
          </select>
        ) : (
          emp.situation === 'A' ? 'Ativo' : 'Inativo'
        )}
      </td>
      <td style={{ display: 'flex', gap: 8 }}>
        {!editing ? (
          <>
            <button className="btn-outline" onClick={() => setEditing(true)}>Edit</button>
            <button onClick={() => api.activateEmployee(emp.id!).then(() => onChange({ ...emp, situation: 'A' }))}>Activate</button>
            <button className="btn-outline" onClick={() => api.deactivateEmployee(emp.id!).then(() => onChange({ ...emp, situation: 'I' }))}>Deactivate</button>
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


