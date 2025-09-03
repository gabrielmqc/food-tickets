import type { AxiosInstance } from "axios";
import type { EmployeeDTO, TicketDTO, UUID } from "../types";
import axios from "axios";

const API_BASE = "";

const axiosInstance: AxiosInstance = axios.create({
  baseURL: API_BASE,
  headers: {
    "Content-Type": "application/json",
  },
});


axiosInstance.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response) {
      const errorMessage = error.response.data?.message 
        || error.response.data 
        || `HTTP Error ${error.response.status}`;
      
      return Promise.reject(new Error(errorMessage));
    }
    
    if (error.request) {
      return Promise.reject(new Error("Network error - no response received"));
    }
    
    return Promise.reject(new Error(error.message || "Unknown error occurred"));
  }
);
export const api = {

  listEmployees: () =>
    axiosInstance.get<EmployeeDTO[]>("/api/employee").then((res) => res.data),

  createEmployee: (
    payload: Omit<EmployeeDTO, "id" | "alterationDate" | "ticketsIds">
  ) =>
    axiosInstance
      .post<EmployeeDTO>("/api/employee", payload)
      .then((res) => res.data),

  updateEmployee: (employeeId: UUID, payload: Partial<EmployeeDTO>) =>
    axiosInstance
      .put<EmployeeDTO>(`/api/employee/${employeeId}`, payload)
      .then((res) => res.data),

  activateEmployee: (employeeId: UUID) =>
    axiosInstance
      .patch<void>(`/api/employee/${employeeId}/activate`)
      .then((res) => res.data),

  deactivateEmployee: (employeeId: UUID) =>
    axiosInstance
      .patch<void>(`/api/employee/${employeeId}/deactivate`)
      .then((res) => res.data),

  listTickets: () =>
    axiosInstance.get<TicketDTO[]>("/api/ticket").then((res) => res.data),

  createTicket: (payload: Omit<TicketDTO, "id" | "alterationDate">) =>
    axiosInstance
      .post<TicketDTO>("/api/ticket", payload)
      .then((res) => res.data),

  updateTicket: (ticketId: UUID, payload: Partial<TicketDTO>) =>
    axiosInstance
      .put<TicketDTO>(`/api/ticket/${ticketId}`, payload)
      .then((res) => res.data),

  activateTicket: (ticketId: UUID) =>
    axiosInstance
      .patch<void>(`/api/ticket/${ticketId}/activate`)
      .then((res) => res.data),

  deactivateTicket: (ticketId: UUID) =>
    axiosInstance
      .patch<void>(`/api/ticket/${ticketId}/deactivate`)
      .then((res) => res.data),

  ticketsPdfByPeriod: (start: string, end: string) =>
    axiosInstance
      .get<Blob>(`/api/ticket/pdf/startDate/${start}/endDate/${end}`, {
        responseType: "blob",
      })
      .then((res) => res.data),

  ticketsPdfByEmployee: (employeeId: UUID) =>
    axiosInstance
      .get<Blob>(`/api/ticket/pdf/employee/${employeeId}`, {
        responseType: "blob",
      })
      .then((res) => res.data),

  ticketsPdfByEmployeeAndPeriod: (
    employeeId: UUID,
    start: string,
    end: string
  ) =>
    axiosInstance
      .get<Blob>(
        `/api/ticket/pdf/employee/${employeeId}/startDate/${start}/endDate/${end}`,
        {
          responseType: "blob",
        }
      )
      .then((res) => res.data),
};

export type Api = typeof api;

export function downloadBlob(blob: Blob, filename: string) {
  const url = URL.createObjectURL(blob);
  const a = document.createElement("a");
  a.href = url;
  a.download = filename;
  document.body.appendChild(a);
  a.click();
  a.remove();
  URL.revokeObjectURL(url);
}
