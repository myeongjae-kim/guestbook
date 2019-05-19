import { Record } from "immutable";
import IErrorFromGuestbookAPI from "main/api/IErrorFromGuestbookAPI";
import * as mentions from "main/api/mentions"
import IMentionResponse from "main/api/mentions/dto/IMentionResponse";
import { alertError } from "main/api/util";
import { ActionType, createAction, getType } from "typesafe-actions";

export type State = Record<{
  pending: boolean;
  rejected: boolean;
}>;

export const deleteMention = createAction("@mentionDeleteButton/DELETE_MENTION",
  action => (id: number) => action(mentions.del(id)));
const deleteMentionPending = createAction("@mentionDeleteButton/DELETE_MENTION_PENDING");
export const deleteMentionFulfilled = createAction("@mentionDeleteButton/DELETE_MENTION_FULFILLED",
  action => (mentionList: IMentionResponse[]) => action(mentionList));
const deleteMentionRejected = createAction("@mentionDeleteButton/DELETE_MENTION_REJECTED",
  action => (error: IErrorFromGuestbookAPI | Error) => action(error))

export type Action = ActionType<
  typeof deleteMention |
  typeof deleteMentionPending |
  typeof deleteMentionFulfilled |
  typeof deleteMentionRejected
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
    case getType(deleteMentionPending):
      return state.set("pending", true);
    case getType(deleteMentionFulfilled):
      return state.merge({
        pending: false
      });
    case getType(deleteMentionRejected):
      alertError(action.payload);
      return state.merge({
        pending: false,
        rejected: true
      });

    default:
      return state.merge({});
  }
}