import type { EmployeeDTO, TicketDTO, UUID } from '../types'

const API_BASE = import.meta.env.VITE_API_BASE ?? ''

async function http<T>(path: string, init?: RequestInit): Promise<T> {
  const hasBody = !!init?.body
  const res = await fetch(`${API_BASE}${path}`, {
    headers: hasBody ? { 'Content-Type': 'application/json' } : undefined,
    ...init,
  })
  if (!res.ok) {
    const text = await res.text()
    throw new Error(text || `HTTP ${res.status}`)
  }
  // Some endpoints return 204
  if (res.status === 204) return undefined as unknown as T
  const contentType = res.headers.get('content-type') || ''
  if (contentType.includes('application/pdf')) {
    const blob = await res.blob()
    return blob as unknown as T
  }
  return (await res.json()) as T
}

export const api = {
  // Employees
  listEmployees: () => http<EmployeeDTO[]>('/api/employee'),
  createEmployee: (payload: Omit<EmployeeDTO, 'id' | 'alterationDate' | 'ticketsIds'>) =>
    http<EmployeeDTO>('/api/employee', {
      method: 'POST',
      body: JSON.stringify({ ...payload }),
    }),
  updateEmployee: (employeeId: UUID, payload: Partial<EmployeeDTO>) =>
    http<EmployeeDTO>(`/api/employee/${employeeId}`, {
      method: 'PUT',
      body: JSON.stringify(payload),
    }),
  activateEmployee: (employeeId: UUID) =>
    http<void>(`/api/employee/${employeeId}/activate`, { method: 'PATCH' }),
  deactivateEmployee: (employeeId: UUID) =>
    http<void>(`/api/employee/${employeeId}/deactivate`, { method: 'PATCH' }),

  // Tickets
  listTickets: () => http<TicketDTO[]>('/api/ticket'),
  createTicket: (payload: Omit<TicketDTO, 'id' | 'alterationDate'>) =>
    http<TicketDTO>('/api/ticket', {
      method: 'POST',
      body: JSON.stringify({ ...payload }),
    }),
  updateTicket: (ticketId: UUID, payload: Partial<TicketDTO>) =>
    http<TicketDTO>(`/api/ticket/${ticketId}`, {
      method: 'PUT',
      body: JSON.stringify(payload),
    }),
  activateTicket: (ticketId: UUID) =>
    http<void>(`/api/ticket/${ticketId}/activate`, { method: 'PATCH' }),
  deactivateTicket: (ticketId: UUID) =>
    http<void>(`/api/ticket/${ticketId}/deactivate`, { method: 'PATCH' }),

  // Reports (PDF)
  ticketsPdfByPeriod: (start: string, end: string) =>
    http<Blob>(`/api/ticket/pdf/startDate/${start}/endDate/${end}`),
  ticketsPdfByEmployee: (employeeId: UUID) =>
    http<Blob>(`/api/ticket/pdf/employee/${employeeId}`),
  ticketsPdfByEmployeeAndPeriod: (employeeId: UUID, start: string, end: string) =>
    http<Blob>(`/api/ticket/pdf/employee/${employeeId}/startDate/${start}/endDate/${end}`),
}

export type Api = typeof api

export function downloadBlob(blob: Blob, filename: string) {
  const url = URL.createObjectURL(blob)
  const a = document.createElement('a')
  a.href = url
  a.download = filename
  document.body.appendChild(a)
  a.click()
  a.remove()
  URL.revokeObjectURL(url)
}

