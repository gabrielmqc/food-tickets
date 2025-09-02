import { useEffect, useMemo, useState } from 'react'
import { api } from '../api/client'
import type { EmployeeDTO } from '../types'
import EmployeesTemplate from '../templates/employees'

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

  async function handleSubmit(e: React.FormEvent) {
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

  function handleFormChange(field: keyof typeof defaultEmployee, value: string) {
    setForm((prev) => ({ ...prev, [field]: value }))
  }

  function handleEmployeeUpdate(updatedEmployee: EmployeeDTO) {
    setItems((prev) => prev.map((e) => (e.id === updatedEmployee.id ? updatedEmployee : e)))
  }

  const hasItems = useMemo(() => items.length > 0, [items])

  return (
    <EmployeesTemplate
      items={items}
      loading={loading}
      error={error}
      form={form}
      hasItems={hasItems}
      onSubmit={handleSubmit}
      onFormChange={handleFormChange}
      onEmployeeUpdate={handleEmployeeUpdate}
    />
  )
}