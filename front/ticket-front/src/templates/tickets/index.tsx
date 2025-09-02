import type { TicketDTO, EmployeeDTO } from '../../types'
import { styles } from './styles'
import TicketRow from '../../components/TicketRow'
import ReportsToolbar from '../../components/ReportsToolbar'

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



