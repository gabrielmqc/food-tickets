import { useEffect, useMemo, useState } from 'react'
import TicketsTemplate from '../templates/tickets'
import type { EmployeeDTO, TicketDTO } from '../types'
import { api } from '../api/client'

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

  async function handleSubmit(e: React.FormEvent) {
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

  function handleFormChange(field: keyof typeof defaultTicket, value: string | number) {
    setForm((prev) => ({ ...prev, [field]: value }))
  }

  function handleTicketUpdate(updatedTicket: TicketDTO) {
    setItems((prev) => prev.map((t) => (t.id === updatedTicket.id ? updatedTicket : t)))
  }

  const hasItems = useMemo(() => items.length > 0, [items])

  return (
    <TicketsTemplate
      items={items}
      employees={employees}
      loading={loading}
      error={error}
      form={form}
      hasItems={hasItems}
      onSubmit={handleSubmit}
      onFormChange={handleFormChange}
      onTicketUpdate={handleTicketUpdate}
    />
  )
}