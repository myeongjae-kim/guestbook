import { Record } from "immutable";
import IError from "main/api/IError";
import * as mentions from "main/api/mentions"
import IMentionResponse from "main/api/mentions/dto/IMentionResponse";
import { ActionType, createAction, getType } from "typesafe-actions";

export type State = Record<{
  mentions: IMentionResponse[];
  pending: boolean;
  rejected: boolean;
}>;

export const getMentionList = createAction("@mentionTable/GET_MENTION_LIST",
  action => () => action(mentions.getList()));
const getMentionListPending = createAction("@mentionTable/GET_MENTION_LIST_PENDING");
const getMentionListFulfilled = createAction("@mentionTable/GET_MENTION_LIST_FULFILLED",
  action => (mentionList: IMentionResponse[]) => action(mentionList));
const getMentionListRejected = createAction("@mentionTable/GET_MENTION_LIST_REJECTED",
  action => (error: IError) => action(error))

export type Action = ActionType<
  typeof getMentionList |
  typeof getMentionListPending |
  typeof getMentionListFulfilled |
  typeof getMentionListRejected
>

const createInitialState = Record({
  mentions: [] as IMentionResponse[],
  pending: false,
  rejected: false
});

export const reducer = (
  state: State = createInitialState(),
  action: Action
): State => {
  switch (action.type) {
    case getType(getMentionListPending):
      return state.set("pending", true);
    case getType(getMentionListFulfilled):
      return state.merge({
        mentions: action.payload,
        pending: false
      });
    case getType(getMentionListRejected):
      alert(action.payload);
      return state.merge({
        pending: false,
        rejected: true
      });

    default:
      return state.merge({});
  }
}