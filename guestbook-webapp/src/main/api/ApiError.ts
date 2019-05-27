import { AxiosError } from "axios";

interface IApiError {
  status: number
  error: string
  message: string
  timestamp: string
}

export default class ApiError implements IApiError {
  public status = -1;
  public error = "Unknown";
  public message = "에러 정보가 없습니다";
  public timestamp = (new Date()).toISOString();

  constructor(e: AxiosError) {
    if (e.response) {
      this.status = e.response.data.status;
      this.error = e.response.data.error;
      this.message = e.response.data.message;
      this.timestamp = e.response.data.timestamp;
    }
  }

  public toString = () => `Error: ${this.error}
Status: ${this.status}
Message: ${this.message}
Timestamp: ${this.timestamp}`
}