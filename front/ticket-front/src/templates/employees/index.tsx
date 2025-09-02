import type { EmployeeDTO } from '../../types'
import { styles } from './styles'
import { maskCPF } from '../../utils/mask'
import EmployeeRow from '../../components/EmployeeRow'

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
    const handleCpfChange = (value: string) => {
        const maskedValue = maskCPF(value);
      
        onFormChange('cpf', maskedValue);
    };
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
                        value={maskCPF(form.cpf)} 
                        onChange={(e) => handleCpfChange(e.target.value)}
                        required
                        style={styles.input}
                        maxLength={14} 
                    />
                    <button type="submit" style={styles.submitButton}>
                        Criar
                    </button>
                </form>
            </div>

            <div style={styles.panel}>
                {loading && <p>Carregando...</p>}
                {error && <p style={styles.error}>{error}</p>}

                {hasItems ? (
                    <table style={styles.table}>
                        <thead>
                            <tr>
                                <th style={styles.th}>Nome</th>
                                <th style={styles.th}>CPF</th>
                                <th style={styles.th}>Situação</th>
                                <th style={styles.th}>Ações</th>
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

