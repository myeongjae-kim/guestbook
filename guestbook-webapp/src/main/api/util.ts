export const throwErrorIfStatusIsNotOk = async (res: Response): Promise<Response> => {
  if (res.ok) {
    return res
  }

  throw res.json()
}

export const returnBodyAsJSON = async (res: Response) => res.json()
export const returnBodyAsNumber = async (res: Response) => parseInt(await res.text(), 10)