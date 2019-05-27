interface IApiError {
  status: number
  error: string
  message: string
  timestamp: string
}

export default class ApiError implements IApiError {
  public status = 0;
  public error = "";
  public message = "";
  public timestamp = "";

  constructor(e: IApiError) {
    this.status = e.status;
    this.error = e.error;
    this.message = e.message;
    this.timestamp = e.timestamp;
  }

  public toString = () => `Error: ${this.error}
Status: ${this.status}
Message: ${this.message}
Timestamp: ${this.timestamp}`
}