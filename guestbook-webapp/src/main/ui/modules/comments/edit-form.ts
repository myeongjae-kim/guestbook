import { Record } from "immutable";
import ApiError from "main/api/ApiError";
import * as comments from "main/api/comments";
import ICommentPutRequest from "main/api/comments/dto/ICommentPutRequest";
import { call, put, takeEvery } from "redux-saga/effects";
import { ActionType, createAction, getType } from "typesafe-actions";

export type State = Record<{
  pending: boolean;
  rejected: boolean;
}>

export const putComment = createAction("@commentEditForm/PUT_COMMENT",
  action => (id: number, commentPutRequest: ICommentPutRequest) => action({ id, commentPutRequest }));
const putCommentPending = createAction("@commentEditForm/PUT_COMMENT_PENDING");
const putCommentFulfilled = createAction("@commentEditForm/PUT_COMMENT_FULFILLED");
const putCommentRejected = createAction("@commentEditForm/PUT_COMMENT_REJECTED",
  action => (error: ApiError | Error) => action(error))

export type Action = ActionType<
  typeof putComment |
  typeof putCommentPending |
  typeof putCommentFulfilled |
  typeof putCommentRejected
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
    case getType(putCommentPending):
      return state.set("pending", true);
    case getType(putCommentFulfilled):
      return createInitialState();
    case getType(putCommentRejected):
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
  yield takeEvery(getType(putComment), sagaPutComment);
}

function* sagaPutComment(action: ActionType<typeof putComment>) {
  yield put(putCommentPending())
  try {
    const { id, commentPutRequest } = action.payload
    yield call(comments.put, id, commentPutRequest);
    alert("댓글을 수정했습니다.")
    yield put(putCommentFulfilled());
  } catch (e) {
    yield put(putCommentRejected(e));
  }
}