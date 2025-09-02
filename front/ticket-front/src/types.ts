export type UUID = string

export type Situation = 'A' | 'I'

export interface EmployeeDTO {
  id: UUID | null
  name: string
  cpf: string
  situation: Situation
  alterationDate: string | null
  ticketsIds: UUID[]
}

export interface TicketDTO {
  id: UUID | null
  employeeId: UUID
  quantity: number
  situation: Situation
  alterationDate: string | null
}

