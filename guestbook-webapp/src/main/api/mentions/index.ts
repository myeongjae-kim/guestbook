import { format } from 'date-fns'
import * as ko from 'date-fns/locale/ko'
import { MENTION_API_DOMAIN } from "../common";
import { returnBodyAs, returnBodyAsNumber } from "../util";
import IMentionRequest from "./dto/IMentionRequest";
import IMentionResponse from "./dto/IMentionResponse";

export const get = (id: number): Promise<IMentionResponse> =>
  fetch(`${MENTION_API_DOMAIN}/${id}`)
    .then(res => returnBodyAs<IMentionResponse>(res))
    .then(formatCreatedAt)

export const getList = (): Promise<IMentionResponse[]> =>
  fetch(MENTION_API_DOMAIN)
    .then(res => returnBodyAs<IMentionResponse[]>(res))
    .then(mentions => mentions.map(formatCreatedAt))

export const post = (requestBody: IMentionRequest): Promise<number> =>
  fetch(MENTION_API_DOMAIN, {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(requestBody)
  })
    .then(returnBodyAsNumber)

export const put = (id: number, requestBody: IMentionRequest): Promise<void> =>
  fetch(`${MENTION_API_DOMAIN}/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(requestBody)
  })
    .then(_ => { return })

export const del = (id: number): Promise<void> =>
  fetch(`${MENTION_API_DOMAIN}/${id}`, { method: 'DELETE' })
    .then(_ => { return })

const formatCreatedAt = (mention: IMentionResponse) => ({
  ...mention,
  createdAt: toDateString(mention.createdAt)
})

const toDateString = (createdAt: string) => format(createdAt, "YYYY. MM. DD. (ddd)", { locale: ko })