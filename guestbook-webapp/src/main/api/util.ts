import IErrorFromGuestbookAPI from "./IErrorFromGuestbookAPI";

export const returnBodyAs = async <T>(res: Response) => res.json() as Promise<T>
export const returnBodyAsNumber = async (res: Response) => parseInt(await res.text(), 10)

export const alertError = (e: IErrorFromGuestbookAPI | Error): void => {
  if (e instanceof Error) {
    alert(e)
  } else {
    alert(errorToString(e))
  }
}

const errorToString = ({ status, error, message, timestamp }: IErrorFromGuestbookAPI) => `Error: ${error}
Status: ${status}
Message: ${message}
Timestamp: ${timestamp}`