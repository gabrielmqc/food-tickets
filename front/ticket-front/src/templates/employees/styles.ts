export const styles = {
  container: {
    display: "grid",
    gap: "20px",
    padding: "20px",
    maxWidth: "1200px",
    margin: "0 auto",
  } as React.CSSProperties,

  panel: {
    backgroundColor: "#111522",
    padding: "20px",
    borderRadius: "8px",
    boxShadow: "0 2px 4px rgba(0,0,0,0.1)",
    border: "1px solid #24304a",
  } as React.CSSProperties,

  title: {
    marginTop: 0,
    marginBottom: "20px",
    color: "#8b97b1",
    fontSize: "24px",
    fontWeight: "bold",
  } as React.CSSProperties,
  formField: {
    position: "relative" as "relative",
    minHeight: "60px", 
  } as React.CSSProperties,

  errorMessage: {
    color: "red",
    fontSize: "12px",
    position: "absolute" as "absolute",
    bottom: "-18px",
    left: "0",
    right: "0",
  } as React.CSSProperties,
 form: {
    display: "grid",
    gridTemplateColumns: "repeat(3, minmax(0, 1fr))",
    gap: "15px",
    alignItems: "end",
    alignContent: "start" // Adicione esta linha
} as React.CSSProperties,

  input: {
    padding: "10px",
    border: "1px solid #24304a",
    borderRadius: "4px",
    fontSize: "14px",
    width: "100%",
    boxSizing: "border-box" as "border-box",
  } as React.CSSProperties,

  select: {
    padding: "10px",
    border: "1px solid #24304a",
    borderRadius: "4px",
    fontSize: "14px",
    width: "100%",
    boxSizing: "border-box" as "border-box",
  } as React.CSSProperties,

  submitButton: {
    padding: "10px 15px",
    backgroundColor: "#007bff",
    color: "white",
    border: "none",
    borderRadius: "4px",
    fontSize: "14px",
    cursor: "pointer",
    fontWeight: "bold",
  } as React.CSSProperties,

  error: {
    color: "salmon",
    margin: "10px 0",
    padding: "10px",
    backgroundColor: "#ffe6e6",
    borderRadius: "4px",
    border: "1px solid #ffcccc",
  } as React.CSSProperties,

  table: {
    width: "100%",
    borderCollapse: "collapse" as "collapse",
    marginTop: "15px",
  } as React.CSSProperties,

  th: {
    padding: "12px",
    textAlign: "center" as "center",
    borderBottom: "1px solid #ddd",
    backgroundColor: "transparent",
    fontWeight: "bold",
    color: "#8b97b1",
  } as React.CSSProperties,

  actionsCell: {
    display: "flex",
    gap: "8px",
    padding: "12px",
  } as React.CSSProperties,

  button: {
    padding: "8px 12px",
    backgroundColor: "#007bff",
    color: "white",
    border: "none",
    borderRadius: "4px",
    fontSize: "12px",
    cursor: "pointer",
    minWidth: "80px",
  } as React.CSSProperties,

  outlineButton: {
    padding: "8px 12px",
    backgroundColor: "transparent",
    color: "#007bff",
    border: "1px solid #007bff",
    borderRadius: "4px",
    fontSize: "12px",
    cursor: "pointer",
    minWidth: "80px",
  } as React.CSSProperties,

  successButton: {
    padding: "8px 12px",
    backgroundColor: "#28a745",
    color: "white",
    border: "none",
    borderRadius: "4px",
    fontSize: "12px",
    cursor: "pointer",
    minWidth: "80px",
  } as React.CSSProperties,

  dangerButton: {
    padding: "8px 12px",
    backgroundColor: "#dc3545",
    color: "white",
    border: "none",
    borderRadius: "4px",
    fontSize: "12px",
    cursor: "pointer",
    minWidth: "80px",
  } as React.CSSProperties,
};
