import { alertError } from "../IError";
import { returnBodyAsJSON, returnBodyAsNumber, throwErrorIfStatusIsNotOk } from "../util";
import IMentionRequest from "./dto/IMentionRequest";
import IMentionResponse from "./dto/IMentionResponse";

export const get = (id: number): Promise<IMentionResponse | undefined> =>
  fetch(`http://localhost:8080/${id}`)
    .then(throwErrorIfStatusIsNotOk)
    .then(returnBodyAsJSON)
    .catch(alertError)

export const getList = (): Promise<IMentionResponse[] | undefined> =>
  fetch(`http://localhost:8080`)
    .then(throwErrorIfStatusIsNotOk)
    .then(returnBodyAsJSON)
    .catch(alertError)

export const post = (requestBody: IMentionRequest): Promise<number | undefined> =>
  fetch(`http://localhost:8080`, {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(requestBody)
  })
    .then(throwErrorIfStatusIsNotOk)
    .then(returnBodyAsNumber)
    .catch(alertError)

export const put = (id: number, requestBody: IMentionRequest): Promise<void> =>
  fetch(`http://localhost:8080/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(requestBody)
  })
    .then(throwErrorIfStatusIsNotOk)
    .catch(alertError)

export const del = (id: number): Promise<void> =>
  fetch(`http://localhost:8080/${id}`, { method: 'DELETE' })
    .then(throwErrorIfStatusIsNotOk)
    .catch(alertError)
