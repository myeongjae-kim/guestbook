import { Record } from "immutable";
import ApiError from "main/api/ApiError";
import * as comments from "main/api/comments";
import ICommentPostRequest from "main/api/comments/dto/ICommentPostRequest";
import { call, put, takeEvery } from "redux-saga/effects";
import { ActionType, createAction, getType } from "typesafe-actions";

export type State = Record<{
  pending: boolean;
  rejected: boolean;
}>

export const postComment = createAction("@commentAddForm/POST_COMMENT", action =>
  (commentPostRequest: ICommentPostRequest, refreshComments: () => void) =>
    action({ commentPostRequest, refreshComments }));
const postCommentPending = createAction("@commentAddForm/POST_COMMENT_PENDING");
const postCommentFulfilled = createAction("@commentAddForm/POST_COMMENT_FULFILLED");
const postCommentRejected = createAction("@commentAddForm/POST_COMMENT_REJECTED",
  action => (error: ApiError | Error) => action(error))

export const changeName = createAction("@commentAddForm/CHANGE_NAME",
  action => (name: string) => action(name))
export const changeContent = createAction("@commentAddForm/CHANGE_CONTENT",
  action => (content: string) => action(content))

export type Action = ActionType<
  typeof postComment |
  typeof postCommentPending |
  typeof postCommentFulfilled |
  typeof postCommentRejected
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
    case getType(postCommentPending):
      return state.set("pending", true);
    case getType(postCommentFulfilled):
      return createInitialState();
    case getType(postCommentRejected):
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
  yield takeEvery(getType(postComment), sagaPostComment);
}

function* sagaPostComment(action: ActionType<typeof postComment>) {
  yield put(postCommentPending())
  try {
    const { commentPostRequest, refreshComments } = action.payload
    yield call(comments.post, commentPostRequest);
    alert("댓글을 등록했습니다.")
    yield put(postCommentFulfilled());

    refreshComments()
  } catch (e) {
    yield put(postCommentRejected(e));
  }
}