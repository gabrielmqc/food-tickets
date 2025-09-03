import { useEffect, useState } from "react";
import { api, downloadBlob } from "../../api/client";
import type { EmployeeDTO } from "../../types";
import { styles } from "./styles";
import { toast } from "react-toastify";

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

  const validateFutureDates = (): boolean => {
  const today = new Date();
  const startDate = new Date(period.start);
  const endDate = new Date(period.end);
  
  if (startDate > today) {
    toast.error('A data de início não pode ser futura');
    return false;
  }
  
  if (endDate > today) {
    toast.error('A data de fim não pode ser futura');
    return false;
  }
  
  return true;
}

const validatePeriod = (): boolean => {
  if (!period.start || !period.end) {
    toast.error('Por favor, selecione ambas as datas (início e fim)');
    return false;
  }
  
  if (new Date(period.start) > new Date(period.end)) {
    toast.error('A data de início deve ser anterior à data de fim');
    return false;
  }
  
  if (!validateFutureDates()) {
    return false;
  }
  
  return true;
}

  const validateEmployee = (): boolean => {
    if (!employeeId) {
      toast.error('Por favor, selecione um funcionário');
      return false;
    }
    return true;
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
          if (!validatePeriod()) return;
          
          try {
            const blob = await api.ticketsPdfByPeriod(formatDate(period.start), formatDate(period.end));
            downloadBlob(blob, 'relatorio-periodo.pdf');
            toast.success('Relatório gerado com sucesso!');
          } catch (error: any) {
            toast.error('Erro ao gerar relatório: ' + error.message);
          }
        }}
      >
        PDF por período
      </button>
      
      <button
        style={styles.outlineButton}
        onClick={async () => {
          if (!validateEmployee()) return;
          
          try {
            const blob = await api.ticketsPdfByEmployee(employeeId);
            downloadBlob(blob, `relatorio-funcionario-${employeeId}.pdf`);
            toast.success('Relatório gerado com sucesso!');
          } catch (error: any) {
            toast.error('Erro ao gerar relatório: ' + error.message);
          }
        }}
      >
        PDF por funcionário
      </button>
      
      <button
        style={styles.outlineButton}
        onClick={async () => {
          if (!validatePeriod() || !validateEmployee()) return;
          
          try {
            const blob = await api.ticketsPdfByEmployeeAndPeriod(
              employeeId, 
              formatDate(period.start), 
              formatDate(period.end)
            );
            downloadBlob(blob, `relatorio-funcionario-${employeeId}-periodo.pdf`);
            toast.success('Relatório gerado com sucesso!');
          } catch (error: any) {
            toast.error('Erro ao gerar relatório: ' + error.message);
          }
        }}
      >
        PDF funcionário + período
      </button>
    </div>
  )
}