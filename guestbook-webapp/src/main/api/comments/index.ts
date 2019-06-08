import axios, { AxiosError, AxiosResponse } from "axios"; import ApiError from "../ApiError";
import { COMMENT_API_DOMAIN } from "../common";
import { toDateString } from "../util";
import ICommentPostRequest from "./dto/ICommentPostRequest";
import ICommentPutRequest from "./dto/ICommentPutRequest";
import ICommentResponse from "./dto/ICommentResponse";

export const get = (id: string): Promise<ICommentResponse> => new Promise((resolve, reject) =>
  axios
    .get(`${COMMENT_API_DOMAIN}/${id}`)
    .then((resp: AxiosResponse<ICommentResponse>) => formatCreatedAt(resp.data))
    .then((commentResponse: ICommentResponse) => resolve(commentResponse))
    .catch((err: AxiosError) => reject(new ApiError(err))))

export const getListOf = (mentionId: number): Promise<ICommentResponse[]> => new Promise((resolve, reject) =>
  axios
    .get(`${COMMENT_API_DOMAIN}/mention/${mentionId}`)
    .then((resp: AxiosResponse<ICommentResponse[]>) => resp.data)
    .then(comments => resolve(comments.map(formatCreatedAt)))
    .catch((err: AxiosError) => reject(new ApiError(err))))

export const post = (requestBody: ICommentPostRequest): Promise<number> => new Promise((resolve, reject) =>
  axios
    .post(COMMENT_API_DOMAIN, requestBody, {
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    })
    .then((resp: AxiosResponse<string>) => resolve(parseInt(resp.data, 10)))
    .catch((err: AxiosError) => reject(new ApiError(err))))

export const put = (id: string, requestBody: ICommentPutRequest): Promise<void> => new Promise((resolve, reject) =>
  axios
    .put(`${COMMENT_API_DOMAIN}/${id}`, requestBody, {
      headers: {
        'Content-Type': 'application/json'
      }
    })
    .then((_: AxiosResponse<void>) => resolve())
    .catch((err: AxiosError) => reject(new ApiError(err))))

export const del = (id: string): Promise<void> => new Promise((resolve, reject) =>
  axios
    .delete(`${COMMENT_API_DOMAIN}/${id}`)
    .then((_: AxiosResponse<void>) => resolve())
    .catch((err: AxiosError) => reject(new ApiError(err))))

const formatCreatedAt = (comment: ICommentResponse) => ({
  ...comment,
  createdAt: toDateString(comment.createdAt)
})