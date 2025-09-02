import { useState } from 'react'
import type { EmployeeDTO } from '../../types'
import { styles } from './styles'
import { api } from '../../api/client'

interface EmployeesTemplateProps {
    items: EmployeeDTO[]
    loading: boolean
    error: string | null
    form: Omit<EmployeeDTO, 'id' | 'alterationDate' | 'ticketsIds'>
    hasItems: boolean
    onSubmit: (e: React.FormEvent) => void
    onFormChange: (field: keyof Omit<EmployeeDTO, 'id' | 'alterationDate' | 'ticketsIds'>, value: string) => void
    onEmployeeUpdate: (employee: EmployeeDTO) => void
}

export default function EmployeesTemplate({
    items,
    loading,
    error,
    form,
    hasItems,
    onSubmit,
    onFormChange,
    onEmployeeUpdate
}: EmployeesTemplateProps) {
    return (
        <div style={styles.container}>
            <div style={styles.panel}>
                <h2 style={styles.title}>Funcionários</h2>
                <form onSubmit={onSubmit} style={styles.form}>
                    <input
                        placeholder="Name"
                        value={form.name}
                        onChange={(e) => onFormChange('name', e.target.value)}
                        required
                        style={styles.input}
                    />
                    <input
                        placeholder="CPF"
                        value={form.cpf}
                        onChange={(e) => onFormChange('cpf', e.target.value)}
                        required
                        style={styles.input}
                    />
                    <button type="submit" style={styles.submitButton}>
                        Criar
                    </button>
                </form>
            </div>

            <div style={styles.panel}>
                {loading && <p>Loading...</p>}
                {error && <p style={styles.error}>{error}</p>}

                {hasItems ? (
                    <table style={styles.table}>
                        <thead>
                            <tr>
                                <th style={styles.th}>Name</th>
                                <th style={styles.th}>CPF</th>
                                <th style={styles.th}>Situation</th>
                                <th style={styles.th}>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {items.map((emp) => (
                                <EmployeeRow
                                    key={emp.id ?? Math.random()}
                                    emp={emp}
                                    onChange={onEmployeeUpdate}
                                />
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
                    <input
                        value={form.name ?? ''}
                        onChange={(e) => setForm((f) => ({ ...f, name: e.target.value }))}
                        style={styles.input}
                    />
                ) : (
                    emp.name
                )}
            </td>
            <td>
                {editing ? (
                    <input
                        value={form.cpf ?? ''}
                        onChange={(e) => setForm((f) => ({ ...f, cpf: e.target.value }))}
                        style={styles.input}
                    />
                ) : (
                    emp.cpf
                )}
            </td>
            <td>
                {emp.situation === 'A' ? 'Ativo' : 'Inativo'}
            </td>
            <td style={styles.actionsCell}>
                {!editing ? (
                    <>
                        <button style={styles.outlineButton} onClick={() => setEditing(true)}>
                            Editar
                        </button>
                        <button style={styles.button} onClick={() => api.activateEmployee(emp.id!).then(() => onChange({ ...emp, situation: 'A' }))}>
                            Ativar
                        </button>
                        <button style={styles.outlineButton} onClick={() => api.deactivateEmployee(emp.id!).then(() => onChange({ ...emp, situation: 'I' }))}>
                            Desativar
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