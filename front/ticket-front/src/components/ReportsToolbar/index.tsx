import { useEffect, useState } from "react";
import { api, downloadBlob } from "../../api/client";
import type { EmployeeDTO } from "../../types";
import { styles } from "./styles";

export default function ReportsToolbar({ employees }: { employees: EmployeeDTO[] }) {
  const [period, setPeriod] = useState<{ start: string; end: string }>({ start: '', end: '' })
  const [employeeId, setEmployeeId] = useState<string>(
    employees[0]?.id ?? ''
  )

  function formatDate(d: string) {
    if (!d) return ''
    const [y, m, day] = d.split('-')
    const dd = day.padStart(2, '0')
    const mm = m.padStart(2, '0')
    return `${dd}-${mm}-${y}`
  }

  useEffect(() => {
    if (employees.length > 0 && !employeeId) {
      setEmployeeId(employees[0].id ?? '')
    }
  }, [employees])

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
          console.log(formatDate(period.start), period.end)
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
          console.log({ employeeId })

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