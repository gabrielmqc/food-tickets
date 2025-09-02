import { useState } from "react";
import { api } from "../../api/client";
import type { TicketDTO, EmployeeDTO } from "../../types";
import { styles } from "./styles";

export default function TicketRow({ t, employees, onChange }: { t: TicketDTO; employees: EmployeeDTO[]; onChange: (x: TicketDTO) => void }) {
    const [editing, setEditing] = useState<boolean>(false)
    const [form, setForm] = useState<Partial<TicketDTO>>({ quantity: t.quantity, situation: t.situation })

    async function save() {
        const updated = await api.updateTicket(t.id!, form)
        onChange(updated)
        setEditing(false)
    }

    return (
        <tr>
            <td>{employees.find((e) => e.id === t.employeeId)?.name ?? t.employeeId}</td>
            <td>
                {editing ? (
                    <input
                        type="number"
                        min={1}
                        value={form.quantity ?? 1}
                        onChange={(e) => setForm((f) => ({ ...f, quantity: Number(e.target.value) }))}
                        style={styles.input}
                    />
                ) : (
                    t.quantity
                )}
            </td>
            <td>
                {t.situation === 'A' ? 'Ativo' : 'Inativo'}
            </td>
            <td style={styles.actionsCell}>
                {!editing ? (
                    <>
                        <button style={styles.outlineButton} onClick={() => setEditing(true)}>
                            Editar
                        </button>
                        <button style={styles.button} onClick={() => api.activateTicket(t.id!).then(() => onChange({ ...t, situation: 'A' }))}>
                            Ativo
                        </button>
                        <button style={styles.outlineButton} onClick={() => api.deactivateTicket(t.id!).then(() => onChange({ ...t, situation: 'I' }))}>
                            Inativo
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