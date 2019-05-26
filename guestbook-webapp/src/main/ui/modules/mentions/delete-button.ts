import { Record } from "immutable";
import IErrorFromGuestbookAPI from "main/api/IErrorFromGuestbookAPI";
import * as mentions from "main/api/mentions"
import { alertError } from "main/api/util";
import { call, put, takeEvery } from "redux-saga/effects";
import { ActionType, createAction, getType } from "typesafe-actions";
import { getMentionList } from "./table";

export type State = Record<{
  pending: boolean;
  rejected: boolean;
}>

export const deleteMention = createAction("@mentionDeleteButton/DELETE_MENTION",
  action => (id: number) => action({ id }));
const deleteMentionPending = createAction("@mentionDeleteButton/DELETE_MENTION_PENDING");
const deleteMentionFulfilled = createAction("@mentionDeleteButton/DELETE_MENTION_FULFILLED");
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

export function* saga() {
  yield takeEvery(getType(deleteMention), sagaDeleteMention);
}

function* sagaDeleteMention(action: ActionType<typeof deleteMention>) {
  yield put(deleteMentionPending())
  try {
    const { id } = action.payload
    yield call(mentions.del, id);
    yield put(deleteMentionFulfilled());
    yield put(getMentionList());
  } catch (e) {
    yield put(deleteMentionRejected(e));
  }
}