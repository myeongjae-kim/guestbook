import { format } from "date-fns";
import * as ko from 'date-fns/locale/ko';
import ApiError from "./ApiError";

export const toDateString = (createdAt: string) => format(createdAt, "YYYY. MM. DD. (ddd)", { locale: ko })

export const throwWhenStatusIsNotOk = async (res: Response) => {
  if (res.ok) {
    return res
  }

  const e = await res.json()
  throw new ApiError(e);
}

export const returnBodyAs = async <T>(res: Response) => res.json() as Promise<T>
export const returnBodyAsNumber = async (res: Response) => parseInt(await res.text(), 10)