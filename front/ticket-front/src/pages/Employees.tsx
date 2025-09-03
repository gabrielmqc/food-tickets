import { useEffect, useMemo, useState } from 'react'
import { api } from '../api/client'
import type { EmployeeDTO } from '../types'
import EmployeesTemplate from '../templates/employees'
import { validateCPF } from '../utils/mask' // Importe a função de validação
import { toast } from 'react-toastify' // Importe o toast

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
  const [formErrors, setFormErrors] = useState<{ // Estado para erros do formulário
    name?: string
    cpf?: string
  }>({})

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

  const validateForm = (): boolean => {
    const errors: { name?: string; cpf?: string } = {}
    
    if (!form.name.trim()) {
      errors.name = 'Nome é obrigatório'
    } else if (form.name.trim().length < 2) {
      errors.name = 'Nome deve ter pelo menos 2 caracteres'
    }
    
    const cleanCPF = form.cpf.replace(/\D/g, '')
    if (!cleanCPF) {
      errors.cpf = 'CPF é obrigatório'
    } else if (cleanCPF.length !== 11) {
      errors.cpf = 'CPF deve ter 11 dígitos'
    } else if (!validateCPF(form.cpf)) {
      errors.cpf = 'CPF inválido'
    }
    
    setFormErrors(errors)
    return Object.keys(errors).length === 0
  }

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    setError(null)
    
    if (!validateForm()) {
      toast.error('Por favor, corrija os erros no formulário')
      return
    }
    
    try {
      const created = await api.createEmployee({ ...form, situation: 'A' })
      setItems((prev) => [created, ...prev])
      setForm({ ...defaultEmployee })
      setFormErrors({}) // Limpa os erros
      toast.success('Funcionário criado com sucesso!')
    } catch (e: any) {
      setError(e.message)
      toast.error('Erro ao criar funcionário: ' + e.message)
    }
  }

  function handleFormChange(field: keyof typeof defaultEmployee, value: string) {
    setForm((prev) => ({ ...prev, [field]: value }))
    
    // Limpa o erro do campo quando o usuário começar a digitar
    if (formErrors[field as keyof typeof formErrors]) {
      setFormErrors((prev) => ({ ...prev, [field]: undefined }))
    }
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
      formErrors={formErrors} // Passe os erros para o template
    />
  )
}