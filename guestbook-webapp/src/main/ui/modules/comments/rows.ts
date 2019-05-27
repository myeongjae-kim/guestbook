import { Record } from "immutable";
import IApiError from "main/api/ApiError";
import * as comments from "main/api/comments"
import ICommentResponse from "main/api/comments/dto/ICommentResponse";
import { call, put, takeEvery } from "redux-saga/effects";
import { ActionType, createAction, getType } from "typesafe-actions";

export type State = Record<{
  pending: boolean;
  rejected: boolean;
}>

export const getAndSetCommentList = createAction("@commentTable/GET_AND_SET_COMMENT_LIST", action =>
  (mentionId: number, setComments: (comments: ICommentResponse[]) => void) =>
    action({ mentionId, setComments }))
const getAndSetCommentListPending = createAction("@commentTable/GET_AND_SET_COMMENT_LIST_PENDING");
const getAndSetCommentListFulfilled = createAction("@commentTable/GET_AND_SET_COMMENT_LIST_FULFILLED");
const getAndSetCommentListRejected = createAction("@commentTable/GET_AND_SET_COMMENT_LIST_REJECTED",
  action => (error: IApiError | Error) => action(error))

export type Action = ActionType<
  typeof getAndSetCommentList |
  typeof getAndSetCommentListPending |
  typeof getAndSetCommentListFulfilled |
  typeof getAndSetCommentListRejected
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
    case getType(getAndSetCommentListPending):
      return state.set("pending", true);
    case getType(getAndSetCommentListFulfilled):
      return state.merge({
        pending: false
      });
    case getType(getAndSetCommentListRejected):
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
  yield takeEvery(getType(getAndSetCommentList), sagaGetCommentList);
}

function* sagaGetCommentList(action: ActionType<typeof getAndSetCommentList>) {
  yield put(getAndSetCommentListPending())
  try {
    const { mentionId, setComments } = action.payload

    const commentList: ICommentResponse[] = yield call(comments.getListOf, mentionId);
    yield put(getAndSetCommentListFulfilled());
    setComments(commentList);

  } catch (e) {
    yield put(getAndSetCommentListRejected(e));
  }
}