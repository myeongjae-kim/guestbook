import { Record } from "immutable";
import IErrorFromGuestbookAPI from "main/api/IErrorFromGuestbookAPI";
import * as mentions from "main/api/mentions"
import IMentionRequest from "main/api/mentions/dto/IMentionRequest";
import { alertError } from "main/api/util";
import { ActionType, createAction, getType } from "typesafe-actions";

export type State = Record<{
  pending: boolean;
  rejected: boolean;
}>

export const putMention = createAction("@mentionEditForm/PUT_MENTION",
  action => (id: number, mentionRequest: IMentionRequest) => action(mentions.put(id, mentionRequest)));
const putMentionPending = createAction("@mentionEditForm/PUT_MENTION_PENDING");
export const putMentionFulfilled = createAction("@mentionEditForm/PUT_MENTION_FULFILLED");
const putMentionRejected = createAction("@mentionEditForm/PUT_MENTION_REJECTED",
  action => (error: IErrorFromGuestbookAPI | Error) => action(error))

export type Action = ActionType<
  typeof putMention |
  typeof putMentionPending |
  typeof putMentionFulfilled |
  typeof putMentionRejected
>

const createInitialState = Record({
  pending: false,
  rejected: false
});

export const reducer = (
  state: State = createInitialState(),
  action: Action
): State => {
  switch (action.type) {
    case getType(putMentionPending):
      return state.set("pending", true);
    case getType(putMentionFulfilled):
      alert("글을 수정했습니다.")
      return createInitialState();
    case getType(putMentionRejected):
      alertError(action.payload);
      return state.merge({
        pending: false,
        rejected: true
      });

    default:
      return state.merge({});
  }
}