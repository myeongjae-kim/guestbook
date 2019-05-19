import { MENTION_API_DOMAIN } from "../common";
import { returnBodyAs, returnBodyAsNumber, throwErrorWhenStatusIsNotOk } from "../util";
import IMentionRequest from "./dto/IMentionRequest";
import IMentionResponse from "./dto/IMentionResponse";

export const get = (id: number): Promise<IMentionResponse> =>
  fetch(`${MENTION_API_DOMAIN}/${id}`)
    .then(throwErrorWhenStatusIsNotOk)
    .then(res => returnBodyAs<IMentionResponse>(res))

export const getList = (): Promise<IMentionResponse[]> =>
  fetch(MENTION_API_DOMAIN)
    .then(throwErrorWhenStatusIsNotOk)
    .then(res => returnBodyAs<IMentionResponse[]>(res))

export const post = (requestBody: IMentionRequest): Promise<number> =>
  fetch(MENTION_API_DOMAIN, {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(requestBody)
  })
    .then(throwErrorWhenStatusIsNotOk)
    .then(returnBodyAsNumber)

export const put = (id: number, requestBody: IMentionRequest): Promise<void> =>
  fetch(`${MENTION_API_DOMAIN}/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(requestBody)
  })
    .then(throwErrorWhenStatusIsNotOk)
    .then(_ => { return })

export const del = (id: number): Promise<void> =>
  fetch(`${MENTION_API_DOMAIN}/${id}`, { method: 'DELETE' })
    .then(throwErrorWhenStatusIsNotOk)
    .then(_ => { return })