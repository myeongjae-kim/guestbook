import { COMMENT_API_DOMAIN } from "../common";
import { returnBodyAs, throwWhenStatusIsNotOk, toDateString } from "../util";
import ICommentPostRequest from "./dto/ICommentPostRequest";
import ICommentPutRequest from "./dto/ICommentPutRequest";
import ICommentResponse from "./dto/ICommentResponse";

export const get = (id: string): Promise<ICommentResponse> =>
  fetch(`${COMMENT_API_DOMAIN}/${id}`)
    .then(throwWhenStatusIsNotOk)
    .then(res => returnBodyAs<ICommentResponse>(res))
    .then(formatCreatedAt)

export const getList = (): Promise<ICommentResponse[]> =>
  fetch(COMMENT_API_DOMAIN)
    .then(throwWhenStatusIsNotOk)
    .then(res => returnBodyAs<ICommentResponse[]>(res))
    .then(mentions => mentions.map(formatCreatedAt))

export const post = (postRequestBody: ICommentPostRequest): Promise<string> =>
  fetch(COMMENT_API_DOMAIN, {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(postRequestBody)
  })
    .then(throwWhenStatusIsNotOk)
    .then(res => returnBodyAs<string>(res))

export const put = (id: string, putRequestBody: ICommentPutRequest): Promise<void> =>
  fetch(`${COMMENT_API_DOMAIN}/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(putRequestBody)
  })
    .then(throwWhenStatusIsNotOk)
    .then(_ => { return })

export const del = (id: string): Promise<void> =>
  fetch(`${COMMENT_API_DOMAIN}/${id}`, { method: 'DELETE' })
    .then(throwWhenStatusIsNotOk)
    .then(_ => { return })

const formatCreatedAt = (mention: ICommentResponse) => ({
  ...mention,
  createdAt: toDateString(mention.createdAt)
})