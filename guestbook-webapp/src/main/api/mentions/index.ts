import axios, { AxiosError, AxiosResponse } from "axios";
import ApiError from "../ApiError";
import { MENTION_API_DOMAIN } from "../common";
import { toDateString } from "../util";
import IMentionRequest from "./dto/IMentionRequest";
import IMentionResponse from "./dto/IMentionResponse";

export const get = (id: number): Promise<IMentionResponse> => new Promise((resolve, reject) =>
  axios
    .get(`${MENTION_API_DOMAIN}/${id}`)
    .then((resp: AxiosResponse<IMentionResponse>) => formatCreatedAt(resp.data))
    .then((mentionResponse: IMentionResponse) => resolve(mentionResponse))
    .catch((err: AxiosError) => reject(new ApiError(err))))

export const getList = (): Promise<IMentionResponse[]> => new Promise((resolve, reject) =>
  axios
    .get(MENTION_API_DOMAIN)
    .then((resp: AxiosResponse<IMentionResponse[]>) => resp.data)
    .then(mentions => resolve(mentions.map(formatCreatedAt)))
    .catch((err: AxiosError) => reject(new ApiError(err))))

export const post = (requestBody: IMentionRequest): Promise<number> => new Promise((resolve, reject) =>
  axios
    .post(MENTION_API_DOMAIN, requestBody, {
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    })
    .then((resp: AxiosResponse<string>) => resolve(parseInt(resp.data, 10)))
    .catch((err: AxiosError) => reject(new ApiError(err))))

export const put = (id: number, requestBody: IMentionRequest): Promise<void> => new Promise((resolve, reject) =>
  axios
    .put(`${MENTION_API_DOMAIN}/${id}`, requestBody, {
      headers: {
        'Content-Type': 'application/json'
      }
    })
    .then((_: AxiosResponse<void>) => resolve())
    .catch((err: AxiosError) => reject(new ApiError(err))))

export const del = (id: number): Promise<void> => new Promise((resolve, reject) =>
  axios
    .delete(`${MENTION_API_DOMAIN}/${id}`)
    .then((_: AxiosResponse<void>) => resolve())
    .catch((err: AxiosError) => reject(new ApiError(err))))

const formatCreatedAt = (mention: IMentionResponse) => ({
  ...mention,
  createdAt: toDateString(mention.createdAt)
})