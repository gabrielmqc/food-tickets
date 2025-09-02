import { useState } from 'react'
import type { TicketDTO, EmployeeDTO } from '../../types'
import { styles } from './styles'
import { api, downloadBlob } from '../../api/client'

interface TicketsTemplateProps {
  items: TicketDTO[]
  employees: EmployeeDTO[]
  loading: boolean
  error: string | null
  form: Omit<TicketDTO, 'id' | 'alterationDate'>
  hasItems: boolean
  onSubmit: (e: React.FormEvent) => void
  onFormChange: (field: keyof Omit<TicketDTO, 'id' | 'alterationDate'>, value: string | number) => void
  onTicketUpdate: (ticket: TicketDTO) => void
}

export default function TicketsTemplate({
  items,
  employees,
  loading,
  error,
  form,
  hasItems,
  onSubmit,
  onFormChange,
  onTicketUpdate
}: TicketsTemplateProps) {
  return (
    <div style={styles.container}>
      <div style={styles.panel}>
        <h2 style={styles.title}>Tickets</h2>
        <form onSubmit={onSubmit} style={styles.form}>
          <select
            value={form.employeeId}
            onChange={(e) => onFormChange('employeeId', e.target.value)}
            style={styles.select}
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
            onChange={(e) => onFormChange('quantity', Number(e.target.value))}
            required
            style={styles.input}
          />
          <button type="submit" disabled={!form.employeeId} style={styles.submitButton}>
            Criar 
          </button>
        </form>

        <ReportsToolbar employees={employees} />
      </div>

      <div style={styles.panel}>
        {loading && <p>Loading...</p>}
        {error && <p style={styles.error}>{error}</p>}

        {hasItems ? (
          <table style={styles.table}>
            <thead>
              <tr>
                <th style={styles.th}>Funcionário</th>
                <th style={styles.th}>Quantidade</th>
                <th style={styles.th}>Situação</th>
                <th style={styles.th}>Ações</th>
              </tr>
            </thead>
            <tbody>
              {items.map((t) => (
                <TicketRow
                  key={t.id ?? Math.random()}
                  t={t}
                  employees={employees}
                  onChange={onTicketUpdate}
                />
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
          <input
            type="number"
            min={1}
            value={form.quantity ?? 1}
            onChange={(e) => setForm((f) => ({ ...f, quantity: Number(e.target.value) }))}
            style={styles.input}
          />
        ) : (
          t.quantity
        )}
      </td>
      <td>
        {editing ? (
          <select
            value={form.situation ?? 'A'}
            onChange={(e) => setForm((f) => ({ ...f, situation: e.target.value as 'A' | 'I' }))}
            style={styles.select}
          >
            <option value="A">Ativo</option>
            <option value="I">Inativo</option>
          </select>
        ) : (
          t.situation === 'A' ? 'Ativo' : 'Inativo'
        )}
      </td>
      <td style={styles.actionsCell}>
        {!editing ? (
          <>
            <button style={styles.outlineButton} onClick={() => setEditing(true)}>
              Editar
            </button>
            <button style={styles.button} onClick={() => api.activateTicket(t.id!).then(() => onChange({ ...t, situation: 'A' }))}>
              Ativo
            </button>
            <button style={styles.outlineButton} onClick={() => api.deactivateTicket(t.id!).then(() => onChange({ ...t, situation: 'I' }))}>
              Inativo
            </button>
          </>
        ) : (
          <>
            <button style={styles.successButton} onClick={save}>
              Salvar
            </button>
            <button style={styles.dangerButton} onClick={() => setEditing(false)}>
              Cancelar
            </button>
          </>
        )}
      </td>
    </tr>
  )
}

function ReportsToolbar({ employees }: { employees: EmployeeDTO[] }) {
  const [period, setPeriod] = useState<{ start: string; end: string }>({ start: '', end: '' })
  const [employeeId, setEmployeeId] = useState<string>('')

  function formatDate(d: string) {
    if (!d) return ''
    const [y, m, day] = d.split('-')
    const dd = day.padStart(2, '0')
    const mm = m.padStart(2, '0')
    return `${dd}-${mm}-${y}`
  }

  return (
    <div style={styles.toolbar}>
      <div style={styles.group}>
        <label>Inicio</label>
        <input
          type="date"
          value={period.start}
          onChange={(e) => setPeriod((p) => ({ ...p, start: e.target.value }))}
          style={styles.input}
        />
      </div>
      <div style={styles.group}>
        <label>Fim</label>
        <input
          type="date"
          value={period.end}
          onChange={(e) => setPeriod((p) => ({ ...p, end: e.target.value }))}
          style={styles.input}
        />
      </div>
      <div style={styles.group}>
        <label>Funcionário</label>
        <select
          value={employeeId}
          onChange={(e) => setEmployeeId(e.target.value)}
          style={styles.select}
        >
          {employees.map((e) => (
            <option key={e.id ?? e.name} value={e.id ?? ''}>{e.name}</option>
          ))}
        </select>
      </div>
      <button
        style={styles.outlineButton}
        onClick={async () => {
          if (!period.start || !period.end) return
          const blob = await api.ticketsPdfByPeriod(formatDate(period.start), formatDate(period.end))
          downloadBlob(blob, 'relatorio-tickets.pdf')
        }}
      >
        PDF por período
      </button>
      <button
        style={styles.outlineButton}
        onClick={async () => {
          if (!employeeId) return
          const blob = await api.ticketsPdfByEmployee(employeeId)
          downloadBlob(blob, 'relatorio-tickets.pdf')
        }}
      >
        PDF por funcionário
      </button>
      <button
        style={styles.outlineButton}
        onClick={async () => {
          if (!employeeId || !period.start || !period.end) return
          const blob = await api.ticketsPdfByEmployeeAndPeriod(employeeId, formatDate(period.start), formatDate(period.end))
          downloadBlob(blob, 'relatorio-tickets.pdf')
        }}
      >
        PDF funcionário + período
      </button>
    </div>
  )
}