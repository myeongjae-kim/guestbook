export default interface IError {
  status: number
  error: string
  message: string
  timestamp: string
}

export const alertError = ({ status, error, message, timestamp }: IError): void => {
  alert(`Error: ${error}
Status: ${status}
Message: ${message}
Timestamp: ${timestamp}`)
}