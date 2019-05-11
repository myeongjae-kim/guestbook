export default interface IError {
  status: number
  error: string
  message: string
  timestamp: string
}

export const alertError = async (e: Promise<IError>): Promise<any> => {
  const { status, error, message, timestamp } = await e;
  alert(`Error: ${error}
Status: ${status}
Message: ${message}
Timestamp: ${timestamp}`)
}