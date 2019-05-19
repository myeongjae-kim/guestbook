import { Record } from "immutable";
import IErrorFromGuestbookAPI from "main/api/IErrorFromGuestbookAPI";
import * as mentions from "main/api/mentions"
import IMentionRequest from "main/api/mentions/dto/IMentionRequest";
import { alertError } from "main/api/util";
import { ActionType, createAction, getType } from "typesafe-actions";

export type State = Record<{
  mentionRequest: IMentionRequest
  pending: boolean;
  rejected: boolean;
}>

export const postMention = createAction("@mentionAddForm/POST_MENTION",
  action => (mentionRequest: IMentionRequest) => action(mentions.post(mentionRequest)));
const postMentionPending = createAction("@mentionAddForm/POST_MENTION_PENDING");
export const postMentionFulfilled = createAction("@mentionAddForm/POST_MENTION_FULFILLED");
const postMentionRejected = createAction("@mentionAddForm/POST_MENTION_REJECTED",
  action => (error: IErrorFromGuestbookAPI | Error) => action(error))

export const changeName = createAction("@mentionAddForm/CHANGE_NAME",
  action => (name: string) => action(name))
export const changeContent = createAction("@mentionAddForm/CHANGE_CONTENT",
  action => (content: string) => action(content))

export type Action = ActionType<
  typeof postMention |
  typeof postMentionPending |
  typeof postMentionFulfilled |
  typeof postMentionRejected |
  typeof changeName |
  typeof changeContent
>

const createInitialState = Record({
  mentionRequest: {
    name: "",
    content: ""
  } as IMentionRequest,
  pending: false,
  rejected: false
});

export const reducer = (
  state: State = createInitialState(),
  action: Action
): State => {
  switch (action.type) {
    case getType(postMentionPending):
      return state.set("pending", true);
    case getType(postMentionFulfilled):
      alert("글을 등록했습니다.")
      return createInitialState();
    case getType(postMentionRejected):
      alertError(action.payload);
      return state.merge({
        pending: false,
        rejected: true
      });

    case getType(changeName):
      return state.set("mentionRequest", {
        ...state.get("mentionRequest"),
        name: action.payload
      });

    case getType(changeContent):
      return state.set("mentionRequest", {
        ...state.get("mentionRequest"),
        content: action.payload
      });

    default:
      return state.merge({});
  }
}