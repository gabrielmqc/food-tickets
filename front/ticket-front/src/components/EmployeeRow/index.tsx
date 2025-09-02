import { useState } from "react";
import type { EmployeeDTO } from "../../types";
import { api } from "../../api/client";
import { styles } from "./styles";
import { maskCPF } from "../../utils/mask";

export default function EmployeeRow({ emp, onChange }: { emp: EmployeeDTO; onChange: (e: EmployeeDTO) => void }) {
    const [editing, setEditing] = useState<boolean>(false)
    const [form, setForm] = useState<Partial<EmployeeDTO>>({ name: emp.name, cpf: emp.cpf, situation: emp.situation })

    const handleCpfChange = (value: string) => {
        const maskedValue = maskCPF(value);

        setForm((f) => ({ ...f, cpf: maskedValue }));
    };

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
                        value={maskCPF(form.cpf ?? '')}
                        onChange={(e) => handleCpfChange(e.target.value)}
                        style={styles.input}
                        maxLength={14}
                    />
                ) : (
                    maskCPF(emp.cpf)
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