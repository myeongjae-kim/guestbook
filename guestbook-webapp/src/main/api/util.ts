export const throwErrorWhenStatusIsNotOk = async (res: Response): Promise<Response> => {
  if (res.ok) {
    return res
  }

  throw res.json()
}

export const returnBodyAs = async <T>(res: Response) => res.json() as Promise<T>
export const returnBodyAsNumber = async (res: Response) => parseInt(await res.text(), 10)