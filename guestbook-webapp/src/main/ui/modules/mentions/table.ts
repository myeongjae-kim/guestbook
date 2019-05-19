import { Record } from "immutable";
import IErrorFromGuestbookAPI from "main/api/IErrorFromGuestbookAPI";
import * as mentions from "main/api/mentions"
import IMentionResponse from "main/api/mentions/dto/IMentionResponse";
import { alertError } from "main/api/util";
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
  action => (error: IErrorFromGuestbookAPI | Error) => action(error))

export type Action = ActionType<
  typeof getMentionList |
  typeof getMentionListPending |
  typeof getMentionListFulfilled |
  typeof getMentionListRejected
>

const createInitialState = Record({
  mentions: [{
    id: -1,
    name: "John Doe",
    content: "Lorem Ipsum",
    createdAt: (new Date()).toDateString()
  }] as IMentionResponse[],
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
      alertError(action.payload);
      return state.merge({
        pending: false,
        rejected: true
      });

    default:
      return state.merge({});
  }
}