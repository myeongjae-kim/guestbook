import { Record } from "immutable";
import IApiError from "main/api/ApiError";
import * as comments from "main/api/comments"
import ICommentResponse from "main/api/comments/dto/ICommentResponse";
import { call, put, takeEvery } from "redux-saga/effects";
import { ActionType, createAction, getType } from "typesafe-actions";

export type State = Record<{
  comments: ICommentResponse[];
  pending: boolean;
  rejected: boolean;
}>

export const getCommentListOf = createAction("@commentTable/GET_COMMENT_LIST_OF",
  action => (mentionId: number) => action({ mentionId }));
const getCommentListOfPending = createAction("@commentTable/GET_COMMENT_LIST_OF_PENDING");
const getCommentListOfFulfilled = createAction("@commentTable/GET_COMMENT_LIST_OF_FULFILLED",
  action => (commentList: ICommentResponse[]) => action(commentList));
const getCommentListOfRejected = createAction("@commentTable/GET_COMMENT_LIST_OF_REJECTED",
  action => (error: IApiError | Error) => action(error))

export type Action = ActionType<
  typeof getCommentListOf |
  typeof getCommentListOfPending |
  typeof getCommentListOfFulfilled |
  typeof getCommentListOfRejected
>

const createInitialState = Record({
  comments: [] as ICommentResponse[],
  pending: false,
  rejected: false
});

export const reducer = (
  state: State = createInitialState(),
  action: Action
): State => {
  switch (action.type) {
    case getType(getCommentListOfPending):
      return state.set("pending", true);
    case getType(getCommentListOfFulfilled):
      return state.merge({
        comments: action.payload,
        pending: false
      });
    case getType(getCommentListOfRejected):
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
  yield takeEvery(getType(getCommentListOf), sagaGetCommentListOf);
}

function* sagaGetCommentListOf(action: ActionType<typeof getCommentListOf>) {
  yield put(getCommentListOfPending())
  try {
    const commentList: ICommentResponse[] = yield call(comments.getListOf, action.payload.mentionId);
    yield put(getCommentListOfFulfilled(commentList));
  } catch (e) {
    yield put(getCommentListOfRejected(e));
  }
}