import { Record } from "immutable";
import ApiError from "main/api/ApiError";
import * as comments from "main/api/comments";
import { call, put, takeEvery } from "redux-saga/effects";
import { ActionType, createAction, getType } from "typesafe-actions";

export type State = Record<{
  pending: boolean;
  rejected: boolean;
}>

export const deleteComment = createAction("@commentDeleteButton/DELETE_COMMENT",
  action => (id: number) => action({ id }));
const deleteCommentPending = createAction("@commentDeleteButton/DELETE_COMMENT_PENDING");
const deleteCommentFulfilled = createAction("@commentDeleteButton/DELETE_COMMENT_FULFILLED");
const deleteCommentRejected = createAction("@commentDeleteButton/DELETE_COMMENT_REJECTED",
  action => (error: ApiError | Error) => action(error))

export type Action = ActionType<
  typeof deleteComment |
  typeof deleteCommentPending |
  typeof deleteCommentFulfilled |
  typeof deleteCommentRejected
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
    case getType(deleteCommentPending):
      return state.set("pending", true);
    case getType(deleteCommentFulfilled):
      return state.merge({
        pending: false
      });
    case getType(deleteCommentRejected):
      alert(action.payload.toString());
      return state.merge({
        pending: false,
        rejected: true
      });

    default:
      return state.merge({});
  }
}

export function* saga() {
  yield takeEvery(getType(deleteComment), sagaDeleteComment);
}

function* sagaDeleteComment(action: ActionType<typeof deleteComment>) {
  yield put(deleteCommentPending())
  try {
    const { id } = action.payload
    yield call(comments.del, id);
    yield put(deleteCommentFulfilled());
  } catch (e) {
    yield put(deleteCommentRejected(e));
  }
}