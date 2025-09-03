import { useState } from "react";
import type { EmployeeDTO } from "../../types";
import { api } from "../../api/client";
import { styles } from "./styles";
import { maskCPF, validateCPF } from "../../utils/mask";
import { toast } from "react-toastify";

export default function EmployeeRow({ emp, onChange }: { emp: EmployeeDTO; onChange: (e: EmployeeDTO) => void }) {
    const [editing, setEditing] = useState<boolean>(false);
    const [form, setForm] = useState<Partial<EmployeeDTO>>({ 
        name: emp.name, 
        cpf: emp.cpf, 
        situation: emp.situation 
    });
    const [formErrors, setFormErrors] = useState<{
        name?: string;
        cpf?: string;
    }>({});

    const handleCpfChange = (value: string) => {
        const maskedValue = maskCPF(value);
        setForm((f) => ({ ...f, cpf: maskedValue }));
        
        if (formErrors.cpf) {
            setFormErrors((prev) => ({ ...prev, cpf: undefined }));
        }
    };

    const handleNameChange = (value: string) => {
        setForm((f) => ({ ...f, name: value }));
        
        if (formErrors.name) {
            setFormErrors((prev) => ({ ...prev, name: undefined }));
        }
    };

    const validateForm = (): boolean => {
        const errors: { name?: string; cpf?: string } = {};
        
        if (!form.name?.trim()) {
            errors.name = 'Nome é obrigatório';
        } else if (form.name.trim().length < 2) {
            errors.name = 'Nome deve ter pelo menos 2 caracteres';
        }
        
        const cleanCPF = form.cpf?.replace(/\D/g, '') || '';
        if (!cleanCPF) {
            errors.cpf = 'CPF é obrigatório';
        } else if (cleanCPF.length !== 11) {
            errors.cpf = 'CPF deve ter 11 dígitos';
        } else if (!validateCPF(form.cpf || '')) {
            errors.cpf = 'CPF inválido';
        }
        
        setFormErrors(errors);
        return Object.keys(errors).length === 0;
    };

    async function save() {
        if (!validateForm()) {
            toast.error('Por favor, corrija os erros antes de salvar');
            return;
        }

        try {
            const updated = await api.updateEmployee(emp.id!, form);
            onChange(updated);
            setEditing(false);
            setFormErrors({});
            toast.success('Funcionário atualizado com sucesso!');
        } catch (error: any) {
            toast.error('Erro ao atualizar funcionário: ' + error.message);
        }
    }

    function cancelEdit() {
        setEditing(false);
        setForm({ name: emp.name, cpf: emp.cpf, situation: emp.situation });
        setFormErrors({});
    }

    return (
        <tr style={styles.tr}>
            <td>
                {editing ? (
                    <div style={{ position: 'relative' as 'relative' }}>
                        <input
                            value={form.name ?? ''}
                            onChange={(e) => handleNameChange(e.target.value)}
                            style={{
                                ...styles.input,
                                borderColor: formErrors.name ? 'red' : '#24304a',
                                marginBottom: formErrors.name ? '20px' : '0'
                            }}
                        />
                        {formErrors.name && (
                            <span style={{
                                color: 'red',
                                fontSize: '12px',
                                position: 'absolute' as 'absolute',
                                bottom: '-18px',
                                left: '0'
                            }}>
                                {formErrors.name}
                            </span>
                        )}
                    </div>
                ) : (
                    emp.name
                )}
            </td>
            <td>
                {editing ? (
                    <div style={{ position: 'relative' as 'relative' }}>
                        <input
                            value={maskCPF(form.cpf ?? '')}
                            onChange={(e) => handleCpfChange(e.target.value)}
                            style={{
                                ...styles.input,
                                borderColor: formErrors.cpf ? 'red' : '#24304a',
                                marginBottom: formErrors.cpf ? '20px' : '0'
                            }}
                            maxLength={14}
                        />
                        {formErrors.cpf && (
                            <span style={{
                                color: 'red',
                                fontSize: '12px',
                                position: 'absolute' as 'absolute',
                                bottom: '-18px',
                                left: '0'
                            }}>
                                {formErrors.cpf}
                            </span>
                        )}
                    </div>
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
                        <button 
                            style={styles.button} 
                            onClick={() => api.activateEmployee(emp.id!)
                                .then(() => {
                                    onChange({ ...emp, situation: 'A' });
                                    toast.success('Funcionário ativado com sucesso!');
                                })
                                .catch((error) => toast.error('Erro ao ativar funcionário: ' + error.message))
                            }
                        >
                            Ativar
                        </button>
                        <button 
                            style={styles.outlineButton} 
                            onClick={() => api.deactivateEmployee(emp.id!)
                                .then(() => {
                                    onChange({ ...emp, situation: 'I' });
                                    toast.success('Funcionário desativado com sucesso!');
                                })
                                .catch((error) => toast.error('Erro ao desativar funcionário: ' + error.message))
                            }
                        >
                            Desativar
                        </button>
                    </>
                ) : (
                    <>
                        <button style={styles.successButton} onClick={save}>
                            Salvar
                        </button>
                        <button style={styles.dangerButton} onClick={cancelEdit}>
                            Cancelar
                        </button>
                    </>
                )}
            </td>
        </tr>
    );
}